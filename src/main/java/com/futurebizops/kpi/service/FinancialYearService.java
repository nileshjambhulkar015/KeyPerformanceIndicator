package com.futurebizops.kpi.service;

import com.futurebizops.kpi.request.EmployeeTypeCreateRequest;
import com.futurebizops.kpi.request.EmployeeTypeUpdateRequest;
import com.futurebizops.kpi.request.FinancialYearCreateRequest;
import com.futurebizops.kpi.request.FinancialYearUpdateRequest;
import com.futurebizops.kpi.response.EmployeeTypeResponse;
import com.futurebizops.kpi.response.KPIResponse;

public interface FinancialYearService {

    public KPIResponse saveFinancialYear(FinancialYearCreateRequest financialYearCreateRequest);

    public KPIResponse updateFinancialYear(FinancialYearUpdateRequest financialYearUpdateRequest);

    public KPIResponse findFinancialYear(Integer finYearId, String finYearName, String statusCd);

    public KPIResponse deleteFinancialYear(Integer finYearId);
}

