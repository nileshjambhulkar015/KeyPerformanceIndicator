package com.futurebizops.kpi.service.serviceimpl;

import com.futurebizops.kpi.constants.KPIConstants;
import com.futurebizops.kpi.dto.EmployeeKppDetailsDto;
import com.futurebizops.kpi.dto.EmployeeKppMasterDto;
import com.futurebizops.kpi.dto.EmployeeKppStatusDto;
import com.futurebizops.kpi.dto.OverallEmployeeKppFeedbackDetailsDto;
import com.futurebizops.kpi.dto.OverallEmployeeKppFeedbackMasterDto;
import com.futurebizops.kpi.dto.OverallEmployeeKppFeedbackStatusDto;
import com.futurebizops.kpi.entity.EmployeeEntity;
import com.futurebizops.kpi.entity.EmployeeKppMasterEntity;
import com.futurebizops.kpi.entity.OverallEmployeeKppFeedbackDetailsEntity;
import com.futurebizops.kpi.entity.OverallEmployeeKppFeedbackMasterEntity;
import com.futurebizops.kpi.exception.KPIException;
import com.futurebizops.kpi.repository.FinancialYearRepo;
import com.futurebizops.kpi.repository.OverallEmployeeKppFeedbackDetailsRepo;
import com.futurebizops.kpi.repository.OverallEmployeeKppFeedbackMasterRepo;
import com.futurebizops.kpi.repository.ReportEmployeeKppMasterRepo;
import com.futurebizops.kpi.request.yearlykpprequest.FreezeEmpKPPDetailsRequest;
import com.futurebizops.kpi.request.yearlykpprequest.FreezeEmpKPPMasterRequest;
import com.futurebizops.kpi.response.EmpKppStatusResponse;
import com.futurebizops.kpi.response.EmployeeResponse;
import com.futurebizops.kpi.response.FreezeEmpKppStatusResponse;
import com.futurebizops.kpi.response.KPIResponse;

import com.futurebizops.kpi.response.OverallEmpDetailsKppFeedbackResponse;
import com.futurebizops.kpi.response.dropdown.KppFinancialYearDDResponse;
import com.futurebizops.kpi.service.OverallEmployeeKppFeedbackService;
import com.futurebizops.kpi.utils.DateTimeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
public class OverallEmployeeKppFeedbackServiceImpl implements OverallEmployeeKppFeedbackService {

    @Autowired
    FinancialYearRepo financialYearRepo;

    @Autowired
    private ReportEmployeeKppMasterRepo reportEmployeeKppMasterRepo;

    @Autowired
    private OverallEmployeeKppFeedbackMasterRepo freezeReportEmployeeKppMasterRepo;

    @Autowired
    OverallEmployeeKppFeedbackDetailsRepo freezeReportEmployeeKppDetailsRepo;

    //shpw only 2 decimal value
    private static final DecimalFormat decfor = new DecimalFormat("0.00");

    @Override
    public KPIResponse getAllEmployeeKppFeedbackDetails(Integer empId,Integer roleId, String finYear, Integer reportingEmpId,Integer gmEmpId, String empKppStatus,String hodKppStatus,String gmKppStatus,Pageable pageable) {
        KPIResponse kpiResponse = new KPIResponse();
        //for all records
        if ("All".equalsIgnoreCase(empKppStatus)) {
            empKppStatus = null;
        }
        String sortName = null;
        //  String sortDirection = null;
        Integer pageSize = pageable.getPageSize();
        Integer pageOffset = (int) pageable.getOffset();
        // pageable = KPIUtils.sort(requestPageable, sortParam, pageDirection);
        Optional<Sort.Order> order = pageable.getSort().get().findFirst();
        if (order.isPresent()) {
            sortName = order.get().getProperty();  //order by this field
            //  sortDirection = order.get().getDirection().toString(); // Sort ASC or DESC
        }

        Integer totalCount = freezeReportEmployeeKppMasterRepo.getEmployeeDetailForKPPCount(empId,roleId, finYear,reportingEmpId,gmEmpId,empKppStatus,hodKppStatus,gmKppStatus);
        List<Object[]> employeeDetail = freezeReportEmployeeKppMasterRepo.getEmployeeDetailForKPP(empId,roleId, finYear, reportingEmpId,gmEmpId,empKppStatus,hodKppStatus,gmKppStatus, sortName, pageSize, pageOffset);
        if (employeeDetail.size() > 0) {
            List<OverallEmpDetailsKppFeedbackResponse> employeeResponses = employeeDetail.stream().map(OverallEmpDetailsKppFeedbackResponse::new).collect(Collectors.toList());
            kpiResponse.setSuccess(true);
            kpiResponse.setResponseData(new PageImpl<>(employeeResponses, pageable, totalCount));
            kpiResponse.setResponseMessage(KPIConstants.RECORD_FETCH);

        } else {
            kpiResponse.setSuccess(false);
            kpiResponse.setResponseMessage(KPIConstants.RECORD_NOT_FOUND);
        }
        return kpiResponse;
    }

