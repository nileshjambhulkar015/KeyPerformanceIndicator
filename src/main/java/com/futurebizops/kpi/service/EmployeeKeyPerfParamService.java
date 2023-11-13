package com.futurebizops.kpi.service;

import com.futurebizops.kpi.enums.RoleSearchEnum;
import com.futurebizops.kpi.enums.StatusCdEnum;
import com.futurebizops.kpi.request.EmployeeKeyPerfParamCreateRequest;
import com.futurebizops.kpi.request.EmployeeKeyPerfParamUpdateRequest;
import com.futurebizops.kpi.request.GMUpdateRequest;
import com.futurebizops.kpi.request.HODApprovalUpdateRequest;
import com.futurebizops.kpi.request.RoleCreateRequest;
import com.futurebizops.kpi.request.RoleUpdateRequest;
import com.futurebizops.kpi.response.HodEmployeeResponse;
import com.futurebizops.kpi.response.KPIResponse;
import com.futurebizops.kpi.response.KPPResponse;
import com.futurebizops.kpi.response.RoleResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EmployeeKeyPerfParamService {

    public KPIResponse saveEmployeeKeyPerfParamDetails(EmployeeKeyPerfParamCreateRequest keyPerfParamCreateRequest);

    public KPIResponse updateEmployeeKeyPerfParamDetails(List<EmployeeKeyPerfParamUpdateRequest> keyPerfParamUpdateRequest);

    public KPIResponse updateHoDApprovalRequest(List<HODApprovalUpdateRequest> hodApprovalUpdateRequests);

    public KPIResponse updateGMApprovalRequest(List<GMUpdateRequest> hodApprovalUpdateRequests);

    public List<KPPResponse> getKeyPerfomanceParameter(Integer roleId, Integer deptId, Integer desigId, String statusCd);

    public KPIResponse getAllEmployeeDetailsForHod(Integer reportingEmployee,Integer empId, Integer desigId, String empFirstName, String empMiddleName, String empLastName, String empMobileNo, String emailId, String statusCd, Pageable pageable);

   // public KPIResponse findRoleDetails(RoleSearchEnum searchEnum, String searchString, StatusCdEnum statusCdEnum, Pageable pageable, String sortParam, String pageDirection);

    //public List<RoleResponse> findAllRolesDetails();
}
