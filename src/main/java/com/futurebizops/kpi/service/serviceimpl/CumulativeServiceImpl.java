package com.futurebizops.kpi.service.serviceimpl;

import com.futurebizops.kpi.constants.KPIConstants;
import com.futurebizops.kpi.exception.KPIException;
import com.futurebizops.kpi.repository.ReportEmployeeKppMasterRepo;
import com.futurebizops.kpi.request.CumulativeUpdateRequest;
import com.futurebizops.kpi.response.CummalitiveEmployeeResponse;
import com.futurebizops.kpi.response.EmployeeKppStatusResponse;
import com.futurebizops.kpi.response.KPIResponse;
import com.futurebizops.kpi.response.cumulative.CumulativeHoDResponse;
import com.futurebizops.kpi.response.cumulative.HODCumulativeData;
import com.futurebizops.kpi.response.cumulative.HoDCumulativeResponse;
import com.futurebizops.kpi.response.cumulative.TotalCumulativeHoD;
import com.futurebizops.kpi.response.dropdown.KppFinancialYearDDResponse;
import com.futurebizops.kpi.service.CumulativeService;
import com.futurebizops.kpi.utils.DateTimeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.DecimalFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CumulativeServiceImpl implements CumulativeService {

    @Autowired
    ReportEmployeeKppMasterRepo reportEmployeeKppMasterRepo;

   //shpw only 2 decimal value
    private static final DecimalFormat decfor = new DecimalFormat("0.00");

    @Override
    @Retryable(include = {KPIException.class}, maxAttemptsExpression = "${retry-max-attempts}")
    public KPIResponse getAllEmployeeKPPStatusReport(String fromDate, String toDate,  Integer empId, Integer roleId, String statusCd, Pageable pageable) {
        log.debug("Inside CumulativeServiceImpl >> getAllEmployeeKPPStatusReport() fromDate : {}, toDate : {}, roleId : {}", fromDate, toDate, roleId);
        String sortName = null;
        String startDate = StringUtils.isNotEmpty(fromDate) ? DateTimeUtils.addOneDayToInstant(fromDate).toString() : DateTimeUtils.getFirstDateOfYear();
        String endDate = StringUtils.isNotEmpty(toDate) ? DateTimeUtils.addOneDayToInstant(toDate).toString() : Instant.now().toString();

        CummalitiveEmployeeResponse cummalitiveEmployeeResponse = new CummalitiveEmployeeResponse();

        // String sortDirection = null;
        Integer pageSize = pageable.getPageSize();
        Integer pageOffset = (int) pageable.getOffset();
        // pageable = KPIUtils.sort(requestPageable, sortParam, pageDirection);
        Optional<Sort.Order> order = pageable.getSort().get().findFirst();
        if (order.isPresent()) {
            sortName = order.get().getProperty();  //order by this field
            //sortDirection = order.get().getDirection().toString(); // Sort ASC or DESC
        }
        try {
            Integer totalCount = reportEmployeeKppMasterRepo.getEmployeeKppStatusReportCount(startDate, endDate, empId, roleId,  statusCd);
            List<Object[]> employeeDetail = reportEmployeeKppMasterRepo.getEmployeeKppStatusReportDetail(startDate, endDate, empId, roleId,  statusCd,  sortName, pageSize, pageOffset);
            if(employeeDetail.size()>0) {
                List<EmployeeKppStatusResponse> employeeKppStatusResponses = employeeDetail.stream().map(EmployeeKppStatusResponse::new).collect(Collectors.toList());

                Double sumOfEmployeeRatings = 0.0;
                Double sumOfHodRatings = 0.0;
                Double sumOfGMRatings = 0.0;

                Double cummulativeRatings = 0.0;
                Double avgCummulativeRatings = 0.0;
                for (EmployeeKppStatusResponse statusResponse : employeeKppStatusResponses) {
                    Double sumOfRatings = 0.0;
                    sumOfRatings = Double.parseDouble(statusResponse.getEmpOverallAchive()) + Double.parseDouble(statusResponse.getHodOverallAchieve()) + Double.parseDouble(statusResponse.getGmOverallAchieve());
                    statusResponse.setSumOfRatings(sumOfRatings);

                    sumOfEmployeeRatings += Double.parseDouble(statusResponse.getEmpOverallAchive());
                    sumOfHodRatings += Double.parseDouble(statusResponse.getHodOverallAchieve());
                    sumOfGMRatings += Double.parseDouble(statusResponse.getGmOverallAchieve());

                    // statusResponse.setEmpOverallAchive(String.valueOf(sumOfEmployeeRatings));
                    cummulativeRatings += sumOfRatings;

                }
                avgCummulativeRatings = Double.valueOf(cummulativeRatings / employeeKppStatusResponses.size());
                employeeKppStatusResponses = employeeKppStatusResponses.stream()
                        .sorted(Comparator.comparing(EmployeeKppStatusResponse::getEkppMonth))
                        .collect(Collectors.toList());

                cummalitiveEmployeeResponse.setEmpId(employeeKppStatusResponses.get(0).getEmpId());
                cummalitiveEmployeeResponse.setEmpEId(employeeKppStatusResponses.get(0).getEmpEId());
                cummalitiveEmployeeResponse.setEmpName(employeeKppStatusResponses.get(0).getEmpFirstName()+" "+employeeKppStatusResponses.get(0).getEmpMiddleName()+" "+employeeKppStatusResponses.get(0).getEmpLastName());
                cummalitiveEmployeeResponse.setDeptId(employeeKppStatusResponses.get(0).getDeptId());
                cummalitiveEmployeeResponse.setDeptName(employeeKppStatusResponses.get(0).getDeptName());
                cummalitiveEmployeeResponse.setDesigId(employeeKppStatusResponses.get(0).getDesigId());
                cummalitiveEmployeeResponse.setDesigName(employeeKppStatusResponses.get(0).getDesigName());
              //  cummalitiveEmployeeResponse.setFinYear(employeeKppStatusResponses.get(0).getFinYear());

                cummalitiveEmployeeResponse.setEmployeeKppStatusResponses(new PageImpl<>(employeeKppStatusResponses, pageable, totalCount));
                cummalitiveEmployeeResponse.setSumOfEmployeeRatings(sumOfEmployeeRatings);
                cummalitiveEmployeeResponse.setSumOfHodRatings(sumOfHodRatings);
                cummalitiveEmployeeResponse.setSumOfGMRatings(sumOfGMRatings);

                cummalitiveEmployeeResponse.setCummulativeRatings(cummulativeRatings);
                cummalitiveEmployeeResponse.setAvgCummulativeRatings(Double.valueOf(decfor.format(avgCummulativeRatings)));
                cummalitiveEmployeeResponse.setTotalMonths(employeeKppStatusResponses.size());
            }
            else{
                return KPIResponse.builder()
                        .isSuccess(false)
                        .responseMessage("KPP is not approved yet")
                        .build();
            }
            return KPIResponse.builder()
                    .isSuccess(true)
                    .responseData(cummalitiveEmployeeResponse)
                    .responseMessage(KPIConstants.RECORD_FETCH)
                    .build();
        } catch (Exception ex) {
            log.error("Inside CumulativeServiceImpl >> getAllEmployeeKPPStatusReport() : {}", ex);
            throw new KPIException("CumulativeServiceImpl >> getAllEmployeeKPPStatusReport()", false, ex.getMessage());
        }
    }


    @Override
    @Retryable(include = {KPIException.class}, maxAttemptsExpression = "${retry-max-attempts}")
    public KPIResponse allEmployeeKppDetails(String fromDate, String toDate, Integer roleId,Integer deptId,Integer desigId,Integer reportingEmpId,Integer gmEmpId,Pageable requestPageable) {
        log.debug("Inside CumulativeServiceImpl >> allEmployeeKppDetails() fromDate : {}, toDate : {}, roleId : {}, deptId : {}, desigId : {}, reportingEmpId : {}, gmEmpId : {}", fromDate, toDate, roleId, deptId, desigId, reportingEmpId, gmEmpId);

        KPIResponse kpiResponse = new KPIResponse();
        String sortName = null;
        //  String sortDirection = null;
        Integer pageSize = requestPageable.getPageSize();
        Integer pageOffset = (int) requestPageable.getOffset();
        // pageable = KPIUtils.sort(requestPageable, sortParam, pageDirection);
        Optional<Sort.Order> order = requestPageable.getSort().get().findFirst();
        if (order.isPresent()) {
            sortName = order.get().getProperty();  //order by this field
            //sortDirection = order.get().getDirection().toString(); // Sort ASC or DESC
        }
        String startDate = StringUtils.isNotEmpty(fromDate) ? DateTimeUtils.convertStringToInstant(fromDate).toString() : DateTimeUtils.getFirstDateOfYear();
        String endDate = StringUtils.isNotEmpty(toDate) ? DateTimeUtils.convertStringToInstant(toDate).toString() : Instant.now().toString();


        try{
            Integer totalCount = reportEmployeeKppMasterRepo.cumulativeEmpForHoDAndGMCount(startDate, endDate,roleId,deptId,desigId, reportingEmpId,gmEmpId);
            List<Object[]> employeeDetail = reportEmployeeKppMasterRepo.cumulativeEmpForHoDAndGM(startDate, endDate,roleId,deptId,desigId, reportingEmpId,gmEmpId, sortName, pageSize, pageOffset);
            List<CumulativeHoDResponse> employeeKppStatusDtos = employeeDetail.stream().map(CumulativeHoDResponse::new).collect(Collectors.toList());

           if(employeeKppStatusDtos.size()>0) {
               Map<HODCumulativeData, List<TotalCumulativeHoD>> hodCumulativeDataListMap =
                       employeeKppStatusDtos.stream().collect(Collectors.groupingBy(CumulativeHoDResponse::getHodCumulativeData, Collectors.mapping(CumulativeHoDResponse::getTotalCumulativeHoD, Collectors.toList())));
               List<HoDCumulativeResponse> hoDCumulativeResponses = new ArrayList<>();

               HoDCumulativeResponse hoDCumulativeResponse;
               for (Map.Entry<HODCumulativeData, List<TotalCumulativeHoD>> statusResponse : hodCumulativeDataListMap.entrySet()) {
                   hoDCumulativeResponse = new HoDCumulativeResponse();
                   hoDCumulativeResponse.setEmpId(statusResponse.getKey().getEmpId());
                   hoDCumulativeResponse.setEmpName(statusResponse.getKey().getEmpName());
                   hoDCumulativeResponse.setEmpEId(statusResponse.getKey().getEmpEId());
                   hoDCumulativeResponse.setRoleId(statusResponse.getKey().getRoleId());
                   hoDCumulativeResponse.setRoleName(statusResponse.getKey().getRoleName());
                   hoDCumulativeResponse.setDeptId(statusResponse.getKey().getDeptId());
                   hoDCumulativeResponse.setDeptName(statusResponse.getKey().getDeptName());
                   hoDCumulativeResponse.setDesigId(statusResponse.getKey().getDesigId());
                   hoDCumulativeResponse.setDesigName(statusResponse.getKey().getDesigName());

                   hoDCumulativeResponse.setTotalCumulativeHoDS(statusResponse.getValue());
                   hoDCumulativeResponses.add(hoDCumulativeResponse);

               }

               for (HoDCumulativeResponse cumulativeHoDResponse : hoDCumulativeResponses) {
                   Double totalKppTotal = 0.0;
                   Double avgHoDKppRating = 0.0;
                   for (TotalCumulativeHoD totalCumulativeHoD : cumulativeHoDResponse.getTotalCumulativeHoDS()) {
                       totalKppTotal += totalCumulativeHoD.getTotalWeight();
                   }
                   avgHoDKppRating = totalKppTotal / cumulativeHoDResponse.getTotalCumulativeHoDS().size();


                   cumulativeHoDResponse.setTotalHodKppRatings(decfor.format(totalKppTotal));
                   cumulativeHoDResponse.setTotalMonths(cumulativeHoDResponse.getTotalCumulativeHoDS().size());
                   cumulativeHoDResponse.setAvgTotalHodKppRatings(decfor.format(avgHoDKppRating));
                   cumulativeHoDResponse.setTotalCumulativeHoDS(null);
               }
               kpiResponse.setResponseMessage("Total Kpp fetched");
               kpiResponse.setResponseData(new PageImpl<>(hoDCumulativeResponses, requestPageable, totalCount));
               kpiResponse.setSuccess(true);
           }
           else {
               kpiResponse.setResponseMessage("Kpp is not approved yet");
               kpiResponse.setResponseData(null);
               kpiResponse.setSuccess(false);
           }
        }
     catch (Exception ex) {
        log.error("Inside CumulativeServiceImpl >> allEmployeeKppDetails() : {}", ex);
        throw new KPIException("CumulativeServiceImpl >> allEmployeeKppDetails()", false, ex.getMessage());
    }
        return kpiResponse;

    }

    @Transactional
    @Override
    @Retryable(include = {KPIException.class}, maxAttemptsExpression = "${retry-max-attempts}")
    public KPIResponse updateOverallEmployeeKppReportRemark(CumulativeUpdateRequest cumulativeUpdateRequest) {
        log.debug("Inside CumulativeServiceImpl >> getAllEmployeeKPPStatusReport() cumulativeUpdateRequest : {}",cumulativeUpdateRequest) ;
        try {
            reportEmployeeKppMasterRepo.updateOverallEmployeeKppReportRemark(cumulativeUpdateRequest.getFinYear(),cumulativeUpdateRequest.getEmpKeyStrength(),cumulativeUpdateRequest.getEmpAreaOfImprovement(),cumulativeUpdateRequest.getEmpTrainginDevelopmentNeeds(),cumulativeUpdateRequest.getEmployeeId(),cumulativeUpdateRequest.getEmpId());
            return KPIResponse.builder()
                    .isSuccess(true)
                    .responseMessage("Employee remark added")
                    .build();
        } catch (Exception ex) {
            log.error("Inside CumulativeServiceImpl >> addEmployeeCumulativeRemark() :{}", ex);
            throw new KPIException("CumulativeServiceImpl >> addEmployeeCumulativeRemark", false, ex.getMessage());
        }
    }

    @Override
    @Retryable(include = {KPIException.class}, maxAttemptsExpression = "${retry-max-attempts}")
    public List<KppFinancialYearDDResponse> ddAllFinancialYear() {
        log.debug("Inside CumulativeServiceImpl >> ddAllFinancialYear()") ;
        try{
        List<Object[]> financialYearData = reportEmployeeKppMasterRepo.ddAllFinancialYear();
        List<KppFinancialYearDDResponse> financialYearDDResponses = new ArrayList<>();
        if (financialYearData.size() > 0) {
            financialYearDDResponses = financialYearData.stream().map(KppFinancialYearDDResponse::new).collect(Collectors.toList());
        }
        return financialYearDDResponses;
        } catch (Exception ex) {
            log.error("Inside CumulativeServiceImpl >> ddAllFinancialYear() : {}", ex);
            throw new KPIException("CumulativeServiceImpl >> ddAllFinancialYear()", false, ex.getMessage());
        }
    }
}
