package com.futurebizops.kpi.service;

import com.futurebizops.kpi.request.CumulativeUpdateRequest;
import com.futurebizops.kpi.request.yearlykpprequest.FreezeEmpKPPMasterRequest;
import com.futurebizops.kpi.response.KPIResponse;

public interface FreezeCumulativeService {
    public KPIResponse saveFreezeCumulativeService(CumulativeUpdateRequest freezeCumulativeCreateRequest);

    public KPIResponse saveEmployeeKPPFeedbackDetails(FreezeEmpKPPMasterRequest freezeEmpKPPMasterRequest);
}
