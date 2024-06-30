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
    private String remark;
    private String statusCd;

    public ComplaintTypeReponse(Object[] objects){
        compTypeId=Integer.parseInt(String.valueOf(objects[0]));
        compTypeName=String.valueOf(objects[1]);
        remark=String.valueOf(objects[2]);
        statusCd=String.valueOf(objects[3]);
    }
}
