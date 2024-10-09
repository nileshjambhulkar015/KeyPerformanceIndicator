package com.futurebizops.kpi.response;

import com.futurebizops.kpi.utils.DateTimeUtils;
import lombok.Data;

import java.time.Instant;

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
    private String empOverallAchive;
    private String empEKppStatus;

    private String hodOverallAchieve;
    private String hodKppStatus;

    private String gmOverallAchieve;
    private String gmKppStatus;

    private String ekppMonth;

    private Double sumOfRatings;


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
        empOverallAchive =String.valueOf(objects[11]);
        empEKppStatus=String.valueOf(objects[12]);

        hodOverallAchieve=String.valueOf(objects[13]);
        hodKppStatus=String.valueOf(objects[14]);
        gmOverallAchieve=String.valueOf(objects[15]);
        gmKppStatus=String.valueOf(objects[16]);

        if(null!=objects[17]) {
            this.ekppMonth = DateTimeUtils.extractDateInDDMMYYY(String.valueOf(objects[17]));
        }

    }
}
