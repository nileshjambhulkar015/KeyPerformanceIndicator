package com.futurebizops.kpi.response.dropdown;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestTypeDDResponse {

    private Integer reqTypeId;
    private String reqTypeName;
    private String statusCd;

    public RequestTypeDDResponse(Object[] objects){
        this.reqTypeId=Integer.parseInt(String.valueOf(objects[0]));
        this.reqTypeName=String.valueOf(objects[1]);
        this.statusCd=String.valueOf(objects[2]);
    }
}
