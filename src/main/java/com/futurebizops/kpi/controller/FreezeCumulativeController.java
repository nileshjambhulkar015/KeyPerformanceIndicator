package com.futurebizops.kpi.controller;

import com.futurebizops.kpi.request.CumulativeUpdateRequest;
import com.futurebizops.kpi.response.KPIResponse;

import com.futurebizops.kpi.service.FreezeCumulativeService;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@RequestMapping(value = "/freeze-cumulative")
public class FreezeCumulativeController {

    @Autowired
    FreezeCumulativeService freezeCumulativeService;
    @PostMapping
    public ResponseEntity<KPIResponse> saveFreezeCumulativeService(@RequestBody CumulativeUpdateRequest freezeCumulativeCreateRequest) {
        log.info("Inside FreezeCumulativeController >> saveFreezeCumulativeService() freezeCumulativeCreateRequest : {}", freezeCumulativeCreateRequest);
        KPIResponse response = freezeCumulativeService.saveFreezeCumulativeService(freezeCumulativeCreateRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
