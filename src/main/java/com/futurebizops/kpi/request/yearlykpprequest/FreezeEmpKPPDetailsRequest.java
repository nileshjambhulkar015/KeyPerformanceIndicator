package com.futurebizops.kpi.request.yearlykpprequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class FreezeEmpKPPDetailsRequest {

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

    @Schema(example = "Overall Target", description = "This field is used for Designation id")
    private String kppOverallTarget;

    @Schema(example = "Overall Weightage", description = "This field is used for Designation id")
    private String kppOverallWeightage;

    @Schema(example = "100", description = "This field is used for Achived Weight")
    private String empAchivedWeight;

    @Schema(example = "80", description = "This field is used for Overall Achivement")
    private String empOverallAchieve;

    @Schema(example = "90", description = "This field is used for Overall Task Completed")
    private String empOverallTaskComp;

    private Integer hodEmpId;


    private String hodAchivedWeight;


    private String hodOverallAchieve;


    private String hodOverallTaskComp;


    private Integer gmEmployeeId;


    private String gmAchivedWeight;


    private String gmOverallAchieve;


    private String gmOverallTaskComp;

    //private String avgOverallRating;


    //private String avgOverallPer;


    private String statusCd;

    @Schema(example = "54", description = "This field is used for Employee Key Performance month")
    String overallRatings;

    @Schema(example = "54", description = "This field is used for Employee Key Performance month")
    String overallPercentage;

    private String empKppFeedback;

    @Schema(example = "e111", description = "This field is used for General Manager KPP status")
    private String employeeId;
}
