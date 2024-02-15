package com.futurebizops.kpi.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssignKPPResponse {

    private Integer kppId;
    private Integer roleId;

    private Integer deptId;

    private Integer desigId;

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
    private String remark;

    public  AssignKPPResponse(Object[] objects){
        kppId=Integer.parseInt(String.valueOf(objects[0]));
        roleId=Integer.parseInt(String.valueOf(objects[1]));

        deptId=Integer.parseInt(String.valueOf(objects[2]));

        desigId=Integer.parseInt(String.valueOf(objects[3]));

        kppObjective=String.valueOf(objects[4]);
        kppPerformanceIndi=String.valueOf(objects[5]);
        kppOverallTarget=String.valueOf(objects[6]);
        kppTargetPeriod=String.valueOf(objects[7]);
        kppUoM=String.valueOf(objects[8]);
        kppOverallWeightage=String.valueOf(objects[9]);
        kppRating1=String.valueOf(objects[10]);
        kppRating2=String.valueOf(objects[11]);
        kppRating3=String.valueOf(objects[12]);
        kppRating4=String.valueOf(objects[13]);
        kppRating5=String.valueOf(objects[14]);
        remark=String.valueOf(objects[15]);
    }

}