package com.futurebizops.kpi.controller;

import com.futurebizops.kpi.enums.DepartmentSearchEnum;
import com.futurebizops.kpi.enums.KeyPerfParamSearchEnum;
import com.futurebizops.kpi.enums.PageDirection;
import com.futurebizops.kpi.enums.StatusCdEnum;
import com.futurebizops.kpi.request.DepartmentCreateRequest;
import com.futurebizops.kpi.request.DepartmentUpdateRequest;
import com.futurebizops.kpi.request.KeyPerfParamCreateRequest;
import com.futurebizops.kpi.request.KeyPerfParamUpdateRequest;
import com.futurebizops.kpi.response.KPIResponse;
import com.futurebizops.kpi.service.DepartmentService;
import com.futurebizops.kpi.service.KeyPerfParameterService;
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
@RequestMapping(value = "/key-perform-parameter")
public class KeyPerfParameterController {

    @Autowired
    private KeyPerfParameterService keyPerfParameterService;

    @PostMapping
    public ResponseEntity<KPIResponse> saveKeyPerfomanceParamDetails(@RequestBody KeyPerfParamCreateRequest keyPerfParamCreateRequest) {
        KPIResponse response = keyPerfParameterService.saveKeyPerfomanceParameter(keyPerfParamCreateRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<KPIResponse> updateKeyPerfomanceParamDetails(@RequestBody KeyPerfParamUpdateRequest keyPerfParamUpdateRequest) {
        KPIResponse response = keyPerfParameterService.updateKeyPerfomanceParameter(keyPerfParamUpdateRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/search")
    @PageableAsQueryParam
    public ResponseEntity<KPIResponse> findKeyPerfomanceParam(@RequestParam(required = false) KeyPerfParamSearchEnum searchEnum,
                                                             @RequestParam(required = false) String searchString,
                                                             @RequestParam(required = false) StatusCdEnum statusCdEnum,
                                                             @Parameter(hidden = true) Pageable pageable,
                                                             @Parameter(hidden = true) PageDirection pageDirection,
                                                             @Parameter(hidden = true) String sortParam) {
        KPIResponse response = keyPerfParameterService.findKeyPerfomanceParameterDetails(searchEnum, searchString, statusCdEnum, pageable, sortParam, KPIUtils.getDirection(pageDirection));
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @GetMapping
    public ResponseEntity<KPIResponse> findAllKeyPerfomanceParam(@RequestParam(required = false) Integer deptId,
                                                              @RequestParam(required = false) Integer desigId,
                                                              @RequestParam(required = false) StatusCdEnum statusCdEnum
                                                              ) {
        KPIResponse response = keyPerfParameterService.getKeyPerfomanceParameter(deptId, desigId, statusCdEnum.getSearchType());
        return new ResponseEntity<>(response, HttpStatus.OK);

    }
}
