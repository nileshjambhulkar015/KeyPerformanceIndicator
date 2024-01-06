package com.futurebizops.kpi.response;
//Showing list of kpp details for Employee kpp status response class

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class KppStatusDetails {

    private Integer ekppId;
    private Instant ekppMonth;
    private Integer kppId;
    private Integer empId;
    private String empEId;
    private Integer roleId;
    private Integer deptId;
    private Integer desigId;
    private String empAchivedWeight;
    private String empOverallAchieve;
    private String empOverallTaskComp;
    private Integer hodEmpId;
    private String hodAchivedWeight;
    private String hodOverallAchieve;
    private String hodOverallTaskComp;
    private Integer gmEmpId;
    private String gmAchivedWeight;
    private String gmOverallAchieve;
    private String gmOverallTaskComp;
}
