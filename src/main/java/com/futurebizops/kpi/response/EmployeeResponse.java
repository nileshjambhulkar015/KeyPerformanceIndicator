package com.futurebizops.kpi.response;

import lombok.Data;

@Data
public class EmployeeResponse {


    private Integer empId;
    private String empEId;
    private Integer roleId;
    private String roleName;
    private Integer deptId;
    private String deptName;
    private Integer desigId;
    private String desigName;
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
    private Integer reportingEmpId;

    public  EmployeeResponse(Object[] objects){
        empId=Integer.parseInt(String.valueOf(objects[0]));
        empEId=String.valueOf(objects[1]);
        roleId=Integer.parseInt(String.valueOf(objects[2]));
        roleName=String.valueOf(objects[3]);
        deptId=Integer.parseInt(String.valueOf(objects[4]));
        deptName=String.valueOf(objects[5]);
        desigId=Integer.parseInt(String.valueOf(objects[6]));
        desigName=String.valueOf(objects[7]);
        regionId=Integer.parseInt(String.valueOf(objects[8]));
        regionName=String.valueOf(objects[9]);
        siteId=Integer.parseInt(String.valueOf(objects[10]));
        siteName=String.valueOf(objects[11]);
        empFirstName=String.valueOf(objects[12]);
        empMiddleName=String.valueOf(objects[13]);
        empLastName=String.valueOf(objects[14]);
        empDob=String.valueOf(objects[15]);
        empMobileNo=String.valueOf(objects[16]);
        empEmerMobileNo=String.valueOf(objects[17]);
        empPhoto=String.valueOf(objects[18]);
        emailId=String.valueOf(objects[19]);
        tempAddress=String.valueOf(objects[20]);
        permAddress=String.valueOf(objects[21]);
        empGender=String.valueOf(objects[22]);
        empBloodgroup=String.valueOf(objects[23]);
        remark=String.valueOf(objects[24]);
        statusCd=String.valueOf(objects[25]);
        reportingEmpId=Integer.parseInt(String.valueOf(objects[26]));
    }

}
