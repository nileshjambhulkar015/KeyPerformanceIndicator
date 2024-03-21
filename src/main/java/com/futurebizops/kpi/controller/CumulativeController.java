package com.futurebizops.kpi.controller;

import com.futurebizops.kpi.response.KPIResponse;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
        log.info("Request for kpp ");
        KPIResponse response = cumulativeService.getAllEmployeeKPPStatusReport(fromDate, toDate,  empId, roleId,  statusCd, pageable);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //load kpp details as per employee id for hod ratings
   /* @GetMapping("/hod-employee-all")
    @PageableAsQueryParam
    public ResponseEntity<KPIResponse> getEmployeeKppStatus(@RequestParam(required = false) String fromDate,
                                                            @RequestParam(required = false) String toDate,
                                                            @RequestParam(required = false) Integer empId) {
        KPIResponse response = cumulativeService.allEmployeeKppDetails(fromDate, toDate, empId);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }*/

    @GetMapping("/hod-cummulatve")
    // @PageableAsQueryParam
    public ResponseEntity<KPIResponse> getHODKppStatusCummulative(@RequestParam(required = false) String fromDate,
                                                                  @RequestParam(required = false) String toDate,
                                                                  @RequestParam(required = false) Integer roleId,
                                                                  @RequestParam(required = false) Integer deptId,
                                                                  @RequestParam(required = false) Integer desigId,
                                                                  @RequestParam(required = false) Integer reportingEmpId,
                                                                  @RequestParam(required = false) Integer gmEmpId) {
        KPIResponse response = cumulativeService.allEmployeeKppDetails(fromDate, toDate,roleId,deptId,desigId, reportingEmpId,gmEmpId);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }
}
