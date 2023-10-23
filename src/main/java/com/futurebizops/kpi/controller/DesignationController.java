package com.futurebizops.kpi.controller;

import com.futurebizops.kpi.enums.DesignationSearchEnum;
import com.futurebizops.kpi.enums.PageDirection;
import com.futurebizops.kpi.enums.StatusCdEnum;
import com.futurebizops.kpi.request.DesignationCreateRequest;
import com.futurebizops.kpi.request.DesignationUpdateRequest;
import com.futurebizops.kpi.response.DepartmentReponse;
import com.futurebizops.kpi.response.DesignationReponse;
import com.futurebizops.kpi.response.KPIResponse;
import com.futurebizops.kpi.service.DesignationService;
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

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/designation")
public class DesignationController {

    @Autowired
    private DesignationService designationService;

    @PostMapping
    public ResponseEntity<KPIResponse> saveDesignationDetails(@RequestBody DesignationCreateRequest designationCreateRequest) {
        KPIResponse response = designationService.saveDesignation(designationCreateRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<KPIResponse> updateDesignationDetails(@RequestBody DesignationUpdateRequest designationUpdateRequest) {
        KPIResponse response = designationService.updateDesignation(designationUpdateRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/search")
    @PageableAsQueryParam
    public ResponseEntity<KPIResponse> findDesignationDetails(@RequestParam(required = false) Integer deptId,
                                                              @RequestParam(required = false) String desigName,
                                                              @RequestParam(required = false) String statusCd,
                                                              @Parameter(hidden = true) Pageable pageable) {
        KPIResponse response = designationService.findDesignationDetails(deptId, desigName, statusCd, pageable);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @GetMapping(value = "/desigId")
    public ResponseEntity<DesignationReponse> findDesignationDetails(@RequestParam(required = false) Integer desigId) {
        DesignationReponse response = designationService.findDesignationById(desigId);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @GetMapping
    public ResponseEntity<Object> findAllDesignationDetails(@RequestParam(required = false) Integer deptId) {
        return new ResponseEntity<>(designationService.findAllDesignationDetails(deptId), HttpStatus.OK);
    }

    @GetMapping(value = "/department")
    public ResponseEntity<Object> findAllDepartmentByDesig(@RequestParam(required = false) Integer deptId) {
        return new ResponseEntity<>(designationService.getAllDepartmentByDesig(deptId), HttpStatus.OK);

    }

}
