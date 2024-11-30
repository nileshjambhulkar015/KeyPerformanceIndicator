package com.futurebizops.kpi.service.serviceimpl;

import com.futurebizops.kpi.constants.KPIConstants;
import com.futurebizops.kpi.entity.EmployeeTypeAudit;
import com.futurebizops.kpi.entity.EmployeeTypeEntity;
import com.futurebizops.kpi.entity.FreezeReportEmployeeKppDetailsEntity;
import com.futurebizops.kpi.entity.FreezeReportEmployeeKppMasterEntity;
import com.futurebizops.kpi.entity.ReportEmployeeKppDetailsEntity;
import com.futurebizops.kpi.entity.ReportEmployeeKppMasterEntity;
import com.futurebizops.kpi.exception.KPIException;
import com.futurebizops.kpi.repository.EmployeeKppMasterRepo;
import com.futurebizops.kpi.repository.FreezeReportEmployeeKppDetailsRepo;
import com.futurebizops.kpi.repository.FreezeReportEmployeeKppMasterRepo;
import com.futurebizops.kpi.repository.KeyPerfParameterRepo;
import com.futurebizops.kpi.repository.ReportEmployeeKppDetailsRepo;
import com.futurebizops.kpi.repository.ReportEmployeeKppMasterRepo;
import com.futurebizops.kpi.request.EmployeeTypeCreateRequest;
import com.futurebizops.kpi.request.FreezeCumulativeCreateRequest;
import com.futurebizops.kpi.response.CummalitiveEmployeeResponse;
import com.futurebizops.kpi.response.EmployeeKppStatusResponse;
import com.futurebizops.kpi.response.KPIResponse;
import com.futurebizops.kpi.response.cumulative.CumulativeHoDResponse;
import com.futurebizops.kpi.response.cumulative.HODCumulativeData;
import com.futurebizops.kpi.response.cumulative.HoDCumulativeResponse;
import com.futurebizops.kpi.response.cumulative.TotalCumulativeHoD;
import com.futurebizops.kpi.service.CumulativeService;
import com.futurebizops.kpi.service.FreezeCumulativeService;
import com.futurebizops.kpi.utils.DateTimeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.persistence.Column;
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
    public KPIResponse saveFreezeCumulativeService(FreezeCumulativeCreateRequest freezeCumulativeCreateRequest) {

        List<FreezeReportEmployeeKppMasterEntity> freezeReportEmployeeKppMasterEntities= new ArrayList<>();

        List<FreezeReportEmployeeKppDetailsEntity> freezeReportEmployeeKppDetailsEntities= new ArrayList<>();

        try{
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
                freezeReportEmployeeKppMasterEntity.setTotalAchivedWeight(employeeKppMasterEntity.getTotalAchivedWeight());
                freezeReportEmployeeKppMasterEntity.setTotalOverallAchieve(employeeKppMasterEntity.getTotalOverallAchieve());
                freezeReportEmployeeKppMasterEntity.setTotalOverallTaskComp(employeeKppMasterEntity.getTotalOverallTaskComp());
                freezeReportEmployeeKppMasterEntity.setEmpKppAppliedDate(employeeKppMasterEntity.getEmpKppAppliedDate());
                freezeReportEmployeeKppMasterEntity.setEmpKppStatus(employeeKppMasterEntity.getEmpKppStatus());
                freezeReportEmployeeKppMasterEntity.setEmpEvidence(employeeKppMasterEntity.getEmpEvidence());
                freezeReportEmployeeKppMasterEntity.setHodEmpId(employeeKppMasterEntity.getHodEmpId());
                freezeReportEmployeeKppMasterEntity.setHodAchivedWeight(employeeKppMasterEntity.getHodAchivedWeight());
                freezeReportEmployeeKppMasterEntity.setHodOverallAchieve(employeeKppMasterEntity.getHodOverallAchieve());
                freezeReportEmployeeKppMasterEntity.setHodOverallTaskComp(employeeKppMasterEntity.getHodOverallTaskComp());
                freezeReportEmployeeKppMasterEntity.setHodKppAppliedDate(employeeKppMasterEntity.getHodKppAppliedDate());
                freezeReportEmployeeKppMasterEntity.setHodKppStatus(employeeKppMasterEntity.getHodKppStatus());
                freezeReportEmployeeKppMasterEntity.setHodRemark(employeeKppMasterEntity.getHodRemark());
                freezeReportEmployeeKppMasterEntity.setGmEmpId(employeeKppMasterEntity.getGmEmpId());
                freezeReportEmployeeKppMasterEntity.setGmAchivedWeight(employeeKppMasterEntity.getGmAchivedWeight());
                freezeReportEmployeeKppMasterEntity.setGmOverallAchieve(employeeKppMasterEntity.getGmOverallAchieve());
                freezeReportEmployeeKppMasterEntity.setGmOverallTaskComp(employeeKppMasterEntity.getGmOverallTaskComp());
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


        } catch (Exception ex){
            log.error("Inside FreezeCumulativeServiceImpl >> saveFreezeCumulativeService() : {}",ex);
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

        } } catch(Exception ex){
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
