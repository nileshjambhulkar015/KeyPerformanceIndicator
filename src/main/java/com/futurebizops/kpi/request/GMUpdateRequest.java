package com.futurebizops.kpi.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.Instant;

@Data
public class GMUpdateRequest {

    @Schema(example = "1", description = "This field is used for Employee Key Performance Id")
    private Integer ekppId;

    @Schema(example = "1", description = "This field is used for Department Id")
    private Integer empId;

    @Schema(example = "1", description = "This field is used for Department Id")
    private Integer deptId;

    @Schema(example = "1", description = "This field is used for Designation id")
    private Integer desigId;

    @Schema(example = "1", description = "This field is used for HOD employee id")
    private String hodEmpId;

    @Schema(example = "Accept", description = "This field is used for HOD KPP status")
    private String gmKppStatus;

    @Schema(example = "1", description = "This field is used for HOD rating")
    private String gmRating;

    @Schema(example = "1", description = "This field is used for HOD remark")
    private String gmRemark;

    @Schema(example = "1", description = "This field is used for HOD Approval Date")
    private Instant gmApprovedDate;

    @Schema(example = "e111", description = "This field is used for General Manager KPP status")
    private String lastUpdatedUserId;
}
