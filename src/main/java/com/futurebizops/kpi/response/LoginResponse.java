package com.futurebizops.kpi.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {

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
    private String empEmailId;

    public LoginResponse(Object[] objects) {
        this.empId = Integer.parseInt(String.valueOf(objects[0]));
        this.empEId = String.valueOf(objects[1]);
        this.roleId = Integer.parseInt(String.valueOf(objects[2]));
        this.roleName = String.valueOf(objects[3]);
        this.deptId = Integer.parseInt(String.valueOf(objects[4]));
        this.deptName = String.valueOf(objects[5]);
        this.desigId = Integer.parseInt(String.valueOf(objects[6]));
        this.desigName = String.valueOf(objects[7]);
        this.empFirstName = String.valueOf(objects[8]);
        this.empMiddleName = String.valueOf(objects[9]);
        this.empLastName = String.valueOf(objects[10]);
        this.empEmailId = String.valueOf(objects[11]);
    }
}
