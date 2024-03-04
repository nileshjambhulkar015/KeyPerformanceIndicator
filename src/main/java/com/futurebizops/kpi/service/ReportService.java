package com.futurebizops.kpi.service;

import com.futurebizops.kpi.response.EmpKppStatusResponse;

import javax.servlet.http.HttpServletResponse;

public interface ReportService {

    public void getEmployeeKppStatusReport(HttpServletResponse httpServletResponse, Integer empId);
    public void getHodKppStatusReport(HttpServletResponse httpServletResponse, Integer empId);
}
