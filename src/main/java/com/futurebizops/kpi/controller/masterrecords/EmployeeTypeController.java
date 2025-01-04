package com.futurebizops.kpi.controller.masterrecords;

import com.futurebizops.kpi.request.DepartmentCreateRequest;
import com.futurebizops.kpi.request.DepartmentUpdateRequest;
import com.futurebizops.kpi.request.EmployeeTypeCreateRequest;
import com.futurebizops.kpi.request.EmployeeTypeUpdateRequest;
import com.futurebizops.kpi.response.EmployeeTypeResponse;
import com.futurebizops.kpi.response.KPIResponse;
import com.futurebizops.kpi.response.dropdown.EmployeeTypeDDResponse;
import com.futurebizops.kpi.response.dropdown.RegionDDResponse;
import com.futurebizops.kpi.service.DepartmentService;
import com.futurebizops.kpi.service.EmployeeTypeService;
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
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/employee-type")
@Slf4j
public class EmployeeTypeController {

    @Autowired
    private EmployeeTypeService employeeTypeService;

    @PostMapping
    public ResponseEntity<KPIResponse> saveEmployeeType(@RequestBody EmployeeTypeCreateRequest employeeTypeCreateRequest) {
        log.info("Inside EmployeeTypeController >> saveEmployeeType() employeeTypeCreateRequest : {}", employeeTypeCreateRequest);

        KPIResponse response = employeeTypeService.saveEmployeeType(employeeTypeCreateRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<KPIResponse> updateEmployeeType(@RequestBody EmployeeTypeUpdateRequest employeeTypeUpdateRequest) {
        log.info("Inside EmployeeTypeController >> updateEmployeeType() employeeTypeUpdateRequest : {}", employeeTypeUpdateRequest);
        KPIResponse response = employeeTypeService.updateEmployeeType(employeeTypeUpdateRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<KPIResponse> findEmployeeTypeDetails(
            @RequestParam(required = false) Integer empTypeId,
            @RequestParam(required = false) String empTypeName,
            @RequestParam(required = false) String statusCd) {
        log.info("Inside EmployeeTypeController >> findEmployeeTypeDetails() empTypeId : {}, empTypeName : {}", empTypeId, empTypeName);
        KPIResponse response = employeeTypeService.findEmployeeTypeDetails(empTypeId, empTypeName, statusCd);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @GetMapping(value = "/by-emptypeid")
    public ResponseEntity<EmployeeTypeResponse> findEmployeeTypeDetailsByEmpTypeId(
            @RequestParam(required = false) Integer empTypeId) {
        log.info("Inside EmployeeTypeController >> findEmployeeTypeDetailsByEmpTypeId() empTypeId : {}", empTypeId);
        EmployeeTypeResponse response = employeeTypeService.findEmployeeTypeDetailsByEmpTypeId(empTypeId);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @DeleteMapping
    public ResponseEntity<KPIResponse> deleteEmployeeTypeDetails(@RequestParam(required = false) Integer empTypeId) {
        log.info("Inside EmployeeTypeController >> deleteEmployeeTypeDetails() empTypeId : {}", empTypeId);
        KPIResponse response = employeeTypeService.deleteEmployeeTypeDetails(empTypeId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
