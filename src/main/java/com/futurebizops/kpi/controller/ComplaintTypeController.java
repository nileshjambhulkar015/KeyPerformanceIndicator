package com.futurebizops.kpi.controller;


import com.futurebizops.kpi.request.ComplaintTypeCreateRequest;
import com.futurebizops.kpi.request.ComplaintTypeUpdateRequest;
import com.futurebizops.kpi.response.KPIResponse;
import com.futurebizops.kpi.response.dropdown.ComplaintTypeDDResponse;
import com.futurebizops.kpi.service.ComplaintTypeService;
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
@RequestMapping(value = "/complaint-type")
public class ComplaintTypeController {

    @Autowired
    private ComplaintTypeService complaintTypeService;

    @PostMapping
    public ResponseEntity<KPIResponse> saveDepartmentDetails(@RequestBody ComplaintTypeCreateRequest complaintTypeCreateRequest) {
        KPIResponse response = complaintTypeService.saveComplaintType(complaintTypeCreateRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/search")
    @PageableAsQueryParam
    public ResponseEntity<KPIResponse> findComplaintTypeDetails(
            @RequestParam(required = false) Integer compTypeId,
            @RequestParam(required = false) String compTypeName,
            @RequestParam(required = false) String statusCd,
            @Parameter(hidden = true) Pageable pageable) {
        KPIResponse response = complaintTypeService.findComplaintTypeDetails(compTypeId, compTypeName, statusCd, pageable);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }
    // show department details when click on view button of department ui table
    @GetMapping(value = "/by-complaint-type-id")
    public ResponseEntity<Object> findAllDepartmentById(@RequestParam(required = false) Integer compTypeId) {
        return new ResponseEntity<>(complaintTypeService.findAllComplaintTypeById(compTypeId), HttpStatus.OK);
    }


    @PutMapping
    public ResponseEntity<KPIResponse> updateComplaintTypeDetails(@RequestBody ComplaintTypeUpdateRequest complaintTypeUpdateRequest) {
        KPIResponse response = complaintTypeService.updateComplaintType(complaintTypeUpdateRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping (value = "/all-dd-comp-type")
    public ResponseEntity<List<ComplaintTypeDDResponse>> findAllComlaintType() {
        List<ComplaintTypeDDResponse> response = complaintTypeService.findAllComlaintType();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
