package com.futurebizops.kpi.service;

import com.futurebizops.kpi.enums.RoleSearchEnum;
import com.futurebizops.kpi.enums.StatusCdEnum;
import com.futurebizops.kpi.request.RoleCreateRequest;
import com.futurebizops.kpi.request.RoleUpdateRequest;
import com.futurebizops.kpi.response.DepartmentReponse;
import com.futurebizops.kpi.response.KPIResponse;
import com.futurebizops.kpi.response.RoleResponse;
import com.futurebizops.kpi.response.dropdown.RoleDDResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RoleService {

    public KPIResponse saveRole(RoleCreateRequest roleCreateRequest);

    public KPIResponse updateRole(RoleUpdateRequest roleUpdateRequest);

    public KPIResponse findRoleDetails(RoleSearchEnum searchEnum, String searchString, StatusCdEnum statusCdEnum, Pageable pageable, String sortParam, String pageDirection);

    public List<RoleResponse> findAllRolesDetails();

    public RoleResponse findAllRoleById(Integer roleId);


    public List<RoleDDResponse> ddEmployeeRoleExceptGM();
}
