package com.futurebizops.kpi.controller;

import com.futurebizops.kpi.response.KPIResponse;
import com.futurebizops.kpi.service.BirthdayEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping(value = "/birthday-wish")
public class BirthdayWishesController {

    @Autowired
    BirthdayEmailService birthdayEmailService;

    @GetMapping
    public ResponseEntity<KPIResponse> findDepartmentDetails() throws Exception {
        KPIResponse response = birthdayEmailService.getBirthdays();
        return new ResponseEntity<>(response, HttpStatus.OK);

    }
}
