package com.futurebizops.kpi.controller;

import com.futurebizops.kpi.request.EmployeeKeyPerfParamCreateRequest;
import com.futurebizops.kpi.request.HODApprovalUpdateRequest;
import com.futurebizops.kpi.response.KPIResponse;
import com.futurebizops.kpi.service.EmployeeKeyPerfParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/hod-approval")
public class HoDApprovalController {
    @Autowired
    private EmployeeKeyPerfParamService keyPerfParamService;

    @PutMapping
    public ResponseEntity<KPIResponse> updateEmployeeKeyPerfomanceDetails(@RequestBody List<HODApprovalUpdateRequest> hodApprovalUpdateRequests) {
        KPIResponse response = keyPerfParamService.updateHoDApprovalRequest(hodApprovalUpdateRequests);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
