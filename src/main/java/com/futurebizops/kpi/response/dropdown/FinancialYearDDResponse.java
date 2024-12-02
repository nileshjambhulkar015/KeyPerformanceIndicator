package com.futurebizops.kpi.response.dropdown;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FinancialYearDDResponse {

    private Integer finYearId;
    private String finYear;

    public FinancialYearDDResponse(Object[] objects){
        this.finYearId=Integer.parseInt(String.valueOf(objects[0]));
        this.finYear=String.valueOf(objects[1]);
    }
}
