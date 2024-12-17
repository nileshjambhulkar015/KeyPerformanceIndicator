package com.futurebizops.kpi.controller;

import com.futurebizops.kpi.request.CumulativeUpdateRequest;
import com.futurebizops.kpi.request.yearlykpprequest.FreezeEmpKPPMasterRequest;
import com.futurebizops.kpi.response.KPIResponse;
import com.futurebizops.kpi.response.dropdown.FinancialYearDDResponse;
import com.futurebizops.kpi.response.dropdown.FreezeFinancialYearDDResponse;
import com.futurebizops.kpi.service.FreezeCumulativeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@Slf4j
@RequestMapping(value = "/freeze-cumulative")
public class FreezeCumulativeController {

    @Autowired
    FreezeCumulativeService freezeCumulativeService;
    @PostMapping
    public ResponseEntity<KPIResponse> saveFreezeCumulativeService(@RequestBody CumulativeUpdateRequest freezeCumulativeCreateRequest) {
        KPIResponse response = freezeCumulativeService.saveFreezeCumulativeService(freezeCumulativeCreateRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = "/employee-kpp-feedback")
    public ResponseEntity<KPIResponse> saveEmployeeKPPFeedbackDetails(@RequestBody FreezeEmpKPPMasterRequest freezeEmpKPPMasterRequest) {
        System.out.println("freezeEmpKPPMasterRequest : "+freezeEmpKPPMasterRequest);
        KPIResponse response = freezeCumulativeService.saveEmployeeKPPFeedbackDetails(freezeEmpKPPMasterRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/dd-freeze-fin-year")
    public ResponseEntity<List<FreezeFinancialYearDDResponse>> ddAllFinancialYear() {
        List<FreezeFinancialYearDDResponse> response = freezeCumulativeService.ddAllFinancialYear();
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

}
