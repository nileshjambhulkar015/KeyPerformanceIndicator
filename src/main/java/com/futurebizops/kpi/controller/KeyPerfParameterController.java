package com.futurebizops.kpi.controller;

import com.futurebizops.kpi.enums.StatusCdEnum;
import com.futurebizops.kpi.request.KeyPerfParamCreateRequest;
import com.futurebizops.kpi.request.KeyPerfParamUpdateRequest;
import com.futurebizops.kpi.response.KPIResponse;
import com.futurebizops.kpi.response.KPPResponse;
import com.futurebizops.kpi.service.KeyPerfParameterService;
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
    public ResponseEntity<KPIResponse> findKeyPerfomanceParam(@RequestParam(required = false) Integer kppId,
                                                              @RequestParam(required = false) Integer roleId,
                                                              @RequestParam(required = false) Integer deptId,
                                                              @RequestParam(required = false) Integer desigId,
                                                              @RequestParam(required = false) String kppObjective,
                                                              @RequestParam(required = false) String statusCd,
                                                              @Parameter(hidden = true) Pageable pageable) {
        KPIResponse response = keyPerfParameterService.findKeyPerfomanceParameterDetails(kppId, roleId, deptId, desigId, kppObjective, statusCd, pageable);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }


    @GetMapping(value = "/{kppId}")
    public ResponseEntity<KPPResponse> findKeyPerfomanceParamById(@PathVariable Integer kppId) {
        KPPResponse response = keyPerfParameterService.findKeyPerfomanceParameterDetailById(kppId);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @GetMapping
    public ResponseEntity<List<KPPResponse>> findAllKeyPerfomanceParam(@RequestParam(required = false) Integer roleId,
                                                                       @RequestParam(required = false) Integer deptId,
                                                                       @RequestParam(required = false) Integer desigId,
                                                                       @RequestParam(required = false) StatusCdEnum statusCdEnum
    ) {
        List<KPPResponse> response = keyPerfParameterService.getKeyPerfomanceParameter(roleId, deptId, desigId, statusCdEnum.getSearchType());
        return new ResponseEntity<>(response, HttpStatus.OK);

    }
}
