package com.futurebizops.kpi.response;

import com.futurebizops.kpi.utils.DateTimeUtils;
import io.swagger.v3.oas.annotations.media.Schema;
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

    private Integer announTypeId;
    private String announTypeName;
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
        announTypeId=Integer.parseInt(String.valueOf(objects[1]));
        announTypeName=String.valueOf(objects[2]);
        if(null!=objects[3]) {
            this.announStartDate = DateTimeUtils.extractDateInDDMMYYY(String.valueOf(objects[3]));
        }
        if(null!=objects[4]) {
            this.announEndDate = DateTimeUtils.extractDateInDDMMYYY(String.valueOf(objects[4]));
        }

        announCreatedByEmpId=Integer.parseInt(String.valueOf(objects[5]));
        announCreatedByEmpEId=String.valueOf(objects[6]);
        announCreatedByEmpName=String.valueOf(objects[7]);
        announCreatedByRoleId=Integer.parseInt(String.valueOf(objects[8]));
        announCreatedByRoleName=String.valueOf(objects[9]);
        announCreatedByDeptId=Integer.parseInt(String.valueOf(objects[10]));
        announCreatedByDeptName=String.valueOf(objects[11]);
        announCreatedByDesigId=Integer.parseInt(String.valueOf(objects[12]));
        announCreatedByDesigName=String.valueOf(objects[13]);
        announVenue=String.valueOf(objects[14]);
        announTitle=String.valueOf(objects[15]);
        announDescription=String.valueOf(objects[16]);
        announStatus=String.valueOf(objects[17]);
        remark=String.valueOf(objects[18]);
        statusCd=String.valueOf(objects[19]);
    }
}
