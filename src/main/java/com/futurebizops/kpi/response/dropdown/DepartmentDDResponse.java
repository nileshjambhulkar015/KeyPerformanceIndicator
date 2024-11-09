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

    private Integer deptId;
    private String deptName;

    public DepartmentDDResponse(Object[] objects){
        this.deptId=Integer.parseInt(String.valueOf(objects[0]));
        this.deptName=String.valueOf(objects[1]);
    }
}
