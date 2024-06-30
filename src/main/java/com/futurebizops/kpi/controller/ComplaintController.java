package com.futurebizops.kpi.controller;

import com.futurebizops.kpi.request.ComplaintCreateRequest;
import com.futurebizops.kpi.request.DepartmentCreateRequest;
import com.futurebizops.kpi.response.KPIResponse;
import com.futurebizops.kpi.service.ComplaintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping(value = "/complaint")
public class ComplaintController {

    @Autowired
    ComplaintService complaintService;

    @PostMapping
    public ResponseEntity<KPIResponse> saveComplaintDetails(@RequestBody ComplaintCreateRequest complaintCreateRequest) {
        KPIResponse response = complaintService.saveComplaint(complaintCreateRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
