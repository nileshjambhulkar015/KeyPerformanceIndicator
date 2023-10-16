package com.futurebizops.kpi.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Column;
import java.time.Instant;

@Data
public class HODApprovalUpdateRequest {
    @Schema(example = "1", description = "This field is used for Employee Key Performance Id")
    private Integer ekppId;

    @Schema(example = "1", description = "This field is used for Employee Key Performance Id")
    private Integer empId;

    @Schema(example = "1", description = "This field is used for Department Id")
    private Integer deptId;

    @Schema(example = "1", description = "This field is used for Designation id")
    private Integer desigId;

    @Schema(example = "1", description = "This field is used for HOD employee id")
    private String hodEmpId;

    @Schema(example = "Accept", description = "This field is used for HOD KPP status")
    private String hodKppStatus;

    @Schema(example = "1", description = "This field is used for HOD rating")
    private String hodRating;

    @Schema(example = "1", description = "This field is used for HOD remark")
    private String hodRemark;

    @Schema(example = "1", description = "This field is used for HOD Approval Date")
    private Instant hodApprovedDate;

    @Schema(example = "e111", description = "This field is used for General Manager KPP status")
    private String lastUpdatedUserId;
}
