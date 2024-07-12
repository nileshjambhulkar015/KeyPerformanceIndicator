package com.futurebizops.kpi.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ComplaintTypeReponse {

    private Integer compTypeId;
    private String compTypeName;
    private Integer compTypeRoleId;
    private Integer compTypeDeptId;
    private String deptName;
    private String remark;
    private String statusCd;

    public ComplaintTypeReponse(Object[] objects){
        compTypeId=Integer.parseInt(String.valueOf(objects[0]));
        compTypeName=String.valueOf(objects[1]);
        compTypeRoleId = Integer.parseInt(String.valueOf(objects[2]));
        compTypeDeptId=Integer.parseInt(String.valueOf(objects[3]));
        deptName=String.valueOf(objects[4]);
        remark=String.valueOf(objects[5]);
        statusCd=String.valueOf(objects[6]);
    }
}
