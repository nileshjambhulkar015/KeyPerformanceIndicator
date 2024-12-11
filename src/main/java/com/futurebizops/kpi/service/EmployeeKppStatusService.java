package com.futurebizops.kpi.service;

import com.futurebizops.kpi.response.EmpKppStatusResponse;

public interface EmployeeKppStatusService {
    public EmpKppStatusResponse getInPrgressEmployeeKppStatus(Integer empId);

    public EmpKppStatusResponse getEmployeeKppDataYearly(Integer empId, String finYear);

    public EmpKppStatusResponse getCompletedEmployeeKppStatus(Integer empId, String ekppMonth);
}
