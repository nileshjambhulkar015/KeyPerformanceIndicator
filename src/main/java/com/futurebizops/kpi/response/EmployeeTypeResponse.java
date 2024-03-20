package com.futurebizops.kpi.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeTypeResponse {

    private Integer empTypeId;
    private String empTypeName;
    private String remark;
    private String statusCd;

    public EmployeeTypeResponse(Object[] objects) {
        empTypeId = Integer.parseInt(String.valueOf(objects[0]));
        empTypeName = String.valueOf(objects[1]);
        remark = String.valueOf(objects[2]);
        statusCd = String.valueOf(objects[3]);
    }
}
