package com.futurebizops.kpi.response.dropdown;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ComplaintTypeDDResponse {

    private Integer compTypeId;
    private String compTypeName;
    private Integer compTypeRoleId;
    private Integer compTypeDeptId;
    private String statusCd;

    public ComplaintTypeDDResponse(Object[] objects){
        this.compTypeId=Integer.parseInt(String.valueOf(objects[0]));
        this.compTypeName=String.valueOf(objects[1]);
        this.compTypeRoleId=Integer.parseInt(String.valueOf(objects[2]));
        this.compTypeDeptId=Integer.parseInt(String.valueOf(objects[3]));
        this.statusCd=String.valueOf(objects[4]);
    }
}
