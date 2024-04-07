package com.futurebizops.kpi.service;

import com.futurebizops.kpi.enums.DepartmentSearchEnum;
import com.futurebizops.kpi.enums.StatusCdEnum;
import com.futurebizops.kpi.request.DepartmentCreateRequest;
import com.futurebizops.kpi.request.DepartmentUpdateRequest;
import com.futurebizops.kpi.response.DepartmentReponse;
import com.futurebizops.kpi.response.KPIResponse;
import com.futurebizops.kpi.response.RoleResponse;
import com.futurebizops.kpi.response.dropdown.DepartmentDDResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface DepartmentService {

    public KPIResponse saveDepartment(DepartmentCreateRequest departmentCreateRequest);

    public KPIResponse updateDepartment(DepartmentUpdateRequest departmentUpdateRequest);

    public KPIResponse findDepartmentDetails(Integer deptId, String deptName, String statusCd, Pageable pageable);


    public List<DepartmentReponse> findAllDepartmentDetails();

    public DepartmentReponse findAllDepartmentById(Integer deptId);
    public void uploadDeptExcelFile(MultipartFile file) throws IOException;

    public List<DepartmentDDResponse> findAllDepartmentExceptGM();

}
