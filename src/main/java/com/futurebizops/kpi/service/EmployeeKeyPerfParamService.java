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

    public KPIResponse updateHoDApprovalRequest(EmpKPPMasterUpdateRequest keyPerfParamCreateRequest);

    public KPIResponse updateGMApprovalRequest(EmpKPPMasterUpdateRequest empKPPMasterUpdateRequest);

    public List<KPPResponse> getKeyPerfomanceParameter(Integer roleId, Integer deptId, Integer desigId, String statusCd);

    public List<HodEmploeeKppResponse> getEmployeeForHodRatings(Integer empId, String empEId,String statusCd);



   // public KPIResponse findRoleDetails(RoleSearchEnum searchEnum, String searchString, StatusCdEnum statusCdEnum, Pageable pageable, String sortParam, String pageDirection);

    //public List<RoleResponse> findAllRolesDetails();
}
