package com.futurebizops.kpi.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class RegionResponse {
    private Integer regionId;
    private String regionName;
    private String remark;
    private String statusCd;

    public RegionResponse(Object[] objects) {
        this.regionId = Integer.parseInt(String.valueOf(objects[0]));
        this.regionName = String.valueOf(objects[1]);
        this.remark = String.valueOf(objects[2]);
        this.statusCd = String.valueOf(objects[3]);
    }
}
