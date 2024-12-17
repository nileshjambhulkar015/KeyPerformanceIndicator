package com.futurebizops.kpi.service;

import com.futurebizops.kpi.request.CumulativeUpdateRequest;
import com.futurebizops.kpi.request.yearlykpprequest.FreezeEmpKPPMasterRequest;
import com.futurebizops.kpi.response.KPIResponse;
import com.futurebizops.kpi.response.dropdown.FinancialYearDDResponse;
import com.futurebizops.kpi.response.dropdown.FreezeFinancialYearDDResponse;

import java.util.List;

public interface FreezeCumulativeService {
    public KPIResponse saveFreezeCumulativeService(CumulativeUpdateRequest freezeCumulativeCreateRequest);

    public KPIResponse saveEmployeeKPPFeedbackDetails(FreezeEmpKPPMasterRequest freezeEmpKPPMasterRequest);

    public List<FreezeFinancialYearDDResponse> ddAllFinancialYear();
}
