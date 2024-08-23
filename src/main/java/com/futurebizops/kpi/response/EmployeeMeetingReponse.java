package com.futurebizops.kpi.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeMeetingReponse {

    private Integer meetId;
    private String meetStartDate;
    private String meetEndDate;
    private Integer meetCreatedByEmpId;
    private String meetCreatedByEmpEId;
    private String meetCreatedByEmpName;
    private Integer meetCreatedByRoleId;
    private String meetCreatedByRoleName;
    private Integer meetCreatedByDeptId;
    private String meetCreatedByDeptName;
    private Integer meetCreatedByDesigId;
    private String meetCreatedByDesigName;
    private String meetVenue;
    private String meetTitle;
    private String meetDescription;
    private String meetStatus;
    private String remark;
    private String statusCd;

    public EmployeeMeetingReponse(Object[] objects){
        meetId=Integer.parseInt(String.valueOf(objects[0]));
        meetStartDate=String.valueOf(objects[1]);
        meetEndDate=String.valueOf(objects[2]);
        meetCreatedByEmpId=Integer.parseInt(String.valueOf(objects[3]));
        meetCreatedByEmpEId=String.valueOf(objects[4]);
        meetCreatedByEmpName=String.valueOf(objects[5]);
        meetCreatedByRoleId=Integer.parseInt(String.valueOf(objects[6]));
        meetCreatedByRoleName=String.valueOf(objects[7]);
        meetCreatedByDeptId=Integer.parseInt(String.valueOf(objects[8]));
        meetCreatedByDeptName=String.valueOf(objects[9]);
        meetCreatedByDesigId=Integer.parseInt(String.valueOf(objects[10]));
        meetCreatedByDesigName=String.valueOf(objects[11]);
        meetVenue=String.valueOf(objects[12]);
        meetTitle=String.valueOf(objects[13]);
        meetDescription=String.valueOf(objects[14]);
        meetStatus=String.valueOf(objects[15]);
        remark=String.valueOf(objects[16]);
        statusCd=String.valueOf(objects[17]);
    }
}
