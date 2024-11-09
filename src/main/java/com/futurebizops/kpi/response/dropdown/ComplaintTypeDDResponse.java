package com.futurebizops.kpi.response.dropdown;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ComplaintTypeDDResponse {

    private Integer compTypeId;
    private String compTypeName;

    public ComplaintTypeDDResponse(Object[] objects){
        this.compTypeId=Integer.parseInt(String.valueOf(objects[0]));
        this.compTypeName=String.valueOf(objects[1]);
    }
}
