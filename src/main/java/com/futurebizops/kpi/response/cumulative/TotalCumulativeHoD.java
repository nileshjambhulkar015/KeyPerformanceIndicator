package com.futurebizops.kpi.response.cumulative;

import lombok.Data;

@Data
public class TotalCumulativeHoD {

    private String ekppMonth;
    private String totalEmpOverallAchieve;
    private String totalHodOverallAchieve;
    private String totalGmOverallAchieve;


   // private String totalEmpAchivedWeight;
    //private String totalHoDAchivedWeight;
    //private String totalGMAchivedWeight;

    private Double totalWeight;
}
