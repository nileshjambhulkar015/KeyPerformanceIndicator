package com.futurebizops.kpi.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.persistence.Column;

@Data
public class EmployeeMeetingUpdateRequest {
    @Schema(example = "1", description = "This field is used for meeting id")
    private Integer meetId;

    @Schema(example = "2023-10-01", description = "This field is used for department id")
    private String meetStartDate;

    @Schema(example = "2023-10-01", description = "This field is used for designation name")
    private String meetEndDate;

    @Schema(example = "1", description = "This field is used for designation remark")
    private Integer meetCreatedByEmpId;

    @Schema(example = "e111", description = "This field is used for designation remark")
    private String meetCreatedByEmpEId;

    @Schema(example = "Nilesh Jambhulkar", description = "This field is used for designation remark")
    private String meetCreatedByEmpName;

    @Schema(example = "1", description = "This field is used for designation remark")
    private Integer meetCreatedByRoleId;

    @Schema(example = "Employee", description = "This field is used for designation remark")
    private String meetCreatedByRoleName;

    @Schema(example = "1", description = "This field is used for designation remark")
    private Integer meetCreatedByDeptId;

    @Schema(example = "Admin", description = "This field is used for designation remark")
    private String meetCreatedByDeptName;

    @Schema(example = "1", description = "This field is used for designation remark")@Column(name = "meet_created_by_emp_desig_id")
    private Integer meetCreatedByDesigId;

    @Schema(example = "Admin", description = "This field is used for designation remark")
    private String meetCreatedByDesigName;

    @Schema(example = "Pune", description = "This field is used for designation remark")
    private String meetVenue;

    @Schema(example = "This is meeting title", description = "This field is used for designation remark")
    private String meetTitle;

    @Schema(example = "This is meeting description", description = "This field is used for designation remark")
    private String meetDescription;

    @Schema(example = "This is meeting status", description = "This field is used for designation remark")
    private String meetStatus;

    @Schema(example = "This is remark", description = "This field is used for designation remark")
    private String remark;

    @Schema(example = "A", description = "This field is used for role status")
    private String statusCd;

    @Schema(example = "PM", description = "This field is used for Created User Id")
    private String employeeId;
}
