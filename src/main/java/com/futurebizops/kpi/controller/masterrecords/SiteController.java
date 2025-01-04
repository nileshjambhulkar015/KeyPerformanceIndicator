package com.futurebizops.kpi.controller.masterrecords;

import com.futurebizops.kpi.request.DepartmentCreateRequest;
import com.futurebizops.kpi.request.DepartmentUpdateRequest;
import com.futurebizops.kpi.request.SiteCreateRequest;
import com.futurebizops.kpi.request.SiteUpdateRequest;
import com.futurebizops.kpi.response.KPIResponse;
import com.futurebizops.kpi.response.RegionResponse;
import com.futurebizops.kpi.response.SiteResponse;
import com.futurebizops.kpi.response.dropdown.RegionDDResponse;
import com.futurebizops.kpi.response.dropdown.SiteDDResponse;
import com.futurebizops.kpi.service.DepartmentService;
import com.futurebizops.kpi.service.SiteService;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/site")
@Slf4j
public class SiteController {

    @Autowired
    private SiteService siteService;

    @PostMapping
    public ResponseEntity<KPIResponse> saveSite(@RequestBody SiteCreateRequest siteCreateRequest) {
        log.info("Inside SiteController >> saveSite() siteCreateRequest : {}", siteCreateRequest);
        KPIResponse response = siteService.saveSite(siteCreateRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<KPIResponse> deleteSiteDetails(@RequestParam(required = false) Integer siteId) {
        log.info("Inside SiteController >> deleteSiteDetails() siteId : {}", siteId);
        KPIResponse response = siteService.deleteSiteDetails(siteId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<KPIResponse> updateSite(@RequestBody SiteUpdateRequest siteUpdateRequest) {
        log.info("Inside SiteController >> updateSite() siteUpdateRequest : {}", siteUpdateRequest);
        KPIResponse response = siteService.updateSite(siteUpdateRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/search")
    @PageableAsQueryParam
    public ResponseEntity<KPIResponse> findSiteDetails(@RequestParam(required = false) Integer siteId,
                                                             @RequestParam(required = false) Integer regionId,
                                                             @RequestParam(required = false) String siteName,
                                                             @RequestParam(required = false) String statusCd,
                                                             @Parameter(hidden = true) Pageable pageable) {
        log.info("Inside SiteController >> findSiteDetails() siteId : {}, regionId : {}", siteId, regionId);
        KPIResponse response = siteService.findSiteDetails(siteId, regionId, siteName, statusCd, pageable);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }



    @GetMapping(value = "/by-site-id")
    public ResponseEntity<SiteResponse> getSitesById(Integer siteId) {
        log.info("Inside SiteController >> getSitesById() siteId : {}", siteId);
        SiteResponse   response = siteService.getSitesById(siteId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    //for region save
    @GetMapping(value = "/dd-sites-sites")
    public ResponseEntity<List<SiteDDResponse>> ddSearchSites(@RequestParam(required = false) Integer regionId,
                                                                @RequestParam(required = false) Integer siteId) {
        log.info("Inside SiteController >> ddSearchSites() siteId : {}, regionId : {}", siteId, regionId);
        List<SiteDDResponse>   response = siteService.ddSearchSites(regionId, siteId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //for region save
    @GetMapping(value = "/dd-regions-sites")
    public ResponseEntity<List<RegionDDResponse>> getDDRegionFromSite(@RequestParam(required = false) Integer regionId,
                                                                  @RequestParam(required = false) Integer siteId
    ) {
        log.info("Inside SiteController >> getDDRegionFromSite() siteId : {}, regionId : {}", siteId, regionId);
        List<RegionDDResponse>   response = siteService.getDDRegionFromSite();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //for all sites save
    @GetMapping(value = "/dd-all-sites")
    public ResponseEntity<List<SiteDDResponse>> getDDAllSite() {
        log.info("Inside SiteController >> getDDAllSite()");
        List<SiteDDResponse>   response = siteService.getDDAllSite();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
