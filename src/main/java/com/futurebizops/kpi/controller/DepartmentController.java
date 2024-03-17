package com.futurebizops.kpi.controller;


import com.futurebizops.kpi.request.DepartmentCreateRequest;
import com.futurebizops.kpi.request.DepartmentUpdateRequest;
import com.futurebizops.kpi.response.DepartmentReponse;
import com.futurebizops.kpi.response.KPIResponse;
import com.futurebizops.kpi.service.DepartmentService;
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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    public ResponseEntity<KPIResponse> findDepartmentDetails(
                                                             @RequestParam(required = false) Integer deptId,
                                                             @RequestParam(required = false) String deptName,
                                                             @RequestParam(required = false) String statusCd,
                                                             @Parameter(hidden = true) Pageable pageable) {
        KPIResponse response = departmentService.findDepartmentDetails(deptId, deptName, statusCd, pageable);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @GetMapping
    public ResponseEntity<List<DepartmentReponse>> getAllDepartmentDetails() {
        List<DepartmentReponse> response = departmentService.findAllDepartmentDetails();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // show department details when click on view button of department ui table
    @GetMapping(value = "/by-dept-id")
    public ResponseEntity<Object> findAllDepartmentById(@RequestParam(required = false) Integer deptId) {
        return new ResponseEntity<>(departmentService.findAllDepartmentById(deptId), HttpStatus.OK);
    }

    //upload excel
    @PostMapping (value = "/upload-department")
    public void uploadDeptExcelFile(@RequestParam("file") MultipartFile file) throws IOException {
        departmentService.uploadDeptExcelFile(file);
    }


}