    @Override
    public List<KppFinancialYearDDResponse> ddAllFinancialYear() {
        List<Object[]> financialYearData = freezeReportEmployeeKppMasterRepo.ddAllFinancialYear();
        List<KppFinancialYearDDResponse> financialYearDDResponses = new ArrayList<>();
        if (financialYearData.size() > 0) {
            financialYearDDResponses = financialYearData.stream().map(KppFinancialYearDDResponse::new).collect(Collectors.toList());
        }
        return financialYearDDResponses;
    }



    @Transactional
    @Override
    public KPIResponse saveEmployeeKPPFeedbackDetails(FreezeEmpKPPMasterRequest freezeEmpKPPMasterRequest) {

        List<Object[]> freezeReportAvailable = freezeReportEmployeeKppMasterRepo.checkKppReportAdded(freezeEmpKPPMasterRequest.getKppUpdateRequests().get(0).getEmpId(), freezeEmpKPPMasterRequest.getFinYear());
        if(freezeReportAvailable.size()>0){
            System.out.println("Record is alreaddy present");
            for(FreezeEmpKPPDetailsRequest freezeEmpKPPDetailsRequest : freezeEmpKPPMasterRequest.getKppUpdateRequests()){
                System.out.println(freezeEmpKPPDetailsRequest);
                log.info("Emp Id : {},Kpp Id : {},fin Year : {}, Feedback : {}",freezeEmpKPPMasterRequest.getEmpId(),freezeEmpKPPDetailsRequest.getKppId(),freezeEmpKPPMasterRequest.getFinYear(),freezeEmpKPPDetailsRequest.getEmpKppFeedback());
                freezeReportEmployeeKppDetailsRepo.updateHODFeedbackKppDetails(freezeEmpKPPMasterRequest.getEmpId(),freezeEmpKPPDetailsRequest.getKppId(),freezeEmpKPPMasterRequest.getFinYear(),freezeEmpKPPDetailsRequest.getEmpKppFeedback());
            }
            return KPIResponse.builder()
                    .isSuccess(true)
                    .responseMessage("Updated KPP feedback successfully")
                    .build();
        }else {

            Instant ekppMonth = DateTimeUtils.convertStringToInstant(freezeEmpKPPMasterRequest.getEkppMonth());
            try {
                List<OverallEmployeeKppFeedbackDetailsEntity> freezeReportEmployeeKppDetailsEntities = freezeReportEmployeeKppDetailsToEntities(freezeEmpKPPMasterRequest, ekppMonth);
                freezeReportEmployeeKppDetailsRepo.saveAll(freezeReportEmployeeKppDetailsEntities);

                OverallEmployeeKppFeedbackMasterEntity freezeReportEmployeeKppMasterEntity = freezeReportEmployeeKppMasterEntities(freezeEmpKPPMasterRequest, ekppMonth);
                freezeReportEmployeeKppMasterRepo.save(freezeReportEmployeeKppMasterEntity);

                return KPIResponse.builder()
                        .isSuccess(true)
                        .responseMessage("Save HOD KPP details successfully")
                        .build();
            } catch (Exception ex) {
                log.error("Inside EmployeeKeyPerfParamServiceImpl >> updateEmployeeKeyPerfParamDetails()", ex);
                throw new KPIException("EmployeeKeyPerfParamServiceImpl >> updateEmployeeKeyPerfParamDetails() Class", false, ex.getMessage());
            }
        }

    }

