package com.futurebizops.kpi.service;

import com.futurebizops.kpi.response.KPIResponse;
import org.springframework.data.domain.Pageable;

public interface CumulativeService {

    public KPIResponse allEmployeeKppDetails(String fromDate, String toDate, Integer reportingEmployee, Integer gmEmpId, Integer empId, String empEId, Integer roleId, Integer deptId, Integer desigId, String empFirstName, String empMiddleName, String empLastName, String empMobileNo, String emailId, String statusCd, String empKppStatus, String hodKppStatus, String gmKppStatus, Pageable pageable);
}
