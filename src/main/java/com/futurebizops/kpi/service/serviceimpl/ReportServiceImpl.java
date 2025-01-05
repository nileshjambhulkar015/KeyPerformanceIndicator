package com.futurebizops.kpi.service.serviceimpl;

import com.futurebizops.kpi.excel.EmployeeCompletedKPPReport;
import com.futurebizops.kpi.excel.HODCompletedKPPReport;
import com.futurebizops.kpi.exception.KPIException;
import com.futurebizops.kpi.response.EmpKppStatusResponse;
import com.futurebizops.kpi.service.EmployeeKppStatusService;
import com.futurebizops.kpi.service.ReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;

@Slf4j
@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    EmployeeKppStatusService employeeKppStatusService;

    @Autowired
    EmployeeCompletedKPPReport employeeCompletedKPPReport;

    @Autowired
    HODCompletedKPPReport hodCompletedKPPReport;

    @Override
    @Retryable(include = {KPIException.class}, maxAttemptsExpression = "${retry-max-attempts}")
    public void getInProgressEmployeeKppStatusReport(HttpServletResponse httpServletResponse, Integer empId) {
        log.debug("Inside ReportServiceImpl >> getInProgressEmployeeKppStatusReport() empId: {}", empId);
        try {
            EmpKppStatusResponse empKppStatusResponse = employeeKppStatusService.getInPrgressEmployeeKppStatus(empId);
            employeeCompletedKPPReport.getEmployeeKppStatusExport(empKppStatusResponse, httpServletResponse);
        } catch (Exception ex) {
            log.error("Inside ReportServiceImpl >> getInProgressEmployeeKppStatusReport() : {}", ex);
            throw new KPIException("ReportServiceImpl >> getInProgressEmployeeKppStatusReport()", false, ex.getMessage());
        }
    }

    @Override
    @Retryable(include = {KPIException.class}, maxAttemptsExpression = "${retry-max-attempts}")
    public void getCompletedEmployeeKppStatusReport(HttpServletResponse httpServletResponse, Integer empId, String ekppMonth) {
        log.debug("Inside ReportServiceImpl >> getCompletedEmployeeKppStatusReport() empId: {}, ekppMonth:{}", empId, ekppMonth);
        try {
            EmpKppStatusResponse empKppStatusResponse = employeeKppStatusService.getCompletedEmployeeKppStatus(empId, ekppMonth);
            //for file name
            empKppStatusResponse.setReportKppMonth(ekppMonth);
            employeeCompletedKPPReport.getEmployeeKppStatusExport(empKppStatusResponse, httpServletResponse);
        } catch (Exception ex) {
            log.error("Inside ReportServiceImpl >> getCompletedEmployeeKppStatusReport() : {}", ex);
            throw new KPIException("ReportServiceImpl >> getCompletedEmployeeKppStatusReport()", false, ex.getMessage());
        }
    }

    @Override
    @Retryable(include = {KPIException.class}, maxAttemptsExpression = "${retry-max-attempts}")
    public void getHodKppStatusReport(HttpServletResponse httpServletResponse, Integer empId) {
        log.debug("Inside ReportServiceImpl >> getHodKppStatusReport() empId: {}", empId);
        try {
            EmpKppStatusResponse empKppStatusResponse = employeeKppStatusService.getInPrgressEmployeeKppStatus(empId);
            hodCompletedKPPReport.getHodKppStatusExport(empKppStatusResponse, httpServletResponse);
        } catch (Exception ex) {
            log.error("Inside ReportServiceImpl >> getHodKppStatusReport() : {}", ex);
            throw new KPIException("ReportServiceImpl >> getHodKppStatusReport()", false, ex.getMessage());
        }
    }

    @Override
    @Retryable(include = {KPIException.class}, maxAttemptsExpression = "${retry-max-attempts}")
    public void getCompletedHODKppStatusReport(HttpServletResponse httpServletResponse, Integer empId, String ekppMonth) {
        log.debug("Inside ReportServiceImpl >> getCompletedHODKppStatusReport() empId: {}, ekppMonth:{}", empId, ekppMonth);
        try {
            EmpKppStatusResponse empKppStatusResponse = employeeKppStatusService.getCompletedEmployeeKppStatus(empId, ekppMonth);
            //for file name
            empKppStatusResponse.setReportKppMonth(ekppMonth);
            hodCompletedKPPReport.getHodKppStatusExport(empKppStatusResponse, httpServletResponse);
        } catch (Exception ex) {
            log.error("Inside ReportServiceImpl >> getCompletedHODKppStatusReport() : {}", ex);
            throw new KPIException("ReportServiceImpl >> getCompletedHODKppStatusReport()", false, ex.getMessage());
        }
    }


}
