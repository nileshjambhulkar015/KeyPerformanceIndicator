package com.futurebizops.kpi.service;

import com.futurebizops.kpi.response.KPIResponse;
import org.springframework.data.domain.Pageable;

public interface CumulativeService {

    public KPIResponse getAllEmployeeKPPStatusReport(String fromDate, String toDate,Integer empId,Integer roleId,  String statusCd, Pageable pageable);
    public KPIResponse allEmployeeKppDetails(String fromDate, String toDate, Integer roleId,Integer deptId,Integer desigId,Integer reportingEmpId,Integer gmEmpId);
}
