package com.futurebizops.kpi.service;

import com.futurebizops.kpi.response.EmpKppStatusResponse;

import java.util.List;

public interface EmployeeKppStatusService {
    public EmpKppStatusResponse getEmployeeKppStatus(Integer empId);
}
