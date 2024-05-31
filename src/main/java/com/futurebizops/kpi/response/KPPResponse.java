package com.futurebizops.kpi.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KPPResponse {
    private Integer kppId;
    private String kppObjectiveNo;
    private String kppObjective;
    private String kppPerformanceIndi;
    private String kppOverallTarget;
    private String kppTargetPeriod;
    private Integer uomId;
    private String uomName;
   // private String kppOverallWeightage;
    private String kppRating1;
    private String kppRating2;
    private String kppRating3;
    private String kppRating4;
    private String kppRating5;
    private String remark;

    public  KPPResponse(Object[] objects){
        kppId=Integer.parseInt(String.valueOf(objects[0]));
        kppObjectiveNo=String.valueOf(objects[1]);
        kppObjective=String.valueOf(objects[2]);
        kppPerformanceIndi=String.valueOf(objects[3]);
        kppOverallTarget=String.valueOf(objects[4]);
        kppTargetPeriod=String.valueOf(objects[5]);
        uomId=Integer.parseInt(String.valueOf(objects[6]));
        uomName=String.valueOf(objects[7]);
       // kppOverallWeightage=String.valueOf(objects[8]);
        kppRating1=String.valueOf(objects[8]);
        kppRating2=String.valueOf(objects[9]);
        kppRating3=String.valueOf(objects[10]);
        kppRating4=String.valueOf(objects[11]);
        kppRating5=String.valueOf(objects[12]);
        remark=String.valueOf(objects[13]);
    }


}
