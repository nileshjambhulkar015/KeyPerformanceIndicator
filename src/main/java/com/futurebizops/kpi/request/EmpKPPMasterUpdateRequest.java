package com.futurebizops.kpi.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class EmpKPPMasterUpdateRequest {
    List<EmpKPPUpdateRequest> kppUpdateRequests;

    @Schema(example = "60", description = "This field is used for Employee Key Performance month")
    String totalAchivedWeightage;

    @Schema(example = "56", description = "This field is used for Employee Key Performance month")
    String totalOverAllAchive;

    @Schema(example = "54", description = "This field is used for Employee Key Performance month")
    String totalOverallTaskCompleted;

    @Schema(example = "Pending / In-Progress / Approved / Reject", description = "This field is used for Employee Key Performance month")
    String ekppStatus;

    @Schema(example = "This is remark", description = "This field is used for Employee Key Performance month")
    String empRemark;

    String evidence;
}
