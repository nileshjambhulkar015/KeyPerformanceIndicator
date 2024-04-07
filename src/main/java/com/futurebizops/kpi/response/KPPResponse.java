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
    private Integer roleId;
    private String roleName;
    private Integer deptId;
    private String deptName;
    private Integer desigId;
    private String desigName;
    private String kppObjectiveNo;
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
    private String remark;

    public  KPPResponse(Object[] objects){
        kppId=Integer.parseInt(String.valueOf(objects[0]));
        roleId=Integer.parseInt(String.valueOf(objects[1]));
        roleName=String.valueOf(objects[2]);
        deptId=Integer.parseInt(String.valueOf(objects[3]));
        deptName=String.valueOf(objects[4]);
        desigId=Integer.parseInt(String.valueOf(objects[5]));
        desigName=String.valueOf(objects[6]);
        kppObjectiveNo=String.valueOf(objects[7]);
        kppObjective=String.valueOf(objects[8]);
        kppPerformanceIndi=String.valueOf(objects[9]);
        kppOverallTarget=String.valueOf(objects[10]);
        kppTargetPeriod=String.valueOf(objects[11]);
        uomId=Integer.parseInt(String.valueOf(objects[12]));
        uomName=String.valueOf(objects[13]);
        kppOverallWeightage=String.valueOf(objects[14]);
        kppRating1=String.valueOf(objects[15]);
        kppRating2=String.valueOf(objects[16]);
        kppRating3=String.valueOf(objects[17]);
        kppRating4=String.valueOf(objects[18]);
        kppRating5=String.valueOf(objects[19]);
        remark=String.valueOf(objects[20]);
    }


}
