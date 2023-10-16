package com.futurebizops.kpi.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class EmployeeKeyPerfParamUpdateRequest {

    @Schema(example = "1", description = "This field is used for Employee Key Performance Id")
    private Integer ekppId;

    @Schema(example = "2023-10-01", description = "This field is used for Employee Key Performance month")
    private Timestamp ekppMonth;

    @Schema(example = "1", description = "This field is used for Employee Key Performance Id")
    private Integer kppId;

    @Schema(example = "1", description = "This field is used for Department Id")
    private Integer deptId;

    @Schema(example = "1", description = "This field is used for Designation id")
    private Integer desigId;

    @Schema(example = "100", description = "This field is used for Achived Weight")
    private String ekppAchivedWeight;

    @Schema(example = "80", description = "This field is used for Overall Achivement")
    private String ekppOverallAchieve;

    @Schema(example = "90", description = "This field is used for Overall Task Completed")
    private String ekppOverallTaskComp;

    @Schema(example = "2023-10-01", description = "This field is used for Applied date")
    private String ekppAppliedDate;

    @Schema(example = "http://data", description = "This field is used for evidence")
    private String ekppEvidence;

    @Schema(example = "e11", description = "This field is used for HOD Id")
    private String hodEmpId;

    @Schema(example = "Accept", description = "This field is used for HOD status")
    private String hodKppStatus;

    @Schema(example = "5", description = "This field is used for HOD rating")
    private String hodRating;

    @Schema(example = "HOD Remark", description = "This field is used for HOD remark")
    private String hodRemark;

    @Schema(example = "2023-10-01", description = "This field is used for HOD Approval date")
    private Timestamp hodApprovedDate;

    @Schema(example = "e1333", description = "This field is used for General Manager Id")
    private String gmEmpId;

    @Schema(example = "Accept", description = "This field is used for General Manager KPP Status")
    private String gmKppStatus;

    @Schema(example = "8", description = "This field is used for General Manager ratings")
    private String gmRating;

    @Schema(example = "GM remark", description = "This field is used for Employee Key Performance General Manager Remark")
    private String gmRemark;

    @Schema(example = "2023-10-01", description = "This field is used for General Manager Approval date")
    private Timestamp gmApprovedDate;

    @Schema(example = "Accept", description = "This field is used for General Manager KPP status")
    private String ekppStatus;

    @Schema(example = "Remark", description = "This field is used for remark")
    private String remark;

    @Schema(example = "A", description = "This field is used for Acive / Inactive")
    private String statusCd;

    @Schema(example = "e111", description = "This field is used for General Manager KPP status")
    private String employeeId;
}
