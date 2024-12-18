package com.futurebizops.kpi.service.serviceimpl;

import com.futurebizops.kpi.constants.KPIConstants;
import com.futurebizops.kpi.dto.EmployeeMasterReportDTO;
import com.futurebizops.kpi.entity.FinancialYearEntity;
import com.futurebizops.kpi.entity.FreezeReportEmployeeKppDetailsEntity;
import com.futurebizops.kpi.entity.FreezeReportEmployeeKppMasterEntity;
import com.futurebizops.kpi.entity.ReportEmployeeKppDetailsEntity;
import com.futurebizops.kpi.entity.ReportEmployeeKppMasterEntity;
import com.futurebizops.kpi.exception.KPIException;
import com.futurebizops.kpi.repository.FreezeReportEmployeeKppDetailsRepo;
import com.futurebizops.kpi.repository.FreezeReportEmployeeKppMasterRepo;
import com.futurebizops.kpi.repository.ReportEmployeeKppDetailsRepo;
import com.futurebizops.kpi.repository.ReportEmployeeKppMasterRepo;
import com.futurebizops.kpi.request.CumulativeUpdateRequest;
import com.futurebizops.kpi.request.EmpKPPUpdateRequest;
import com.futurebizops.kpi.request.yearlykpprequest.FreezeEmpKPPDetailsRequest;
import com.futurebizops.kpi.request.yearlykpprequest.FreezeEmpKPPMasterRequest;
import com.futurebizops.kpi.response.DesignationReponse;
import com.futurebizops.kpi.response.KPIResponse;
import com.futurebizops.kpi.response.dropdown.FinancialYearDDResponse;
import com.futurebizops.kpi.response.dropdown.FreezeFinancialYearDDResponse;
import com.futurebizops.kpi.service.FreezeCumulativeService;
import com.futurebizops.kpi.utils.DateTimeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.persistence.Column;
import javax.transaction.Transactional;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FreezeCumulativeServiceImpl implements FreezeCumulativeService {


    @Autowired
    ReportEmployeeKppMasterRepo reportEmployeeKppMasterRepo;

    @Autowired
    ReportEmployeeKppDetailsRepo reportEmployeeKppDetailsRepo;

    @Autowired
    FreezeReportEmployeeKppMasterRepo freezeReportEmployeeKppMasterRepo;

    @Autowired
    FreezeReportEmployeeKppDetailsRepo freezeReportEmployeeKppDetailsRepo;

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
                List<FreezeReportEmployeeKppDetailsEntity> freezeReportEmployeeKppDetailsEntities = freezeReportEmployeeKppDetailsToEntities(freezeEmpKPPMasterRequest, ekppMonth);
                freezeReportEmployeeKppDetailsRepo.saveAll(freezeReportEmployeeKppDetailsEntities);

                FreezeReportEmployeeKppMasterEntity freezeReportEmployeeKppMasterEntity = freezeReportEmployeeKppMasterEntities(freezeEmpKPPMasterRequest, ekppMonth);
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

    private FreezeReportEmployeeKppMasterEntity freezeReportEmployeeKppMasterEntities(FreezeEmpKPPMasterRequest freezeEmpKPPMasterRequest, Instant ekppMonth) {
        FreezeReportEmployeeKppMasterEntity reportEmployeeKppMasterEntity = new FreezeReportEmployeeKppMasterEntity();
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
        reportEmployeeKppMasterEntity.setHodKppStatus(freezeEmpKPPMasterRequest.getHodKppStatus());
        reportEmployeeKppMasterEntity.setHodRemark(freezeEmpKPPMasterRequest.getHodRemark());

        reportEmployeeKppMasterEntity.setGmEmpId(freezeEmpKPPMasterRequest.getGmEmpId());
        reportEmployeeKppMasterEntity.setTotalGmOverallAchieve(freezeEmpKPPMasterRequest.getTotalGmOverallAchieve());
        reportEmployeeKppMasterEntity.setTotalGmAchivedWeight(freezeEmpKPPMasterRequest.getTotalGmAchivedWeight());
        reportEmployeeKppMasterEntity.setTotalGmOverallTaskComp(freezeEmpKPPMasterRequest.getTotalGmOverallTaskComp());
        reportEmployeeKppMasterEntity.setGmKppAppliedDate(freezeEmpKPPMasterRequest.getGmKppAppliedDate());
        reportEmployeeKppMasterEntity.setGmKppStatus(freezeEmpKPPMasterRequest.getGmKppStatus());
        reportEmployeeKppMasterEntity.setGmRemark(freezeEmpKPPMasterRequest.getGmRemark());

        reportEmployeeKppMasterEntity.setAvgTotalOverallRating(freezeEmpKPPMasterRequest.getAvgTotalOverallRating());
        reportEmployeeKppMasterEntity.setAvgTotalOverallPer(freezeEmpKPPMasterRequest.getAvgTotalOverallPer());
        reportEmployeeKppMasterEntity.setStatusCd(freezeEmpKPPMasterRequest.getStatusCd());


        return reportEmployeeKppMasterEntity;
    }

    private List<FreezeReportEmployeeKppDetailsEntity> freezeReportEmployeeKppDetailsToEntities(FreezeEmpKPPMasterRequest freezeEmpKPPMasterRequest, Instant ekppMonth) {
        List<FreezeReportEmployeeKppDetailsEntity> freezeReportEmployeeKppDetailsEntities = new ArrayList<>();

        for (FreezeEmpKPPDetailsRequest empKPPUpdateRequest : freezeEmpKPPMasterRequest.getKppUpdateRequests()) {
            FreezeReportEmployeeKppDetailsEntity reportEmployeeKppDetails = new FreezeReportEmployeeKppDetailsEntity();
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

    @Transactional
    @Override
    public KPIResponse saveFreezeCumulativeService(CumulativeUpdateRequest freezeCumulativeCreateRequest) {

        List<FreezeReportEmployeeKppMasterEntity> freezeReportEmployeeKppMasterEntities = new ArrayList<>();

        List<FreezeReportEmployeeKppDetailsEntity> freezeReportEmployeeKppDetailsEntities = new ArrayList<>();

        try {
            //for report employee kpp master
            List<ReportEmployeeKppMasterEntity> reportEmployeeKppMasterEntity = reportEmployeeKppMasterRepo.findByEmpIdAndStatusCd(freezeCumulativeCreateRequest.getEmpId(), "A");

            if (reportEmployeeKppMasterEntity.size() > 0 && !CollectionUtils.isEmpty(reportEmployeeKppMasterEntity)) {
                reportEmployeeKppMasterEntity.forEach(employeeKppMasterEntity -> {
                            FreezeReportEmployeeKppMasterEntity freezeReportEmployeeKppMasterEntity = new FreezeReportEmployeeKppMasterEntity();
                            freezeReportEmployeeKppMasterEntity.setFinYear(freezeCumulativeCreateRequest.getFinYear());
                            freezeReportEmployeeKppMasterEntity.setEmpKeyStrength(freezeCumulativeCreateRequest.getEmpKeyStrength());
                            freezeReportEmployeeKppMasterEntity.setEmpAreaOfImprovement(freezeCumulativeCreateRequest.getEmpAreaOfImprovement());
                            freezeReportEmployeeKppMasterEntity.setEmpTrainginDevelopmentNeeds(freezeCumulativeCreateRequest.getEmpTrainginDevelopmentNeeds());

                            freezeReportEmployeeKppMasterEntity.setEkppMonth(employeeKppMasterEntity.getEkppMonth());
                            freezeReportEmployeeKppMasterEntity.setEmpId(employeeKppMasterEntity.getEmpId());
                            freezeReportEmployeeKppMasterEntity.setEmpEId(employeeKppMasterEntity.getEmpEId());
                            freezeReportEmployeeKppMasterEntity.setRoleId(employeeKppMasterEntity.getRoleId());
                            freezeReportEmployeeKppMasterEntity.setDeptId(employeeKppMasterEntity.getDeptId());
                            freezeReportEmployeeKppMasterEntity.setDesigId(employeeKppMasterEntity.getDesigId());
                            freezeReportEmployeeKppMasterEntity.setTotalEmpOverallAchieve(employeeKppMasterEntity.getTotalAchivedWeight());
                            freezeReportEmployeeKppMasterEntity.setTotalEmpOverallAchieve(employeeKppMasterEntity.getTotalOverallAchieve());
                            freezeReportEmployeeKppMasterEntity.setTotalEmpOverallTaskComp(employeeKppMasterEntity.getTotalOverallTaskComp());
                            freezeReportEmployeeKppMasterEntity.setEmpKppAppliedDate(employeeKppMasterEntity.getEmpKppAppliedDate());
                            freezeReportEmployeeKppMasterEntity.setEmpKppStatus(employeeKppMasterEntity.getEmpKppStatus());
                            freezeReportEmployeeKppMasterEntity.setEmpEvidence(employeeKppMasterEntity.getEmpEvidence());
                            freezeReportEmployeeKppMasterEntity.setHodEmpId(employeeKppMasterEntity.getHodEmpId());
                            freezeReportEmployeeKppMasterEntity.setTotalHodAchivedWeight(employeeKppMasterEntity.getHodAchivedWeight());
                            freezeReportEmployeeKppMasterEntity.setTotalHodOverallAchieve(employeeKppMasterEntity.getHodOverallAchieve());
                            freezeReportEmployeeKppMasterEntity.setTotalHodOverallTaskComp(employeeKppMasterEntity.getHodOverallTaskComp());
                            freezeReportEmployeeKppMasterEntity.setHodKppAppliedDate(employeeKppMasterEntity.getHodKppAppliedDate());
                            freezeReportEmployeeKppMasterEntity.setHodKppStatus(employeeKppMasterEntity.getHodKppStatus());
                            freezeReportEmployeeKppMasterEntity.setHodRemark(employeeKppMasterEntity.getHodRemark());
                            freezeReportEmployeeKppMasterEntity.setGmEmpId(employeeKppMasterEntity.getGmEmpId());
                            freezeReportEmployeeKppMasterEntity.setTotalGmAchivedWeight(employeeKppMasterEntity.getGmAchivedWeight());
                            freezeReportEmployeeKppMasterEntity.setTotalGmAchivedWeight(employeeKppMasterEntity.getGmOverallAchieve());
                            freezeReportEmployeeKppMasterEntity.setTotalGmOverallTaskComp(employeeKppMasterEntity.getGmOverallTaskComp());
                            freezeReportEmployeeKppMasterEntity.setGmKppAppliedDate(employeeKppMasterEntity.getGmKppAppliedDate());
                            freezeReportEmployeeKppMasterEntity.setGmKppStatus(employeeKppMasterEntity.getGmKppStatus());
                            freezeReportEmployeeKppMasterEntity.setGmRemark(employeeKppMasterEntity.getGmRemark());
                            freezeReportEmployeeKppMasterEntity.setAvgTotalOverallRating(employeeKppMasterEntity.getAvgTotalOverallRating());
                            freezeReportEmployeeKppMasterEntity.setAvgTotalOverallPer(employeeKppMasterEntity.getAvgTotalOverallPer());
                            freezeReportEmployeeKppMasterEntity.setStatusCd(employeeKppMasterEntity.getStatusCd());

                            freezeReportEmployeeKppMasterEntities.add(freezeReportEmployeeKppMasterEntity);

                        }

                );
                freezeReportEmployeeKppMasterRepo.saveAll(freezeReportEmployeeKppMasterEntities);
            }


        } catch (Exception ex) {
            log.error("Inside FreezeCumulativeServiceImpl >> saveFreezeCumulativeService() : {}", ex);
            throw new KPIException("FreezeCumulativeServiceImpl >> saveFreezeCumulativeService()", false, ex.getMessage());
        }

        try {
            //for report employee kpp details
            List<ReportEmployeeKppDetailsEntity> reportEmployeeKppDetailsEntities = reportEmployeeKppDetailsRepo.findByEmpIdAndStatusCd(freezeCumulativeCreateRequest.getEmpId(), "A");
            if (reportEmployeeKppDetailsEntities.size() > 0 && !CollectionUtils.isEmpty(reportEmployeeKppDetailsEntities)) {
                reportEmployeeKppDetailsEntities.forEach(employeeKppDetails -> {
                    FreezeReportEmployeeKppDetailsEntity freezeReportEmployeeKppDetailsEntity = new FreezeReportEmployeeKppDetailsEntity();

                    freezeReportEmployeeKppDetailsEntity.setEkppMonth(employeeKppDetails.getEkppMonth());

                    freezeReportEmployeeKppDetailsEntity.setKppId(employeeKppDetails.getKppId());
                    freezeReportEmployeeKppDetailsEntity.setEmpId(employeeKppDetails.getEmpId());
                    freezeReportEmployeeKppDetailsEntity.setEmpEId(employeeKppDetails.getEmpEId());
                    freezeReportEmployeeKppDetailsEntity.setRoleId(employeeKppDetails.getRoleId());
                    freezeReportEmployeeKppDetailsEntity.setDeptId(employeeKppDetails.getDeptId());
                    freezeReportEmployeeKppDetailsEntity.setDesigId(employeeKppDetails.getDesigId());

                    freezeReportEmployeeKppDetailsEntity.setKppOverallTarget(employeeKppDetails.getKppOverallTarget());
                    freezeReportEmployeeKppDetailsEntity.setKppOverallWeightage(employeeKppDetails.getKppOverallWeightage());
                    freezeReportEmployeeKppDetailsEntity.setEmpAchivedWeight(employeeKppDetails.getEmpAchivedWeight());
                    freezeReportEmployeeKppDetailsEntity.setEmpOverallAchieve(employeeKppDetails.getEmpOverallAchieve());
                    freezeReportEmployeeKppDetailsEntity.setHodEmpId(employeeKppDetails.getHodEmpId());

                    freezeReportEmployeeKppDetailsEntity.setHodAchivedWeight(employeeKppDetails.getHodAchivedWeight());
                    freezeReportEmployeeKppDetailsEntity.setHodOverallAchieve(employeeKppDetails.getHodOverallAchieve());
                    freezeReportEmployeeKppDetailsEntity.setHodOverallTaskComp(employeeKppDetails.getHodOverallTaskComp());
                    freezeReportEmployeeKppDetailsEntity.setGmEmpId(employeeKppDetails.getGmEmpId());
                    freezeReportEmployeeKppDetailsEntity.setGmAchivedWeight(employeeKppDetails.getGmAchivedWeight());
                    freezeReportEmployeeKppDetailsEntity.setGmOverallAchieve(employeeKppDetails.getGmOverallAchieve());

                    freezeReportEmployeeKppDetailsEntity.setGmOverallTaskComp(employeeKppDetails.getGmOverallTaskComp());
                    freezeReportEmployeeKppDetailsEntity.setAvgOverallRating(employeeKppDetails.getAvgOverallRating());
                    freezeReportEmployeeKppDetailsEntity.setAvgOverallPer(employeeKppDetails.getAvgOverallPer());
                    freezeReportEmployeeKppDetailsEntity.setStatusCd(employeeKppDetails.getStatusCd());

                    freezeReportEmployeeKppDetailsEntities.add(freezeReportEmployeeKppDetailsEntity);
                });
                freezeReportEmployeeKppDetailsRepo.saveAll(freezeReportEmployeeKppDetailsEntities);

                return KPIResponse.builder()
                        .isSuccess(true)
                        .responseMessage(KPIConstants.RECORD_SUCCESS)
                        .build();

            }
        } catch (Exception ex) {
            log.error("Inside FreezeCumulativeServiceImpl >> saveFreezeCumulativeService() : {}", ex);
            throw new KPIException("FreezeCumulativeServiceImpl >> saveFreezeCumulativeService()", false, ex.getMessage());
        }
        log.error("KPP not tranfer to freeze table");
        return KPIResponse.builder()
                .isSuccess(false)
                .responseMessage("record not tranfered")
                .build();

    }

    @Override
    public List<FreezeFinancialYearDDResponse> ddAllFinancialYear() {
        List<Object[]> financialYearData = freezeReportEmployeeKppMasterRepo.ddAllFinancialYear();
        List<FreezeFinancialYearDDResponse> financialYearDDResponses = new ArrayList<>();
        if (financialYearData.size() > 0) {
            financialYearDDResponses = financialYearData.stream().map(FreezeFinancialYearDDResponse::new).collect(Collectors.toList());
        }
        return financialYearDDResponses;
    }
}
