package com.futurebizops.kpi.service.serviceimpl;

import com.futurebizops.kpi.constants.KPIConstants;
import com.futurebizops.kpi.dto.EmployeeKppDetailsDto;
import com.futurebizops.kpi.dto.EmployeeKppMasterDto;
import com.futurebizops.kpi.dto.EmployeeKppStatusDto;
import com.futurebizops.kpi.exception.KPIException;
import com.futurebizops.kpi.repository.EmployeeKppMasterRepo;
import com.futurebizops.kpi.repository.KeyPerfParameterRepo;
import com.futurebizops.kpi.response.CummalitiveEmployeeResponse;
import com.futurebizops.kpi.response.EmpKppStatusResponse;
import com.futurebizops.kpi.response.cumulative.CumulativeHoDResponse;
import com.futurebizops.kpi.response.EmployeeKppStatusResponse;
import com.futurebizops.kpi.response.cumulative.HODCumulativeData;
import com.futurebizops.kpi.response.KPIResponse;
import com.futurebizops.kpi.response.cumulative.HoDCumulativeResponse;
import com.futurebizops.kpi.response.cumulative.TotalCumulativeHoD;
import com.futurebizops.kpi.service.CumulativeService;
import com.futurebizops.kpi.utils.DateTimeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

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
    EmployeeKppMasterRepo employeeKppMasterRepo;

    @Autowired
    private KeyPerfParameterRepo keyPerfParameterRepo;

    //shpw only 2 decimal value
    private static final DecimalFormat decfor = new DecimalFormat("0.00");

    @Override
    public KPIResponse getAllEmployeeKPPStatusReport(String fromDate, String toDate, Integer reportingEmployee, Integer gmEmpId, Integer empId, String empEId, Integer roleId, Integer deptId, Integer desigId, String empFirstName, String empMiddleName, String empLastName, String empMobileNo, String emailId, String statusCd, String empKppStatus, String hodKppStatus, String gmKppStatus, Pageable pageable) {
        String sortName = null;
        String startDate = StringUtils.isNotEmpty(fromDate) ? DateTimeUtils.convertStringToInstant(fromDate).toString() : DateTimeUtils.getFirstDateOfYear();
        String endDate = StringUtils.isNotEmpty(toDate) ? DateTimeUtils.convertStringToInstant(toDate).toString() : Instant.now().toString();

        CummalitiveEmployeeResponse cummalitiveEmployeeResponse = new CummalitiveEmployeeResponse();
        //for all records
        if ("All".equalsIgnoreCase(empKppStatus)) {
            empKppStatus = null;
        }
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
            Integer totalCount = keyPerfParameterRepo.getEmployeeKppStatusDetailCount(reportingEmployee, gmEmpId, empId, empEId, roleId, deptId, desigId, empFirstName, empMiddleName, empLastName, empMobileNo, emailId, statusCd, empKppStatus, hodKppStatus, gmKppStatus);
            List<Object[]> employeeDetail = keyPerfParameterRepo.getEmployeeKppStatusReportDetail(startDate, endDate, reportingEmployee, gmEmpId, empId, empEId, roleId, deptId, desigId, empFirstName, empMiddleName, empLastName, empMobileNo, emailId, statusCd, empKppStatus, hodKppStatus, gmKppStatus, sortName, pageSize, pageOffset);

            if(employeeDetail.size()>0) {
                List<EmployeeKppStatusResponse> employeeKppStatusResponses = employeeDetail.stream().map(EmployeeKppStatusResponse::new).collect(Collectors.toList());

                Integer sumOfEmployeeRatings = 0;
                Integer sumOfHodRatings = 0;
                Integer sumOfGMRatings = 0;

                Integer cummulativeRatings = 0;
                Float avgCummulativeRatings = 0.0f;
                for (EmployeeKppStatusResponse statusResponse : employeeKppStatusResponses) {
                    Integer sumOfRatings = 0;
                    sumOfRatings = Integer.parseInt(statusResponse.getEmpOverallAchive()) + Integer.parseInt(statusResponse.getHodOverallAchieve()) + Integer.parseInt(statusResponse.getGmOverallAchieve());
                    statusResponse.setSumOfRatings(sumOfRatings);

                    sumOfEmployeeRatings += Integer.parseInt(statusResponse.getEmpOverallAchive());
                    sumOfHodRatings += Integer.parseInt(statusResponse.getHodOverallAchieve());
                    sumOfGMRatings += Integer.parseInt(statusResponse.getGmOverallAchieve());

                    // statusResponse.setEmpOverallAchive(String.valueOf(sumOfEmployeeRatings));
                    cummulativeRatings += sumOfRatings;

                }
                avgCummulativeRatings = Float.valueOf(cummulativeRatings / employeeKppStatusResponses.size());
                employeeKppStatusResponses = employeeKppStatusResponses.stream()
                        .sorted(Comparator.comparing(EmployeeKppStatusResponse::getEkppMonth))
                        .collect(Collectors.toList());


                cummalitiveEmployeeResponse.setEmployeeKppStatusResponses(new PageImpl<>(employeeKppStatusResponses, pageable, totalCount));
                cummalitiveEmployeeResponse.setSumOfEmployeeRatings(sumOfEmployeeRatings);
                cummalitiveEmployeeResponse.setSumOfHodRatings(sumOfHodRatings);
                cummalitiveEmployeeResponse.setSumOfGMRatings(sumOfGMRatings);

                cummalitiveEmployeeResponse.setCummulativeRatings(cummulativeRatings);
                cummalitiveEmployeeResponse.setAvgCummulativeRatings(avgCummulativeRatings);
            }
            else{
                return KPIResponse.builder()
                        .isSuccess(false)
                        .responseData(cummalitiveEmployeeResponse)
                        .responseMessage("No Record found")
                        .build();
            }
            return KPIResponse.builder()
                    .isSuccess(true)
                    .responseData(cummalitiveEmployeeResponse)
                    .responseMessage(KPIConstants.RECORD_FETCH)
                    .build();
        } catch (Exception ex) {
            log.error("Inside EmployeeKeyPerfParamServiceImpl >> getAllEmployeeDetailsForHod()");
            throw new KPIException("EmployeeKeyPerfParamServiceImpl", false, ex.getMessage());
        }
    }


    @Override
    public KPIResponse allEmployeeKppDetails(String fromDate, String toDate, Integer empId) {
        String sortName = null;

        KPIResponse kpiResponse = new KPIResponse();

        String startDate = StringUtils.isNotEmpty(fromDate) ? DateTimeUtils.convertStringToInstant(fromDate).toString() : DateTimeUtils.getFirstDateOfYear();
        String endDate = StringUtils.isNotEmpty(toDate) ? DateTimeUtils.convertStringToInstant(toDate).toString() : Instant.now().toString();


        try{
            //Integer totalCount = employeeKppMasterRepo.getEmployeeKppStatusDetailCount(reportingEmployee, gmEmpId, empId, empEId, roleId, deptId, desigId, empFirstName, empMiddleName, empLastName, empMobileNo, emailId, statusCd, empKppStatus, hodKppStatus, gmKppStatus);
            List<Object[]> employeeDetail = employeeKppMasterRepo.cumulativeEmpForHoDAndGM(empId, startDate, endDate);
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
               System.out.println(hoDCumulativeResponses);

               Double totalKppTotal = 0.0;
               Double avgHoDKppRating = 0.0;
               for (HoDCumulativeResponse cumulativeHoDResponse : hoDCumulativeResponses) {

                   for (TotalCumulativeHoD totalCumulativeHoD : cumulativeHoDResponse.getTotalCumulativeHoDS()) {
                       totalKppTotal += totalCumulativeHoD.getTotalWeight();
                   }
                   avgHoDKppRating = totalKppTotal / cumulativeHoDResponse.getTotalCumulativeHoDS().size();


                   cumulativeHoDResponse.setTotalHodKppRatings(totalKppTotal);
                   cumulativeHoDResponse.setTotalMonths(cumulativeHoDResponse.getTotalCumulativeHoDS().size());
                   cumulativeHoDResponse.setAvgTotalHodKppRatings(decfor.format(avgHoDKppRating));
                   cumulativeHoDResponse.setTotalCumulativeHoDS(null);
               }
               kpiResponse.setResponseMessage("Total Kpp fetched");
               kpiResponse.setResponseData(hoDCumulativeResponses);
               kpiResponse.setSuccess(true);
           }
           else {
               kpiResponse.setResponseMessage("Kpp is not approved yet");
               kpiResponse.setResponseData(null);
               kpiResponse.setSuccess(true);
           }
        }
        catch (Exception ex){

        }
        return kpiResponse;

    }
}
