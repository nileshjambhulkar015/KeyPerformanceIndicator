package com.futurebizops.kpi.service;

import com.futurebizops.kpi.request.EmployeeTypeCreateRequest;
import com.futurebizops.kpi.request.EmployeeTypeUpdateRequest;
import com.futurebizops.kpi.response.KPIResponse;
import com.futurebizops.kpi.response.dropdown.EmployeeTypeDDResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EmployeeTypeService {

    public KPIResponse saveEmployeeType(EmployeeTypeCreateRequest employeeTypeCreateRequest);

    public KPIResponse updateEmployeeType(EmployeeTypeUpdateRequest employeeTypeUpdateRequest);

    public KPIResponse findEmployeeTypeDetails(Integer empTypeId, String empTypeName, String statusCd);
}

