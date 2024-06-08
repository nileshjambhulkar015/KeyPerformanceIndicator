package com.futurebizops.kpi.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import lombok.AllArgsConstructor;
        import lombok.Builder;
        import lombok.Data;
        import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssignKPPResponseSearch {

    private Integer kppId;
    private String kppObjectiveNo;
    private String kppObjective;
    private String kppPerformanceIndi;
    private Integer uomId;
    private String kppUoM;

    private String kppRating1;
    private String kppRating2;
    private String kppRating3;
    private String kppRating4;
    private String kppRating5;


    private String kppTargetPeriod;

    public  AssignKPPResponseSearch(Object[] objects){
        kppId=Integer.parseInt(String.valueOf(objects[0]));
        kppObjectiveNo=String.valueOf(objects[1]);
        kppObjective=String.valueOf(objects[2]);
        kppPerformanceIndi=String.valueOf(objects[3]);

        kppTargetPeriod=String.valueOf(objects[4]);
        uomId=Integer.parseInt(String.valueOf(objects[5]));
        kppUoM=String.valueOf(objects[6]);

        kppRating1=String.valueOf(objects[7]);
        kppRating3=String.valueOf(objects[8]);
        kppRating2=String.valueOf(objects[9]);
        kppRating4=String.valueOf(objects[10]);
        kppRating5=String.valueOf(objects[11]);

    }

}
