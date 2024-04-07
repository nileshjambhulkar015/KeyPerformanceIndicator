package com.futurebizops.kpi.service;

import com.futurebizops.kpi.request.EmployeeCreateRequest;
import com.futurebizops.kpi.request.EmployeeUpdateRequest;
import com.futurebizops.kpi.response.EmployeeResponse;
import com.futurebizops.kpi.response.EmployeeSearchResponse;
import com.futurebizops.kpi.response.KPIResponse;
import com.futurebizops.kpi.response.dropdown.CompanyDDResponse;
import com.futurebizops.kpi.response.dropdown.RoleDDResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface EmployeeService {

    public KPIResponse saveEmployee(EmployeeCreateRequest employeeRequest);

    public KPIResponse updateEmployee(EmployeeUpdateRequest employeeUpdateRequest);

    public KPIResponse getAllEmployeeDetails(Integer empId,String empEId,Integer roleId, Integer deptId, Integer desigId, String empFirstName, String empMiddleName, String empLastName, String empMobileNo, String emailId, String statusCd,Integer empTypeId,Integer companyId, Pageable pageable);

    public KPIResponse getAllEmployeeAdvanceSearch(Integer roleId, Integer deptId, Integer desigId,Integer regionId,Integer siteId, Integer companyId, Integer empTypeId, Pageable pageable);
    //public KPIResponse getAllEmployeeAdvanceSearch(String roleName, String deptName, String desigName,String regionName,String sietName, String companyName, String empTypeName, Pageable pageable);

    public EmployeeResponse getAllEmployeeById(Integer empId);

    public EmployeeSearchResponse getEmployeeSearchById(Integer empId);

    public List<EmployeeSearchResponse> getEmployeeSuggestByName(Integer roleId, Integer deptId, Integer desigId);

    public KPIResponse getAllEmployeeKPPStatus(Integer reportingEmployee,Integer gmEmpId,Integer empId,String empEId,Integer roleId, Integer deptId,Integer desigId, String empFirstName, String empMiddleName, String empLastName, String empMobileNo, String emailId, String statusCd,String empKppStatus, String hodKppStatus, String gmKppStatus, Pageable pageable);




    public KPIResponse processExcelFile(MultipartFile file);


}
