package com.futurebizops.kpi.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.persistence.Column;

@Data
public class AnnouncementUpdateRequest {
    @Schema(example = "1", description = "This field is used for meeting id")
    private Integer announId;

    @Schema(example = "2023-10-01", description = "This field is used for department id")
    private String announStartDate;

    @Schema(example = "2023-10-01", description = "This field is used for designation name")
    private String announEndDate;

    @Schema(example = "1", description = "This field is used for designation remark")
    private Integer announCreatedByEmpId;

    @Schema(example = "e111", description = "This field is used for designation remark")
    private String announCreatedByEmpEId;

    @Schema(example = "Nilesh Jambhulkar", description = "This field is used for designation remark")
    private String announCreatedByEmpName;

    @Schema(example = "1", description = "This field is used for designation remark")
    private Integer announCreatedByRoleId;

    @Schema(example = "Employee", description = "This field is used for designation remark")
    private String announCreatedByRoleName;

    @Schema(example = "1", description = "This field is used for designation remark")
    private Integer announCreatedByDeptId;

    @Schema(example = "Admin", description = "This field is used for designation remark")
    private String announCreatedByDeptName;

    @Schema(example = "1", description = "This field is used for designation remark")@Column(name = "announ_created_by_emp_desig_id")
    private Integer announCreatedByDesigId;

    @Schema(example = "Admin", description = "This field is used for designation remark")
    private String announCreatedByDesigName;

    @Schema(example = "Pune", description = "This field is used for designation remark")
    private String announVenue;

    @Schema(example = "This is announing title", description = "This field is used for designation remark")
    private String announTitle;

    @Schema(example = "This is announing description", description = "This field is used for designation remark")
    private String announDescription;

    @Schema(example = "This is announing status", description = "This field is used for designation remark")
    private String announStatus;

    @Schema(example = "This is remark", description = "This field is used for designation remark")
    private String remark;

    @Schema(example = "A", description = "This field is used for role status")
    private String statusCd;

    @Schema(example = "PM", description = "This field is used for Created User Id")
    private String employeeId;
}
