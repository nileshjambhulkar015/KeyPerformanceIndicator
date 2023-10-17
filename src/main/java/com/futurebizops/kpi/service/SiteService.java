package com.futurebizops.kpi.service;

import com.futurebizops.kpi.request.SiteCreateRequest;
import com.futurebizops.kpi.request.SiteUpdateRequest;
import com.futurebizops.kpi.response.KPIResponse;
import org.springframework.data.domain.Pageable;

public interface SiteService {

    public KPIResponse saveSite(SiteCreateRequest siteCreateRequest);

    public KPIResponse updateSite(SiteUpdateRequest siteUpdateRequest);

    public KPIResponse findSiteDetails(Integer siteId, Integer regionId, String siteName, String statusCd, Pageable requestPageable);

}
