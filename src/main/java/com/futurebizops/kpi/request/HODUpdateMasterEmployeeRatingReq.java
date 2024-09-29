package com.futurebizops.kpi.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Data
@ToString
@EqualsAndHashCode
public class HODUpdateMasterEmployeeRatingReq {

    List<HODUpdateDetailsEmpRatingsReq> kppUpdateRequests;

    @Schema(example = "2023-10-01", description = "This field is used for Employee Key Performance month")
    private String ekppMonth;

    @Schema(example = "60", description = "This field is used for Employee Key Performance month")
    String hodTotalAchivedWeight;

    @Schema(example = "56", description = "This field is used for Employee Key Performance month")
    String hodTotalOverallAchieve;

    @Schema(example = "54", description = "This field is used for Employee Key Performance month")
    String hodTotalOverallTaskComp;

    @Schema(example = "54", description = "This field is used for Employee Key Performance month")
    String totalOverallRatings;

    @Schema(example = "54", description = "This field is used for Employee Key Performance month")
    String totalOverallPercentage;

    @Schema(example = "Pending / In-Progress / Approved / Reject", description = "This field is used for Employee Key Performance month")
    String hodKppStatus;


    @Schema(example = "This is remark", description = "This field is used for Employee Key Performance month")
    String hodRemark;
}
