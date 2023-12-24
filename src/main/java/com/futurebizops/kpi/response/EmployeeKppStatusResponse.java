package com.futurebizops.kpi.response;

import lombok.Data;

@Data
public class EmployeeKppStatusResponse {

    private Integer empId;
    private String empEId;
    private String empFirstName;
    private String empMiddleName;
    private String empLastName;
    private Integer deptId;
    private String deptName;
    private Integer desigId;
    private String desigName;
    private String empMobileNo;
    private String emailId;
    private String kppOverallAchivement;
    private String empEKppStatus;

    public EmployeeKppStatusResponse(Object[] objects) {
        empId = Integer.parseInt(String.valueOf(objects[0]));
        empEId = String.valueOf(objects[1]);
        empFirstName=String.valueOf(objects[2]);
        empMiddleName=String.valueOf(objects[3]);
        empLastName=String.valueOf(objects[4]);
        deptId = Integer.parseInt(String.valueOf(objects[5]));
        deptName = String.valueOf(objects[6]);
        desigId = Integer.parseInt(String.valueOf(objects[7]));
        desigName = String.valueOf(objects[8]);
        empMobileNo=String.valueOf(objects[9]);
        emailId=String.valueOf(objects[10]);
        kppOverallAchivement=String.valueOf(objects[11]);
        empEKppStatus=String.valueOf(objects[12]);
    }
}
