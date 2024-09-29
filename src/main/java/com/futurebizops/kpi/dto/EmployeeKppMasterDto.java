package com.futurebizops.kpi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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
    private String totalOverallRatings;
    private String totalOverallPercentage;

    private String gmKppAppliedDate;
    private String gmKppStatus;
    private String gmRemark;
    private String remark;
    private String companyId;
    private String companyName;
    private String companyAddress;
    private String companyMbNo;
    private String companyFinYear;


   public EmployeeKppMasterDto(Object[] objects){
        this.eKppMId=Integer.parseInt(String.valueOf(objects[0]));
        this.ekppMonth=String.valueOf(objects[1]);
        this.empId=Integer.parseInt(String.valueOf(objects[2]));
        this.empName=String.valueOf(objects[3]);
        this.empEId=String.valueOf(objects[4]);
        this.roleId=Integer.parseInt(String.valueOf(objects[5]));
        this.roleName=String.valueOf(objects[6]);
        this.deptId=Integer.parseInt(String.valueOf(objects[7]));
        this.deptName=String.valueOf(objects[8]);
        this.desigId=Integer.parseInt(String.valueOf(objects[9]));
        this.desigName=String.valueOf(objects[10]);
        this.totalEmpAchivedWeight=String.valueOf(objects[11]);
        this.totalEmpOverallAchieve=String.valueOf(objects[12]);
        this.totalEmpOverallTaskComp=String.valueOf(objects[13]);
        this.empKppAppliedDate=String.valueOf(objects[14]);
        this.empKppStatus=String.valueOf(objects[15]);
        this.empRemark=String.valueOf(objects[16]);
        this.hodEmpId=Integer.parseInt(String.valueOf(objects[17]));
        this.totalHodAchivedWeight=String.valueOf(objects[18]);
        this.totalHodOverallAchieve=String.valueOf(objects[19]);
        this.totalHodOverallTaskComp=String.valueOf(objects[20]);
        this.hodKppAppliedDate=String.valueOf(objects[21]);
        this.hodKppStatus=String.valueOf(objects[22]);
        this.hodRemark=String.valueOf(objects[23]);
        this.gmEmpId=Integer.parseInt(String.valueOf(objects[24]));
        this.totalGmAchivedWeight=String.valueOf(objects[25]);
        this.totalGmOverallAchieve=String.valueOf(objects[26]);
        this.totalGmOverallTaskComp=String.valueOf(objects[27]);

       this.totalOverallRatings=String.valueOf(objects[28]);
       this.totalOverallPercentage=String.valueOf(objects[29]);

        this.gmKppAppliedDate=String.valueOf(objects[30]);
        this.gmKppStatus=String.valueOf(objects[31]);
        this.gmRemark=String.valueOf(objects[32]);
        this.remark=String.valueOf(objects[33]);
    }
}
