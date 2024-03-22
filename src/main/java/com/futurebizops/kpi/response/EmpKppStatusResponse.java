package com.futurebizops.kpi.response;

import com.futurebizops.kpi.dto.EmployeeKppDetailsDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmpKppStatusResponse {

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
    private Instant empKppAppliedDate;
    private String empKppStatus;
    private String empRemark;
    private Integer hodEmpId;
    private String totalHodAchivedWeight;
    private String totalHodOverallAchieve;
    private String totalHodOverallTaskComp;
    private Instant hodKppAppliedDate;
    private String hodKppStatus;
    private String hodRemark;
    private Integer gmEmpId;
    private String totalGmAchivedWeight;
    private String totalGmOverallAchieve;
    private String totalGmOverallTaskComp;
    private Instant gmKppAppliedDate;
    private String gmKppStatus;
    private String gmRemark;
    private String remark;

    private String companyId;
    private String companyName;
    private String companyAddress;
    private String companyMbNo;
    private String companyFinYear;

    List<EmployeeKppDetailsDto> kppStatusDetails;
}
