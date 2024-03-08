package com.futurebizops.kpi.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyMasterResponse {

    private Integer companyId;
    private Integer regionId;
    private String regionName;
    private Integer siteId;
    private String siteName;
    private String companyName;
    private String companyAddress;
    private String companyMbNo;
    private String companyFinYear;
    private String remark;
    private String statusCd;

    public CompanyMasterResponse(Object[] objects){
        this.companyId=Integer.parseInt(String.valueOf(objects[0]));
        this.regionId=Integer.parseInt(String.valueOf(objects[1]));
        this.regionName=String.valueOf(objects[2]);
        this.siteId=Integer.parseInt(String.valueOf(objects[3]));
        this.siteName=String.valueOf(objects[4]);
        this.companyName=String.valueOf(objects[5]);
        this.companyAddress=String.valueOf(objects[6]);
        this.companyMbNo=String.valueOf(objects[7]);
        this.companyFinYear=String.valueOf(objects[8]);
        this.remark=String.valueOf(objects[9]);
        this.statusCd=String.valueOf(objects[10]);
    }
}
