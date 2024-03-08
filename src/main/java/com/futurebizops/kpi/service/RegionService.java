package com.futurebizops.kpi.service;

import com.futurebizops.kpi.request.RegionCreateRequest;
import com.futurebizops.kpi.request.RegionUpdateRequest;
import com.futurebizops.kpi.response.KPIResponse;
import com.futurebizops.kpi.response.RegionResponse;
import com.futurebizops.kpi.response.dropdown.RegionDDResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RegionService {

    public KPIResponse saveRegion(RegionCreateRequest regionCreateRequest);

    public KPIResponse updateRegion(RegionUpdateRequest regionUpdateRequest);

    public KPIResponse findRegionDetails(Integer regionId, String regionName, String statusCd, Pageable requestPageable);

    public KPIResponse findRegionDetails(Integer regionIde);

    public List<RegionDDResponse> ddRegionDetails(Integer regionId);
}
