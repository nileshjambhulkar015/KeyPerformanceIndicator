package com.futurebizops.kpi.service;

import com.futurebizops.kpi.enums.DepartmentSearchEnum;
import com.futurebizops.kpi.enums.StatusCdEnum;
import com.futurebizops.kpi.request.DepartmentCreateRequest;
import com.futurebizops.kpi.request.DepartmentUpdateRequest;
import com.futurebizops.kpi.response.DepartmentReponse;
import com.futurebizops.kpi.response.KPIResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DepartmentService {

    public KPIResponse saveDepartment(DepartmentCreateRequest departmentCreateRequest);

    public KPIResponse updateDepartment(DepartmentUpdateRequest departmentUpdateRequest);

    public KPIResponse findDepartmentDetails(DepartmentSearchEnum searchEnum, String searchString, StatusCdEnum statusCdEnum, Pageable pageable, String sortParam, String pageDirection );

    public List<DepartmentReponse> findAllDepartmentDetails();

    public List<DepartmentReponse> findAllDepartmentDetailsForEmployee();

    public DepartmentReponse findAllDepartmentById(Integer deptId);

}
