package com.futurebizops.kpi.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class HODUpdateDetailsEmpRatingsReq {


    @Schema(example = "1", description = "This field is used for Employee Key Performance Id")
    private Integer kppId;

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

    @Schema(example = "100", description = "This field is used for Achived Weight")
    private String hodAchivedWeight;

    @Schema(example = "80", description = "This field is used for Overall Achivement")
    private String hodOverallAchieve;

    @Schema(example = "90", description = "This field is used for Overall Task Completed")
    private String hodOverallTaskComp;

    @Schema(example = "e111", description = "This field is used for General Manager KPP status")
    private String employeeId;
}