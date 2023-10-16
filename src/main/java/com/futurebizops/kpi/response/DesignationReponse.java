package com.futurebizops.kpi.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DesignationReponse {

    private Integer deptId;
    private String deptName;
    private Integer desigId;

    private String desigName;
    private String statusCd;

    public DesignationReponse(Object[] objects){
        this.deptId=Integer.parseInt(String.valueOf(objects[0]));
        this.deptName=String.valueOf(objects[1]);
        this.desigId=Integer.parseInt(String.valueOf(objects[2]));
        this.desigName=String.valueOf(objects[3]);
        this.statusCd=String.valueOf(objects[4]);
    }
}
