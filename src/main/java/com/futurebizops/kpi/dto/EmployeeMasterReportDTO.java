package com.futurebizops.kpi.dto;

import lombok.Data;

@Data
public class EmployeeMasterReportDTO {

    private Integer empId;
    private String ekppMonth;

    public EmployeeMasterReportDTO(Object[] objects){
        this.empId=Integer.parseInt(String.valueOf(objects[0]));
        this.ekppMonth=String.valueOf(objects[1]);
    }
}
