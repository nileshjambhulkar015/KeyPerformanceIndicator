package com.futurebizops.kpi.response.dropdown;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KppFinancialYearDDResponse {

    private String finYearId;
    private String finYear;

    public KppFinancialYearDDResponse(Object[] objects){
        this.finYearId=String.valueOf(objects[0]);
        this.finYear=String.valueOf(objects[0]);
    }
}
