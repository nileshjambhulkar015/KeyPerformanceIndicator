package com.futurebizops.kpi.service;

import com.futurebizops.kpi.request.EmployeeTypeCreateRequest;
import com.futurebizops.kpi.request.EmployeeTypeUpdateRequest;
import com.futurebizops.kpi.request.FinancialYearCreateRequest;
import com.futurebizops.kpi.request.FinancialYearUpdateRequest;
import com.futurebizops.kpi.response.EmployeeTypeResponse;
import com.futurebizops.kpi.response.KPIResponse;
import com.futurebizops.kpi.response.dropdown.DepartmentDDResponse;
import com.futurebizops.kpi.response.dropdown.FinancialYearDDResponse;

import java.util.List;

public interface FinancialYearService {

    public KPIResponse saveFinancialYear(FinancialYearCreateRequest financialYearCreateRequest);

    public KPIResponse updateFinancialYear(FinancialYearUpdateRequest financialYearUpdateRequest);

    public KPIResponse findFinancialYear(Integer finYearId, String finYearName, String statusCd);

    public KPIResponse deleteFinancialYear(Integer finYearId);

    public List<FinancialYearDDResponse> ddAllFinancialYear();
}

