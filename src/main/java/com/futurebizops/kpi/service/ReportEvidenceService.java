package com.futurebizops.kpi.service;

import com.futurebizops.kpi.request.ReportEvidenceCreateRequest;
import com.futurebizops.kpi.response.KPIResponse;

public interface ReportEvidenceService {
    public KPIResponse saveReportEvidence(ReportEvidenceCreateRequest reportEvidenceCreateRequest);

    public byte[] findReportEvidenceByEmpIdAndMonth(Integer empId, String evMonth);
}
