package com.futurebizops.kpi.response.dropdown;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDDResponse {
    private Integer companyId;
    private String companyName;

    private String statusCd;

    public CompanyDDResponse(Object[] objects){
        this.companyId=Integer.parseInt(String.valueOf(objects[0]));
        this.companyName=String.valueOf(objects[1]);

        this.statusCd=String.valueOf(objects[2]);
    }
}
