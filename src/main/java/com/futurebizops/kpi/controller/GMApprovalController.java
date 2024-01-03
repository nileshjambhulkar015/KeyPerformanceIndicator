package com.futurebizops.kpi.controller;

import com.futurebizops.kpi.request.EmpKPPMasterUpdateRequest;
import com.futurebizops.kpi.request.GMUpdateRequest;
import com.futurebizops.kpi.response.KPIResponse;
import com.futurebizops.kpi.service.EmployeeKeyPerfParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/gm-approval")
public class GMApprovalController {

    @Autowired
    private EmployeeKeyPerfParamService keyPerfParamService;

    @Transactional
    @PutMapping
    public ResponseEntity<KPIResponse> updateEmployeeKeyPerfomanceDetails(@RequestBody EmpKPPMasterUpdateRequest empKPPMasterUpdateRequest) {
        KPIResponse response = keyPerfParamService.updateGMApprovalRequest(empKPPMasterUpdateRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Transactional
    @PutMapping(value = "/report")
    public ResponseEntity<KPIResponse> generateEmployeeKppReport(@RequestParam(required = false) Integer empId,@RequestParam(required = false) String statusCd) {
        KPIResponse response = keyPerfParamService.generateEmployeeKppReport(empId, statusCd);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
