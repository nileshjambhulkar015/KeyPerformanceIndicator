package com.futurebizops.kpi.service.serviceImpl;

import com.futurebizops.kpi.constants.KPIConstants;
import com.futurebizops.kpi.entity.EmployeeKeyPerfParamAudit;
import com.futurebizops.kpi.entity.EmployeeKeyPerfParamEntity;
import com.futurebizops.kpi.exception.KPIException;
import com.futurebizops.kpi.repository.EmployeeKeyPerfParamAuditRepo;
import com.futurebizops.kpi.repository.EmployeeKeyPerfParamRepo;
import com.futurebizops.kpi.request.EmployeeKeyPerfParamCreateRequest;
import com.futurebizops.kpi.request.EmployeeKeyPerfParamUpdateRequest;
import com.futurebizops.kpi.request.GMUpdateRequest;
import com.futurebizops.kpi.request.HODApprovalUpdateRequest;
import com.futurebizops.kpi.response.KPIResponse;
import com.futurebizops.kpi.service.EmployeeKeyPerfParamService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class EmployeeKeyPerfParamServiceImpl implements EmployeeKeyPerfParamService {

    @Autowired
    private EmployeeKeyPerfParamRepo keyPerfParamRepo;

    @Autowired
    private EmployeeKeyPerfParamAuditRepo keyPerfParamAuditRepo;


    @Override
    public KPIResponse saveEmployeeKeyPerfParamDetails(EmployeeKeyPerfParamCreateRequest keyPerfParamCreateRequest) {
        EmployeeKeyPerfParamEntity keyPerfParamEntity = convertEmployeeKPPCreateRequestToEntity(keyPerfParamCreateRequest);
        try {
            keyPerfParamRepo.save(keyPerfParamEntity);
            EmployeeKeyPerfParamAudit partAudit = new EmployeeKeyPerfParamAudit(keyPerfParamEntity);
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

    @Override
    public KPIResponse updateEmployeeKeyPerfParamDetails(EmployeeKeyPerfParamUpdateRequest keyPerfParamUpdateRequest) {
        return null;
    }

    @Override
    @Transactional
    public KPIResponse updateHoDApprovalRequest(List<HODApprovalUpdateRequest> hodApprovalUpdateRequests) {

        try {
            for (HODApprovalUpdateRequest hodApprovalUpdateRequest : hodApprovalUpdateRequests) {
                keyPerfParamRepo.updateHODEmplyeeKppStatus(hodApprovalUpdateRequest.getHodEmpId(), hodApprovalUpdateRequest.getHodKppStatus(), hodApprovalUpdateRequest.getHodRating(), hodApprovalUpdateRequest.getHodRemark(), hodApprovalUpdateRequest.getHodApprovedDate(), hodApprovalUpdateRequest.getLastUpdatedUserId(), hodApprovalUpdateRequest.getEkppId(), hodApprovalUpdateRequest.getEmpId(), hodApprovalUpdateRequest.getDeptId(), hodApprovalUpdateRequest.getDesigId());
            }
            return KPIResponse.builder()
                    .isSuccess(true)
                    .responseMessage(KPIConstants.RECORD_SUCCESS)
                    .build();
        } catch (Exception ex) {
            log.error("Inside EmployeeKeyPerfParamServiceImpl >> updateHoDApprovalRequest()");
            throw new KPIException("EmployeeKeyPerfParamServiceImpl", false, ex.getMessage());
        }
    }

    @Override
    public KPIResponse updateGMApprovalRequest(List<GMUpdateRequest> hodApprovalUpdateRequests) {
        try {
            for (GMUpdateRequest gmApprovalUpdateRequest : hodApprovalUpdateRequests) {
                keyPerfParamRepo.updateGMEmplyeeKppStatus(gmApprovalUpdateRequest.getHodEmpId(), gmApprovalUpdateRequest.getGmKppStatus(), gmApprovalUpdateRequest.getGmRating(), gmApprovalUpdateRequest.getGmRemark(), gmApprovalUpdateRequest.getGmApprovedDate(), gmApprovalUpdateRequest.getLastUpdatedUserId(), gmApprovalUpdateRequest.getEkppId(), gmApprovalUpdateRequest.getEkppId(), gmApprovalUpdateRequest.getDeptId(), gmApprovalUpdateRequest.getDesigId());


            }
            return KPIResponse.builder()
                    .isSuccess(true)
                    .responseMessage(KPIConstants.RECORD_SUCCESS)
                    .build();
        } catch (Exception ex) {
            log.error("Inside EmployeeKeyPerfParamServiceImpl >> updateGMApprovalRequest()");
            throw new KPIException("EmployeeKeyPerfParamServiceImpl", false, ex.getMessage());
        }
    }



    private EmployeeKeyPerfParamEntity convertEmployeeKPPCreateRequestToEntity(EmployeeKeyPerfParamCreateRequest keyPerfParamCreateRequest) {
        return EmployeeKeyPerfParamEntity.keyEmployeePerfParamEntityBuilder()
                .ekppMonth(keyPerfParamCreateRequest.getEkppMonth())
                .kppId(keyPerfParamCreateRequest.getKppId())
                .empId(keyPerfParamCreateRequest.getEmpId())
                .deptId(keyPerfParamCreateRequest.getDeptId())
                .desigId(keyPerfParamCreateRequest.getDesigId())
                .ekppAchivedWeight(keyPerfParamCreateRequest.getEkppAchivedWeight())
                .ekppOverallAchieve(keyPerfParamCreateRequest.getEkppOverallAchieve())
                .ekppOverallTaskComp(keyPerfParamCreateRequest.getEkppOverallTaskComp())
                .ekppAppliedDate(keyPerfParamCreateRequest.getEkppAppliedDate())
                .ekppEvidence(keyPerfParamCreateRequest.getEkppEvidence())
                .ekppStatus(keyPerfParamCreateRequest.getEkppStatus())
                .remark(keyPerfParamCreateRequest.getRemark())
                .statusCd(keyPerfParamCreateRequest.getStatusCd())
                .createdUserId(keyPerfParamCreateRequest.getEmployeeId())
                .build();
    }
}
