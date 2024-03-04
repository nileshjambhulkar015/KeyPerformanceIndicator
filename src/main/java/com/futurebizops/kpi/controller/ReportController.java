package com.futurebizops.kpi.controller;

import com.futurebizops.kpi.response.EmpKppStatusResponse;
import com.futurebizops.kpi.service.EmployeeKppStatusService;
import com.futurebizops.kpi.service.ReportService;
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
public class ReportController {
@Autowired
ReportService reportService;

    @GetMapping("/employee-kpp-status")
    public void exportToExcelEmployee(HttpServletResponse httpServletResponse, @RequestParam(required = false) Integer empId)  {
        reportService.getEmployeeKppStatusReport(httpServletResponse, empId);
    }

    @GetMapping("/hod-kpp-status")
    public void exportToExcelHod(HttpServletResponse httpServletResponse, @RequestParam(required = false) Integer empId)  {
        reportService.getHodKppStatusReport(httpServletResponse, empId);
    }
}
