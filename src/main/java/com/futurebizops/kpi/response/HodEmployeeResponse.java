package com.futurebizops.kpi.response;

import lombok.Data;

@Data
public class HodEmployeeResponse {

    private Integer empId;
    private String empEId;
    private Integer desigId;
    private String desigName;
    private String empFirstName;
    private String empMiddleName;
    private String empLastName;
    private String empMobileNo;
    private String emailId;
    private String kppOverallAchivement;
    private String empEKppStatus;

    public  HodEmployeeResponse(Object[] objects) {
        empId = Integer.parseInt(String.valueOf(objects[0]));
        empEId = String.valueOf(objects[1]);
        empFirstName=String.valueOf(objects[2]);
        empMiddleName=String.valueOf(objects[3]);
        empLastName=String.valueOf(objects[4]);
        desigId = Integer.parseInt(String.valueOf(objects[5]));
        desigName = String.valueOf(objects[6]);
        empMobileNo=String.valueOf(objects[7]);
        emailId=String.valueOf(objects[8]);
        kppOverallAchivement=String.valueOf(objects[9]);
        empEKppStatus=String.valueOf(objects[10]);

    }
}
