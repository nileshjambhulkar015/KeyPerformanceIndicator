package com.futurebizops.kpi.response;

import com.futurebizops.kpi.utils.DateTimeUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OverallEmpDetailsKppFeedbackResponse {

    private Integer empId;
    private String empEId;
    private String empName;

    private Integer roleId;
    private String roleName;
    private Integer deptId;
    private String deptName;
    private Integer desigId;
    private String desigName;
    private String finYear;
    private String totalEmpAchivedWeight;
    private String totalEmpOverallAchieve;
    private String totalEmpOverallTaskComp;
    private Integer hodEmpId;
    private String totalHodAchivedWeight;
    private String totalHodOverallAchieve;
    private String totalHodOverallTaskComp;
    private Integer gmEmpId;
    private String totalGmAchivedWeight;
    private String totalGmOverallAchieve;
    private String totalGmOverallTaskComp;

    public OverallEmpDetailsKppFeedbackResponse(Object[] objects) {
        empId = Integer.parseInt(String.valueOf(objects[0]));
        empEId = String.valueOf(objects[1]);
        empName = String.valueOf(objects[2]);
        roleId = Integer.parseInt(String.valueOf(objects[3]));
        roleName = String.valueOf(objects[4]);
        deptId = Integer.parseInt(String.valueOf(objects[5]));
        deptName = String.valueOf(objects[6]);
        desigId = Integer.parseInt(String.valueOf(objects[7]));
        desigName = String.valueOf(objects[8]);
        finYear = String.valueOf(objects[9]);
        totalEmpAchivedWeight = String.valueOf(objects[10]);
        totalEmpOverallAchieve = String.valueOf(objects[11]);
        totalEmpOverallTaskComp = String.valueOf(objects[12]);
        hodEmpId = Integer.parseInt(String.valueOf(objects[13]));
        totalHodAchivedWeight = String.valueOf(objects[14]);
        totalHodOverallAchieve = String.valueOf(objects[15]);
        totalHodOverallTaskComp = String.valueOf(objects[16]);
        gmEmpId = Integer.parseInt(String.valueOf(objects[17]));
        totalGmAchivedWeight = String.valueOf(objects[18]);
        totalGmOverallAchieve = String.valueOf(objects[19]);
        totalGmOverallTaskComp = String.valueOf(objects[20]);

    }
}
