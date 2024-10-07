package com.futurebizops.kpi.service;

import com.futurebizops.kpi.request.ComplaintTypeCreateRequest;
import com.futurebizops.kpi.request.ComplaintTypeUpdateRequest;
import com.futurebizops.kpi.response.ComplaintTypeReponse;
import com.futurebizops.kpi.response.KPIResponse;
import com.futurebizops.kpi.response.dropdown.ComplaintTypeDDResponse;
import com.futurebizops.kpi.response.dropdown.DepartmentDDResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ComplaintTypeService {

    public KPIResponse saveComplaintType(ComplaintTypeCreateRequest complaintCreateRequest);

    public KPIResponse findComplaintTypeDetails(Integer compTypeId, String compTypeName,Integer deptId, String statusCd, Pageable pageable);

    public ComplaintTypeReponse findAllComplaintTypeById(Integer compTypeId);

    public KPIResponse updateComplaintType(ComplaintTypeUpdateRequest complaintTypeUpdateRequest);

    public List<DepartmentDDResponse> findAllDepartmentFromComplaintType();

    public List<ComplaintTypeDDResponse> findAllComplaintTypeByDeptId(Integer deptId);
}
