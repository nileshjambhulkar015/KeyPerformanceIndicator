package com.futurebizops.kpi.service;

import com.futurebizops.kpi.request.yearlykpprequest.FreezeEmpKPPMasterRequest;
import com.futurebizops.kpi.response.KPIResponse;

import com.futurebizops.kpi.response.dropdown.KppFinancialYearDDResponse;

import java.util.List;

public interface OverallEmployeeKppFeedbackService {

    public KPIResponse getEmployeeKppDataYearly(Integer empId, String finYear);

    public KPIResponse saveEmployeeKPPFeedbackDetails(FreezeEmpKPPMasterRequest freezeEmpKPPMasterRequest);

    public List<KppFinancialYearDDResponse> ddAllFinancialYear();
}
