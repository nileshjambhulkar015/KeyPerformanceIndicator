package com.futurebizops.kpi.request.uploadexcel;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class KeyPerfParamExcelReadData {

    @Schema(example = "1", description = "This field is used for role id")
    private Integer roleId;

    @Schema(example = "Objective of KPP", description = "This field is used for rating ratio 1")
    private String roleName;

    @Schema(example = "1", description = "This field is used for rating ratio 1")
    private Integer deptId;

    @Schema(example = "Objective of KPP", description = "This field is used for rating ratio 1")
    private String deptName;

    @Schema(example = "1", description = "This field is used for rating ratio 1")
    private Integer desigId;

    @Schema(example = "Objective of KPP", description = "This field is used for rating ratio 1")
    private String desigName;

    @Schema(example = "Objective of KPP", description = "This field is used for rating ratio 1")
    private String kppObjective;

    @Schema(example = "1", description = "This field is used for rating ratio 1")
    private String kppPerformanceIndi;

    @Schema(example = "100", description = "This field is used for rating ratio 1")
    private String kppOverallTarget;

    @Schema(example = "monthly", description = "This field is used for rating ratio 1")
    private String kppTargetPeriod;

    @Schema(example = "perentage", description = "This field is used for rating ratio 1")
    private String kppUoM;

    @Schema(example = "25", description = "This field is used for rating ratio 1")
    private String kppOverallWeightage;

    @Schema(example = "95%", description = "This field is used for rating ratio 1")
    private String kppRating1;

    @Schema(example = "92%", description = "This field is used for rating ratio 2")
    private String kppRating2;

    @Schema(example = "80%", description = "This field is used for rating ratio 3")
    private String kppRating3;

    @Schema(example = "87%", description = "This field is used for rating ratio 4")
    private String kppRating4;

    @Schema(example = "88%", description = "This field is used for rating ratio 5")
    private String kppRating5;

    @Schema(example = "description", description = "This field is used for department remark")
    private String rrDescription;

    @Schema(example = "This is remark", description = "This field is used for department remark")
    private String remark;

    @Schema(example = "A", description = "This field is used for role status")
    private String statusCd;

    @Schema(example = "nilesh", description = "This field is used for Created User Id")
    private String employeeId;
}
