package com.futurebizops.kpi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeKppMasterDto {

    private Integer eKppMId;
    private String ekppMonth;
    private Integer empId;
    private String empName;
    private String empEId;
    private Integer roleId;
    private String roleName;
    private Integer deptId;
    private String deptName;
    private Integer desigId;
    private String desigName;
    private String totalEmpAchivedWeight;
    private String totalEmpOverallAchieve;
    private String totalEmpOverallTaskComp;
    private String empKppAppliedDate;
    private String empKppStatus;
    private String empRemark;
    private Integer hodEmpId;
    private String totalHodAchivedWeight;
    private String totalHodOverallAchieve;
    private String totalHodOverallTaskComp;
    private String hodKppAppliedDate;
    private String hodKppStatus;
    private String hodRemark;
    private Integer gmEmpId;
    private String totalGmAchivedWeight;
    private String totalGmOverallAchieve;
    private String totalGmOverallTaskComp;
    private String gmKppAppliedDate;
    private String gmKppStatus;
    private String gmRemark;
    private String remark;

}
