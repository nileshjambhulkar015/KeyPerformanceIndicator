package com.futurebizops.kpi.controller;

import com.futurebizops.kpi.response.EmpKppStatusResponse;
import com.futurebizops.kpi.service.EmployeeKppStatusService;
import com.futurebizops.kpi.service.ReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@CrossOrigin
@RestController
@RequestMapping(value = "/report")
@Slf4j
public class ReportController {
@Autowired
ReportService reportService;

    @GetMapping("/in-progress-employee-kpp-status")
    public void getInProgressEmployeeKppStatusReport(HttpServletResponse httpServletResponse, @RequestParam(required = false) Integer empId)  {
        log.info("Inside ReportController >> getInProgressEmployeeKppStatusReport() empId : {}", empId);
        reportService.getInProgressEmployeeKppStatusReport(httpServletResponse, empId);
    }

    @GetMapping("/completed-employee-kpp-status")
    public void getCompletedEmployeeKppStatusReport(HttpServletResponse httpServletResponse, @RequestParam(required = false) Integer empId, @RequestParam(required = false) String ekppMonth)  {
        log.info("Inside ReportController >> getCompletedEmployeeKppStatusReport() empId : {}, ekppMonth : {}", empId, ekppMonth);
        reportService.getCompletedEmployeeKppStatusReport(httpServletResponse, empId,ekppMonth);
    }

    @GetMapping("/in-progress-hod-kpp-status")
    public void getHodKppStatusReport(HttpServletResponse httpServletResponse, @RequestParam(required = false) Integer empId)  {
        log.info("Inside ReportController >> getHodKppStatusReport() empId : {}", empId);
        reportService.getHodKppStatusReport(httpServletResponse, empId);
    }

    @GetMapping("/completed-hod-kpp-status")
    public void getCompletedHODKppStatusReport(HttpServletResponse httpServletResponse, @RequestParam(required = false) Integer empId, @RequestParam(required = false) String ekppMonth)  {
        log.info("Inside ReportController >> getCompletedHODKppStatusReport() empId : {}, ekppMonth : {}", empId, ekppMonth);
        reportService.getCompletedHODKppStatusReport(httpServletResponse, empId,ekppMonth);
    }

}
