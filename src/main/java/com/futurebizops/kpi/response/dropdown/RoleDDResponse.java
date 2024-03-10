package com.futurebizops.kpi.response.dropdown;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleDDResponse {

    private Integer roleId;
    private String roleName;
    private String statusCd;

    public RoleDDResponse(Object[] objects){
        this.roleId=Integer.parseInt(String.valueOf(objects[0]));
        this.roleName=String.valueOf(objects[1]);

        this.statusCd=String.valueOf(objects[2]);
    }
}
