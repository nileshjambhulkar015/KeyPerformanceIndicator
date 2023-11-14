package com.futurebizops.kpi.controller;

import com.futurebizops.kpi.request.DepartmentCreateRequest;
import com.futurebizops.kpi.response.KPIResponse;
import com.futurebizops.kpi.service.EmployeeLoginService;
import org.springframework.beans.factory.annotation.Autowired;
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

@CrossOrigin
@RestController
@RequestMapping(value = "/login")
public class EmployeeLoginController {

    @Autowired
    private EmployeeLoginService employeeLoginService;

    @GetMapping
    public ResponseEntity<KPIResponse> findPartDetails(@RequestParam(required = false) String userName,
                                                       @RequestParam(required = false) String userPassword) {
        KPIResponse response = employeeLoginService.employeeLogin(userName, userPassword);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping(value = "/change-password")
    public ResponseEntity<KPIResponse> changePasswords(@RequestParam(required = false) String userName,
                                                             @RequestParam(required = false) String userPassword) {
        KPIResponse response = employeeLoginService.updateLoginPassword(userName, userPassword);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
