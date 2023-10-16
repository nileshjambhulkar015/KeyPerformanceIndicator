package com.futurebizops.kpi.controller;

import com.futurebizops.kpi.enums.EmployeeSearchEnum;
import com.futurebizops.kpi.enums.PageDirection;
import com.futurebizops.kpi.enums.StatusCdEnum;
import com.futurebizops.kpi.request.EmployeeCreateRequest;
import com.futurebizops.kpi.request.EmployeeUpdateRequest;
import com.futurebizops.kpi.response.KPIResponse;
import com.futurebizops.kpi.service.EmployeeService;
import com.futurebizops.kpi.utils.KPIUtils;
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

    @GetMapping(value = "/search")
    @PageableAsQueryParam
    public ResponseEntity<KPIResponse> getAllEmployee(@RequestParam(required = false) EmployeeSearchEnum searchEnum,
                                                     @RequestParam(required = false) String searchString,
                                                     @RequestParam(required = false) StatusCdEnum statusCdEnum,
                                                     @Parameter(hidden = true) Pageable pageable,
                                                     @Parameter(hidden = true) PageDirection pageDirection,
                                                     @Parameter(hidden = true) String sortParam) {
        KPIResponse response = employeeService.findEmployee(searchEnum, searchString, statusCdEnum, pageable, sortParam, KPIUtils.getDirection(pageDirection));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Object> findAllDepartmentDetailsForEmployee() {
        return new ResponseEntity<>(employeeService.findAllEmployeeDetails(), HttpStatus.OK);

    }
}
