package com.futurebizops.kpi.response;

import lombok.Data;

@Data
public class HodEmploeeKppResponse {
    private Integer eKppId;
    private String ekppMonth;
    private Integer empId;
    private String empEId;

    private Integer roleId;
    private String roleName;
    private Integer deptId;
    private String deptName;
    private Integer desigId;
    private String desigName;
    private Integer kppId;
    private String kppObjective;
    private String kppPerformanceIndi;
    private String kppOverallTarget;
    private String kppTargetPeriod;
    private String kppUoM;
    private String kppOverallWeightage;
    private String ekppAchivedWeight;
    private String ekppOverallAchieve;
    private String ekppOverallTaskComp;
    private String ekppAppliedDate;
    private String ekppEvidence;
    private String kppRating1;
    private String kppRating2;
    private String kppRating3;
    private String kppRating4;
    private String kppRating5;
    private String ekppStatus;

    public HodEmploeeKppResponse(Object[] objects) {
        eKppId = Integer.parseInt(String.valueOf(objects[0]));
        ekppMonth = String.valueOf(objects[1]);
        empId = Integer.parseInt(String.valueOf(objects[2]));
        empEId = String.valueOf(objects[3]);
        roleId = Integer.parseInt(String.valueOf(objects[4]));
        roleName = String.valueOf(objects[5]);
        deptId = Integer.parseInt(String.valueOf(objects[6]));
        deptName = String.valueOf(objects[7]);
        desigId = Integer.parseInt(String.valueOf(objects[8]));
        desigName = String.valueOf(objects[9]);
        kppId = Integer.parseInt(String.valueOf(objects[10]));
        kppObjective = String.valueOf(objects[11]);
        kppPerformanceIndi = String.valueOf(objects[12]);
       kppOverallTarget = String.valueOf(objects[13]);
       kppTargetPeriod = String.valueOf(objects[14]);
       kppUoM = String.valueOf(objects[15]);
        kppOverallWeightage= String.valueOf(objects[16]);
        ekppAchivedWeight = String.valueOf(objects[17]);
        ekppOverallAchieve = String.valueOf(objects[18]);
        ekppOverallTaskComp = String.valueOf(objects[19]);
        ekppAppliedDate = String.valueOf(objects[20]);
        ekppEvidence = String.valueOf(objects[21]);
        kppRating1 = String.valueOf(objects[22]);
        kppRating2 = String.valueOf(objects[23]);
        kppRating3 = String.valueOf(objects[24]);
        kppRating4 = String.valueOf(objects[25]);
        kppRating5 = String.valueOf(objects[26]);
        ekppStatus= String.valueOf(objects[27]);
    }
}
