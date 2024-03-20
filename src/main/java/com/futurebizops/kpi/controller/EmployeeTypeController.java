package com.futurebizops.kpi.controller;

import com.futurebizops.kpi.request.DepartmentCreateRequest;
import com.futurebizops.kpi.request.DepartmentUpdateRequest;
import com.futurebizops.kpi.request.EmployeeTypeCreateRequest;
import com.futurebizops.kpi.request.EmployeeTypeUpdateRequest;
import com.futurebizops.kpi.response.KPIResponse;
import com.futurebizops.kpi.response.dropdown.EmployeeTypeDDResponse;
import com.futurebizops.kpi.response.dropdown.RegionDDResponse;
import com.futurebizops.kpi.service.DepartmentService;
import com.futurebizops.kpi.service.EmployeeTypeService;
import io.swagger.v3.oas.annotations.Parameter;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/employee-type")
public class EmployeeTypeController {

    @Autowired
    private EmployeeTypeService employeeTypeService;



    @PostMapping
    public ResponseEntity<KPIResponse> saveDepartmentDetails(@RequestBody EmployeeTypeCreateRequest employeeTypeCreateRequest) {
        KPIResponse response = employeeTypeService.saveEmployeeType(employeeTypeCreateRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<KPIResponse> updateDepartmentDetails(@RequestBody EmployeeTypeUpdateRequest employeeTypeUpdateRequest) {
        KPIResponse response = employeeTypeService.updateEmployeeType(employeeTypeUpdateRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<KPIResponse> findDepartmentDetails(
            @RequestParam(required = false) Integer empTypeId,
            @RequestParam(required = false) String empTypeName,
            @RequestParam(required = false) String statusCd) {
        KPIResponse response = employeeTypeService.findEmployeeTypeDetails(empTypeId, empTypeName, statusCd);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

}
