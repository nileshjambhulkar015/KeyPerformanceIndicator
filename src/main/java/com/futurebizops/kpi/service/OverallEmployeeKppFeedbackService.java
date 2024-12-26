package com.futurebizops.kpi.service;

import com.futurebizops.kpi.request.yearlykpprequest.FinishKppFeedbackRequest;
import com.futurebizops.kpi.request.yearlykpprequest.FreezeEmpKPPMasterRequest;
import com.futurebizops.kpi.response.KPIResponse;

import com.futurebizops.kpi.response.dropdown.KppFinancialYearDDResponse;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface OverallEmployeeKppFeedbackService {

    public KPIResponse getEmployeeKppDataYearly(Integer empId, String finYear);

    public KPIResponse saveEmployeeKPPFeedbackDetails(FreezeEmpKPPMasterRequest freezeEmpKPPMasterRequest);
    
    public KPIResponse updateGMKPPFeedbackForEmployee(FreezeEmpKPPMasterRequest freezeEmpKPPMasterRequest);

    public KPIResponse finishByGMKppFeedback(FinishKppFeedbackRequest finishKppFeedbackRequest);

    public KPIResponse updateHODKPPFeedbackForEmployee(FreezeEmpKPPMasterRequest freezeEmpKPPMasterRequest);
    public List<KppFinancialYearDDResponse> ddAllFinancialYear();

    public KPIResponse getAllEmployeeKppFeedbackDetails(Integer empId,Integer roleId,String finYear,Integer reportingEmpId,Integer gmEmpId,String empKppStatus,String hodKppStatus,String gmKppStatus,Pageable pageable);
}
