package com.futurebizops.kpi.service;

import com.futurebizops.kpi.request.SiteCreateRequest;
import com.futurebizops.kpi.request.SiteUpdateRequest;
import com.futurebizops.kpi.response.KPIResponse;
import com.futurebizops.kpi.response.SiteResponse;
import com.futurebizops.kpi.response.dropdown.RegionDDResponse;
import com.futurebizops.kpi.response.dropdown.SiteDDResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SiteService {

    public KPIResponse saveSite(SiteCreateRequest siteCreateRequest);

    public KPIResponse updateSite(SiteUpdateRequest siteUpdateRequest);

    public KPIResponse findSiteDetails(Integer siteId, Integer regionId, String siteName, String statusCd, Pageable requestPageable);

    public List<SiteDDResponse> ddSearchSites(Integer regionId, Integer siteId);

    public SiteResponse getSitesById(Integer siteId);

    public List<RegionDDResponse> getDDRegionFromSite();

    public List<SiteDDResponse> getDDAllSite();
}
