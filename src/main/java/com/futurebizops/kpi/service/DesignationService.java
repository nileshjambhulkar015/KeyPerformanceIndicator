package com.futurebizops.kpi.service;

import com.futurebizops.kpi.request.DesignationCreateRequest;
import com.futurebizops.kpi.request.DesignationUpdateRequest;
import com.futurebizops.kpi.response.DepartmentReponse;
import com.futurebizops.kpi.response.DesignationReponse;
import com.futurebizops.kpi.response.KPIResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DesignationService {

    public KPIResponse saveDesignation(DesignationCreateRequest departmentCreateRequest);

    public KPIResponse updateDesignation(DesignationUpdateRequest departmentUpdateRequest);


    public KPIResponse findDesignationDetails( Integer roleId, Integer deptId, String desigName, String statusCd, Pageable pageable);

    public DesignationReponse findDesignationById(Integer desigId);



    public List<DesignationReponse> findAllDesignationByDeptId(Integer deptId);

    public List<DepartmentReponse> getAllDepartmentFromDesig(Integer deptId);
}
