package com.futurebizops.kpi.service;

import com.futurebizops.kpi.response.EmpKppStatusResponse;
import com.futurebizops.kpi.response.KPIResponse;

public interface EmployeeKppStatusService {
    public EmpKppStatusResponse getInPrgressEmployeeKppStatus(Integer empId);



    public EmpKppStatusResponse getCompletedEmployeeKppStatus(Integer empId, String ekppMonth);
}
