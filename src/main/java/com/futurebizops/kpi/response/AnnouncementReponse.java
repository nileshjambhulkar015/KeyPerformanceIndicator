package com.futurebizops.kpi.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnnouncementReponse {

    private Integer announId;
    private String announStartDate;
    private String announEndDate;
    private Integer announCreatedByEmpId;
    private String announCreatedByEmpEId;
    private String announCreatedByEmpName;
    private Integer announCreatedByRoleId;
    private String announCreatedByRoleName;
    private Integer announCreatedByDeptId;
    private String announCreatedByDeptName;
    private Integer announCreatedByDesigId;
    private String announCreatedByDesigName;
    private String announVenue;
    private String announTitle;
    private String announDescription;
    private String announStatus;
    private String remark;
    private String statusCd;

    public AnnouncementReponse(Object[] objects){
        announId=Integer.parseInt(String.valueOf(objects[0]));
        announStartDate=String.valueOf(objects[1]);
        announEndDate=String.valueOf(objects[2]);
        announCreatedByEmpId=Integer.parseInt(String.valueOf(objects[3]));
        announCreatedByEmpEId=String.valueOf(objects[4]);
        announCreatedByEmpName=String.valueOf(objects[5]);
        announCreatedByRoleId=Integer.parseInt(String.valueOf(objects[6]));
        announCreatedByRoleName=String.valueOf(objects[7]);
        announCreatedByDeptId=Integer.parseInt(String.valueOf(objects[8]));
        announCreatedByDeptName=String.valueOf(objects[9]);
        announCreatedByDesigId=Integer.parseInt(String.valueOf(objects[10]));
        announCreatedByDesigName=String.valueOf(objects[11]);
        announVenue=String.valueOf(objects[12]);
        announTitle=String.valueOf(objects[13]);
        announDescription=String.valueOf(objects[14]);
        announStatus=String.valueOf(objects[15]);
        remark=String.valueOf(objects[16]);
        statusCd=String.valueOf(objects[17]);
    }
}
