package com.futurebizops.kpi.service;

import com.futurebizops.kpi.enums.RoleSearchEnum;
import com.futurebizops.kpi.enums.StatusCdEnum;
import com.futurebizops.kpi.request.RoleCreateRequest;
import com.futurebizops.kpi.request.RoleUpdateRequest;
import com.futurebizops.kpi.request.UoMCreateRequest;
import com.futurebizops.kpi.request.UoMUpdateRequest;
import com.futurebizops.kpi.response.KPIResponse;
import com.futurebizops.kpi.response.RoleResponse;
import org.springframework.data.domain.Pageable;

public interface UoMService {

    public KPIResponse saveUoM(UoMCreateRequest uoMCreateRequest);

    public KPIResponse updateUoM(UoMUpdateRequest uoMUpdateRequest);

    public KPIResponse findUoMDetails(Integer uomId,  String uomName, String statusCd, Pageable pageable);

    public KPIResponse findUoMDetails(Integer uomId);
    public RoleResponse findUoMById(Integer roleId);

}
