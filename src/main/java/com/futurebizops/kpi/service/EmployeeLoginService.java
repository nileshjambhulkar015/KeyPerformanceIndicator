package com.futurebizops.kpi.service;

import com.futurebizops.kpi.response.KPIResponse;

public interface EmployeeLoginService {
    

    public KPIResponse employeeLogin(Integer roleId, String userName, String userPassword);

    public KPIResponse updateLoginPassword(String userName, String userPassword);
}
