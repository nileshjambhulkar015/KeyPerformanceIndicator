package com.futurebizops.kpi.service;

import com.futurebizops.kpi.request.RegionCreateRequest;
import com.futurebizops.kpi.request.RegionUpdateRequest;
import com.futurebizops.kpi.response.KPIResponse;
import org.springframework.data.domain.Pageable;

public interface RegionService {

    public KPIResponse saveRegion(RegionCreateRequest regionCreateRequest);

    public KPIResponse updateRegion(RegionUpdateRequest regionUpdateRequest);

    public KPIResponse findRegionDetails(Integer regionId, String regionName, String statusCd, Pageable requestPageable);

    public KPIResponse findRegionDetails(Integer regionIde);
}
