package com.futurebizops.kpi.excel;

import com.futurebizops.kpi.dto.EmployeeKppDetailsDto;
import com.futurebizops.kpi.response.EmpKppStatusResponse;
import com.futurebizops.kpi.utils.DateTimeUtils;
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
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.text.DecimalFormat;
import java.util.List;

@Service
public class EmployeeCompletedKPPReport {


    //For employee kpp report
    public void getEmployeeKppStatusExport(EmpKppStatusResponse response, HttpServletResponse httpServletResponse) {
        //display 2 digit after decimal
        DecimalFormat formatter = new DecimalFormat("#0.00");
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet(response.getEmpEId() + response.getEmpName());
        Row row0 = sheet.createRow(0);
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 24));


        CellStyle font16Style = workbook.createCellStyle();
        Font font16 = workbook.createFont();
        font16.setFontHeightInPoints((short) 16);
        font16.setBold(true);
        font16Style.setFont(font16);
        font16Style.setAlignment(HorizontalAlignment.CENTER);
        font16Style.setVerticalAlignment(VerticalAlignment.CENTER);
        font16Style.setBorderTop(BorderStyle.THIN);
        font16Style.setBorderBottom(BorderStyle.THIN);
        font16Style.setBorderLeft(BorderStyle.THIN);
        font16Style.setBorderRight(BorderStyle.THIN);
        // Create a cell style with font size 12
        CellStyle font12Style = workbook.createCellStyle();
        Font font12 = workbook.createFont();
        font12.setFontHeightInPoints((short) 12);
        font12.setBold(true);
        font12Style.setFont(font12);
        font12Style.setAlignment(HorizontalAlignment.CENTER);
        font12Style.setVerticalAlignment(VerticalAlignment.CENTER);
        font12Style.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
        font12Style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        font12Style.setBorderTop(BorderStyle.THIN);
        font12Style.setBorderBottom(BorderStyle.THIN);
        font12Style.setBorderLeft(BorderStyle.THIN);
        font12Style.setBorderRight(BorderStyle.THIN);

        // Create a cell with rich text
        Cell cell0000 = row0.createCell(0);

        // Add the first line with font size 16
        RichTextString richTextString1 = new XSSFRichTextString(" "+response.getCompanyName()+"  ");
        richTextString1.applyFont(font16); // Set font size 16 for the entire string

        // Add a new line with font size 12
        RichTextString newLine = new XSSFRichTextString("\n  "+ response.getCompanyAddress());
        Font font19 = workbook.createFont();
        font19.setFontHeightInPoints((short) 12);
        font19.setBold(false);
        newLine.applyFont(font19); // Set font size 12 for the entire string

        // Combine both lines into a single RichTextString
        RichTextString combinedText = new XSSFRichTextString();
        ((XSSFRichTextString) combinedText).append(String.valueOf(richTextString1));
        ((XSSFRichTextString) combinedText).append(String.valueOf(newLine));

        // Set the combined text to the cell
        cell0000.setCellValue(combinedText);
        cell0000.setCellStyle(font16Style);

        // Adjust the row height to accommodate the two lines
        cell0000.getRow().setHeightInPoints((float) (font16.getFontHeightInPoints() + font19.getFontHeightInPoints()));


        Row row1 = sheet.createRow(1);
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 24));

        Cell cell0001 = row1.createCell(0);
        cell0001.setCellValue("EMPLOYEE-WISE KEY PERFORMANCE INDICATORS (KPIs) FY "+response.getCompanyFinYear());
        cell0001.setCellStyle(font12Style);


        int fifthColumnWidthInPixels = 200; // Set your desired width in pixels
        int sixthColumnWidthInPixels = 200; // Set your desired width in pixels
        int headerColumnWidthInPixels = 200;
        int fifthColumnWidthInUnits = (fifthColumnWidthInPixels / 8) * 256;
        int sixthColumnWidthInUnits = (sixthColumnWidthInPixels / 8) * 256;

        int headerColumnWidthInUnits = (headerColumnWidthInPixels / 8) * 256;
        sheet.setColumnWidth(5, fifthColumnWidthInUnits);
        sheet.setColumnWidth(6, sixthColumnWidthInUnits);
        for (int i = 1; i <= 4; i++) {
            sheet.setColumnWidth(i, 140 / 8 * 256);

        }
        for (int i = 7; i <= 24; i++) {
            sheet.setColumnWidth(i, 100 / 8 * 256);

        }
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
        int kppHeaderRow = 2;
        Row row = sheet.createRow(kppHeaderRow);
        for (int i = 0; i <= 24; i++) {
            Cell headerCell = row.createCell(i);
            headerCell.setCellValue(getHeaderCellValue(i)); // Replace with your logic to get header cell value
            headerCell.setCellStyle(headerStyle);

        }

        List<EmployeeKppDetailsDto> kppDetails = response.getKppStatusDetails();


        int kppDataRow = 3;
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
        wrapTextStyle.setWrapText(true);
        wrapTextStyle.setAlignment(HorizontalAlignment.CENTER);
        wrapTextStyle.setVerticalAlignment(VerticalAlignment.CENTER);//
        wrapTextStyle.setBorderTop(BorderStyle.THIN);
        wrapTextStyle.setBorderBottom(BorderStyle.THIN);
        wrapTextStyle.setBorderLeft(BorderStyle.THIN);
        wrapTextStyle.setBorderRight(BorderStyle.THIN);

        Integer totalKpp =kppDetails.size();
        Double overallCumulativeRatings = 0.0;
        Double overallCumulativeRatingsPer = 0.0;
        Double totalKPPWeightage = 0.0;
        for (EmployeeKppDetailsDto employeeKppDetailsDto : kppDetails) {
            Row kppRow = sheet.createRow(kppDataRow);

            Cell cell00 = kppRow.createCell(0);
            cell00.setCellValue("" + srno); // Replace this with your logic to get the cell value
            cell00.setCellStyle(dataStyle);
            //kppRow.createCell(1).setCellValue(response.getEmpEId());
            Cell cell1 = kppRow.createCell(1);
            cell1.setCellValue(response.getEmpEId()); // Replace this with your logic to get the cell value
            cell1.setCellStyle(dataStyle);

            Cell cell2 = kppRow.createCell(2);
            cell2.setCellValue(response.getDeptName()); // Replace this with your logic to get the cell value
            cell2.setCellStyle(dataStyle);
            Cell cell3 = kppRow.createCell(3);
            cell3.setCellValue(response.getDesigName()); // Replace this with your logic to get the cell value
            cell3.setCellStyle(dataStyle);
            Cell cell4 = kppRow.createCell(4);
            cell4.setCellValue(response.getEmpName()); // Replace this with your logic to get the cell value
            cell4.setCellStyle(dataStyle);
            Cell cell5 = kppRow.createCell(5);
            cell5.setCellValue(employeeKppDetailsDto.getKppObjective()); // Replace this with your logic to get the cell value
            cell5.setCellStyle(wrapTextStyle);
            Cell cell = kppRow.createCell(6);
            cell.setCellValue(employeeKppDetailsDto.getKppPerformanceIndi()); // Replace this with your logic to get the cell value
            cell.setCellStyle(wrapTextStyle);

            Cell cell7 = kppRow.createCell(7);
            cell7.setCellValue(employeeKppDetailsDto.getKppOverallTarget()); // Replace this with your logic to get the cell value
            cell7.setCellStyle(getCellText(workbook, 7));

            Cell cell8 = kppRow.createCell(8);
            cell8.setCellValue(employeeKppDetailsDto.getKppTargetPeriod()); // Replace this with your logic to get the cell value
            cell8.setCellStyle(wrapTextStyle);

            Cell cell9 = kppRow.createCell(9);
            cell9.setCellValue(employeeKppDetailsDto.getUomName()); // Replace this with your logic to get the cell value
            cell9.setCellStyle(wrapTextStyle);

            Cell cell10 = kppRow.createCell(10);
            cell10.setCellValue(employeeKppDetailsDto.getKppOverallWeightage()); // Replace this with your logic to get the cell value
            cell10.setCellStyle(getCellText(workbook, 10));

            totalKPPWeightage += Double.parseDouble(employeeKppDetailsDto.getKppOverallWeightage());

            Cell cell11 = kppRow.createCell(11);
            cell11.setCellValue(employeeKppDetailsDto.getEmpOverallAchieve()); // Replace this with your logic to get the cell value
            cell11.setCellStyle(getCellText(workbook, 11));

            Cell cell12 = kppRow.createCell(12);
            cell12.setCellValue(employeeKppDetailsDto.getHodOverallAchieve());
            cell12.setCellStyle(getCellText(workbook, 12));

            Cell cell13 = kppRow.createCell(13);
            cell13.setCellValue(employeeKppDetailsDto.getGmOverallAchieve()); // Replace this with your logic to get the cell value
            cell13.setCellStyle(getCellText(workbook, 13));


            Cell cell14 = kppRow.createCell(14);
           // Integer totalApprover = 3;
           // Double avgCummRatings = (Double.parseDouble(employeeKppDetailsDto.getEmpOverallAchieve()) + Double.parseDouble(employeeKppDetailsDto.getHodOverallAchieve()) + Double.parseDouble(employeeKppDetailsDto.getGmOverallAchieve()))/totalApprover;
            cell14.setCellValue(employeeKppDetailsDto.getOverallRatings()); // Replace this with your logic to get the cell value
            cell14.setCellStyle(wrapTextStyle);



            Cell cell15 = kppRow.createCell(15);
            //Double avgRatingsPercentage = (avgCummRatings / 5)*100;
            cell15.setCellValue(employeeKppDetailsDto.getOverallPercentage()); // Replace this with your logic to get the cell value
            cell15.setCellStyle(wrapTextStyle);

           // overallCumulativeRatingsPer+=avgRatingsPercentage;

            Cell cell16 = kppRow.createCell(16);
            cell16.setCellValue(employeeKppDetailsDto.getKppRating1()); // Replace this with your logic to get the cell value
            cell16.setCellStyle(wrapTextStyle);

            Cell cell17 = kppRow.createCell(17);
            cell17.setCellValue(employeeKppDetailsDto.getKppRating2()); // Replace this with your logic to get the cell value
            cell17.setCellStyle(wrapTextStyle);

            Cell cell18 = kppRow.createCell(18);
            cell18.setCellValue(employeeKppDetailsDto.getKppRating3()); // Replace this with your logic to get the cell value
            cell18.setCellStyle(wrapTextStyle);

            Cell cell19 = kppRow.createCell(19);
            cell19.setCellValue(employeeKppDetailsDto.getKppRating4()); // Replace this with your logic to get the cell value
            cell19.setCellStyle(wrapTextStyle);


            Cell cell20 = kppRow.createCell(20);
            cell20.setCellValue(employeeKppDetailsDto.getKppRating5()); // Replace this with your logic to get the cell value
            cell20.setCellStyle(wrapTextStyle);

            kppDataRow++;
            srno++;
        }

        Row kppTotalRow = sheet.createRow(kppDataRow);

        XSSFCellStyle kppTotalRowHeaderStyle = (XSSFCellStyle) workbook.createCellStyle();
        Font kppTotalRowHeaderFont = workbook.createFont();
        kppTotalRowHeaderFont.setBold(true);
        kppTotalRowHeaderFont.setFontHeightInPoints((short) 10);
        kppTotalRowHeaderStyle.setFont(kppTotalRowHeaderFont);
        kppTotalRowHeaderStyle.setAlignment(HorizontalAlignment.CENTER);
        kppTotalRowHeaderStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        kppTotalRowHeaderStyle.setWrapText(true);
        kppTotalRowHeaderStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
        kppTotalRowHeaderStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        kppTotalRowHeaderStyle.setBorderTop(BorderStyle.THIN);
        kppTotalRowHeaderStyle.setBorderBottom(BorderStyle.THIN);
        kppTotalRowHeaderStyle.setBorderLeft(BorderStyle.THIN);
        kppTotalRowHeaderStyle.setBorderRight(BorderStyle.THIN);
        for (int i=0; i <= 24; i++)
        {
            Cell cell = kppTotalRow.createCell(i);
            cell.setCellValue("");
            cell.setCellStyle(kppTotalRowHeaderStyle);
        }

        Cell cell000 = kppTotalRow.createCell(6);
        cell000.setCellValue("Total: ");
        cell000.setCellStyle(kppTotalRowHeaderStyle);


        Cell cell010 = kppTotalRow.createCell(10);
        cell010.setCellValue(formatter.format(totalKPPWeightage));
        cell010.setCellStyle(kppTotalRowHeaderStyle);

        Cell cell001 = kppTotalRow.createCell(11);
       // Double avgTotalEmpAchiveWeight =Double.parseDouble(response.getTotalEmpAchivedWeight()) / totalKpp;
        cell001.setCellValue(response.getTotalEmpOverallAchieve());
        cell001.setCellStyle(kppTotalRowHeaderStyle);

        Cell cell002 = kppTotalRow.createCell(12);
       // Double avgTotalHODAchiveWeight =Double.parseDouble(response.getTotalHodAchivedWeight()) / totalKpp;
        cell002.setCellValue(response.getTotalHodOverallAchieve());
        cell002.setCellStyle(kppTotalRowHeaderStyle);

        Cell cell003 = kppTotalRow.createCell(13);
        //Double avgTotalGMAchiveWeight =Double.parseDouble(response.getTotalGmOverallAchieve()) / totalKpp;
        cell003.setCellValue(response.getTotalGmOverallAchieve());
        cell003.setCellStyle(kppTotalRowHeaderStyle);

        Cell cell004 = kppTotalRow.createCell(14);

        cell004.setCellValue(response.getTotalOverallRatings());
        cell004.setCellStyle(kppTotalRowHeaderStyle);

        Cell cell005 = kppTotalRow.createCell(15);
        cell005.setCellValue(response.getTotalOverallPercentage());
        cell005.setCellStyle(kppTotalRowHeaderStyle);

        //if we get more than 2 kpp then merge cell into one
        if((kppDataRow>=5)) {
            sheet.addMergedRegion(new CellRangeAddress(3, kppDataRow - 1, 1, 1));
            sheet.addMergedRegion(new CellRangeAddress(3, kppDataRow - 1, 2, 2));
            sheet.addMergedRegion(new CellRangeAddress(3, kppDataRow - 1, 3, 3));
            sheet.addMergedRegion(new CellRangeAddress(3, kppDataRow - 1, 4, 4));
        }
        // Create data rows

        // Set content type and headers
        httpServletResponse.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        //  httpServletResponse.setHeader("Content-Disposition", "attachment; filename=" + response.getEmpEId() + " " + response.getEmpName() + ".xlsx");
        httpServletResponse.setHeader("Content-Disposition", "attachment; filename=" + response.getEmpEId() + "-" + DateTimeUtils.extractMonthName(response.getEkppMonth()) + "-"+ DateTimeUtils.extractYear(response.getEkppMonth())+".xlsx");
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
        wrapTextStyle.setWrapText(true);
        wrapTextStyle.setAlignment(HorizontalAlignment.CENTER);
        wrapTextStyle.setVerticalAlignment(VerticalAlignment.CENTER);//
        wrapTextStyle.setBorderTop(BorderStyle.THIN);
        wrapTextStyle.setBorderBottom(BorderStyle.THIN);
        wrapTextStyle.setBorderLeft(BorderStyle.THIN);
        wrapTextStyle.setBorderRight(BorderStyle.THIN);

        switch (columnIndex) {
            case 7:
                wrapTextStyle.setFillForegroundColor(IndexedColors.GOLD.getIndex());
                wrapTextStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                break;
            case 10:
                wrapTextStyle.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
                wrapTextStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                break;
            case 11:
                wrapTextStyle.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
                wrapTextStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                break;
            case 12:
                wrapTextStyle.setFillForegroundColor(IndexedColors.GOLD.getIndex());
                wrapTextStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                break;
            case 13:
                wrapTextStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
                wrapTextStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                break;

            case 5:
                break;
            case 6:
                break;
            case 0:
                break;
            case 8:
                break;
            case 9:
                break;
        }
        return wrapTextStyle;
    }

    private String getHeaderCellValue(int columnIndex) {
        String Header = "";
        switch (columnIndex) {
            case 0:
                Header = "Sr.No";
                break;
            case 1:
                Header = "EMP CODE";
                break;
            case 2:
                Header = "DEPARTMENT";
                break;
            case 3:
                Header = "DESIGNATION";
                break;
            case 4:
                Header = "EMPLOYEE NAME";
                break;
            case 5:
                Header = "Individual KPI /Objectives";
                break;
            case 6:
                Header = "Performance Indicator";
                break;
            case 7:
                Header = "OverAll Target";
                break;
            case 8:
                Header = "Period";
                break;
            case 9:
                Header = "UOM";
                break;
            case 10:
                Header = "KPI Weightage %";
                break;
            case 11:
                Header = "SELF RATING (OUT OF 5)";
                break;

            case 12:
                Header = "1ST APP RATING (OUT OF 5)";
                break;

            case 13:
                Header = "2ND APP RATING (OUT OF 5)";
                break;
            case 14:
                Header = "Overall Evaluation / Cummulative Rating";
                break;
            case 15:
                Header = "Overall Achievement in %";
                break;
            case 16:
                Header = "Rating 5";
                break;
            case 17:
                Header = "Rating 4";
                break;
            case 18:
                Header = "Rating 3";
                break;
            case 19:
                Header = "Rating 2";
                break;

            case 20:
                Header = "Rating 1";
                break;
        }
        return Header;
    }

}
