package com.futurebizops.kpi.service;

import com.futurebizops.kpi.request.ComplaintCreateRequest;
import com.futurebizops.kpi.request.DepartmentCreateRequest;
import com.futurebizops.kpi.request.DepartmentUpdateRequest;
import com.futurebizops.kpi.response.KPIResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestParam;

public interface ComplaintService {

    public KPIResponse saveComplaint(ComplaintCreateRequest complaintCreateRequest);

    public KPIResponse updateComplaint(DepartmentUpdateRequest departmentUpdateRequest);

    public KPIResponse findComplaintDetails(Integer empId,String empEId,Integer roleId,Integer deptId,Integer desigId, String statusCd, Pageable pageable);
}