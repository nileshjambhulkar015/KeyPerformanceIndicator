package com.futurebizops.kpi.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UoMCreateRequest {

    @Schema(example = "KG", description = "This field is used for role name")
    private String uomName;

    @Schema(example = "This is remark", description = "This field is used for role remark")
    private String remark;

    @Schema(example = "A", description = "This field is used for role status")
    private String statusCd;

    @Schema(example = "PM", description = "This field is used for Created User Id")
    private String employeeId;
}
