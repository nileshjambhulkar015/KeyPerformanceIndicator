package com.futurebizops.kpi.controller;

import com.futurebizops.kpi.model.EmployeeAdvSearchModel;
import com.futurebizops.kpi.request.EmployeeCreateRequest;
import com.futurebizops.kpi.request.EmployeeUpdateDeptDesigRequest;
import com.futurebizops.kpi.request.EmployeeUpdateReportingRequest;
import com.futurebizops.kpi.request.EmployeeUpdateRequest;
import com.futurebizops.kpi.request.EmployeeUpdateRoleRequest;
import com.futurebizops.kpi.request.advsearch.EmployeeAdvSearchRequest;
import com.futurebizops.kpi.response.EmployeeResponse;
import com.futurebizops.kpi.response.EmployeeSearchResponse;
import com.futurebizops.kpi.response.KPIResponse;
import com.futurebizops.kpi.response.dropdown.DepartmentDDResponse;
import com.futurebizops.kpi.response.dropdown.DesignationDDResponse;
import com.futurebizops.kpi.response.dropdown.EmployeeDDResponse;
import com.futurebizops.kpi.response.dropdown.RegionDDResponse;
import com.futurebizops.kpi.response.dropdown.RoleDDResponse;
import com.futurebizops.kpi.service.EmployeeService;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/employee")
@Slf4j
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<KPIResponse> saveEmployee(@RequestBody EmployeeCreateRequest employeeRequest) {
        log.info("Inside EmployeeController >> saveEmployee() employeeRequest : {}", employeeRequest);
        return ResponseEntity.ok(employeeService.saveEmployee(employeeRequest));
    }

    @DeleteMapping
    public ResponseEntity<KPIResponse> deleteEmployeeDetails(@RequestParam(required = false) Integer empId) {
        log.info("Inside EmployeeController >> deleteEmployeeDetails() empId :{}", empId);
        KPIResponse response = employeeService.deleteEmployeeDetails(empId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<KPIResponse> updateEmployee(@RequestBody EmployeeUpdateRequest employeeUpdateRequest) {
        log.info("Inside EmployeeController >> updateEmployee() employeeRequest : {}", employeeUpdateRequest);
        return ResponseEntity.ok(employeeService.updateEmployee(employeeUpdateRequest));
    }

    @PutMapping(value = "/change-dept-or-desig")
    public ResponseEntity<KPIResponse> updateEmployeeDeptOrDesignation(@RequestBody EmployeeUpdateDeptDesigRequest employeeUpdateDeptDesigRequest) {
        log.info("Inside EmployeeController >> updateEmployeeDeptOrDesignation() employeeRequest : {}", employeeUpdateDeptDesigRequest);
        return ResponseEntity.ok(employeeService.updateEmployeeDeptOrDesignation(employeeUpdateDeptDesigRequest));
    }

    @PutMapping(value = "/change-role")
    public ResponseEntity<KPIResponse> updateEmployeeRole(@RequestBody EmployeeUpdateRoleRequest employeeUpdateRoleRequest) {
        log.info("Inside EmployeeController >> updateEmployeeRole() employeeUpdateRoleRequest : {}", employeeUpdateRoleRequest);
        return ResponseEntity.ok(employeeService.updateEmployeeRole(employeeUpdateRoleRequest));
    }

    @PutMapping(value = "/change-reporting")
    public ResponseEntity<KPIResponse> updateEmployeeReportingName(@RequestBody EmployeeUpdateReportingRequest employeeUpdateReportingRequest) {
        log.info("Inside EmployeeController >> updateEmployeeReportingName() employeeUpdateReportingRequest : {}", employeeUpdateReportingRequest);
        return ResponseEntity.ok(employeeService.updateEmployeeReportingName(employeeUpdateReportingRequest));
    }


    @PutMapping(value = "/update-dob")
    public ResponseEntity<KPIResponse> updateEmployeeDOB(@RequestParam(required = false) Integer empId,
                                                         @RequestParam(required = false) String empDob) {
        log.info("Inside EmployeeController >> updateEmployeeDOB() empId : {}, empDob : {}", empId,empDob);
        return ResponseEntity.ok(employeeService.updateEmployeeDOB(empId, empDob));
    }


    //for employee master page to get details of employee
    @GetMapping(value = "/search")
    @PageableAsQueryParam
    public ResponseEntity<KPIResponse> getAllEmployeeDetails(@RequestParam(required = false) Integer empId,
                                                      @RequestParam(required = false) String empEId,
                                                      @RequestParam(required = false) Integer roleId,
                                                      @RequestParam(required = false) Integer deptId,
                                                      @RequestParam(required = false) Integer desigId,
                                                      @RequestParam(required = false) String empFirstName,
                                                      @RequestParam(required = false) String empMiddleName,
                                                      @RequestParam(required = false) String empLastName,
                                                      @RequestParam(required = false) String empMobileNo,
                                                      @RequestParam(required = false) String emailId,
                                                      @RequestParam(required = false) String statusCd,
                                                      @RequestParam(required = false) Integer empTypeId,
                                                      @RequestParam(required = false) Integer companyId,
                                                      @RequestParam(required = false) Integer reportingEmpId,
                                                      @Parameter(hidden = true) Pageable pageable) {
        log.info("Inside EmployeeController >> getAllEmployeeDetails() empId : {}, empEId : {}, roleId : {}, deptId : {}, desigId: {}, reportingEmpId : {}", empId,empEId, roleId, deptId, desigId, reportingEmpId);
        KPIResponse response = employeeService.getAllEmployeeDetails(empId, empEId, roleId, deptId, desigId, empFirstName, empMiddleName, empLastName, empMobileNo, emailId, statusCd, empTypeId, companyId, reportingEmpId, pageable);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = "/adv-search")
    @PageableAsQueryParam
    public ResponseEntity<KPIResponse> empAdvanceSearch(@RequestBody EmployeeAdvSearchRequest employeeAdvSearchRequest, @Parameter(hidden = true) Pageable pageable) {
        log.info("Inside EmployeeController >> updateEmployeeDOB() employeeAdvSearchRequest : {}", employeeAdvSearchRequest);
        EmployeeAdvSearchModel employeeAdvSearchModel = EmployeeAdvSearchModel.builder()
                .roleId(employeeAdvSearchRequest.getRoleId())
                .deptId(employeeAdvSearchRequest.getDeptId())
                .desigId(employeeAdvSearchRequest.getDesigId())
                .regionId(employeeAdvSearchRequest.getRegionId())
                .siteId(employeeAdvSearchRequest.getSiteId())
                .companyId(employeeAdvSearchRequest.getCompanyId())
                .empTypeId(employeeAdvSearchRequest.getEmpTypeId())
                .pageable(pageable)
                .build();


        KPIResponse response = employeeService.getAllEmployeeAdvanceSearch(employeeAdvSearchModel.getRoleId(), employeeAdvSearchModel.getDeptId(), employeeAdvSearchModel.getDesigId(), employeeAdvSearchModel.getRegionId(), employeeAdvSearchModel.getSiteId(), employeeAdvSearchModel.getCompanyId(), employeeAdvSearchModel.getEmpTypeId(), employeeAdvSearchModel.getPageable());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/byEmpId")
    public ResponseEntity<EmployeeResponse> getAllEmployeeById(@RequestParam(required = false) Integer empId) {
        log.info("Inside EmployeeController >> getAllEmployeeById() empId : {}", empId);
        EmployeeResponse response = employeeService.getAllEmployeeById(empId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/search-by-id")
    public ResponseEntity<EmployeeSearchResponse> getEmployeeSearchById(@RequestParam(required = false) Integer empId) {
        EmployeeSearchResponse response = employeeService.getEmployeeSearchById(empId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/reportToEmpName")
    public ResponseEntity<List<EmployeeSearchResponse>> getEmployeeSuggestByName(@RequestParam(required = false) Integer roleId,
                                                                           @RequestParam(required = false) Integer deptId,
                                                                           @RequestParam(required = false) Integer desigId) {
        log.info("Inside EmployeeController >> getEmployeeSuggestByName() roleId : {}, deptId : {}, desigId :{}", roleId, deptId,desigId);
        List<EmployeeSearchResponse> response = employeeService.getEmployeeSuggestByName(roleId, deptId, desigId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //get status of kpp  for employee, HOD and GM to approve or reject kpp details
    @GetMapping(value = "/employee-kpp-status")
    @PageableAsQueryParam
    public ResponseEntity<KPIResponse> getAllEmployeeKPPForApproveOrReject(@RequestParam(required = false) Integer reportingEmployee,
                                                                           @RequestParam(required = false) Integer gmEmployeedId,
                                                                           @RequestParam(required = false) Integer empId,
                                                                           @RequestParam(required = false) String empEId,
                                                                           @RequestParam(required = false) Integer roleId,
                                                                           @RequestParam(required = false) Integer deptId,
                                                                           @RequestParam(required = false) Integer desigId,
                                                                           @RequestParam(required = false) String statusCd,
                                                                           @RequestParam(required = false) String empKppStatus,
                                                                           @RequestParam(required = false) String hodKppStatus,
                                                                           @RequestParam(required = false) String gmKppStatus,
                                                                           @Parameter(hidden = true) Pageable pageable) {
        log.info("Inside EmployeeController >> getEmployeeSuggestByName() reportingEmployee : {},gmEmployeedId : {},empId : {}, roleId : {}, deptId : {}, desigId :{}", reportingEmployee,gmEmployeedId,empId, roleId, deptId,desigId);
        KPIResponse response = employeeService.getAllEmployeeKPPStatus(reportingEmployee, gmEmployeedId, empId, empEId, roleId, deptId, desigId, statusCd, empKppStatus, hodKppStatus, gmKppStatus, pageable);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @PostMapping(value = "/upload-employee")
    public ResponseEntity<KPIResponse> processExcelFile(@RequestPart("file") MultipartFile file) {
        log.info("Inside EmployeeController >> processExcelFile()");
        KPIResponse response = employeeService.processExcelFile(file);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //for roles except Employee
    @GetMapping(value = "/dd-role-except-emp-role")
    public ResponseEntity<List<RoleDDResponse>> getRolesExceptEmployee(@RequestParam(required = false) Integer roleId,
                                                                       @RequestParam(required = false) String roleName) {
        log.info("Inside EmployeeController >> getRolesExceptEmployee() roleId : {}, roleName: {}", roleId, roleName);
        List<RoleDDResponse> response = employeeService.getRolesExceptEmployee(roleId, roleName);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //for roles except Employee
    @GetMapping(value = "/dd-dept-emp")
    public ResponseEntity<List<DepartmentDDResponse>> getDepartmentFromEmployee(@RequestParam(required = false) Integer roleId,
                                                                                @RequestParam(required = false) Integer deptId) {
        log.info("Inside EmployeeController >> getDepartmentFromEmployee() roleId : {}, deptId: {}", roleId, deptId);
        List<DepartmentDDResponse> response = employeeService.getDepartmentFromEmployee(roleId, deptId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //for roles except Employee
    @GetMapping(value = "/dd-desig-emp")
    public ResponseEntity<List<DesignationDDResponse>> getDesignationFromEmployee(@RequestParam(required = false) Integer roleId,
                                                                                  @RequestParam(required = false) Integer deptId,
                                                                                  @RequestParam(required = false) Integer desigId) {
        log.info("Inside EmployeeController >> getDepartmentFromEmployee() roleId : {}, deptId: {}, desigId: {}", roleId, deptId,desigId);
        List<DesignationDDResponse> response = employeeService.getDesignationFromEmployee(roleId, deptId, desigId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/dd-employee")
    public ResponseEntity<List<EmployeeDDResponse>> getDDEmpName(@RequestParam(required = false) Integer roleId,
                                                                 @RequestParam(required = false) Integer deptId,
                                                                 @RequestParam(required = false) Integer desigId) {
        log.info("Inside EmployeeController >> getDDEmpName() roleId : {}, deptId: {}, desigId: {}", roleId, deptId,desigId);
        List<EmployeeDDResponse> response = employeeService.getDDEmpName(roleId, deptId, desigId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
