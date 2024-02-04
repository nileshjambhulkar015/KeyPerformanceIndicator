package com.futurebizops.kpi.service;

import com.futurebizops.kpi.response.KPIResponse;

public interface EmployeeLoginService {
    

    public KPIResponse employeeLogin(String userName, String userPassword);

    public KPIResponse updateLoginPassword(String userName, String userPassword);
}
