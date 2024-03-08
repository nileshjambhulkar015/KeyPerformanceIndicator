package com.futurebizops.kpi.response.dropdown;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SiteDDResponse {

    private String siteName;
    private Integer siteId;
    private String statusCd;

    public SiteDDResponse(Object[] objects){
        this.siteId=Integer.parseInt(String.valueOf(objects[0]));
        this.siteName=String.valueOf(objects[1]);

        this.statusCd=String.valueOf(objects[2]);
    }
}
