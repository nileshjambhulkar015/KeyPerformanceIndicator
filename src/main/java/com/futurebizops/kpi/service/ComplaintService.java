package com.futurebizops.kpi.service;

import com.futurebizops.kpi.request.ComplaintCreateRequest;
import com.futurebizops.kpi.request.EmployeeComplaintUpdateRequest;
import com.futurebizops.kpi.response.EmployeeComplaintResponse;
import com.futurebizops.kpi.response.KPIResponse;
import org.springframework.data.domain.Pageable;

public interface ComplaintService {

    public KPIResponse saveComplaint(ComplaintCreateRequest complaintCreateRequest);

    public KPIResponse updateEmployeeComplaint(EmployeeComplaintUpdateRequest complaintUpdateRequest);

    public KPIResponse updateAdminHandleComplaint(EmployeeComplaintUpdateRequest complaintUpdateRequest);

    public EmployeeComplaintResponse findAllEmployeeCompById(Integer empCompId);
    public KPIResponse findComplaintDetails(Integer empId, String compId,Integer roleId,Integer deptId,String compDesc, String compStatus,Integer compTypeRoleId,Integer compTypeDeptId, String statusCd, Pageable pageable);

    public KPIResponse deleteEmployeeComplaint(Integer empCompId);



}