    private OverallEmployeeKppFeedbackMasterEntity freezeReportEmployeeKppMasterEntities(FreezeEmpKPPMasterRequest freezeEmpKPPMasterRequest, Instant ekppMonth) {
        OverallEmployeeKppFeedbackMasterEntity reportEmployeeKppMasterEntity = new OverallEmployeeKppFeedbackMasterEntity();
        reportEmployeeKppMasterEntity.setFinYear(freezeEmpKPPMasterRequest.getFinYear());
        reportEmployeeKppMasterEntity.setEkppMonth(ekppMonth);
        reportEmployeeKppMasterEntity.setEmpId(freezeEmpKPPMasterRequest.getEmpId());
        reportEmployeeKppMasterEntity.setEmpEId(freezeEmpKPPMasterRequest.getEmpEId());
        reportEmployeeKppMasterEntity.setRoleId(freezeEmpKPPMasterRequest.getRoleId());
        reportEmployeeKppMasterEntity.setDeptId(freezeEmpKPPMasterRequest.getDeptId());
        reportEmployeeKppMasterEntity.setDesigId(freezeEmpKPPMasterRequest.getDesigId());

        reportEmployeeKppMasterEntity.setTotalEmpAchivedWeight(freezeEmpKPPMasterRequest.getTotalEmpAchivedWeight());
        reportEmployeeKppMasterEntity.setTotalEmpOverallAchieve(freezeEmpKPPMasterRequest.getTotalEmpOverallAchieve());
        reportEmployeeKppMasterEntity.setTotalEmpOverallTaskComp(freezeEmpKPPMasterRequest.getTotalEmpOverallTaskComp());
        reportEmployeeKppMasterEntity.setEmpKppAppliedDate(ekppMonth);
        reportEmployeeKppMasterEntity.setEmpKppStatus(freezeEmpKPPMasterRequest.getEmpKppStatus());
        reportEmployeeKppMasterEntity.setEmpRemark(freezeEmpKPPMasterRequest.getEmpRemark());
        reportEmployeeKppMasterEntity.setEmpEvidence(freezeEmpKPPMasterRequest.getEmpEvidence());

        reportEmployeeKppMasterEntity.setHodEmpId(freezeEmpKPPMasterRequest.getHodEmpId());
        reportEmployeeKppMasterEntity.setTotalHodAchivedWeight(freezeEmpKPPMasterRequest.getTotalHodAchivedWeight());
        reportEmployeeKppMasterEntity.setTotalHodOverallAchieve(freezeEmpKPPMasterRequest.getTotalHodOverallAchieve());
        reportEmployeeKppMasterEntity.setTotalHodOverallTaskComp(freezeEmpKPPMasterRequest.getTotalHodOverallTaskComp());
        reportEmployeeKppMasterEntity.setHodKppAppliedDate(freezeEmpKPPMasterRequest.getHodKppAppliedDate());
        reportEmployeeKppMasterEntity.setHodKppStatus("Pending");
        reportEmployeeKppMasterEntity.setHodRemark(freezeEmpKPPMasterRequest.getHodRemark());

        reportEmployeeKppMasterEntity.setGmEmpId(freezeEmpKPPMasterRequest.getGmEmpId());
        reportEmployeeKppMasterEntity.setTotalGmOverallAchieve(freezeEmpKPPMasterRequest.getTotalGmOverallAchieve());
        reportEmployeeKppMasterEntity.setTotalGmAchivedWeight(freezeEmpKPPMasterRequest.getTotalGmAchivedWeight());
        reportEmployeeKppMasterEntity.setTotalGmOverallTaskComp(freezeEmpKPPMasterRequest.getTotalGmOverallTaskComp());
        reportEmployeeKppMasterEntity.setGmKppAppliedDate(freezeEmpKPPMasterRequest.getGmKppAppliedDate());
        reportEmployeeKppMasterEntity.setGmKppStatus("Pending");
        reportEmployeeKppMasterEntity.setGmRemark(freezeEmpKPPMasterRequest.getGmRemark());

        reportEmployeeKppMasterEntity.setAvgTotalOverallRating(freezeEmpKPPMasterRequest.getAvgTotalOverallRating());
        reportEmployeeKppMasterEntity.setAvgTotalOverallPer(freezeEmpKPPMasterRequest.getAvgTotalOverallPer());
        reportEmployeeKppMasterEntity.setStatusCd(freezeEmpKPPMasterRequest.getStatusCd());


        return reportEmployeeKppMasterEntity;
    }

