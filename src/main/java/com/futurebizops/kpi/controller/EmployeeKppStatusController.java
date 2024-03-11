package com.futurebizops.kpi.controller;

import com.futurebizops.kpi.response.EmpKppStatusResponse;
import com.futurebizops.kpi.service.EmployeeKppStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping(value = "/employee-kpp-status")
public class EmployeeKppStatusController {

    @Autowired
    private EmployeeKppStatusService employeeKppStatusService;

    //load kpp details as per employee id for hod ratings
    @GetMapping
    public ResponseEntity<EmpKppStatusResponse> getEmployeeKppStatus(@RequestParam(required = false) Integer empId) {
        EmpKppStatusResponse response = employeeKppStatusService.getInPrgressEmployeeKppStatus(empId);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }
}
