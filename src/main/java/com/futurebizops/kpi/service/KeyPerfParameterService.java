package com.futurebizops.kpi.service;

import com.futurebizops.kpi.enums.KeyPerfParamSearchEnum;
import com.futurebizops.kpi.enums.StatusCdEnum;
import com.futurebizops.kpi.request.KeyPerfParamCreateRequest;
import com.futurebizops.kpi.request.KeyPerfParamUpdateRequest;
import com.futurebizops.kpi.response.KPIResponse;
import com.futurebizops.kpi.response.KPPResponse;
import org.springframework.data.domain.Pageable;

public interface KeyPerfParameterService {

    public KPIResponse saveKeyPerfomanceParameter(KeyPerfParamCreateRequest keyPerfParamCreateRequest);

    public KPIResponse updateKeyPerfomanceParameter(KeyPerfParamUpdateRequest keyPerfParamUpdateRequest);

    public KPIResponse findKeyPerfomanceParameterDetails(Integer kppId, Integer deptId,Integer desigId,String kppObjective,String statusCd,Pageable pageable);
    public KPPResponse findKeyPerfomanceParameterDetailById(Integer kppId);

    public KPIResponse getKeyPerfomanceParameter(Integer deptId, Integer desigId, String statusCd);
}