    private List<OverallEmployeeKppFeedbackDetailsEntity> freezeReportEmployeeKppDetailsToEntities(FreezeEmpKPPMasterRequest freezeEmpKPPMasterRequest, Instant ekppMonth) {
        List<OverallEmployeeKppFeedbackDetailsEntity> freezeReportEmployeeKppDetailsEntities = new ArrayList<>();

        for (FreezeEmpKPPDetailsRequest empKPPUpdateRequest : freezeEmpKPPMasterRequest.getKppUpdateRequests()) {
            OverallEmployeeKppFeedbackDetailsEntity reportEmployeeKppDetails = new OverallEmployeeKppFeedbackDetailsEntity();
            reportEmployeeKppDetails.setFinYear(freezeEmpKPPMasterRequest.getFinYear());
            reportEmployeeKppDetails.setEkppMonth(ekppMonth);
            reportEmployeeKppDetails.setKppId(empKPPUpdateRequest.getKppId());
            reportEmployeeKppDetails.setEmpId(freezeEmpKPPMasterRequest.getEmpId());
            reportEmployeeKppDetails.setEmpEId(freezeEmpKPPMasterRequest.getEmpEId());
            reportEmployeeKppDetails.setRoleId(freezeEmpKPPMasterRequest.getRoleId());
            reportEmployeeKppDetails.setDeptId(freezeEmpKPPMasterRequest.getDeptId());
            reportEmployeeKppDetails.setDesigId(freezeEmpKPPMasterRequest.getDesigId());
            reportEmployeeKppDetails.setKppOverallTarget(empKPPUpdateRequest.getKppOverallTarget());
            reportEmployeeKppDetails.setKppOverallWeightage(empKPPUpdateRequest.getKppOverallWeightage());
            reportEmployeeKppDetails.setEmpAchivedWeight(empKPPUpdateRequest.getEmpAchivedWeight());
            reportEmployeeKppDetails.setEmpOverallAchieve(empKPPUpdateRequest.getEmpOverallAchieve());
            reportEmployeeKppDetails.setEmpOverallTaskComp(empKPPUpdateRequest.getEmpOverallTaskComp());
            reportEmployeeKppDetails.setHodEmpId(empKPPUpdateRequest.getHodEmpId());
            reportEmployeeKppDetails.setHodAchivedWeight(empKPPUpdateRequest.getHodAchivedWeight());
            reportEmployeeKppDetails.setHodOverallAchieve(empKPPUpdateRequest.getHodOverallAchieve());
            reportEmployeeKppDetails.setHodOverallTaskComp(empKPPUpdateRequest.getHodOverallTaskComp());
            reportEmployeeKppDetails.setGmEmpId(empKPPUpdateRequest.getGmEmployeeId());
            reportEmployeeKppDetails.setGmAchivedWeight(empKPPUpdateRequest.getGmAchivedWeight());
            reportEmployeeKppDetails.setGmAchivedWeight(empKPPUpdateRequest.getGmAchivedWeight());
            reportEmployeeKppDetails.setGmOverallAchieve(empKPPUpdateRequest.getGmOverallAchieve());
            reportEmployeeKppDetails.setGmOverallTaskComp(empKPPUpdateRequest.getGmOverallTaskComp());
            reportEmployeeKppDetails.setAvgOverallRating(empKPPUpdateRequest.getOverallRatings());
            reportEmployeeKppDetails.setAvgOverallPer(empKPPUpdateRequest.getOverallPercentage());
            reportEmployeeKppDetails.setStatusCd(empKPPUpdateRequest.getStatusCd());
            reportEmployeeKppDetails.setEmpKppFeedback(empKPPUpdateRequest.getEmpKppFeedback());
            reportEmployeeKppDetails.setHodKppFeedback(empKPPUpdateRequest.getHodKppFeedback());
            reportEmployeeKppDetails.setGmKppFeedback(empKPPUpdateRequest.getGmKppFeedback());
            freezeReportEmployeeKppDetailsEntities.add(reportEmployeeKppDetails);
        }
        return freezeReportEmployeeKppDetailsEntities;
    }


    @Override
    public KPIResponse getEmployeeKppDataYearly(Integer empId, String finYear) {

        KPIResponse kpiResponse = new KPIResponse();
        List<Object[]> employeeKppData = null;


        List<Object[]> freezeReportAvailable = freezeReportEmployeeKppMasterRepo.checkKppReportAdded(empId, finYear);
        if(freezeReportAvailable.size()>0){

            employeeKppData = freezeReportEmployeeKppMasterRepo.getEmployeeKppDataYearlyFromFreezeTable(empId, finYear);
            List<OverallEmployeeKppFeedbackStatusDto> employeeKppStatusDtos = employeeKppData.stream().map(OverallEmployeeKppFeedbackStatusDto::new).collect(Collectors.toList());
            Map<OverallEmployeeKppFeedbackMasterDto, List<OverallEmployeeKppFeedbackDetailsDto>> employeeKppMasterDtoListMap =
                    employeeKppStatusDtos.stream().collect(Collectors.groupingBy(OverallEmployeeKppFeedbackStatusDto::getEmployeeKppMasterDto, Collectors.mapping(OverallEmployeeKppFeedbackStatusDto::getEmployeeKppDetailsDto, Collectors.toList())));

            kpiResponse = existingEmployeeKppFeedback(employeeKppMasterDtoListMap);
        } else {
            //if finanical year is empty then read it from financial year table of current year
           /* Optional<FinancialYearEntity> financialYearEntity = financialYearRepo.findById(1);
            if(financialYearEntity.isPresent()){
                finYear=financialYearEntity.get().getFinYear();
            }*/
            System.out.println("finYear : "+finYear);
            //if yearly kpp employee feedback not added
            employeeKppData = reportEmployeeKppMasterRepo.getEmployeeKppDataYearly(empId, finYear);
            if (employeeKppData.size() > 0) {
                List<EmployeeKppStatusDto> employeeKppStatusDtos = employeeKppData.stream().map(EmployeeKppStatusDto::new).collect(Collectors.toList());
                Map<EmployeeKppMasterDto, List<EmployeeKppDetailsDto>> employeeKppMasterDtoListMap =
                        employeeKppStatusDtos.stream().collect(Collectors.groupingBy(EmployeeKppStatusDto::getEmployeeKppMasterDto, Collectors.mapping(EmployeeKppStatusDto::getEmployeeKppDetailsDto, Collectors.toList())));

                kpiResponse = newEmployeeKppFeedback(employeeKppMasterDtoListMap,finYear);
            }

        }

        return kpiResponse;
    }


