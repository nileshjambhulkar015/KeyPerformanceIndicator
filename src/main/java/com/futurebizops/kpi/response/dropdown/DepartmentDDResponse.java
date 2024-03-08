package com.futurebizops.kpi.response.dropdown;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentDDResponse {

    private String deptName;
    private Integer deptId;
    private String statusCd;

    public DepartmentDDResponse(Object[] objects){
        this.deptName=String.valueOf(objects[0]);
        this.deptId=Integer.parseInt(String.valueOf(objects[1]));
        this.statusCd=String.valueOf(objects[2]);
    }
}
