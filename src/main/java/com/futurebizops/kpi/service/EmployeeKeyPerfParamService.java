package com.futurebizops.kpi.service;

import com.futurebizops.kpi.enums.RoleSearchEnum;
import com.futurebizops.kpi.enums.StatusCdEnum;
import com.futurebizops.kpi.request.EmployeeKeyPerfParamCreateRequest;
import com.futurebizops.kpi.request.EmployeeKeyPerfParamUpdateRequest;
import com.futurebizops.kpi.request.GMUpdateRequest;
import com.futurebizops.kpi.request.HODApprovalUpdateRequest;
import com.futurebizops.kpi.request.RoleCreateRequest;
import com.futurebizops.kpi.request.RoleUpdateRequest;
import com.futurebizops.kpi.response.KPIResponse;
import com.futurebizops.kpi.response.RoleResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EmployeeKeyPerfParamService {

    public KPIResponse saveEmployeeKeyPerfParamDetails(EmployeeKeyPerfParamCreateRequest keyPerfParamCreateRequest);

    public KPIResponse updateEmployeeKeyPerfParamDetails(EmployeeKeyPerfParamUpdateRequest keyPerfParamUpdateRequest);

    public KPIResponse updateHoDApprovalRequest(List<HODApprovalUpdateRequest> hodApprovalUpdateRequests);

    public KPIResponse updateGMApprovalRequest(List<GMUpdateRequest> hodApprovalUpdateRequests);



   // public KPIResponse findRoleDetails(RoleSearchEnum searchEnum, String searchString, StatusCdEnum statusCdEnum, Pageable pageable, String sortParam, String pageDirection);

    //public List<RoleResponse> findAllRolesDetails();
}
