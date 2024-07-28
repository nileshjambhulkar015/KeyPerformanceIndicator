package com.futurebizops.kpi.response;

import lombok.Data;

@Data
public class EmployeeBirthdayResponse {

    private Integer empId;
    private String empFirstName;
    private String empMiddleName;
    private String empLastName;
    private String empMobileNo;
    private String emailId;

    public  EmployeeBirthdayResponse(Object[] objects){
        empId=Integer.parseInt(String.valueOf(objects[0]));
        empFirstName=String.valueOf(objects[1]);
        empMiddleName=String.valueOf(objects[2]);
        empLastName=String.valueOf(objects[3]);
        empMobileNo=String.valueOf(objects[4]);
        emailId=String.valueOf(objects[5]);

    }

}
