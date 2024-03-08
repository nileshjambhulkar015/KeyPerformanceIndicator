package com.futurebizops.kpi.response.dropdown;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleDDResponse {

    private String roleName;
    private Integer roleId;
    private String statusCd;

    public RoleDDResponse(Object[] objects){
        this.roleName=String.valueOf(objects[0]);
        this.roleId=Integer.parseInt(String.valueOf(objects[1]));
        this.statusCd=String.valueOf(objects[2]);
    }
}
