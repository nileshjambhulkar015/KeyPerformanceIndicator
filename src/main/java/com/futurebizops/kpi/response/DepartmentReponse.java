package com.futurebizops.kpi.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentReponse {

    private Integer deptId;
    private String deptName;
    private Integer roleId;
    private String roleName;
    private String remark;
    private String statusCd;

    public DepartmentReponse(Object[] objects){
        deptId=Integer.parseInt(String.valueOf(objects[0]));
        deptName=String.valueOf(objects[1]);
        roleId=Integer.parseInt(String.valueOf(objects[2]));
        roleName=String.valueOf(objects[3]);
        remark=String.valueOf(objects[4]);
        statusCd=String.valueOf(objects[5]);
    }
}
