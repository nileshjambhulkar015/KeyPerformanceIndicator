package com.futurebizops.kpi.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UoMResponse {

    private Integer uomId;
    private String uomName;
    private String remark;
    private String statusCd;

    public UoMResponse(Object[] objects) {
        uomId = Integer.parseInt(String.valueOf(objects[0]));
        uomName = String.valueOf(objects[1]);
        remark = String.valueOf(objects[2]);
        statusCd = String.valueOf(objects[3]);
    }
}
