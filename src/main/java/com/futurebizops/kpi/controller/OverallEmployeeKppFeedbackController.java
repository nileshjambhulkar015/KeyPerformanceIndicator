package com.futurebizops.kpi.controller;

import com.futurebizops.kpi.request.yearlykpprequest.FreezeEmpKPPMasterRequest;
import com.futurebizops.kpi.response.KPIResponse;

import com.futurebizops.kpi.response.dropdown.KppFinancialYearDDResponse;
import com.futurebizops.kpi.service.OverallEmployeeKppFeedbackService;
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

import java.util.List;

@CrossOrigin
@RestController
@Slf4j
@RequestMapping(value = "/overall-kpp-feedback")
public class OverallEmployeeKppFeedbackController {

    @Autowired
    OverallEmployeeKppFeedbackService overallEmployeeKppFeedbackService;



    @GetMapping(value = "/yearly-kpp")
    public ResponseEntity<KPIResponse> getEmployeeKppDataYearly(@RequestParam(required = false) Integer empId, @RequestParam(required = false) String finYear) {
        KPIResponse response = overallEmployeeKppFeedbackService.getEmployeeKppDataYearly(empId,finYear);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @PostMapping(value = "/employee-kpp-feedback")
    public ResponseEntity<KPIResponse> saveEmployeeKPPFeedbackDetails(@RequestBody FreezeEmpKPPMasterRequest freezeEmpKPPMasterRequest) {
        System.out.println("freezeEmpKPPMasterRequest : "+freezeEmpKPPMasterRequest);
        KPIResponse response = overallEmployeeKppFeedbackService.saveEmployeeKPPFeedbackDetails(freezeEmpKPPMasterRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/dd-overall-fin-year")
    public ResponseEntity<List<KppFinancialYearDDResponse>> ddAllFinancialYear() {
        List<KppFinancialYearDDResponse> response = overallEmployeeKppFeedbackService.ddAllFinancialYear();
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @GetMapping(value = "/employee")
    @PageableAsQueryParam
    public ResponseEntity<KPIResponse> getAllEmployeeKppFeedbackDetails(@RequestParam(required = false) Integer empId,

                                                      @RequestParam(required = false) String finYear,
                                                      @RequestParam(required = false)  Integer reportingEmpId,
                                                                        @RequestParam(required = false)  Integer gmEmpId,
                                                      @Parameter(hidden = true) Pageable pageable) {

        KPIResponse response = overallEmployeeKppFeedbackService.getAllEmployeeKppFeedbackDetails(empId, finYear,reportingEmpId,gmEmpId, pageable);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
