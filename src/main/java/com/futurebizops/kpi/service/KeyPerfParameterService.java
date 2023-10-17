package com.futurebizops.kpi.service;

import com.futurebizops.kpi.enums.KeyPerfParamSearchEnum;
import com.futurebizops.kpi.enums.StatusCdEnum;
import com.futurebizops.kpi.request.KeyPerfParamCreateRequest;
import com.futurebizops.kpi.request.KeyPerfParamUpdateRequest;
import com.futurebizops.kpi.response.KPIResponse;
import org.springframework.data.domain.Pageable;

public interface KeyPerfParameterService {

    public KPIResponse saveKeyPerfomanceParameter(KeyPerfParamCreateRequest keyPerfParamCreateRequest);

    public KPIResponse updateKeyPerfomanceParameter(KeyPerfParamUpdateRequest keyPerfParamUpdateRequest);

    public KPIResponse findKeyPerfomanceParameterDetails(KeyPerfParamSearchEnum searchEnum, String searchString, StatusCdEnum statusCdEnum, Pageable pageable, String sortParam, String pageDirection);

    public KPIResponse getKeyPerfomanceParameter(Integer deptId, Integer desigId, String statusCd);
}
