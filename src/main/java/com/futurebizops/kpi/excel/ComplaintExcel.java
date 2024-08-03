package com.futurebizops.kpi.excel;

import com.futurebizops.kpi.response.EmployeeComplaintResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Service
@Slf4j
public class ComplaintExcel {

    //For employee kpp report
    public void getEmployeeComplaintExcel(List<EmployeeComplaintResponse> response, HttpServletResponse httpServletResponse) {

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet(response.get(0).getEmpEId() + response.get(0).getEmpName());

        //-------Header Style Start-----
        XSSFCellStyle headerStyle = (XSSFCellStyle) workbook.createCellStyle();
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 10);
        headerStyle.setFont(headerFont);
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        headerStyle.setWrapText(true);
        XSSFColor customColor = new XSSFColor(new java.awt.Color(255, 223, 186), null); // RGB values for faint orange
        headerStyle.setFillForegroundColor(customColor);
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setBorderTop(BorderStyle.THIN);
        headerStyle.setBorderBottom(BorderStyle.THIN);
        headerStyle.setBorderLeft(BorderStyle.THIN);
        headerStyle.setBorderRight(BorderStyle.THIN);

        //Header Style End
        int kppHeaderRow = 0;
        Row row = sheet.createRow(kppHeaderRow);

        for (int i = 0; i <= 15; i++) {
            Cell headerCell = row.createCell(i);

            headerCell.setCellValue(getHeaderCellValue(i)); // Replace with your logic to get header cell value
            headerCell.setCellStyle(headerStyle);

        }


        int kppDataRow = 1;
        int srno = 1;
        int k = 0;
        //--Data Style
        CellStyle dataStyle = workbook.createCellStyle();

        Font datafont = workbook.createFont();
        datafont.setBold(true);
        dataStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        dataStyle.setAlignment(HorizontalAlignment.CENTER);
        dataStyle.setWrapText(true);
        dataStyle.setFont(datafont);
        dataStyle.setBorderTop(BorderStyle.THIN);
        dataStyle.setBorderBottom(BorderStyle.THIN);
        dataStyle.setBorderLeft(BorderStyle.THIN);
        dataStyle.setBorderRight(BorderStyle.THIN);
        //-------------------------------
        CellStyle wrapTextStyle = workbook.createCellStyle();
        //  wrapTextStyle.setWrapText(true);
        wrapTextStyle.setAlignment(HorizontalAlignment.CENTER);
        wrapTextStyle.setVerticalAlignment(VerticalAlignment.CENTER);//
        wrapTextStyle.setBorderTop(BorderStyle.THIN);
        wrapTextStyle.setBorderBottom(BorderStyle.THIN);
        wrapTextStyle.setBorderLeft(BorderStyle.THIN);
        wrapTextStyle.setBorderRight(BorderStyle.THIN);

        CellStyle wrapTextStyleForCompId = workbook.createCellStyle();
        wrapTextStyleForCompId.setWrapText(true);
        wrapTextStyleForCompId.setAlignment(HorizontalAlignment.CENTER);
        wrapTextStyleForCompId.setVerticalAlignment(VerticalAlignment.CENTER);//
        wrapTextStyleForCompId.setBorderTop(BorderStyle.THIN);
        wrapTextStyleForCompId.setBorderBottom(BorderStyle.THIN);
        wrapTextStyleForCompId.setBorderLeft(BorderStyle.THIN);
        wrapTextStyleForCompId.setBorderRight(BorderStyle.THIN);

