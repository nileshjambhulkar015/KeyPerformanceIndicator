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
    private String gmAchivedWeight;
    private String gmOverallAchieve;
    private String gmOverallTaskComp;

    private String kppObjective;
    private String kppPerformanceIndi;
    private String kppOverallTarget;
    private String kppTargetPeriod;
    private Integer uomId;
    private String uomName;
    private String kppOverallWeightage;
    private String kppRating1;
    private String kppRating2;
    private String kppRating3;
    private String kppRating4;
    private String kppRating5;
    private String ekppStatus;
}
