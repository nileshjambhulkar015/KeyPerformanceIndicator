package com.futurebizops.kpi.response.dropdown;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DesignationDDResponse {

    private String desigName;
    private Integer desigId;
    private String statusCd;

    public DesignationDDResponse(Object[] objects){
        this.desigName=String.valueOf(objects[0]);
        this.desigId=Integer.parseInt(String.valueOf(objects[1]));
        this.statusCd=String.valueOf(objects[2]);
    }
}
