package com.futurebizops.kpi.service.serviceimpl;

import com.futurebizops.kpi.constants.KPIConstants;
import com.futurebizops.kpi.dto.EmployeeKppDetailsDto;
import com.futurebizops.kpi.dto.EmployeeKppMasterDto;
import com.futurebizops.kpi.dto.EmployeeKppStatusDto;
import com.futurebizops.kpi.entity.EmployeeKppDetailsAudit;
import com.futurebizops.kpi.entity.EmployeeKppDetailsEntity;
import com.futurebizops.kpi.entity.EmployeeKppMasterEntity;
import com.futurebizops.kpi.entity.KeyPerfParamEntity;
import com.futurebizops.kpi.entity.ReportEmployeeKppDetailsEntity;
import com.futurebizops.kpi.entity.ReportEmployeeKppMasterEntity;
import com.futurebizops.kpi.exception.KPIException;
import com.futurebizops.kpi.repository.EmployeeKppDetailsAuditRepo;
import com.futurebizops.kpi.repository.EmployeeKppDetailsRepo;
import com.futurebizops.kpi.repository.EmployeeKppMasterAuditRepo;
import com.futurebizops.kpi.repository.EmployeeKppMasterRepo;
import com.futurebizops.kpi.repository.KeyPerfParameterRepo;
import com.futurebizops.kpi.repository.ReportEmployeeKppDetailsRepo;
import com.futurebizops.kpi.repository.ReportEmployeeKppMasterRepo;
import com.futurebizops.kpi.request.EmpKPPMasterUpdateRequest;
import com.futurebizops.kpi.request.EmpKPPUpdateRequest;
import com.futurebizops.kpi.request.EmployeeKeyPerfParamCreateRequest;
import com.futurebizops.kpi.response.EmpKppStatusResponse;
import com.futurebizops.kpi.response.HodEmploeeKppResponse;
import com.futurebizops.kpi.response.KPIResponse;
import com.futurebizops.kpi.response.KPPResponse;
import com.futurebizops.kpi.service.EmployeeKeyPerfParamService;
import com.futurebizops.kpi.utils.DateTimeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class EmployeeKeyPerfParamServiceImpl implements EmployeeKeyPerfParamService {


    @Autowired
    private EmployeeKppDetailsAuditRepo keyPerfParamAuditRepo;

    @Autowired
    private KeyPerfParameterRepo keyPerfParameterRepo;

    @Autowired
    EmployeeKppMasterAuditRepo employeeKeyPerfParamMasterAuditRepo;

    @Autowired
    EmployeeKppMasterRepo employeeKppMasterRepo;

    @Autowired
    EmployeeKppDetailsRepo employeeKppDetailsRepo;

    @Autowired
    ReportEmployeeKppMasterRepo reportKppMasterRepo;

    @Autowired
    ReportEmployeeKppDetailsRepo reportEmployeeKppDetailsRepo;

    @Override
    public KPIResponse saveEmployeeKeyPerfParamDetails(EmployeeKeyPerfParamCreateRequest keyPerfParamCreateRequest) {
        EmployeeKppDetailsEntity keyPerfParamEntity = convertEmployeeKPPCreateRequestToEntity(keyPerfParamCreateRequest);
        try {
            employeeKppDetailsRepo.save(keyPerfParamEntity);
            EmployeeKppDetailsAudit partAudit = new EmployeeKppDetailsAudit(keyPerfParamEntity);
            keyPerfParamAuditRepo.save(partAudit);
            return KPIResponse.builder()
                    .isSuccess(true)
                    .responseMessage(KPIConstants.RECORD_SUCCESS)
                    .build();
        } catch (Exception ex) {
            log.error("Inside DepartmentServiceImpl >> saveDepartment()");
            throw new KPIException("DepartmentServiceImpl", false, ex.getMessage());
        }
    }

    @Transactional
    @Override
    public KPIResponse updateEmployeeKeyPerfParamDetails(EmpKPPMasterUpdateRequest empKPPMasterUpdateRequest) {
        Instant ekppMonth = StringUtils.isNotEmpty(empKPPMasterUpdateRequest.getKppUpdateRequests().get(0).getEkppMonth()) ? DateTimeUtils.convertStringToInstant(empKPPMasterUpdateRequest.getKppUpdateRequests().get(0).getEkppMonth()) : Instant.now();
        try {
            for (EmpKPPUpdateRequest paramUpdateRequest : empKPPMasterUpdateRequest.getKppUpdateRequests()) {

                employeeKppDetailsRepo.updateEmployeeKppDetails(paramUpdateRequest.getEmpId(), ekppMonth, paramUpdateRequest.getEkppAchivedWeight(), paramUpdateRequest.getEkppOverallAchieve(), paramUpdateRequest.getEkppOverallTaskComp(), paramUpdateRequest.getKppId(), paramUpdateRequest.getEmpEId(), paramUpdateRequest.getRoleId(), paramUpdateRequest.getDeptId(), paramUpdateRequest.getDesigId());
            }
            employeeKppMasterRepo.updateEmployeeKppMaster(empKPPMasterUpdateRequest.getKppUpdateRequests().get(0).getEmpId(), ekppMonth, empKPPMasterUpdateRequest.getTotalAchivedWeightage(), empKPPMasterUpdateRequest.getTotalOverAllAchive(), empKPPMasterUpdateRequest.getTotalOverallTaskCompleted(), Instant.now(), empKPPMasterUpdateRequest.getEkppStatus(), empKPPMasterUpdateRequest.getRemark(), empKPPMasterUpdateRequest.getEvidence(), empKPPMasterUpdateRequest.getKppUpdateRequests().get(0).getEmpEId(), empKPPMasterUpdateRequest.getKppUpdateRequests().get(0).getRoleId(), empKPPMasterUpdateRequest.getKppUpdateRequests().get(0).getDeptId(), empKPPMasterUpdateRequest.getKppUpdateRequests().get(0).getDesigId());

            return KPIResponse.builder()
                    .isSuccess(true)
                    .responseMessage(KPIConstants.RECORD_SUCCESS)
                    .build();
        } catch (Exception ex) {
            log.error("Inside EmployeeKeyPerfParamServiceImpl >> updateEmployeeKeyPerfParamDetails()");
            throw new KPIException("EmployeeKeyPerfParamServiceImpl Class", false, ex.getMessage());
        }
    }

    //when employee fill kpp then coming for hod approval
    @Override
    @Transactional
    public KPIResponse updateHoDApprovalRequest(EmpKPPMasterUpdateRequest empKPPMasterUpdateRequest) {
        String empKppStatus = "In-Progress";
        try {
            for (EmpKPPUpdateRequest paramUpdateRequest : empKPPMasterUpdateRequest.getKppUpdateRequests()) {

                //employeeKeyPerfParamDetailsRepo.updateEmpApproveOrRejectHod(paramUpdateRequest.getEkppAchivedWeight(), paramUpdateRequest.getEkppOverallAchieve(), paramUpdateRequest.getEkppOverallTaskComp(), paramUpdateRequest.getKppId(), paramUpdateRequest.getEmpEId(), paramUpdateRequest.getRoleId(), paramUpdateRequest.getDeptId(), paramUpdateRequest.getDesigId());
                employeeKppDetailsRepo.updateEmpApproveOrRejectHod(paramUpdateRequest.getEkppAchivedWeight(), paramUpdateRequest.getEkppOverallAchieve(), paramUpdateRequest.getEkppOverallTaskComp(), paramUpdateRequest.getKppId(), paramUpdateRequest.getEmpId());
            }
            if ("Approved".equalsIgnoreCase(empKPPMasterUpdateRequest.getEkppStatus())) {
                empKppStatus = "Approved";
            } else if ("Reject".equalsIgnoreCase(empKPPMasterUpdateRequest.getEkppStatus())) {
                empKppStatus = "Reject";
            }
            employeeKppMasterRepo.updateEmpKppApproveOrRejectByHod(empKppStatus, empKPPMasterUpdateRequest.getTotalAchivedWeightage(), empKPPMasterUpdateRequest.getTotalOverAllAchive(), empKPPMasterUpdateRequest.getTotalOverallTaskCompleted(), Instant.now(), empKPPMasterUpdateRequest.getEkppStatus(), empKPPMasterUpdateRequest.getRemark(), empKPPMasterUpdateRequest.getKppUpdateRequests().get(0).getEmpId());

            return KPIResponse.builder()
                    .isSuccess(true)
                    .responseMessage(KPIConstants.RECORD_SUCCESS)
                    .build();
        } catch (Exception ex) {
            log.error("Inside EmployeeKeyPerfParamServiceImpl >> updateEmployeeKeyPerfParamDetails()");
            throw new KPIException("EmployeeKeyPerfParamServiceImpl Class", false, ex.getMessage());
        }
    }

    @Override
    public KPIResponse updateGMApprovalRequest(EmpKPPMasterUpdateRequest empKPPMasterUpdateRequest) {
        log.info("Request comming");
        String empKppStatus = "In-Progress";
        try {
            for (EmpKPPUpdateRequest paramUpdateRequest : empKPPMasterUpdateRequest.getKppUpdateRequests()) {
                employeeKppDetailsRepo.updateGMApproveOrRejectHod(paramUpdateRequest.getEkppAchivedWeight(), paramUpdateRequest.getEkppOverallAchieve(), paramUpdateRequest.getEkppOverallTaskComp(), paramUpdateRequest.getKppId(), paramUpdateRequest.getEmpId());
            }
            if ("Approved".equalsIgnoreCase(empKPPMasterUpdateRequest.getEkppStatus())) {
                empKppStatus = "Approved";
            } else if ("Reject".equalsIgnoreCase(empKPPMasterUpdateRequest.getEkppStatus())) {
                empKppStatus = "Reject";
            }
            employeeKppMasterRepo.updateGMKppApproveOrRejectByHod(empKppStatus, empKPPMasterUpdateRequest.getTotalAchivedWeightage(), empKPPMasterUpdateRequest.getTotalOverAllAchive(), empKPPMasterUpdateRequest.getTotalOverallTaskCompleted(), Instant.now(), empKPPMasterUpdateRequest.getEkppStatus(), empKPPMasterUpdateRequest.getRemark(), empKPPMasterUpdateRequest.getKppUpdateRequests().get(0).getEmpId());

            return KPIResponse.builder()
                    .isSuccess(true)
                    .responseMessage(KPIConstants.RECORD_SUCCESS)
                    .build();
        } catch (Exception ex) {
            log.error("Inside EmployeeKeyPerfParamServiceImpl >> updateEmployeeKeyPerfParamDetails()");
            throw new KPIException("EmployeeKeyPerfParamServiceImpl Class", false, ex.getMessage());
        }
    }

    @Override
    public List<KPPResponse> getKeyPerfomanceParameter(Integer roleId, Integer deptId, Integer desigId, String statusCd) {
        try {
            List<KeyPerfParamEntity> keyPerfParamEntities = keyPerfParameterRepo.findByRoleIdAndDeptIdAndDesigIdAndStatusCd(roleId, deptId, desigId, statusCd);
            return convertEntityListToResponse(keyPerfParamEntities);
        } catch (Exception ex) {
            log.error("Inside EmployeeKeyPerfParamServiceImpl >> updateGMApprovalRequest()");
            throw new KPIException("EmployeeKeyPerfParamServiceImpl", false, ex.getMessage());
        }
    }

    @Override
    public List<HodEmploeeKppResponse> getEmployeeForHodRatings(Integer empId, String empEId, String statusCd) {
        List<Object[]> employeeKppData = keyPerfParameterRepo.getEmployeeKeyPerfParameterDetail(empId, empEId, statusCd);
        List<HodEmploeeKppResponse> hodEmployeeResponses = employeeKppData.stream().map(HodEmploeeKppResponse::new).collect(Collectors.toList());
        hodEmployeeResponses = hodEmployeeResponses.stream()
                .sorted(Comparator.comparing(HodEmploeeKppResponse::getKppObjective))
                .collect(Collectors.toList());
        return hodEmployeeResponses;
    }

    @Override
    public List<EmpKppStatusResponse> getEmployeeKppStatus(Integer empId) {
        List<EmpKppStatusResponse> empKppStatusResponses = new ArrayList<>();
        List<Object[]> employeeKppData = keyPerfParameterRepo.getEmployeeKPPStatus(empId);
        List<EmployeeKppStatusDto> employeeKppStatusDtos = employeeKppData.stream().map(EmployeeKppStatusDto::new).collect(Collectors.toList());

        Map<EmployeeKppMasterDto, List<EmployeeKppDetailsDto>> employeeKppMasterDtoListMap =
                employeeKppStatusDtos.stream().collect(Collectors.groupingBy(EmployeeKppStatusDto::getEmployeeKppMasterDto, Collectors.mapping(EmployeeKppStatusDto::getEmployeeKppDetailsDto, Collectors.toList())));

        for (Map.Entry<EmployeeKppMasterDto, List<EmployeeKppDetailsDto>> masterDtoListEntry : employeeKppMasterDtoListMap.entrySet()) {
            EmpKppStatusResponse statusResponse = new EmpKppStatusResponse();
            statusResponse.setEKppMId(masterDtoListEntry.getKey().getEKppMId());
            statusResponse.setEmpEId(masterDtoListEntry.getKey().getEmpEId());

            statusResponse.setEKppMId(masterDtoListEntry.getKey().getEKppMId());
            //  statusResponse.setEkppMonth(masterDtoListEntry.getKey().getEkppMonth());
            statusResponse.setEmpId(masterDtoListEntry.getKey().getEmpId());
            statusResponse.setEmpName(masterDtoListEntry.getKey().getEmpName());
            statusResponse.setEmpEId(masterDtoListEntry.getKey().getEmpEId());
            statusResponse.setRoleId(masterDtoListEntry.getKey().getRoleId());
            statusResponse.setRoleName(masterDtoListEntry.getKey().getRoleName());
            statusResponse.setDeptId(masterDtoListEntry.getKey().getDeptId());
            statusResponse.setDeptName(masterDtoListEntry.getKey().getDeptName());
            statusResponse.setDesigId(masterDtoListEntry.getKey().getDesigId());
            statusResponse.setDesigName(masterDtoListEntry.getKey().getDesigName());
            statusResponse.setTotalAchivedWeight(masterDtoListEntry.getKey().getTotalAchivedWeight());
            statusResponse.setTotalOverallAchieve(masterDtoListEntry.getKey().getTotalOverallAchieve());
            statusResponse.setTotalOverallTaskComp(masterDtoListEntry.getKey().getTotalOverallTaskComp());
            //statusResponse.setEmpKppAppliedDate(masterDtoListEntry.getKey().getEmpKppAppliedDate());
            statusResponse.setEmpKppStatus(masterDtoListEntry.getKey().getEmpKppStatus());
            statusResponse.setEmpRemark(masterDtoListEntry.getKey().getEmpRemark());
            statusResponse.setHodEmpId(masterDtoListEntry.getKey().getHodEmpId());
            statusResponse.setTotalHodAchivedWeight(masterDtoListEntry.getKey().getTotalHodAchivedWeight());
            statusResponse.setTotalHodOverallAchieve(masterDtoListEntry.getKey().getTotalHodOverallAchieve());
            statusResponse.setTotalHodOverallTaskComp(masterDtoListEntry.getKey().getTotalHodOverallTaskComp());
            //  statusResponse.setHodKppAppliedDate(masterDtoListEntry.getKey().getHodKppAppliedDate());
            statusResponse.setHodKppStatus(masterDtoListEntry.getKey().getHodKppStatus());
            statusResponse.setHodRemark(masterDtoListEntry.getKey().getHodRemark());
            statusResponse.setGmEmpId(masterDtoListEntry.getKey().getGmEmpId());
            statusResponse.setTotalGmAchivedWeight(masterDtoListEntry.getKey().getTotalGmAchivedWeight());
            statusResponse.setTotalGmOverallAchieve(masterDtoListEntry.getKey().getTotalGmOverallAchieve());
            statusResponse.setTotalGmOverallTaskComp(masterDtoListEntry.getKey().getTotalGmOverallTaskComp());
            // statusResponse.setGmKppAppliedDate(masterDtoListEntry.getKey().getGmKppAppliedDate());
            statusResponse.setGmKppStatus(masterDtoListEntry.getKey().getGmKppStatus());
            statusResponse.setGmRemark(masterDtoListEntry.getKey().getGmRemark());
            statusResponse.setRemark(masterDtoListEntry.getKey().getRemark());

            statusResponse.setKppStatusDetails(masterDtoListEntry.getValue());
            empKppStatusResponses.add(statusResponse);
        }
        //hodEmployeeResponses = hodEmployeeResponses.stream()
        //      .sorted(Comparator.comparing(EmployeeKppStatusDto::getEKppMId))
        //    .collect(Collectors.toList());
        return empKppStatusResponses;
    }


    @Transactional
    @Override
    public KPIResponse generateEmployeeKppReport(Integer empId, String statusCd) {
        Optional<EmployeeKppMasterEntity> employeeKppMasterEntity = employeeKppMasterRepo.findByEmpIdAndStatusCd(empId, statusCd);
        ReportEmployeeKppMasterEntity kppMaster = new ReportEmployeeKppMasterEntity();

        if (employeeKppMasterEntity.isPresent()) {
            EmployeeKppMasterEntity kppMasterEntity = employeeKppMasterEntity.get();
            kppMaster.setEkppMonth(kppMasterEntity.getEkppMonth());
            kppMaster.setEmpId(kppMasterEntity.getEmpId());
            kppMaster.setEmpEId(kppMasterEntity.getEmpEId());
            kppMaster.setRoleId(kppMasterEntity.getRoleId());
            kppMaster.setDeptId(kppMasterEntity.getRoleId());
            kppMaster.setDesigId(kppMasterEntity.getDesigId());
            kppMaster.setTotalAchivedWeight(kppMasterEntity.getTotalAchivedWeight());
            kppMaster.setTotalOverallAchieve(kppMasterEntity.getTotalOverallAchieve());
            kppMaster.setTotalOverallTaskComp(kppMasterEntity.getTotalOverallTaskComp());
            kppMaster.setEmpKppAppliedDate(kppMasterEntity.getEmpKppAppliedDate());
            kppMaster.setEmpKppStatus(kppMasterEntity.getEmpKppStatus());
            kppMaster.setEmpRemark(kppMasterEntity.getEmpRemark());
            kppMaster.setEmpEvidence(kppMasterEntity.getEmpEvidence());
            kppMaster.setHodEmpId(kppMasterEntity.getHodEmpId());
            kppMaster.setHodAchivedWeight(kppMasterEntity.getHodAchivedWeight());
            kppMaster.setHodOverallAchieve(kppMasterEntity.getHodOverallAchieve());
            kppMaster.setHodOverallTaskComp(kppMasterEntity.getHodOverallTaskComp());
            kppMaster.setHodKppAppliedDate(kppMasterEntity.getHodKppAppliedDate());
            kppMaster.setHodKppStatus(kppMasterEntity.getHodKppStatus());
            kppMaster.setHodRemark(kppMasterEntity.getHodRemark());
            kppMaster.setGmEmpId(kppMasterEntity.getGmEmpId());
            kppMaster.setGmAchivedWeight(kppMasterEntity.getGmAchivedWeight());
            kppMaster.setGmOverallAchieve(kppMasterEntity.getGmOverallAchieve());
            kppMaster.setGmOverallTaskComp(kppMasterEntity.getGmOverallTaskComp());
            kppMaster.setGmKppAppliedDate(kppMasterEntity.getGmKppAppliedDate());
            kppMaster.setGmKppStatus(kppMasterEntity.getGmKppStatus());
            kppMaster.setGmRemark(kppMasterEntity.getGmRemark());
            kppMaster.setRemark(kppMasterEntity.getRemark());
            kppMaster.setStatusCd(kppMasterEntity.getStatusCd());

            reportKppMasterRepo.save(kppMaster);
            employeeKppMasterRepo.resetEmployeeKppByGM(empId, statusCd);
        }

        List<EmployeeKppDetailsEntity> employeeKppDetailsEntities = employeeKppDetailsRepo.findByEmpIdAndStatusCd(empId, statusCd);
        List<ReportEmployeeKppDetailsEntity> kppDetailsEntities = new ArrayList<>();

        ReportEmployeeKppDetailsEntity detailsEntity = null;
        if (employeeKppDetailsEntities.size() > 0) {
            for (EmployeeKppDetailsEntity employeeKppDetailsEntity : employeeKppDetailsEntities) {
                detailsEntity = new ReportEmployeeKppDetailsEntity();

                detailsEntity.setEkppMonth(employeeKppDetailsEntity.getEkppMonth());
                detailsEntity.setEmpId(employeeKppDetailsEntity.getEmpId());
                detailsEntity.setEmpEId(employeeKppDetailsEntity.getEmpEId());
                detailsEntity.setRoleId(employeeKppDetailsEntity.getRoleId());
                detailsEntity.setDeptId(employeeKppDetailsEntity.getRoleId());
                detailsEntity.setDesigId(employeeKppDetailsEntity.getDesigId());
                detailsEntity.setKppId(employeeKppDetailsEntity.getKppId());
                detailsEntity.setEmpAchivedWeight(employeeKppDetailsEntity.getEmpAchivedWeight());
                detailsEntity.setEmpOverallAchieve(employeeKppDetailsEntity.getEmpOverallAchieve());
                detailsEntity.setEmpOverallTaskComp(employeeKppDetailsEntity.getEmpOverallTaskComp());
                detailsEntity.setHodEmpId(employeeKppDetailsEntity.getHodEmpId());
                detailsEntity.setHodAchivedWeight(employeeKppDetailsEntity.getHodAchivedWeight());
                detailsEntity.setHodOverallAchieve(employeeKppDetailsEntity.getHodOverallAchieve());
                detailsEntity.setHodOverallTaskComp(employeeKppDetailsEntity.getHodOverallTaskComp());
                detailsEntity.setGmEmpId(employeeKppDetailsEntity.getGmEmpId());
                detailsEntity.setGmAchivedWeight(employeeKppDetailsEntity.getGmAchivedWeight());
                detailsEntity.setGmOverallAchieve(employeeKppDetailsEntity.getGmOverallAchieve());
                detailsEntity.setGmOverallTaskComp(employeeKppDetailsEntity.getGmOverallTaskComp());
                detailsEntity.setStatusCd(employeeKppDetailsEntity.getStatusCd());

                kppDetailsEntities.add(detailsEntity);
            }
            reportEmployeeKppDetailsRepo.saveAll(kppDetailsEntities);
            employeeKppDetailsRepo.resetEmployeeKpp(empId, statusCd);
        }
        return null;
    }


    private List<KPPResponse> convertEntityListToResponse(List<KeyPerfParamEntity> keyPerfParamEntities) {
        return keyPerfParamEntities
                .stream()
                .map(keyPerfParamEntity -> KPPResponse.builder()
                        .kppId(keyPerfParamEntity.getKppId())
                        .roleId(keyPerfParamEntity.getRoleId())
                        .deptId(keyPerfParamEntity.getDeptId())
                        .desigId(keyPerfParamEntity.getDesigId())
                        .kppObjective(keyPerfParamEntity.getKppObjective())
                        .kppPerformanceIndi(keyPerfParamEntity.getKppPerformanceIndi())
                        .kppOverallTarget(keyPerfParamEntity.getKppOverallTarget())
                        .kppTargetPeriod(keyPerfParamEntity.getKppTargetPeriod())
                        .kppUoM(keyPerfParamEntity.getKppUoM())
                        .kppOverallWeightage(keyPerfParamEntity.getKppOverallWeightage())
                        .kppRating1(keyPerfParamEntity.getKppRating1())
                        .kppRating2(keyPerfParamEntity.getKppRating2())
                        .kppRating3(keyPerfParamEntity.getKppRating3())
                        .kppRating4(keyPerfParamEntity.getKppRating4())
                        .kppRating5(keyPerfParamEntity.getKppRating5())
                        .build()
                )
                .collect(Collectors.toList());
    }


    private EmployeeKppDetailsEntity convertEmployeeKPPCreateRequestToEntity(EmployeeKeyPerfParamCreateRequest keyPerfParamCreateRequest) {
        return EmployeeKppDetailsEntity.keyEmployeePerfParamEntityBuilder()
                .ekppMonth(keyPerfParamCreateRequest.getEkppMonth())
                .kppId(keyPerfParamCreateRequest.getKppId())
                .empId(keyPerfParamCreateRequest.getEmpId())
                .empEId(keyPerfParamCreateRequest.getEmpEId())
                .roleId(keyPerfParamCreateRequest.getRoleId())
                .deptId(keyPerfParamCreateRequest.getDeptId())
                .desigId(keyPerfParamCreateRequest.getDesigId())
                .ekppAchivedWeight(keyPerfParamCreateRequest.getEkppAchivedWeight())
                .ekppOverallAchieve(keyPerfParamCreateRequest.getEkppOverallAchieve())
                .ekppOverallTaskComp(keyPerfParamCreateRequest.getEkppOverallTaskComp())
                .createdUserId(keyPerfParamCreateRequest.getEmployeeId())
                .build();
    }
}
