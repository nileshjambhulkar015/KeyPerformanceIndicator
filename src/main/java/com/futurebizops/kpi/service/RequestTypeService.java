package com.futurebizops.kpi.service;

import com.futurebizops.kpi.request.RequestTypeCreateRequest;
import com.futurebizops.kpi.request.RequestTypeUpdateRequest;
import com.futurebizops.kpi.response.KPIResponse;
import com.futurebizops.kpi.response.RequestTypeReponse;
import com.futurebizops.kpi.response.dropdown.DepartmentDDResponse;
import com.futurebizops.kpi.response.dropdown.RequestTypeDDResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RequestTypeService {

    public KPIResponse saveRequestType(RequestTypeCreateRequest requestTypeCreateRequest);

    public KPIResponse findRequestTypeDetails(Integer reqTypeId, String reqTypeName, String statusCd, Pageable pageable);

    public RequestTypeReponse findAllRequestTypeById(Integer reqTypeId);

    public KPIResponse updateRequestType(RequestTypeUpdateRequest requestTypeUpdateRequest);

    public List<DepartmentDDResponse> findAllDepartmentFromRequestType();

    public List<RequestTypeDDResponse> findAllRequestTypeByDeptId(Integer deptId);
}
