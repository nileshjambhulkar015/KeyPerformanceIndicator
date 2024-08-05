package com.futurebizops.kpi.controller;


import com.futurebizops.kpi.request.RequestTypeCreateRequest;
import com.futurebizops.kpi.request.RequestTypeUpdateRequest;
import com.futurebizops.kpi.response.KPIResponse;
import com.futurebizops.kpi.response.dropdown.DepartmentDDResponse;
import com.futurebizops.kpi.response.dropdown.RequestTypeDDResponse;
import com.futurebizops.kpi.service.RequestTypeService;
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
@RequestMapping(value = "/request-type")
public class RequestTypeController {

    @Autowired
    private RequestTypeService requestTypeService;

    @PostMapping
    public ResponseEntity<KPIResponse> saveRequestDetails(@RequestBody RequestTypeCreateRequest requestTypeCreateRequest) {
        KPIResponse response = requestTypeService.saveRequestType(requestTypeCreateRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/search")
    @PageableAsQueryParam
    public ResponseEntity<KPIResponse> findRequestTypeDetails(
            @RequestParam(required = false) Integer reqTypeId,
            @RequestParam(required = false) String reqTypeName,
            @RequestParam(required = false) String statusCd,
            @Parameter(hidden = true) Pageable pageable) {
        KPIResponse response = requestTypeService.findRequestTypeDetails(reqTypeId, reqTypeName, statusCd, pageable);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    // show department details when click on view button of department ui table
    @GetMapping(value = "/by-request-type-id")
    public ResponseEntity<Object> findAllDepartmentById(@RequestParam(required = false) Integer reqTypeId) {
        return new ResponseEntity<>(requestTypeService.findAllRequestTypeById(reqTypeId), HttpStatus.OK);
    }


    @PutMapping
    public ResponseEntity<KPIResponse> updateRequestTypeDetails(@RequestBody RequestTypeUpdateRequest requestTypeUpdateRequest) {
        KPIResponse response = requestTypeService.updateRequestType(requestTypeUpdateRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/req-type-dd-dept")
    public ResponseEntity<List<DepartmentDDResponse>> findAllDepartmentFromRequestType() {
        List<DepartmentDDResponse> response = requestTypeService.findAllDepartmentFromRequestType();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/dd-req-type-by-dept-id")
    public ResponseEntity<List<RequestTypeDDResponse>> findAllRequestTypeByDeptId(@RequestParam(required = false) Integer reqTypeDeptId) {
        List<RequestTypeDDResponse> response = requestTypeService.findAllRequestTypeByDeptId(reqTypeDeptId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
