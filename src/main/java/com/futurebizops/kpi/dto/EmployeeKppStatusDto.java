package com.futurebizops.kpi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeKppStatusDto {

    EmployeeKppMasterDto employeeKppMasterDto=new EmployeeKppMasterDto();
    EmployeeKppDetailsDto employeeKppDetailsDto=new EmployeeKppDetailsDto();
   /* private Integer eKppMId;
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
    private String totalAchivedWeight;
    private String totalOverallAchieve;
    private String totalOverallTaskComp;
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
    private String gmAchivedWeight;
    private String gmOverallAchieve;
    private String gmOverallTaskComp;
    private String gmKppAppliedDate;
    private String gmKppStatus;
    private String gmRemark;
    private String remark;

    private Integer ekppId;
    private Integer kppId;
    private String empAchivedWeight;
    private String empOverallAchieve;
    private String empOverallTaskComp;
    private Integer hodEmployeeId;
    private String hodAchivedWeight;
    private String hodOverallAchieve;
    private String hodOverallTaskComp;
    private Integer gmEmployeeId;
    private String totalGmAchivedWeight;
    private String totalGmOverallAchieve;
    private String totalGmOverallTaskComp;*/

    public EmployeeKppStatusDto(Object[] objects) {
        employeeKppMasterDto.setEKppMId(Integer.parseInt(String.valueOf(objects[0])));
        employeeKppMasterDto.setEkppMonth(String.valueOf(objects[1]));
        employeeKppMasterDto.setEmpId(Integer.parseInt(String.valueOf(objects[2])));
        employeeKppMasterDto.setEmpName(String.valueOf(objects[3]) + " " + String.valueOf(objects[4]) + " " + String.valueOf(objects[5]));
        employeeKppMasterDto.setEmpEId(String.valueOf(objects[6]));
        employeeKppMasterDto.setRoleId(Integer.parseInt(String.valueOf(objects[7])));
        employeeKppMasterDto.setRoleName(String.valueOf(objects[8]));
        employeeKppMasterDto.setDeptId(Integer.parseInt(String.valueOf(objects[9])));
        employeeKppMasterDto.setDeptName(String.valueOf(objects[10]));
        employeeKppMasterDto.setDesigId(Integer.parseInt(String.valueOf(objects[11])));
        employeeKppMasterDto.setDesigName(String.valueOf(objects[12]));
        employeeKppMasterDto.setTotalAchivedWeight(String.valueOf(objects[13]));
        employeeKppMasterDto.setTotalOverallAchieve(String.valueOf(objects[14]));
        employeeKppMasterDto.setTotalOverallTaskComp(String.valueOf(objects[15]));
        employeeKppMasterDto.setEmpKppAppliedDate(String.valueOf(objects[16]));
        employeeKppMasterDto.setEmpKppStatus(String.valueOf(objects[17]));
        employeeKppMasterDto.setEmpRemark(String.valueOf(objects[18]));
        employeeKppMasterDto.setHodEmpId(Integer.parseInt(String.valueOf(objects[19])));
        employeeKppMasterDto.setTotalHodAchivedWeight(String.valueOf(objects[20]));
        employeeKppMasterDto.setTotalOverallAchieve(String.valueOf(objects[21]));
        employeeKppMasterDto.setTotalOverallTaskComp(String.valueOf(objects[22]));
        employeeKppMasterDto.setHodKppAppliedDate(String.valueOf(objects[23]));
        employeeKppMasterDto.setHodKppStatus(String.valueOf(objects[24]));
        employeeKppMasterDto.setHodRemark(String.valueOf(objects[25]));
        employeeKppMasterDto.setGmEmpId(Integer.parseInt(String.valueOf(objects[26])));
        employeeKppMasterDto.setTotalGmAchivedWeight(String.valueOf(objects[27]));
        employeeKppMasterDto.setTotalGmOverallAchieve(String.valueOf(objects[28]));
        employeeKppMasterDto.setTotalGmOverallTaskComp(String.valueOf(objects[29]));
        employeeKppMasterDto.setGmKppAppliedDate(String.valueOf(objects[30]));
        employeeKppMasterDto.setGmKppStatus(String.valueOf(objects[31]));
        employeeKppMasterDto.setGmRemark(String.valueOf(objects[32]));
        employeeKppMasterDto.setRemark(String.valueOf(objects[33]));

        employeeKppDetailsDto.setEkppId(Integer.parseInt(String.valueOf(objects[34])));
        employeeKppDetailsDto.setKppId(Integer.parseInt(String.valueOf(objects[35])));
        employeeKppDetailsDto.setEmpAchivedWeight(String.valueOf(objects[36]));
        employeeKppDetailsDto.setEmpOverallAchieve(String.valueOf(objects[37]));
        employeeKppDetailsDto.setEmpOverallTaskComp(String.valueOf(objects[38]));
        employeeKppDetailsDto.setHodEmployeeId(Integer.parseInt(String.valueOf(objects[39])));
        employeeKppDetailsDto.setHodAchivedWeight(String.valueOf(objects[40]));
        employeeKppDetailsDto.setHodOverallAchieve(String.valueOf(objects[41]));
        employeeKppDetailsDto.setHodOverallTaskComp(String.valueOf(objects[42]));
        employeeKppDetailsDto.setGmEmployeeId(Integer.parseInt(String.valueOf(objects[43])));
        employeeKppDetailsDto.setGmAchivedWeight(String.valueOf(objects[44]));
        employeeKppDetailsDto.setGmOverallAchieve(String.valueOf(objects[45]));
        employeeKppDetailsDto.setGmOverallTaskComp(String.valueOf(objects[46]));

        employeeKppDetailsDto.setKppObjective(String.valueOf(objects[47]));
        employeeKppDetailsDto.setKppPerformanceIndi(String.valueOf(objects[48]));
        employeeKppDetailsDto.setKppOverallTarget(String.valueOf(objects[49]));
        employeeKppDetailsDto.setKppTargetPeriod(String.valueOf(objects[50]));
        employeeKppDetailsDto.setKppUoM(String.valueOf(objects[51]));
        employeeKppDetailsDto.setKppOverallWeightage(String.valueOf(objects[52]));

        employeeKppDetailsDto.setKppRating1(String.valueOf(objects[53]));
        employeeKppDetailsDto.setKppRating2(String.valueOf(objects[54]));
        employeeKppDetailsDto.setKppRating3(String.valueOf(objects[55]));
        employeeKppDetailsDto.setKppRating4(String.valueOf(objects[56]));
        employeeKppDetailsDto.setKppRating5(String.valueOf(objects[57]));
    }
}
