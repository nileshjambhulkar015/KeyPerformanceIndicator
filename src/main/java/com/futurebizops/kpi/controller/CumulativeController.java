package com.futurebizops.kpi.controller;

import com.futurebizops.kpi.request.CompanyMasterCreateRequest;
import com.futurebizops.kpi.request.CumulativeUpdateRequest;
import com.futurebizops.kpi.response.KPIResponse;

import com.futurebizops.kpi.response.dropdown.KppFinancialYearDDResponse;
import com.futurebizops.kpi.service.CumulativeService;
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
@RequestMapping(value = "/cumulative")
public class CumulativeController {

    @Autowired
    CumulativeService cumulativeService;



    //get status of kpp  for employee, HOD and GM to approve or reject kpp details
    @GetMapping(value = "/employee-kpp-cumulative")
    @PageableAsQueryParam
    public ResponseEntity<KPIResponse> getAllEmployeeKPPForReport(
            @RequestParam(required = false) String fromDate,
            @RequestParam(required = false) String toDate,
            @RequestParam(required = false) Integer empId,
            @RequestParam(required = false) Integer roleId,
            @RequestParam(required = false) String statusCd,
            @Parameter(hidden = true) Pageable pageable) {
        log.info("Inside CumulativeController >> getAllEmployeeKPPStatusReport() fromDate : {}, toDate : {}, empId : {}, roleId : {}", fromDate, toDate,empId, roleId);
        KPIResponse response = cumulativeService.getAllEmployeeKPPStatusReport(fromDate, toDate,  empId, roleId,  statusCd, pageable);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @GetMapping("/hod-cummulatve")
    // @PageableAsQueryParam
    public ResponseEntity<KPIResponse> allEmployeeKppDetails(@RequestParam(required = false) String fromDate,
                                                                  @RequestParam(required = false) String toDate,
                                                                  @RequestParam(required = false) Integer roleId,
                                                                  @RequestParam(required = false) Integer deptId,
                                                                  @RequestParam(required = false) Integer desigId,
                                                                  @RequestParam(required = false) Integer reportingEmpId,
                                                                  @RequestParam(required = false) Integer gmEmpId,
                                                                  @Parameter(hidden = true) Pageable pageable) {
        log.info("Inside CumulativeController >> getAllEmployeeKPPStatusReport() fromDate : {}, toDate : {}, deptId : {}, roleId : {},desigId : {}", fromDate, toDate,deptId, roleId,desigId);
        KPIResponse response = cumulativeService.allEmployeeKppDetails(fromDate, toDate,roleId,deptId,desigId, reportingEmpId,gmEmpId,pageable);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @PutMapping(value = "/add-employee-remark")
    public ResponseEntity<KPIResponse> updateOverallEmployeeKppReportRemark(@RequestBody CumulativeUpdateRequest cumulativeUpdateRequest) {
        log.info("Inside CumulativeController >> updateOverallEmployeeKppReportRemark() cumulativeUpdateRequest : {}", cumulativeUpdateRequest);
        KPIResponse response = cumulativeService.updateOverallEmployeeKppReportRemark(cumulativeUpdateRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/dd-report-fin-year")
    public ResponseEntity<List<KppFinancialYearDDResponse>> ddAllFinancialYear() {
        log.info("Inside CumulativeController >> ddAllFinancialYear()");
        List<KppFinancialYearDDResponse> response = cumulativeService.ddAllFinancialYear();
        return new ResponseEntity<>(response, HttpStatus.OK);

    }
}
