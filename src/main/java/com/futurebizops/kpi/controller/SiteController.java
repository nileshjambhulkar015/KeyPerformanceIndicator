package com.futurebizops.kpi.controller;

import com.futurebizops.kpi.request.DepartmentCreateRequest;
import com.futurebizops.kpi.request.DepartmentUpdateRequest;
import com.futurebizops.kpi.request.SiteCreateRequest;
import com.futurebizops.kpi.request.SiteUpdateRequest;
import com.futurebizops.kpi.response.KPIResponse;
import com.futurebizops.kpi.service.DepartmentService;
import com.futurebizops.kpi.service.SiteService;
import io.swagger.v3.oas.annotations.Parameter;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping(value = "/site")
public class SiteController {

    @Autowired
    private SiteService siteService;

    @PostMapping
    public ResponseEntity<KPIResponse> saveSiteDetails(@RequestBody SiteCreateRequest siteCreateRequest) {
        KPIResponse response = siteService.saveSite(siteCreateRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<KPIResponse> updateSiteDetails(@RequestBody SiteUpdateRequest siteUpdateRequest) {
        KPIResponse response = siteService.updateSite(siteUpdateRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/search")
    @PageableAsQueryParam
    public ResponseEntity<KPIResponse> findDepartmentDetails(@RequestParam(required = false) Integer siteId,
                                                             @RequestParam(required = false) Integer regionId,
                                                             @RequestParam(required = false) String siteName,
                                                             @RequestParam(required = false) String statusCd,
                                                             @Parameter(hidden = true) Pageable pageable) {
        KPIResponse response = siteService.findSiteDetails(siteId, regionId, siteName, statusCd, pageable);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }


}
