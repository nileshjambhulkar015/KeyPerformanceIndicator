package com.futurebizops.kpi.controller;

import com.futurebizops.kpi.request.GMUpdateMasterEmployeeRatingReq;
import com.futurebizops.kpi.response.KPIResponse;
import com.futurebizops.kpi.service.EmployeeKeyPerfParamService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping(value = "/gm-approval")
@Slf4j
public class GMApprovalController {

    @Autowired
    private EmployeeKeyPerfParamService keyPerfParamService;

    @Transactional
    @PutMapping
    public ResponseEntity<KPIResponse> updateGMApprovalRequest(@RequestBody GMUpdateMasterEmployeeRatingReq empKPPMasterUpdateRequest) {
        log.info("Inside GMApprovalController >> updateGMApprovalRequest() empKPPMasterUpdateRequest : {}", empKPPMasterUpdateRequest);
        KPIResponse response = keyPerfParamService.updateGMApprovalRequest(empKPPMasterUpdateRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/finish")
    public ResponseEntity<KPIResponse> generateEmployeeKppReport(@RequestParam(required = false) Integer empId, @RequestParam(required = false) String finYear, @RequestParam(required = false) String statusCd) {
        log.info("Inside GMApprovalController >> generateEmployeeKppReport() empId : {}, finYear : {}, statusCd : {}", empId, finYear, statusCd);
        KPIResponse response = keyPerfParamService.generateEmployeeKppReport(empId, finYear, statusCd);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
