package com.futurebizops.kpi.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeKppRequest {
    private String empFullName;
    private Date kppDate;
    private String deptName;
    private String desigName;
    private String kppObjective;
    private String perfomanceIndicator;
    private double kppOverAllTarget;
    private String kppTargetPeriod;
    private String kppUom;
    private double kppOverallWeightage;
    private int kppRating1;
    private int kppRating2;
    private int kppRating3;
    private int kppRating4;
    private int kppRating5;
    private double achivedWeight;
}
