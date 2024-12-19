package com.futurebizops.kpi.service;

import com.futurebizops.kpi.request.CumulativeUpdateRequest;
import com.futurebizops.kpi.response.KPIResponse;
import com.futurebizops.kpi.response.dropdown.KppFinancialYearDDResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CumulativeService {

    public KPIResponse getAllEmployeeKPPStatusReport(String fromDate, String toDate,Integer empId,Integer roleId,  String statusCd, Pageable pageable);
    public KPIResponse allEmployeeKppDetails(String fromDate, String toDate, Integer roleId,Integer deptId,Integer desigId,Integer reportingEmpId,Integer gmEmpId,Pageable pageable);

    public KPIResponse updateOverallEmployeeKppReportRemark(CumulativeUpdateRequest cumulativeUpdateRequest);

    public List<KppFinancialYearDDResponse> ddAllFinancialYear();
}