    private KPIResponse newEmployeeKppFeedback(Map<EmployeeKppMasterDto, List<EmployeeKppDetailsDto>> employeeKppMasterDtoListMap, String finYear) {
        KPIResponse kpiResponse = new KPIResponse();
        // List<EmpKppStatusResponse> empKppStatusResponses = new ArrayList<>();
        EmpKppStatusResponse statusResponse = null;

        Double totalEmpAchivedWeight = 0.0;
        Double totalEmpOverallAchieve = 0.0;
        Double totalEmpOverallTaskComp = 0.0;

        Double totalHodAchivedWeight = 0.0;
        Double totalHodOverallAchieve = 0.0;
        Double totalHodOverallTaskComp = 0.0;

        Double totalGmAchivedWeight = 0.0;
        Double totalGmOverallAchieve = 0.0;
        Double totalGmOverallTaskComp = 0.0;

        if (null !=employeeKppMasterDtoListMap) {

            for (Map.Entry<EmployeeKppMasterDto, List<EmployeeKppDetailsDto>> masterDtoListEntry : employeeKppMasterDtoListMap.entrySet()) {
                statusResponse = new EmpKppStatusResponse();
                statusResponse.setEKppMId(masterDtoListEntry.getKey().getEKppMId());
                statusResponse.setEmpEId(masterDtoListEntry.getKey().getEmpEId());

                statusResponse.setEKppMId(masterDtoListEntry.getKey().getEKppMId());
                statusResponse.setEkppMonth(masterDtoListEntry.getKey().getEkppMonth());
                statusResponse.setEmpId(masterDtoListEntry.getKey().getEmpId());
                statusResponse.setEmpName(masterDtoListEntry.getKey().getEmpName());
                statusResponse.setEmpEId(masterDtoListEntry.getKey().getEmpEId());
                statusResponse.setRoleId(masterDtoListEntry.getKey().getRoleId());
                statusResponse.setRoleName(masterDtoListEntry.getKey().getRoleName());
                statusResponse.setDeptId(masterDtoListEntry.getKey().getDeptId());
                statusResponse.setDeptName(masterDtoListEntry.getKey().getDeptName());
                statusResponse.setDesigId(masterDtoListEntry.getKey().getDesigId());
                statusResponse.setDesigName(masterDtoListEntry.getKey().getDesigName());

                totalEmpAchivedWeight += Double.parseDouble(masterDtoListEntry.getKey().getTotalEmpAchivedWeight());
                totalEmpOverallAchieve += Double.parseDouble(masterDtoListEntry.getKey().getTotalEmpOverallAchieve());
                totalEmpOverallTaskComp += Double.parseDouble(masterDtoListEntry.getKey().getTotalEmpOverallTaskComp());

                statusResponse.setTotalOverallRatings(masterDtoListEntry.getKey().getTotalOverallRatings());
                statusResponse.setTotalOverallPercentage(masterDtoListEntry.getKey().getTotalOverallPercentage());
                //statusResponse.setEmpKppAppliedDate(masterDtoListEntry.getKey().getEmpKppAppliedDate());
                statusResponse.setEmpKppStatus(masterDtoListEntry.getKey().getEmpKppStatus());
                statusResponse.setEmpRemark(masterDtoListEntry.getKey().getEmpRemark());
                statusResponse.setHodEmpId(masterDtoListEntry.getKey().getHodEmpId());

                totalHodAchivedWeight += Double.parseDouble(masterDtoListEntry.getKey().getTotalHodAchivedWeight());
                totalHodOverallAchieve += Double.parseDouble(masterDtoListEntry.getKey().getTotalHodOverallAchieve());

                totalHodOverallTaskComp += Double.parseDouble(masterDtoListEntry.getKey().getTotalHodOverallTaskComp());


                //  statusResponse.setHodKppAppliedDate(masterDtoListEntry.getKey().getHodKppAppliedDate());
                statusResponse.setHodKppStatus(masterDtoListEntry.getKey().getHodKppStatus());
                statusResponse.setHodRemark(masterDtoListEntry.getKey().getHodRemark());
                statusResponse.setGmEmpId(masterDtoListEntry.getKey().getGmEmpId());

                totalGmAchivedWeight += Double.parseDouble(masterDtoListEntry.getKey().getTotalGmAchivedWeight());
                totalGmOverallAchieve += Double.parseDouble(masterDtoListEntry.getKey().getTotalGmOverallAchieve());
                totalGmOverallTaskComp += Double.parseDouble(masterDtoListEntry.getKey().getTotalGmOverallTaskComp());

                // statusResponse.setGmKppAppliedDate(masterDtoListEntry.getKey().getGmKppAppliedDate());
                statusResponse.setGmKppStatus(masterDtoListEntry.getKey().getGmKppStatus());
                statusResponse.setGmRemark(masterDtoListEntry.getKey().getGmRemark());
                statusResponse.setRemark(masterDtoListEntry.getKey().getRemark());
                statusResponse.setCompanyId(masterDtoListEntry.getKey().getCompanyId());
                statusResponse.setCompanyName(masterDtoListEntry.getKey().getCompanyName());
                statusResponse.setCompanyAddress(masterDtoListEntry.getKey().getCompanyAddress());
                statusResponse.setCompanyMbNo(masterDtoListEntry.getKey().getCompanyMbNo());
                statusResponse.setCompanyFinYear(masterDtoListEntry.getKey().getCompanyFinYear());
                statusResponse.setFinYear(finYear);
                // statusResponse.setKppStatusDetails(masterDtoListEntry.getValue());

                List<EmployeeKppDetailsDto> employeeKppDetailsDtos = new ArrayList<>();

                employeeKppDetailsDtos = masterDtoListEntry.getValue().stream().collect(Collectors.groupingBy(EmployeeKppDetailsDto::getKppId,
                        Collectors.collectingAndThen(Collectors.toList(),
                                data -> {
                                    double empAchivedWeight = data.stream().mapToDouble(test -> Double.parseDouble(test.getEmpAchivedWeight())).sum();
                                    double empOverallAchieve = data.stream().mapToDouble(test -> Double.parseDouble(test.getEmpOverallAchieve())).sum();
                                    double empOverallTaskComp = data.stream().mapToDouble(test -> Double.parseDouble(test.getEmpOverallTaskComp())).sum();

                                    double hodAchivedWeight = data.stream().mapToDouble(test -> Double.parseDouble(test.getHodAchivedWeight())).sum();
                                    double hodOverallAchieve = data.stream().mapToDouble(test -> Double.parseDouble(test.getHodOverallAchieve())).sum();
                                    double hodOverallTaskComp = data.stream().mapToDouble(test -> Double.parseDouble(test.getHodOverallTaskComp())).sum();

                                    double gmAchivedWeight = data.stream().mapToDouble(test -> Double.parseDouble(test.getGmAchivedWeight())).sum();
                                    double gmOverallAchieve = data.stream().mapToDouble(test -> Double.parseDouble(test.getGmOverallAchieve())).sum();
                                    double gmOverallTaskComp = data.stream().mapToDouble(test -> Double.parseDouble(test.getGmOverallTaskComp())).sum();

                                    return new EmployeeKppDetailsDto(data.iterator().next().getEkppId(),finYear,  data.iterator().next().getKppId(), String.valueOf(empAchivedWeight), String.valueOf(empOverallAchieve), String.valueOf(empOverallTaskComp), data.iterator().next().getHodEmpId(), String.valueOf(hodAchivedWeight), String.valueOf(hodOverallAchieve), String.valueOf(hodOverallTaskComp),
                                            data.iterator().next().getGmEmployeeId(), String.valueOf(gmAchivedWeight), String.valueOf(gmOverallAchieve), String.valueOf(gmOverallTaskComp), data.iterator().next().getOverallRatings(), data.iterator().next().getOverallPercentage(), data.iterator().next().getKppObjective(), data.iterator().next().getKppPerformanceIndi(), data.iterator().next().getKppOverallTarget(), data.iterator().next().getKppTargetPeriod(), data.iterator().next().getUomId(), data.iterator().next().getUomName(),
                                            data.iterator().next().getKppOverallWeightage(), data.iterator().next().getKppRating1(), data.iterator().next().getKppRating2(), data.iterator().next().getKppRating3(), data.iterator().next().getKppRating4(), data.iterator().next().getKppRating5(), data.iterator().next().getEkppStatus());
                                })
                )).values().stream().collect(Collectors.toList());

                statusResponse.setKppStatusDetails(employeeKppDetailsDtos);
            }
        } else {
            //  log.error("EmployeeKppStatusServiceImpl >> getEmployeeKppStatus()  ");
            //  throw new KPIException("DepartmentServiceImpl", false, "No record found");
            return null;
        }


        statusResponse.setTotalEmpAchivedWeight(totalEmpAchivedWeight.toString());
        statusResponse.setTotalEmpOverallAchieve(totalEmpOverallAchieve.toString());
        statusResponse.setTotalEmpOverallTaskComp(totalEmpOverallTaskComp.toString());

        statusResponse.setTotalHodAchivedWeight(totalHodAchivedWeight.toString());
        statusResponse.setTotalHodOverallAchieve(totalHodOverallAchieve.toString());
        statusResponse.setTotalHodOverallTaskComp(totalHodOverallTaskComp.toString());

        statusResponse.setTotalGmAchivedWeight(decfor.format(totalGmAchivedWeight));
        statusResponse.setTotalGmOverallAchieve(totalGmOverallAchieve.toString());
        statusResponse.setTotalGmOverallTaskComp(totalGmOverallTaskComp.toString());
        kpiResponse.setResponseData(statusResponse);
        return kpiResponse;
    }
    private KPIResponse existingEmployeeKppFeedback(Map<OverallEmployeeKppFeedbackMasterDto, List<OverallEmployeeKppFeedbackDetailsDto>> employeeKppMasterDtoListMap) {
        KPIResponse kpiResponse = new KPIResponse();
        // List<EmpKppStatusResponse> empKppStatusResponses = new ArrayList<>();
        FreezeEmpKppStatusResponse statusResponse = null;
        List<Object[]> employeeKppData = null;
        Double totalEmpAchivedWeight = 0.0;
        Double totalEmpOverallAchieve = 0.0;
        Double totalEmpOverallTaskComp = 0.0;

        Double totalHodAchivedWeight = 0.0;
        Double totalHodOverallAchieve = 0.0;
        Double totalHodOverallTaskComp = 0.0;

        Double totalGmAchivedWeight = 0.0;
        Double totalGmOverallAchieve = 0.0;
        Double totalGmOverallTaskComp = 0.0;

        if (null!=employeeKppMasterDtoListMap) {

            for (Map.Entry<OverallEmployeeKppFeedbackMasterDto, List<OverallEmployeeKppFeedbackDetailsDto>> masterDtoListEntry : employeeKppMasterDtoListMap.entrySet()) {
                statusResponse = new FreezeEmpKppStatusResponse();
                statusResponse.setEKppMId(masterDtoListEntry.getKey().getEKppMId());
                statusResponse.setEmpEId(masterDtoListEntry.getKey().getEmpEId());

                statusResponse.setEKppMId(masterDtoListEntry.getKey().getEKppMId());
                statusResponse.setEkppMonth(masterDtoListEntry.getKey().getEkppMonth());
                statusResponse.setEmpId(masterDtoListEntry.getKey().getEmpId());
                statusResponse.setEmpName(masterDtoListEntry.getKey().getEmpName());
                statusResponse.setEmpEId(masterDtoListEntry.getKey().getEmpEId());
                statusResponse.setRoleId(masterDtoListEntry.getKey().getRoleId());
                statusResponse.setRoleName(masterDtoListEntry.getKey().getRoleName());
                statusResponse.setDeptId(masterDtoListEntry.getKey().getDeptId());
                statusResponse.setDeptName(masterDtoListEntry.getKey().getDeptName());
                statusResponse.setDesigId(masterDtoListEntry.getKey().getDesigId());
                statusResponse.setDesigName(masterDtoListEntry.getKey().getDesigName());

                totalEmpAchivedWeight += Double.parseDouble(masterDtoListEntry.getKey().getTotalEmpAchivedWeight());
                totalEmpOverallAchieve += Double.parseDouble(masterDtoListEntry.getKey().getTotalEmpOverallAchieve());
                totalEmpOverallTaskComp += Double.parseDouble(masterDtoListEntry.getKey().getTotalEmpOverallTaskComp());

                statusResponse.setTotalOverallRatings(masterDtoListEntry.getKey().getTotalOverallRatings());
                statusResponse.setTotalOverallPercentage(masterDtoListEntry.getKey().getTotalOverallPercentage());
                //statusResponse.setEmpKppAppliedDate(masterDtoListEntry.getKey().getEmpKppAppliedDate());
                statusResponse.setEmpKppStatus(masterDtoListEntry.getKey().getEmpKppStatus());
                statusResponse.setEmpRemark(masterDtoListEntry.getKey().getEmpRemark());
                statusResponse.setHodEmpId(masterDtoListEntry.getKey().getHodEmpId());

                totalHodAchivedWeight += Double.parseDouble(masterDtoListEntry.getKey().getTotalHodAchivedWeight());
                totalHodOverallAchieve += Double.parseDouble(masterDtoListEntry.getKey().getTotalHodOverallAchieve());

                totalHodOverallTaskComp += Double.parseDouble(masterDtoListEntry.getKey().getTotalHodOverallTaskComp());


                //  statusResponse.setHodKppAppliedDate(masterDtoListEntry.getKey().getHodKppAppliedDate());
                statusResponse.setHodKppStatus(masterDtoListEntry.getKey().getHodKppStatus());
                statusResponse.setHodRemark(masterDtoListEntry.getKey().getHodRemark());
                statusResponse.setGmEmpId(masterDtoListEntry.getKey().getGmEmpId());

                totalGmAchivedWeight += Double.parseDouble(masterDtoListEntry.getKey().getTotalGmAchivedWeight());
                totalGmOverallAchieve += Double.parseDouble(masterDtoListEntry.getKey().getTotalGmOverallAchieve());
                totalGmOverallTaskComp += Double.parseDouble(masterDtoListEntry.getKey().getTotalGmOverallTaskComp());

                // statusResponse.setGmKppAppliedDate(masterDtoListEntry.getKey().getGmKppAppliedDate());
                statusResponse.setGmKppStatus(masterDtoListEntry.getKey().getGmKppStatus());
                statusResponse.setGmRemark(masterDtoListEntry.getKey().getGmRemark());
                statusResponse.setRemark(masterDtoListEntry.getKey().getRemark());
                statusResponse.setCompanyId(masterDtoListEntry.getKey().getCompanyId());
                statusResponse.setCompanyName(masterDtoListEntry.getKey().getCompanyName());
                statusResponse.setCompanyAddress(masterDtoListEntry.getKey().getCompanyAddress());
                statusResponse.setCompanyMbNo(masterDtoListEntry.getKey().getCompanyMbNo());
                statusResponse.setCompanyFinYear(masterDtoListEntry.getKey().getCompanyFinYear());

                // statusResponse.setKppStatusDetails(masterDtoListEntry.getValue());

                List<OverallEmployeeKppFeedbackDetailsDto> employeeKppDetailsDtos = new ArrayList<>();

                employeeKppDetailsDtos = masterDtoListEntry.getValue().stream().collect(Collectors.groupingBy(OverallEmployeeKppFeedbackDetailsDto::getKppId,
                        Collectors.collectingAndThen(Collectors.toList(),
                                data -> {
                                    double empAchivedWeight = data.stream().mapToDouble(test -> Double.parseDouble(test.getEmpAchivedWeight())).sum();
                                    double empOverallAchieve = data.stream().mapToDouble(test -> Double.parseDouble(test.getEmpOverallAchieve())).sum();
                                    double empOverallTaskComp = data.stream().mapToDouble(test -> Double.parseDouble(test.getEmpOverallTaskComp())).sum();

                                    double hodAchivedWeight = data.stream().mapToDouble(test -> Double.parseDouble(test.getHodAchivedWeight())).sum();
                                    double hodOverallAchieve = data.stream().mapToDouble(test -> Double.parseDouble(test.getHodOverallAchieve())).sum();
                                    double hodOverallTaskComp = data.stream().mapToDouble(test -> Double.parseDouble(test.getHodOverallTaskComp())).sum();

                                    double gmAchivedWeight = data.stream().mapToDouble(test -> Double.parseDouble(test.getGmAchivedWeight())).sum();
                                    double gmOverallAchieve = data.stream().mapToDouble(test -> Double.parseDouble(test.getGmOverallAchieve())).sum();
                                    double gmOverallTaskComp = data.stream().mapToDouble(test -> Double.parseDouble(test.getGmOverallTaskComp())).sum();

                                    return new OverallEmployeeKppFeedbackDetailsDto(data.iterator().next().getEkppId(), data.iterator().next().getKppId(), String.valueOf(empAchivedWeight), String.valueOf(empOverallAchieve), String.valueOf(empOverallTaskComp), data.iterator().next().getHodEmpId(), String.valueOf(hodAchivedWeight), String.valueOf(hodOverallAchieve), String.valueOf(hodOverallTaskComp),
                                            data.iterator().next().getGmEmployeeId(), String.valueOf(gmAchivedWeight), String.valueOf(gmOverallAchieve), String.valueOf(gmOverallTaskComp), data.iterator().next().getOverallRatings(), data.iterator().next().getOverallPercentage(), data.iterator().next().getKppObjective(), data.iterator().next().getKppPerformanceIndi(), data.iterator().next().getKppOverallTarget(), data.iterator().next().getKppTargetPeriod(), data.iterator().next().getUomId(), data.iterator().next().getUomName(),
                                            data.iterator().next().getKppOverallWeightage(), data.iterator().next().getKppRating1(), data.iterator().next().getKppRating2(), data.iterator().next().getKppRating3(), data.iterator().next().getKppRating4(), data.iterator().next().getKppRating5(), data.iterator().next().getEkppStatus(),data.iterator().next().getEmpKppFeedback(),data.iterator().next().getHodKppFeedback(),data.iterator().next().getGmKppFeedback());
                                })
                )).values().stream().collect(Collectors.toList());

                statusResponse.setKppStatusDetails(employeeKppDetailsDtos);
            }
        } else {
            //  log.error("EmployeeKppStatusServiceImpl >> getEmployeeKppStatus()  ");
            //  throw new KPIException("DepartmentServiceImpl", false, "No record found");
            return null;
        }


        statusResponse.setTotalEmpAchivedWeight(totalEmpAchivedWeight.toString());
        statusResponse.setTotalEmpOverallAchieve(totalEmpOverallAchieve.toString());
        statusResponse.setTotalEmpOverallTaskComp(totalEmpOverallTaskComp.toString());

        statusResponse.setTotalHodAchivedWeight(totalHodAchivedWeight.toString());
        statusResponse.setTotalHodOverallAchieve(totalHodOverallAchieve.toString());
        statusResponse.setTotalHodOverallTaskComp(totalHodOverallTaskComp.toString());

        statusResponse.setTotalGmAchivedWeight(decfor.format(totalGmAchivedWeight));
        statusResponse.setTotalGmOverallAchieve(totalGmOverallAchieve.toString());
        statusResponse.setTotalGmOverallTaskComp(totalGmOverallTaskComp.toString());
        kpiResponse.setResponseData(statusResponse);
        return kpiResponse;
    }


}
