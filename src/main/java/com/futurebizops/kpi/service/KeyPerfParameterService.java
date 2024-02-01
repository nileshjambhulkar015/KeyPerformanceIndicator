package com.futurebizops.kpi.service;

import com.futurebizops.kpi.enums.KeyPerfParamSearchEnum;
import com.futurebizops.kpi.enums.StatusCdEnum;
import com.futurebizops.kpi.request.KeyPerfParamCreateRequest;
import com.futurebizops.kpi.request.KeyPerfParamUpdateRequest;
import com.futurebizops.kpi.response.KPIResponse;
import com.futurebizops.kpi.response.KPPResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface KeyPerfParameterService {

    public KPIResponse saveKeyPerfomanceParameter(KeyPerfParamCreateRequest keyPerfParamCreateRequest);

    public KPIResponse updateKeyPerfomanceParameter(KeyPerfParamUpdateRequest keyPerfParamUpdateRequest);

    public KPIResponse findKeyPerfomanceParameterDetails(Integer kppId, Integer roleId, Integer deptId,Integer desigId,String kppObjective,String statusCd,Pageable pageable);

    //show kpp when assign kpp for employee
    public KPIResponse assignEmployeeKppSearch(Integer kppId, Integer roleId, Integer deptId,Integer desigId,String kppObjective,String statusCd,Pageable pageable);
    public KPPResponse findKeyPerfomanceParameterDetailById(Integer kppId);

    public void uploadKppExcelFile(MultipartFile file) throws IOException;
}
