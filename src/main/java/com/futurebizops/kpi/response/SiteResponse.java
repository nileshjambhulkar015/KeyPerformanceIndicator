package com.futurebizops.kpi.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SiteResponse {

    private Integer siteId;
    private String siteName;
    private Integer regionId;
    private String regionName;
    private String remark;
    private String statusCd;

    public SiteResponse(Object[] objects){
        siteId=Integer.parseInt(String.valueOf(objects[0]));
        siteName=String.valueOf(objects[1]);
        regionId=Integer.parseInt(String.valueOf(objects[2]));
        regionName=String.valueOf(objects[3]);
        remark=String.valueOf(objects[4]);
        statusCd=String.valueOf(objects[5]);
    }
}
