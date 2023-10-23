package com.futurebizops.kpi.controller;


import com.futurebizops.kpi.enums.DepartmentSearchEnum;
import com.futurebizops.kpi.enums.PageDirection;
import com.futurebizops.kpi.enums.StatusCdEnum;
import com.futurebizops.kpi.request.DepartmentCreateRequest;
import com.futurebizops.kpi.request.DepartmentUpdateRequest;
import com.futurebizops.kpi.response.DepartmentReponse;
import com.futurebizops.kpi.response.KPIResponse;
import com.futurebizops.kpi.service.DepartmentService;
import com.futurebizops.kpi.utils.KPIUtils;
import io.swagger.v3.oas.annotations.Parameter;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
@RequestMapping(value = "/department")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @PostMapping
    public ResponseEntity<KPIResponse> saveDepartmentDetails(@RequestBody DepartmentCreateRequest departmentCreateRequest) {
        KPIResponse response = departmentService.saveDepartment(departmentCreateRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<KPIResponse> updateDepartmentDetails(@RequestBody DepartmentUpdateRequest departmentUpdateRequest) {
        KPIResponse response = departmentService.updateDepartment(departmentUpdateRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/search")
    @PageableAsQueryParam
    public ResponseEntity<KPIResponse> findDepartmentDetails(@RequestParam(required = false) DepartmentSearchEnum searchEnum,
                                                      @RequestParam(required = false) String searchString,
                                                      @RequestParam(required = false) StatusCdEnum statusCdEnum,
                                                      @Parameter(hidden = true) Pageable pageable,
                                                      @Parameter(hidden = true) PageDirection pageDirection,
                                                      @Parameter(hidden = true) String sortParam) {
        KPIResponse response = departmentService.findDepartmentDetails(searchEnum, searchString, statusCdEnum, pageable, sortParam, KPIUtils.getDirection(pageDirection));
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @GetMapping
    public ResponseEntity<List<DepartmentReponse>> getAllDepartmentDetails() {
        List<DepartmentReponse> response = departmentService.findAllDepartmentDetails();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }



    @GetMapping(value = "/{deptId}")
    public ResponseEntity<Object> findAllDepartmentById(@PathVariable Integer deptId) {
        return new ResponseEntity<>(departmentService.findAllDepartmentById(deptId), HttpStatus.OK);
    }
}