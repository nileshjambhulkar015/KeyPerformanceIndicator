package com.futurebizops.kpi.service;

import com.futurebizops.kpi.request.EmployeeCreateRequest;
import com.futurebizops.kpi.request.EmployeeUpdateRequest;
import com.futurebizops.kpi.response.EmployeeResponse;
import com.futurebizops.kpi.response.KPIResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EmployeeService {

    public KPIResponse saveEmployee(EmployeeCreateRequest employeeRequest);

    public KPIResponse updateEmployee(EmployeeUpdateRequest employeeUpdateRequest);

    public KPIResponse getAllEmployeeDetails(Integer empId, Integer deptId, Integer desigId, String empFirstName, String empMiddleName, String empLastName, String empMobileNo, String emailId, String statusCd, Pageable pageable);

    public EmployeeResponse getAllEmployeeById(Integer empId);

}
