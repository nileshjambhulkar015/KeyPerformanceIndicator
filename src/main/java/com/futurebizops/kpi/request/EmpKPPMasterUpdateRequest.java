package com.futurebizops.kpi.request;

import lombok.Data;

import java.util.List;

@Data
public class EmpKPPMasterUpdateRequest {
    List<EmpKPPUpdateRequest> kppUpdateRequests;
    String totalAchivedWeightage;
    String totalOverAllAchive;
    String totalOverallTaskCompleted;
    String ekppStatus;
    String remark;
    String evidence;
}
