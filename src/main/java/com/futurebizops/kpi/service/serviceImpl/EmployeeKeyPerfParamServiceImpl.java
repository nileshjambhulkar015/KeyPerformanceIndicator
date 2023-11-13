package com.futurebizops.kpi.service.serviceImpl;

import com.futurebizops.kpi.constants.KPIConstants;
import com.futurebizops.kpi.entity.EmployeeKeyPerfParamAudit;
import com.futurebizops.kpi.entity.EmployeeKeyPerfParamEntity;
import com.futurebizops.kpi.entity.KeyPerfParamEntity;
import com.futurebizops.kpi.exception.KPIException;
import com.futurebizops.kpi.repository.EmployeeKeyPerfParamAuditRepo;
import com.futurebizops.kpi.repository.EmployeeKeyPerfParamRepo;
import com.futurebizops.kpi.repository.KeyPerfParameterRepo;
import com.futurebizops.kpi.request.EmployeeKeyPerfParamCreateRequest;
import com.futurebizops.kpi.request.EmployeeKeyPerfParamUpdateRequest;
import com.futurebizops.kpi.request.GMUpdateRequest;
import com.futurebizops.kpi.request.HODApprovalUpdateRequest;
import com.futurebizops.kpi.response.EmployeeResponse;
import com.futurebizops.kpi.response.HodEmploeeKppResponse;
import com.futurebizops.kpi.response.HodEmployeeResponse;
import com.futurebizops.kpi.response.KPIResponse;
import com.futurebizops.kpi.response.KPPResponse;
import com.futurebizops.kpi.service.EmployeeKeyPerfParamService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class EmployeeKeyPerfParamServiceImpl implements EmployeeKeyPerfParamService {

    @Autowired
    private EmployeeKeyPerfParamRepo keyPerfParamRepo;

    @Autowired
    private EmployeeKeyPerfParamAuditRepo keyPerfParamAuditRepo;

    @Autowired
    private KeyPerfParameterRepo keyPerfParameterRepo;

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

    @Transactional
    @Override
    public KPIResponse updateEmployeeKeyPerfParamDetails(List<EmployeeKeyPerfParamUpdateRequest> keyPerfParamUpdateRequests) {
        try {
            for (EmployeeKeyPerfParamUpdateRequest paramUpdateRequest : keyPerfParamUpdateRequests) {
             int i=   keyPerfParamRepo.updateEmployeeKpp(paramUpdateRequest.getEmpId(), paramUpdateRequest.getEkppMonth(), paramUpdateRequest.getEkppAchivedWeight(), paramUpdateRequest.getEkppOverallAchieve(), paramUpdateRequest.getEkppOverallTaskComp(), paramUpdateRequest.getEkppAppliedDate(), paramUpdateRequest.getEkppEvidence(),paramUpdateRequest.getEmpRemark(), paramUpdateRequest.getEkppStatus(), paramUpdateRequest.getKppId(),paramUpdateRequest.getEmpEId(), paramUpdateRequest.getRoleId(), paramUpdateRequest.getDeptId(),paramUpdateRequest.getDesigId());
                System.out.println(i);
            }
            return KPIResponse.builder()
                    .isSuccess(true)
                    .responseMessage(KPIConstants.RECORD_SUCCESS)
                    .build();
        } catch (Exception ex) {
            log.error("Inside EmployeeKeyPerfParamServiceImpl >> updateEmployeeKeyPerfParamDetails()");
            throw new KPIException("EmployeeKeyPerfParamServiceImpl", false, ex.getMessage());
        }
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

    @Override
    public List<KPPResponse>  getKeyPerfomanceParameter(Integer roleId, Integer deptId, Integer desigId, String statusCd) {
        List<KeyPerfParamEntity> keyPerfParamEntities = keyPerfParameterRepo.findByRoleIdAndDeptIdAndDesigIdAndStatusCd(roleId,deptId, desigId, statusCd);
        return  convertEntityListToResponse(keyPerfParamEntities);
    }

    @Override
    public List<HodEmploeeKppResponse> getEmployeeForHodRatings(Integer empId, String statusCd) {
        List<Object[]> employeeKppData = keyPerfParameterRepo.getEmployeeKeyPerfParameterDetail(empId, statusCd);
        List<HodEmploeeKppResponse> hodEmployeeResponses = employeeKppData.stream().map(HodEmploeeKppResponse::new).collect(Collectors.toList());
        hodEmployeeResponses = hodEmployeeResponses.stream()
                .sorted(Comparator.comparing(HodEmploeeKppResponse::getKppObjective))
                .collect(Collectors.toList());
        return hodEmployeeResponses;
    }

    @Override
    public KPIResponse  getAllEmployeeDetailsForHod(Integer reportingEmployee,Integer empId, Integer desigId, String empFirstName, String empMiddleName, String empLastName, String empMobileNo, String emailId, String statusCd, Pageable pageable) {
        String sortName = null;
        String sortDirection = null;
        Integer pageSize = pageable.getPageSize();
        Integer pageOffset = (int) pageable.getOffset();
        // pageable = KPIUtils.sort(requestPageable, sortParam, pageDirection);
        Optional<Sort.Order> order = pageable.getSort().get().findFirst();
        if (order.isPresent()) {
            sortName = order.get().getProperty();  //order by this field
            sortDirection = order.get().getDirection().toString(); // Sort ASC or DESC
        }

        Integer totalCount = keyPerfParameterRepo.getEmployeeCount(reportingEmployee,empId,  desigId, empFirstName, empMiddleName, empLastName, empMobileNo, emailId, statusCd);
        List<Object[]> employeeDetail = keyPerfParameterRepo.getEmployeeDetail(reportingEmployee,empId, desigId, empFirstName, empMiddleName, empLastName, empMobileNo, emailId, statusCd, sortName, pageSize, pageOffset);

        List<HodEmployeeResponse> hodEmployeeResponses = employeeDetail.stream().map(HodEmployeeResponse::new).collect(Collectors.toList());
        hodEmployeeResponses = hodEmployeeResponses.stream()
                .sorted(Comparator.comparing(HodEmployeeResponse::getDesigName))
                .collect(Collectors.toList());

        return KPIResponse.builder()
                .isSuccess(true)
                .responseData(new PageImpl(hodEmployeeResponses, pageable, totalCount))
                .responseMessage(KPIConstants.RECORD_FETCH)
                .build();
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
