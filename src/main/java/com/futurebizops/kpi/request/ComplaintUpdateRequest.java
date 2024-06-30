package com.futurebizops.kpi.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ComplaintUpdateRequest {

    @Schema(example = "1", description = "This field is used for complaint date")
    private String compId;

    @Schema(example = "HR and Admin", description = "This field is used for complaint date")
    private String compDate;

    @Schema(example = "1", description = "This field is used for complaint type")
    private Integer compTypeId;

    @Schema(example = "HR and Admin", description = "This field is used for complaint description")
    private String compDesc;

    @Schema(example = "This is remark", description = "This field is used for department remark")
    private String remark;

    @Schema(example = "A", description = "This field is used for role status")
    private String statusCd;

    @Schema(example = "e1422", description = "This field is used for Created User Id")
    private String employeeId;
}
