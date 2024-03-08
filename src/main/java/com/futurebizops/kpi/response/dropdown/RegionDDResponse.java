package com.futurebizops.kpi.response.dropdown;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegionDDResponse {

    private String regionName;
    private Integer regionId;
    private String statusCd;

    public RegionDDResponse(Object[] objects){

        this.regionId=Integer.parseInt(String.valueOf(objects[0]));
        this.regionName=String.valueOf(objects[1]);
        this.statusCd=String.valueOf(objects[2]);
    }
}
