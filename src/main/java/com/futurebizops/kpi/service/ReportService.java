package com.futurebizops.kpi.service;

import com.futurebizops.kpi.response.EmpKppStatusResponse;

import javax.servlet.http.HttpServletResponse;

public interface ReportService {

    public void getInProgressEmployeeKppStatusReport(HttpServletResponse httpServletResponse, Integer empId);

    public void getCompletedEmployeeKppStatusReport(HttpServletResponse httpServletResponse, Integer empId,String ekppMonth);
    public void getHodKppStatusReport(HttpServletResponse httpServletResponse, Integer empId);
}
