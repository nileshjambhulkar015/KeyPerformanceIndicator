package com.futurebizops.kpi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeKppDetailsDto {

    private Integer ekppId;
    private Integer kppId;
    private String empAchivedWeight;
    private String empOverallAchieve;
    private String empOverallTaskComp;
    private Integer hodEmployeeId;
    private String hodAchivedWeight;
    private String hodOverallAchieve;
    private String hodOverallTaskComp;
    private Integer gmEmployeeId;
    private String totalGmAchivedWeight;
    private String totalGmOverallAchieve;
    private String totalGmOverallTaskComp;
}
