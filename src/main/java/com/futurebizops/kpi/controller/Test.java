package com.futurebizops.kpi.controller;

import com.futurebizops.kpi.entity.EmployeeKeyPerfParamEntity;
import com.futurebizops.kpi.entity.KeyPerfParamEntity;

import javax.persistence.Column;
import java.time.Instant;
import java.util.List;

public class Test {
    Integer empId;
    Integer deptId;
    Integer desigId;
    Instant currentDate;

    List<KeyPerfParam> keyPerfParamEntities;
}

class KeyPerfParam{
    private Integer kppId;
    private String kppObjective;
    private String kppPerformanceIndi;
    private String kppOverallTarget;
    private String kppTargetPeriod;
    private String kppUoM;
    private String kppOverallWeightage;
    private String kppRating1;
    private String kppRating2;
    private String kppRating3;
    private String kppRating4;
    private String kppRating5;
    private String kppDescription;
    //employeeeKPP Table
    private String ekppAchivedWeight;
    private String ekppOverallAchieve;
    private String ekppOverallTaskComp;
}