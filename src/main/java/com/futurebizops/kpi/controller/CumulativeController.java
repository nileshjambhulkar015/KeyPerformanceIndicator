package com.futurebizops.kpi.controller;

import com.futurebizops.kpi.response.EmpKppStatusResponse;
import com.futurebizops.kpi.response.KPIResponse;
import com.futurebizops.kpi.service.CumulativeService;
import io.swagger.v3.oas.annotations.Parameter;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping(value = "/cumulative")
public class CumulativeController {

    @Autowired
    CumulativeService cumulativeService;

    //load kpp details as per employee id for hod ratings
    @GetMapping("/hod-employee-all")
    @PageableAsQueryParam
    public ResponseEntity<KPIResponse> getEmployeeKppStatus(  @RequestParam(required = false) String fromDate,
                                                                       @RequestParam(required = false) String toDate,
                                                                       @RequestParam(required = false) Integer reportingEmpId,
                                                                       @RequestParam(required = false) Integer gmEmployeedId,
                                                                       @RequestParam(required = false) Integer empId,
                                                                       @RequestParam(required = false) String empEId,
                                                                       @RequestParam(required = false) Integer roleId,
                                                                       @RequestParam(required = false) Integer deptId,
                                                                       @RequestParam(required = false) Integer desigId,
                                                                       @RequestParam(required = false) String empFirstName,
                                                                       @RequestParam(required = false) String empMiddleName,
                                                                       @RequestParam(required = false) String empLastName,
                                                                       @RequestParam(required = false) String empMobileNo,
                                                                       @RequestParam(required = false) String emailId,
                                                                       @RequestParam(required = false) String statusCd,
                                                                       @RequestParam(required = false) String empKppStatus,
                                                                       @RequestParam(required = false) String hodKppStatus,
                                                                       @RequestParam(required = false) String gmKppStatus,
                                                                       @Parameter(hidden = true) Pageable pageable) {
        KPIResponse response = cumulativeService.allEmployeeKppDetails(fromDate, toDate, reportingEmpId, gmEmployeedId, empId, empEId, roleId, deptId, desigId, empFirstName, empMiddleName, empLastName, empMobileNo, emailId, statusCd, empKppStatus, hodKppStatus, gmKppStatus, pageable);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }
}