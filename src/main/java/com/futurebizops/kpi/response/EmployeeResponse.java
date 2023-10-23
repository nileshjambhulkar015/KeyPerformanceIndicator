package com.futurebizops.kpi.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class EmployeeResponse {


    private Integer empId;
    private Integer deptId;
    private String deptName;
    private Integer desigId;
    private String desigName;
    private Integer roleId;
    private Integer regionId;
    private String regionName;
    private Integer siteId;
    private String siteName;
    private String empFirstName;
    private String empMiddleName;
    private String empLastName;
    private String empDob;
    private String empMobileNo;
    private String empEmerMobileNo;
    private String empPhoto;
    private String emailId;
    private String tempAddress;
    private String permAddress;
    private String empGender;
    private String empBloodgroup;
    private String remark;
    private String statusCd;

    public  EmployeeResponse(Object[] objects){
        empId=Integer.parseInt(String.valueOf(objects[0]));
        deptId=Integer.parseInt(String.valueOf(objects[1]));
        deptName=String.valueOf(objects[2]);
        desigId=Integer.parseInt(String.valueOf(objects[3]));
        desigName=String.valueOf(objects[4]);
        roleId=Integer.parseInt(String.valueOf(objects[5]));
        regionId=Integer.parseInt(String.valueOf(objects[6]));
        regionName=String.valueOf(objects[7]);
        siteId=Integer.parseInt(String.valueOf(objects[8]));
        siteName=String.valueOf(objects[9]);
        empFirstName=String.valueOf(objects[10]);
        empMiddleName=String.valueOf(objects[11]);
        empLastName=String.valueOf(objects[12]);
        empDob=String.valueOf(objects[13]);
        empMobileNo=String.valueOf(objects[14]);
        empEmerMobileNo=String.valueOf(objects[15]);
        empPhoto=String.valueOf(objects[16]);
        emailId=String.valueOf(objects[17]);
        tempAddress=String.valueOf(objects[18]);
        permAddress=String.valueOf(objects[19]);
        empGender=String.valueOf(objects[20]);
        empBloodgroup=String.valueOf(objects[21]);
        remark=String.valueOf(objects[22]);
        statusCd=String.valueOf(objects[23]);
    }

}
