package com.futurebizops.kpi.controller;

import com.futurebizops.kpi.request.yearlykpprequest.FinishKppFeedbackRequest;
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
import org.springframework.web.bind.annotation.PutMapping;
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
        log.info("Inside OverallEmployeeKppFeedbackController >> getEmployeeKppDataYearly() empId : {}, finYear : {}", empId, finYear);
        KPIResponse response = overallEmployeeKppFeedbackService.getEmployeeKppDataYearly(empId, finYear);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = "/employee-kpp-feedback")
    public ResponseEntity<KPIResponse> saveEmployeeKPPFeedbackDetails(@RequestBody FreezeEmpKPPMasterRequest freezeEmpKPPMasterRequest) {
        log.info("Inside OverallEmployeeKppFeedbackController >> saveEmployeeKPPFeedbackDetails() freezeEmpKPPMasterRequest : {}", freezeEmpKPPMasterRequest);
        KPIResponse response = overallEmployeeKppFeedbackService.saveEmployeeKPPFeedbackDetails(freezeEmpKPPMasterRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping(value = "/gm-kpp-feedback")
    public ResponseEntity<KPIResponse> updateGMKPPFeedbackForEmployee(@RequestBody FreezeEmpKPPMasterRequest freezeEmpKPPMasterRequest) {
        log.info("Inside OverallEmployeeKppFeedbackController >> updateGMKPPFeedbackForEmployee() freezeEmpKPPMasterRequest : {}", freezeEmpKPPMasterRequest);
        KPIResponse response = overallEmployeeKppFeedbackService.updateGMKPPFeedbackForEmployee(freezeEmpKPPMasterRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping(value = "/gm-kpp-feedback-finish")
    public ResponseEntity<KPIResponse> finishByGMKppFeedback(@RequestBody FinishKppFeedbackRequest finishKppFeedbackRequest) {
        log.info("Inside OverallEmployeeKppFeedbackController >> finishByGMKppFeedback() finishKppFeedbackRequest : {}", finishKppFeedbackRequest);
        KPIResponse response = overallEmployeeKppFeedbackService.finishByGMKppFeedback(finishKppFeedbackRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping(value = "/hod-kpp-feedback-employee")
    public ResponseEntity<KPIResponse> updateHODKPPFeedbackForEmployee(@RequestBody FreezeEmpKPPMasterRequest freezeEmpKPPMasterRequest) {
        log.info("Inside OverallEmployeeKppFeedbackController >> updateHODKPPFeedbackForEmployee() freezeEmpKPPMasterRequest : {}", freezeEmpKPPMasterRequest);
        KPIResponse response = overallEmployeeKppFeedbackService.updateHODKPPFeedbackForEmployee(freezeEmpKPPMasterRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/dd-overall-fin-year")
    public ResponseEntity<List<KppFinancialYearDDResponse>> ddAllFinancialYear() {
        log.info("Inside OverallEmployeeKppFeedbackController >> ddAllFinancialYear()");
        List<KppFinancialYearDDResponse> response = overallEmployeeKppFeedbackService.ddAllFinancialYear();
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @GetMapping(value = "/dd-overall-completed-fin-year")
    public ResponseEntity<List<KppFinancialYearDDResponse>> ddCompletedAllFinancialYear() {
        log.info("Inside OverallEmployeeKppFeedbackController >> ddCompletedAllFinancialYear()");
        List<KppFinancialYearDDResponse> response = overallEmployeeKppFeedbackService.ddCompletedAllFinancialYear();
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @GetMapping(value = "/employee")
    @PageableAsQueryParam
    public ResponseEntity<KPIResponse> getAllEmployeeKppFeedbackDetails(@RequestParam(required = false) Integer empId,
                                                                        @RequestParam(required = false) Integer roleId,
                                                                        @RequestParam(required = false) String finYear,
                                                                        @RequestParam(required = false) Integer reportingEmpId,
                                                                        @RequestParam(required = false) Integer gmEmpId,
                                                                        @RequestParam(required = false) String empKppStatus,
                                                                        @RequestParam(required = false) String hodKppStatus,
                                                                        @RequestParam(required = false) String gmKppStatus,
                                                                        @Parameter(hidden = true) Pageable pageable) {
        log.info("Inside OverallEmployeeKppFeedbackController >> getAllEmployeeKppFeedbackDetails() empId : {}, roleId : {}, finYear : {}, reportingEmpId : {}, gmEmpId : {}, empKppStatus : {}, hodKppStatus : {}, gmKppStatus : {}", empId, roleId, finYear, reportingEmpId, gmEmpId, empKppStatus, hodKppStatus, gmKppStatus);

        KPIResponse response = overallEmployeeKppFeedbackService.getAllEmployeeKppFeedbackDetails(empId, roleId, finYear, reportingEmpId, gmEmpId,empKppStatus,hodKppStatus,gmKppStatus, pageable);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
