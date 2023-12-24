package com.futurebizops.kpi.controller;

import com.futurebizops.kpi.request.EmployeeCreateRequest;
import com.futurebizops.kpi.request.EmployeeUpdateRequest;
import com.futurebizops.kpi.response.EmployeeResponse;
import com.futurebizops.kpi.response.EmployeeSearchResponse;
import com.futurebizops.kpi.response.KPIResponse;
import com.futurebizops.kpi.service.EmployeeService;
import io.swagger.v3.oas.annotations.Parameter;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<KPIResponse> saveEmployee(@RequestBody EmployeeCreateRequest employeeRequest) {
        return ResponseEntity.ok(employeeService.saveEmployee(employeeRequest));
    }

    @PutMapping
    public ResponseEntity<KPIResponse> updateEmployee(@RequestBody EmployeeUpdateRequest employeeUpdateRequest) {
        return ResponseEntity.ok(employeeService.updateEmployee(employeeUpdateRequest));
    }

    //for employee master page to get details of employee
    @GetMapping(value = "/search")
    @PageableAsQueryParam
    public ResponseEntity<KPIResponse> getAllEmployee(@RequestParam(required = false) Integer empId,
                                                      @RequestParam(required = false) Integer roleId,
                                                      @RequestParam(required = false) Integer deptId,
                                                      @RequestParam(required = false) Integer desigId,
                                                      @RequestParam(required = false) String empFirstName,
                                                      @RequestParam(required = false) String empMiddleName,
                                                      @RequestParam(required = false) String empLastName,
                                                      @RequestParam(required = false) String empMobileNo,
                                                      @RequestParam(required = false) String emailId,
                                                      @RequestParam(required = false) String statusCd,
                                                      @Parameter(hidden = true) Pageable pageable) {
        KPIResponse response = employeeService.getAllEmployeeDetails(empId, roleId, deptId, desigId, empFirstName, empMiddleName, empLastName, empMobileNo, emailId, statusCd, pageable);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/{empId}")
    public ResponseEntity<EmployeeResponse> getAllEmployee(@PathVariable Integer empId) {
        EmployeeResponse response = employeeService.getAllEmployeeById(empId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/search-by-id")
    public ResponseEntity<EmployeeSearchResponse> getEmployeeSearchById(@RequestParam(required = false) Integer empId) {
        EmployeeSearchResponse response = employeeService.getEmployeeSearchById(empId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/reportToEmpName")
    public ResponseEntity<List<EmployeeSearchResponse>> getEmployeeSuggest(@RequestParam(required = false) Integer roleId,
                                                                           @RequestParam(required = false) Integer deptId,
                                                                           @RequestParam(required = false) Integer desigId) {
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
                                                                           @RequestParam(required = false) String empFirstName,
                                                                           @RequestParam(required = false) String empMiddleName,
                                                                           @RequestParam(required = false) String empLastName,
                                                                           @RequestParam(required = false) String empMobileNo,
                                                                           @RequestParam(required = false) String emailId,
                                                                           @RequestParam(required = false) String statusCd,
                                                                           @RequestParam(required = false) String empKppStatus,
                                                                           @RequestParam(required = false) String hodKppStatus,
                                                                           @RequestParam(required = false) String gmKppStatus,
                                                                           @Parameter(hidden = true) Pageable pageable) {
        KPIResponse response = employeeService.getAllEmployeeKPPStatus(reportingEmployee,gmEmployeedId, empId, empEId,roleId,deptId, desigId, empFirstName, empMiddleName, empLastName, empMobileNo, emailId, statusCd, empKppStatus,hodKppStatus,gmKppStatus, pageable);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
