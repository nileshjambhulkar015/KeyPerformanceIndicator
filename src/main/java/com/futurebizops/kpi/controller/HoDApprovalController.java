package com.futurebizops.kpi.controller;

import com.futurebizops.kpi.request.EmployeeKeyPerfParamCreateRequest;
import com.futurebizops.kpi.request.HODApprovalUpdateRequest;
import com.futurebizops.kpi.response.EmployeeResponse;
import com.futurebizops.kpi.response.HodEmployeeResponse;
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
@RequestMapping(value = "/hod-approval")
public class HoDApprovalController {
    @Autowired
    private EmployeeKeyPerfParamService keyPerfParamService;

    @PutMapping
    public ResponseEntity<KPIResponse> updateEmployeeKeyPerfomanceDetails(@RequestBody List<HODApprovalUpdateRequest> hodApprovalUpdateRequests) {
        KPIResponse response = keyPerfParamService.updateHoDApprovalRequest(hodApprovalUpdateRequests);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/employee")
    @PageableAsQueryParam
    public ResponseEntity<KPIResponse> getAllEmployee(@RequestParam(required = false) Integer reportingEmployee,
            @RequestParam(required = false) Integer empId,
                                                      @RequestParam(required = false) Integer desigId,
                                                      @RequestParam(required = false) String empFirstName,
                                                      @RequestParam(required = false) String empMiddleName,
                                                      @RequestParam(required = false) String empLastName,
                                                      @RequestParam(required = false) String empMobileNo,
                                                      @RequestParam(required = false) String emailId,
                                                      @RequestParam(required = false) String statusCd,
                                                      @Parameter(hidden = true) Pageable pageable) {
        KPIResponse response = keyPerfParamService.getAllEmployeeDetailsForHod(reportingEmployee, empId, desigId, empFirstName, empMiddleName, empLastName, empMobileNo, emailId, statusCd, pageable);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
