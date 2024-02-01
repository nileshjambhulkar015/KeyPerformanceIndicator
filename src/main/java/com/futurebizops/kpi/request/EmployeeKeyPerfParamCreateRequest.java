package com.futurebizops.kpi.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.persistence.Column;
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

    @Schema(example = "1", description = "This field is used for Role Id")
    private Integer roleId;

    @Schema(example = "1", description = "This field is used for Department Id")
    private Integer deptId;

    @Schema(example = "1", description = "This field is used for Designation id")
    private Integer desigId;

    @Schema(example = "1", description = "This field is used for reporting employee id")
    private Integer reportingEmpId;

    @Schema(example = "A", description = "This field is used for Acive / Inactive")
    private String statusCd;

    @Schema(example = "e111", description = "This field is used for General Manager KPP status")
    private String employeeId;
}