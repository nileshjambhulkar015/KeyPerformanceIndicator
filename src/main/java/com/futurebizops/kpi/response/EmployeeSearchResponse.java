package com.futurebizops.kpi.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeSearchResponse {

    private Integer empId;
    private String empEId;
    private Integer roleId;
    private String roleName;
    private Integer deptId;
    private String deptName;
    private Integer desigId;
    private String desigName;
    private String empFirstName;
    private String empMiddleName;
    private String empLastName;

    public EmployeeSearchResponse(Object[] objects){
        empId=Integer.parseInt(String.valueOf(objects[0]));
        empEId=String.valueOf(objects[1]);
        roleId=Integer.parseInt(String.valueOf(objects[2]));
        roleName=String.valueOf(objects[3]);
        deptId=Integer.parseInt(String.valueOf(objects[4]));
        deptName=String.valueOf(objects[5]);
        desigId=Integer.parseInt(String.valueOf(objects[6]));
        desigName=String.valueOf(objects[7]);
        empFirstName=String.valueOf(objects[8]);
        empMiddleName=String.valueOf(objects[9]);
        empLastName=String.valueOf(objects[10]);
    }
}
