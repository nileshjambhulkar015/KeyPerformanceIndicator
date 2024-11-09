package com.futurebizops.kpi.response.dropdown;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDDResponse {
    private Integer empId;
    private String empName;

    public EmployeeDDResponse(Object[] objects){
        this.empId=Integer.parseInt(String.valueOf(objects[0]));
        this.empName=String.valueOf(objects[1])+" "+String.valueOf(objects[2])+" "+String.valueOf(objects[3]);
    }
}
