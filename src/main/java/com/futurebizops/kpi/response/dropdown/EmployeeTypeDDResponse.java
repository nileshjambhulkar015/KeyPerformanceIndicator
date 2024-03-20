package com.futurebizops.kpi.response.dropdown;

import lombok.Data;

@Data
public class EmployeeTypeDDResponse {
    private Integer empTypeId;
    private String empTypeName;
    private String statusCd;

    public EmployeeTypeDDResponse(Object[] objects){
        this.empTypeId=Integer.parseInt(String.valueOf(objects[0]));
        this.empTypeName=String.valueOf(objects[1]);
        this.statusCd=String.valueOf(objects[2]);
    }
}
