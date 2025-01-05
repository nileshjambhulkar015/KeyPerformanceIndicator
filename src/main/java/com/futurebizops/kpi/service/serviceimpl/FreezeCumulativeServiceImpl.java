package com.futurebizops.kpi.service.serviceimpl;

import com.futurebizops.kpi.constants.KPIConstants;
import com.futurebizops.kpi.entity.OverallEmployeeKppFeedbackDetailsEntity;
import com.futurebizops.kpi.entity.OverallEmployeeKppFeedbackMasterEntity;
import com.futurebizops.kpi.entity.ReportEmployeeKppDetailsEntity;
import com.futurebizops.kpi.entity.ReportEmployeeKppMasterEntity;
import com.futurebizops.kpi.exception.KPIException;
import com.futurebizops.kpi.repository.OverallEmployeeKppFeedbackDetailsRepo;
import com.futurebizops.kpi.repository.OverallEmployeeKppFeedbackMasterRepo;
import com.futurebizops.kpi.repository.ReportEmployeeKppDetailsRepo;
import com.futurebizops.kpi.repository.ReportEmployeeKppMasterRepo;
import com.futurebizops.kpi.request.CumulativeUpdateRequest;
import com.futurebizops.kpi.response.KPIResponse;
import com.futurebizops.kpi.service.FreezeCumulativeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class FreezeCumulativeServiceImpl implements FreezeCumulativeService {


    @Autowired
    ReportEmployeeKppMasterRepo reportEmployeeKppMasterRepo;

    @Autowired
    ReportEmployeeKppDetailsRepo reportEmployeeKppDetailsRepo;

    @Autowired
    OverallEmployeeKppFeedbackMasterRepo freezeReportEmployeeKppMasterRepo;

    @Autowired
    OverallEmployeeKppFeedbackDetailsRepo freezeReportEmployeeKppDetailsRepo;

    @Transactional
    @Override
    @Retryable(include = {KPIException.class}, maxAttemptsExpression = "${retry-max-attempts}")
    public KPIResponse saveFreezeCumulativeService(CumulativeUpdateRequest freezeCumulativeCreateRequest) {
        log.debug("Inside FreezeCumulativeServiceImpl >> saveFreezeCumulativeService() freezeCumulativeCreateRequest : {}", freezeCumulativeCreateRequest);
        List<OverallEmployeeKppFeedbackMasterEntity> freezeReportEmployeeKppMasterEntities = new ArrayList<>();
        List<OverallEmployeeKppFeedbackDetailsEntity> freezeReportEmployeeKppDetailsEntities = new ArrayList<>();
        try {
            //for report employee kpp master
            List<ReportEmployeeKppMasterEntity> reportEmployeeKppMasterEntity = reportEmployeeKppMasterRepo.findByEmpIdAndStatusCd(freezeCumulativeCreateRequest.getEmpId(), "A");

            if (reportEmployeeKppMasterEntity.size() > 0 && !CollectionUtils.isEmpty(reportEmployeeKppMasterEntity)) {
                reportEmployeeKppMasterEntity.forEach(employeeKppMasterEntity -> {
                            OverallEmployeeKppFeedbackMasterEntity freezeReportEmployeeKppMasterEntity = new OverallEmployeeKppFeedbackMasterEntity();
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
                freezeReportEmployeeKppMasterEntities.stream().forEach(data->{
                    freezeReportEmployeeKppMasterRepo.save(data);
                });
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
                    OverallEmployeeKppFeedbackDetailsEntity freezeReportEmployeeKppDetailsEntity = new OverallEmployeeKppFeedbackDetailsEntity();
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
                freezeReportEmployeeKppDetailsEntities.stream().forEach(data->{
                    freezeReportEmployeeKppDetailsRepo.save(data);
                });
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
}