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

    private Integer desigId;
    private String desigName;
    private String statusCd;

    public DesignationDDResponse(Object[] objects){

        this.desigId=Integer.parseInt(String.valueOf(objects[0]));
        this.desigName=String.valueOf(objects[1]);
        this.statusCd=String.valueOf(objects[2]);
    }
}
