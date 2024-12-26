package com.futurebizops.kpi.request.yearlykpprequest;

import com.futurebizops.kpi.request.EmpKPPUpdateRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

import javax.persistence.Column;
import java.time.Instant;
import java.util.List;

@Data
@ToString
public class FreezeEmpKPPMasterRequest {
    List<FreezeEmpKPPDetailsRequest> kppUpdateRequests;

    private String finYear;

    @Schema(example = "1", description = "This field is used for Employee E Id")
    private Integer empId;

    @Schema(example = "e1111", description = "This field is used for Employee E Id")
    private String empEId;

    @Schema(example = "1", description = "This field is used for Employee E Id")
    private Integer roleId;

    @Schema(example = "1", description = "This field is used for Department Id")
    private Integer deptId;

    @Schema(example = "1", description = "This field is used for Designation id")
    private Integer desigId;

    @Schema(example = "2023-10-01", description = "This field is used for Employee Key Performance month")
    private String ekppMonth;

    @Schema(example = "60", description = "This field is used for Employee Key Performance month")
    String totalEmpAchivedWeight;

    @Schema(example = "56", description = "This field is used for Employee Key Performance month")
    String totalEmpOverallAchieve;

    @Schema(example = "54", description = "This field is used for Employee Key Performance month")
    String totalEmpOverallTaskComp;

    private String empKppStatus;

    private String empRemark;

    private String empEvidence;

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

    private String avgTotalOverallRating;

    private String avgTotalOverallPer;

    private String statusCd;

    private String empKeyStrength;
    private String empAreaOfImprovement;
    private String empTrainginDevelopmentNeeds;
    private String remark;

    @Schema(example = "PM", description = "This field is used for Created User Id")
    private String employeeId;
}