        for (EmployeeComplaintResponse complaintResponse : response) {
            Row kppRow = sheet.createRow(kppDataRow);


            Cell cell00 = kppRow.createCell(0);
            cell00.setCellValue("" + srno); // Replace this with your logic to get the cell value
            cell00.setCellStyle(wrapTextStyle);

            Cell cell1 = kppRow.createCell(1);
            cell1.setCellValue(complaintResponse.getCompId()); // Replace this with your logic to get the cell value
            cell1.setCellStyle(wrapTextStyleForCompId);

            Cell cell2 = kppRow.createCell(2);
            cell2.setCellValue(complaintResponse.getEmpEId()); // Replace this with your logic to get the cell value
            cell2.setCellStyle(wrapTextStyle);

            Cell cell3 = kppRow.createCell(3);
            cell3.setCellValue(complaintResponse.getEmpName()); // Replace this with your logic to get the cell value
            cell3.setCellStyle(wrapTextStyle);

            Cell cell4 = kppRow.createCell(4);
            cell4.setCellValue(complaintResponse.getEmpMobileNo()); // Replace this with your logic to get the cell value
            cell4.setCellStyle(wrapTextStyle);

            Cell cell5 = kppRow.createCell(5);
            cell5.setCellValue(complaintResponse.getRoleName()); // Replace this with your logic to get the cell value
            cell5.setCellStyle(wrapTextStyle);

            Cell cell = kppRow.createCell(6);
            cell.setCellValue(complaintResponse.getDeptName()); // Replace this with your logic to get the cell value
            cell.setCellStyle(wrapTextStyle);

            Cell cell7 = kppRow.createCell(7);
            cell7.setCellValue(complaintResponse.getDesigName()); // Replace this with your logic to get the cell value
            cell7.setCellStyle(getCellText(workbook, 7));

            Cell cell8 = kppRow.createCell(8);
            cell8.setCellValue(complaintResponse.getCompDate()); // Replace this with your logic to get the cell value
            cell8.setCellStyle(wrapTextStyle);

            Cell cell9 = kppRow.createCell(9);
            cell9.setCellValue(complaintResponse.getCompResolveDate()); // Replace this with your logic to get the cell value
            cell9.setCellStyle(wrapTextStyle);

            Cell cell10 = kppRow.createCell(10);
            cell10.setCellValue(complaintResponse.getCompTypeName()); // Replace this with your logic to get the cell value
            cell10.setCellStyle(getCellText(workbook, 10));

            Cell cell11 = kppRow.createCell(11);
            cell11.setCellValue(complaintResponse.getCompDesc()); // Replace this with your logic to get the cell value
            cell11.setCellStyle(getCellText(workbook, 11));

            Cell cell12 = kppRow.createCell(12);
            cell12.setCellValue(complaintResponse.getCompStatus()); // Replace this with your logic to get the cell value
            cell12.setCellStyle(getCellText(workbook, 12));

            Cell cell13 = kppRow.createCell(13);
            cell13.setCellValue(complaintResponse.getCompResolveEmpName()); // Replace this with your logic to get the cell value
            cell13.setCellStyle(getCellText(workbook, 13));

            Cell cell14 = kppRow.createCell(14);
            cell14.setCellValue(complaintResponse.getCompResolveEmpEId()); // Replace this with your logic to get the cell value
            cell14.setCellStyle(wrapTextStyle);

            Cell cell15 = kppRow.createCell(15);
            cell15.setCellValue(complaintResponse.getRemark()); // Replace this with your logic to get the cell value
            cell15.setCellStyle(wrapTextStyle);

            kppDataRow++;
            srno++;
        }


        // Create data rows

        // Set content type and headers
        httpServletResponse.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        //  httpServletResponse.setHeader("Content-Disposition", "attachment; filename=" + response.getEmpEId() + " " + response.getEmpName() + ".xlsx");
        httpServletResponse.setHeader("Content-Disposition", "attachment; filename=Employee Complaint " + response.get(0).getEmpEId() + ".xlsx");
        // Write the workbook to the response output stream
        try {
            workbook.write(httpServletResponse.getOutputStream());
            // Close the workbook to prevent memory leaks
            workbook.close();
        } catch (Exception e) {

        }

    }


    private CellStyle getCellText(Workbook workbook, int columnIndex) {
        CellStyle wrapTextStyle = workbook.createCellStyle();
        //wrapTextStyle.setWrapText(true);
        wrapTextStyle.setAlignment(HorizontalAlignment.CENTER);
        wrapTextStyle.setVerticalAlignment(VerticalAlignment.CENTER);//
        wrapTextStyle.setBorderTop(BorderStyle.THIN);
        wrapTextStyle.setBorderBottom(BorderStyle.THIN);
        wrapTextStyle.setBorderLeft(BorderStyle.THIN);
        wrapTextStyle.setBorderRight(BorderStyle.THIN);


        return wrapTextStyle;
    }

    private String getHeaderCellValue(int columnIndex) {
        String Header = "";
        switch (columnIndex) {
            case 0:
                Header = "Sr.No";
                break;
            case 1:
                Header = "Complaint Id";
                break;
            case 2:
                Header = "Employee Id";
                break;
            case 3:
                Header = "Employee Name";
                break;
            case 4:
                Header = "Mobile No";
                break;
            case 5:
                Header = "Role";
                break;
            case 6:
                Header = "Department";
                break;
            case 7:
                Header = "Designation";
                break;
            case 8:
                Header = "Complaint Start Date";
                break;
            case 9:
                Header = "Complaint End Date";
                break;
            case 10:
                Header = "Complaint Type name";
                break;
            case 11:
                Header = "Complaint Description";
                break;
            case 12:
                Header = "Complaint Status";
                break;
            case 13:
                Header = "Resolved Employee Name";
                break;
            case 14:
                Header = "Resolved Employee Id";
                break;
            case 15:
                Header = "Remark";
                break;
        }
        return Header;
    }


}
