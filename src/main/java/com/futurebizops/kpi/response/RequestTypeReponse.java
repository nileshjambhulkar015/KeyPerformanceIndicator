package com.futurebizops.kpi.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestTypeReponse {

    private Integer reqTypeId;
    private String reqTypeName;
    private Integer reqTypeDeptId;
    private String deptName;
    private String remark;
    private String statusCd;

    public RequestTypeReponse(Object[] objects){
        reqTypeId=Integer.parseInt(String.valueOf(objects[0]));
        reqTypeName=String.valueOf(objects[1]);

        reqTypeDeptId=Integer.parseInt(String.valueOf(objects[2]));
        deptName=String.valueOf(objects[3]);
        remark=String.valueOf(objects[4]);
        statusCd=String.valueOf(objects[5]);
    }
}
