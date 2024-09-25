package com.futurebizops.kpi.service.serviceimpl;

import com.futurebizops.kpi.dto.EmployeeKppDetailsDto;
import com.futurebizops.kpi.excel.EmployeeCompletedKPPReport;
import com.futurebizops.kpi.excel.HODCompletedKPPReport;
import com.futurebizops.kpi.response.EmpKppStatusResponse;
import com.futurebizops.kpi.service.EmployeeKppStatusService;
import com.futurebizops.kpi.service.ReportService;
import com.futurebizops.kpi.utils.DateTimeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.text.DecimalFormat;
import java.util.List;

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
    public void getInProgressEmployeeKppStatusReport(HttpServletResponse httpServletResponse, Integer empId) {
        EmpKppStatusResponse empKppStatusResponse = employeeKppStatusService.getInPrgressEmployeeKppStatus(empId);
        employeeCompletedKPPReport.getEmployeeKppStatusExport(empKppStatusResponse,httpServletResponse);
    }

    @Override
    public void getCompletedEmployeeKppStatusReport(HttpServletResponse httpServletResponse, Integer empId,String ekppMonth) {
        EmpKppStatusResponse empKppStatusResponse = employeeKppStatusService.getCompletedEmployeeKppStatus(empId,ekppMonth);
        employeeCompletedKPPReport.getEmployeeKppStatusExport(empKppStatusResponse,httpServletResponse);
    }

    @Override
    public void getHodKppStatusReport(HttpServletResponse httpServletResponse, Integer empId) {
        EmpKppStatusResponse empKppStatusResponse = employeeKppStatusService.getInPrgressEmployeeKppStatus(empId);
        hodCompletedKPPReport.getHodKppStatusExport(empKppStatusResponse,httpServletResponse);
    }

    @Override
    public void getCompletedHODKppStatusReport(HttpServletResponse httpServletResponse, Integer empId, String ekppMonth) {
        EmpKppStatusResponse empKppStatusResponse = employeeKppStatusService.getCompletedEmployeeKppStatus(empId,ekppMonth);
        hodCompletedKPPReport.getHodKppStatusExport(empKppStatusResponse,httpServletResponse);
    }



}
