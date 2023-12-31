package com.futurebizops.kpi.controller;

import com.futurebizops.kpi.request.EmpKPPMasterUpdateRequest;
import com.futurebizops.kpi.request.EmployeeKeyPerfParamCreateRequest;
import com.futurebizops.kpi.request.HODApprovalUpdateRequest;
import com.futurebizops.kpi.response.HodEmploeeKppResponse;
import com.futurebizops.kpi.response.KPIResponse;
import com.futurebizops.kpi.service.EmployeeKeyPerfParamService;
import io.swagger.v3.oas.annotations.Parameter;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/hod-approval")
public class HoDApprovalController {
    @Autowired
    private EmployeeKeyPerfParamService keyPerfParamService;

    @PutMapping
    public ResponseEntity<KPIResponse> updateEmployeeKeyPerfomanceDetails(@RequestBody EmpKPPMasterUpdateRequest empKPPMasterUpdateRequest) {
        KPIResponse response = keyPerfParamService.updateHoDApprovalRequest(empKPPMasterUpdateRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    //load kpp details as per employee id for hod ratings
    @GetMapping(value = "/employee-kpp")
    public ResponseEntity<List<HodEmploeeKppResponse>> getEmployeeForHodRatings(@RequestParam(required = false) Integer empId,@RequestParam(required = false) String empEId,
                                                                        @RequestParam(required = false) String statusCd) {
        List<HodEmploeeKppResponse> response = keyPerfParamService.getEmployeeForHodRatings(empId,empEId, statusCd);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

}
