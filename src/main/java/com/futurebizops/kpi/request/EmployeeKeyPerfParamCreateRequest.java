package com.futurebizops.kpi.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.persistence.Column;
import java.sql.Timestamp;
import java.time.Instant;

@Data
public class EmployeeKeyPerfParamCreateRequest {


    @Schema(example = "2023-10-01", description = "This field is used for Employee Key Performance month")
    private Instant ekppMonth;

    @Schema(example = "1", description = "This field is used for Employee Key Performance Id")
    private Integer kppId;

    @Schema(example = "1", description = "This field is used for Employee Id")
    private Integer empId;

    @Schema(example = "e1111", description = "This field is used for Employee E Id")
    private String empEId;

    @Column(name = "role_id")
    private Integer roleId;

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
    private Instant ekppAppliedDate;

    @Schema(example = "file.txt", description = "This field is used for evidence")
    private String ekppEvidence;

    @Schema(example = "Accept", description = "This field is used for General Manager KPP status")
    private String ekppStatus;

    @Schema(example = "Remark", description = "This field is used for remark")
    private String remark;

    @Schema(example = "A", description = "This field is used for Acive / Inactive")
    private String statusCd;

    @Schema(example = "e111", description = "This field is used for General Manager KPP status")
    private String employeeId;
}
