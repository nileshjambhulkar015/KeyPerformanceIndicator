package com.futurebizops.kpi.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Data
@ToString
@EqualsAndHashCode
public class GMUpdateMasterEmployeeRatingReq {


    List<GMUpdateDetailsEmpRatingsReq> kppUpdateRequests;

    @Schema(example = "2023-10-01", description = "This field is used for Employee Key Performance month")
    private String ekppMonth;

    @Schema(example = "60", description = "This field is used for Employee Key Performance month")
    String gmTotalAchivedWeight;

    @Schema(example = "56", description = "This field is used for Employee Key Performance month")
    String gmTotalOverallAchieve;

    @Schema(example = "54", description = "This field is used for Employee Key Performance month")
    String gmTotalOverallTaskComp;

    @Schema(example = "54", description = "This field is used for Employee Key Performance month")
    String totalOverallRatings;

    @Schema(example = "54", description = "This field is used for Employee Key Performance month")
    String totalOverallPercentage;

    @Schema(example = "Pending / In-Progress / Approved / Reject", description = "This field is used for Employee Key Performance month")
    String gmKppStatus;

    @Schema(example = "This is remark", description = "This field is used for Employee Key Performance month")
    String gmRemark;

}
