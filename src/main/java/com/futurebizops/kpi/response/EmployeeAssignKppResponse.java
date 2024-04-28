package com.futurebizops.kpi.response;

import lombok.Data;
import org.springframework.data.domain.PageImpl;

import java.util.List;

@Data
public class EmployeeAssignKppResponse {
    Double empKppOverallTargetCount;
    PageImpl<AssignKPPResponse> kppResponses;
}
