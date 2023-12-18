package com.futurebizops.kpi.service;

import com.futurebizops.kpi.request.EmpKPPMasterUpdateRequest;
import com.futurebizops.kpi.request.EmployeeKeyPerfParamCreateRequest;
import com.futurebizops.kpi.request.EmpKPPUpdateRequest;
import com.futurebizops.kpi.request.GMUpdateRequest;
import com.futurebizops.kpi.request.HODApprovalUpdateRequest;
import com.futurebizops.kpi.response.HodEmploeeKppResponse;
import com.futurebizops.kpi.response.KPIResponse;
import com.futurebizops.kpi.response.KPPResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EmployeeKeyPerfParamService {

    public KPIResponse saveEmployeeKeyPerfParamDetails(EmployeeKeyPerfParamCreateRequest keyPerfParamCreateRequest);

    public KPIResponse updateEmployeeKeyPerfParamDetails(EmpKPPMasterUpdateRequest empKPPMasterUpdateRequest);

    public KPIResponse updateHoDApprovalRequest(List<HODApprovalUpdateRequest> hodApprovalUpdateRequests);

    public KPIResponse updateGMApprovalRequest(List<GMUpdateRequest> hodApprovalUpdateRequests);

    public List<KPPResponse> getKeyPerfomanceParameter(Integer roleId, Integer deptId, Integer desigId, String statusCd);

    public List<HodEmploeeKppResponse> getEmployeeForHodRatings(Integer empId, String statusCd);

    public KPIResponse getAllEmployeeDetailsForHod(Integer reportingEmployee,Integer empId,String empEId, Integer desigId, String empFirstName, String empMiddleName, String empLastName, String empMobileNo, String emailId, String statusCd,String empKppStatus, Pageable pageable);

   // public KPIResponse findRoleDetails(RoleSearchEnum searchEnum, String searchString, StatusCdEnum statusCdEnum, Pageable pageable, String sortParam, String pageDirection);

    //public List<RoleResponse> findAllRolesDetails();
}
