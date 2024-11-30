package com.futurebizops.kpi.controller;

import com.futurebizops.kpi.request.CompanyMasterCreateRequest;
import com.futurebizops.kpi.request.EmployeeTypeCreateRequest;
import com.futurebizops.kpi.request.FreezeCumulativeCreateRequest;
import com.futurebizops.kpi.response.KPIResponse;
import com.futurebizops.kpi.service.CumulativeService;
import com.futurebizops.kpi.service.FreezeCumulativeService;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@Slf4j
@RequestMapping(value = "/freeze-cumulative")
public class FreezeCumulativeController {

    @Autowired
    FreezeCumulativeService freezeCumulativeService;
    @PostMapping
    public ResponseEntity<KPIResponse> saveFreezeCumulativeService(@RequestBody FreezeCumulativeCreateRequest freezeCumulativeCreateRequest) {
        KPIResponse response = freezeCumulativeService.saveFreezeCumulativeService(freezeCumulativeCreateRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
