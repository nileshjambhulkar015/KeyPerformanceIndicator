package com.futurebizops.kpi.service;

import com.futurebizops.kpi.response.EmpKppStatusResponse;

import java.util.List;

public interface EmployeeKppStatusService {
    public List<EmpKppStatusResponse> getEmployeeKppStatus(Integer empId);
}
