package com.futurebizops.kpi.service.serviceimpl;

import com.futurebizops.kpi.constants.KPIConstants;
import com.futurebizops.kpi.entity.EmployeeKppDetailsAudit;
import com.futurebizops.kpi.entity.EmployeeKppDetailsEntity;
import com.futurebizops.kpi.entity.KeyPerfParamEntity;
import com.futurebizops.kpi.exception.KPIException;
import com.futurebizops.kpi.repository.EmployeeKppDetailsAuditRepo;
import com.futurebizops.kpi.repository.EmployeeKppDetailsRepo;
import com.futurebizops.kpi.repository.EmployeeKppMasterAuditRepo;
import com.futurebizops.kpi.repository.EmployeeKppMasterRepo;
import com.futurebizops.kpi.repository.KeyPerfParameterRepo;
import com.futurebizops.kpi.request.EmpKPPMasterUpdateRequest;
import com.futurebizops.kpi.request.EmployeeKeyPerfParamCreateRequest;
import com.futurebizops.kpi.request.EmpKPPUpdateRequest;
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
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class EmployeeKeyPerfParamServiceImpl implements EmployeeKeyPerfParamService {

    @Autowired
    private EmployeeKppDetailsRepo employeeKeyPerfParamDetailsRepo;

    @Autowired
    private EmployeeKppDetailsAuditRepo keyPerfParamAuditRepo;

    @Autowired
    private KeyPerfParameterRepo keyPerfParameterRepo;

    @Autowired
    EmployeeKppMasterRepo employeeKeyPerfParamMasterRepo;

    @Autowired
    EmployeeKppMasterAuditRepo employeeKeyPerfParamMasterAuditRepo;



    @Override
    public KPIResponse saveEmployeeKeyPerfParamDetails(EmployeeKeyPerfParamCreateRequest keyPerfParamCreateRequest) {
        EmployeeKppDetailsEntity keyPerfParamEntity = convertEmployeeKPPCreateRequestToEntity(keyPerfParamCreateRequest);
        try {
            employeeKeyPerfParamDetailsRepo.save(keyPerfParamEntity);
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
        Instant ekppMonth=null;
        try {
            for (EmpKPPUpdateRequest paramUpdateRequest : empKPPMasterUpdateRequest.getKppUpdateRequests()) {
                ekppMonth = StringUtils.isNotEmpty(paramUpdateRequest.getEkppMonth()) ?DateTimeUtils.convertStringToInstant(paramUpdateRequest.getEkppMonth()) :null;
                employeeKeyPerfParamDetailsRepo.updateEmployeeKppDetails(paramUpdateRequest.getEmpId(), ekppMonth, paramUpdateRequest.getEkppAchivedWeight(), paramUpdateRequest.getEkppOverallAchieve(), paramUpdateRequest.getEkppOverallTaskComp(), paramUpdateRequest.getKppId(), paramUpdateRequest.getEmpEId(), paramUpdateRequest.getRoleId(), paramUpdateRequest.getDeptId(), paramUpdateRequest.getDesigId());
            }
            employeeKeyPerfParamMasterRepo.updateEmployeeKppMaster(empKPPMasterUpdateRequest.getKppUpdateRequests().get(0).getEmpId(),ekppMonth,empKPPMasterUpdateRequest.getTotalAchivedWeightage(),empKPPMasterUpdateRequest.getTotalOverAllAchive(),empKPPMasterUpdateRequest.getTotalOverallTaskCompleted(), Instant.now(), empKPPMasterUpdateRequest.getEkppStatus(),empKPPMasterUpdateRequest.getRemark(),empKPPMasterUpdateRequest.getEvidence(),empKPPMasterUpdateRequest.getKppUpdateRequests().get(0).getEmpEId(),empKPPMasterUpdateRequest.getKppUpdateRequests().get(0).getRoleId(),empKPPMasterUpdateRequest.getKppUpdateRequests().get(0).getDeptId(),empKPPMasterUpdateRequest.getKppUpdateRequests().get(0).getDesigId());

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
        String empKppStatus="In-Progress";
        try {
            for (EmpKPPUpdateRequest paramUpdateRequest : empKPPMasterUpdateRequest.getKppUpdateRequests()) {

                //employeeKeyPerfParamDetailsRepo.updateEmpApproveOrRejectHod(paramUpdateRequest.getEkppAchivedWeight(), paramUpdateRequest.getEkppOverallAchieve(), paramUpdateRequest.getEkppOverallTaskComp(), paramUpdateRequest.getKppId(), paramUpdateRequest.getEmpEId(), paramUpdateRequest.getRoleId(), paramUpdateRequest.getDeptId(), paramUpdateRequest.getDesigId());
                employeeKeyPerfParamDetailsRepo.updateEmpApproveOrRejectHod(paramUpdateRequest.getEkppAchivedWeight(), paramUpdateRequest.getEkppOverallAchieve(), paramUpdateRequest.getEkppOverallTaskComp(), paramUpdateRequest.getKppId(), paramUpdateRequest.getEmpId());
            }
            if("Approved".equalsIgnoreCase(empKPPMasterUpdateRequest.getEkppStatus())){
                empKppStatus="Approved";
            } else if("Reject".equalsIgnoreCase(empKPPMasterUpdateRequest.getEkppStatus())){
                empKppStatus="Reject";
            }
            employeeKeyPerfParamMasterRepo.updateEmpKppApproveOrRejectByHod(empKppStatus,empKPPMasterUpdateRequest.getTotalAchivedWeightage(),empKPPMasterUpdateRequest.getTotalOverAllAchive(),empKPPMasterUpdateRequest.getTotalOverallTaskCompleted(), Instant.now(), empKPPMasterUpdateRequest.getEkppStatus(),empKPPMasterUpdateRequest.getRemark(),empKPPMasterUpdateRequest.getKppUpdateRequests().get(0).getEmpId());


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
        String empKppStatus="In-Progress";
        try {
            for (EmpKPPUpdateRequest paramUpdateRequest : empKPPMasterUpdateRequest.getKppUpdateRequests()) {
                employeeKeyPerfParamDetailsRepo.updateGMApproveOrRejectHod(paramUpdateRequest.getEkppAchivedWeight(), paramUpdateRequest.getEkppOverallAchieve(), paramUpdateRequest.getEkppOverallTaskComp(), paramUpdateRequest.getKppId(), paramUpdateRequest.getEmpId());
            }
            if("Approved".equalsIgnoreCase(empKPPMasterUpdateRequest.getEkppStatus())){
                empKppStatus="Approved";
            } else if("Reject".equalsIgnoreCase(empKPPMasterUpdateRequest.getEkppStatus())){
                empKppStatus="Reject";
            }
            employeeKeyPerfParamMasterRepo.updateGMKppApproveOrRejectByHod(empKppStatus,empKPPMasterUpdateRequest.getTotalAchivedWeightage(),empKPPMasterUpdateRequest.getTotalOverAllAchive(),empKPPMasterUpdateRequest.getTotalOverallTaskCompleted(), Instant.now(), empKPPMasterUpdateRequest.getEkppStatus(),empKPPMasterUpdateRequest.getRemark(),empKPPMasterUpdateRequest.getKppUpdateRequests().get(0).getEmpId());


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
    public List<HodEmploeeKppResponse> getEmployeeForHodRatings(Integer empId,String empEId, String statusCd) {
        List<Object[]> employeeKppData = keyPerfParameterRepo.getEmployeeKeyPerfParameterDetail(empId,empEId, statusCd);
        List<HodEmploeeKppResponse> hodEmployeeResponses = employeeKppData.stream().map(HodEmploeeKppResponse::new).collect(Collectors.toList());
        hodEmployeeResponses = hodEmployeeResponses.stream()
                .sorted(Comparator.comparing(HodEmploeeKppResponse::getKppObjective))
                .collect(Collectors.toList());
        return hodEmployeeResponses;
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
