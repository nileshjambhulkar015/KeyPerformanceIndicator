package com.futurebizops.kpi.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.Column;

@Data
@AllArgsConstructor
@NoArgsConstructor
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

    private Integer hodEmpId;
    private String hodAchivedWeight;
    private String hodOverallAchieve;
    private String hodOverallTaskComp;
    private Integer gmEmpId;
    private String gmAchivedWeight;
    private String gmOverallAchieve;
    private String gmOverallTaskComp;


    private String kppRating1;
    private String kppRating2;
    private String kppRating3;
    private String kppRating4;
    private String kppRating5;
    private String ekppStatus;

    private String empName;

    public HodEmploeeKppResponse(Object[] objects) {
        eKppId = Integer.parseInt(String.valueOf(objects[0]));
        ekppMonth = String.valueOf(objects[1]);
        empId = null!=objects[2]? Integer.parseInt(String.valueOf(objects[2])):null;
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

       hodEmpId=Integer.parseInt(String.valueOf(objects[20]));
        hodAchivedWeight= String.valueOf(objects[21]);
      hodOverallAchieve= String.valueOf(objects[22]);
       hodOverallTaskComp= String.valueOf(objects[23]);
       gmEmpId= Integer.parseInt(String.valueOf(objects[24]));
       gmAchivedWeight= String.valueOf(objects[25]);
       gmOverallAchieve= String.valueOf(objects[26]);
        gmOverallTaskComp= String.valueOf(objects[27]);
        kppRating1 = String.valueOf(objects[28]);
        kppRating2 = String.valueOf(objects[29]);
        kppRating3 = String.valueOf(objects[30]);
        kppRating4 = String.valueOf(objects[31]);
        kppRating5 = String.valueOf(objects[32]);

        empName=String.valueOf(objects[33])+" "+String.valueOf(objects[34])+" "+String.valueOf(objects[35]);
    }
}
