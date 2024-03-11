package com.futurebizops.kpi.controller;

import com.futurebizops.kpi.request.CompanyMasterCreateRequest;
import com.futurebizops.kpi.request.CompanyMasterUpdateRequest;
import com.futurebizops.kpi.response.CompanyMasterResponse;
import com.futurebizops.kpi.response.KPIResponse;
import com.futurebizops.kpi.response.dropdown.CompanyDDResponse;
import com.futurebizops.kpi.response.dropdown.RegionDDResponse;
import com.futurebizops.kpi.response.dropdown.SiteDDResponse;
import com.futurebizops.kpi.service.CompanyMasterService;
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

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/company-master")
public class CompanyController {

    @Autowired
    CompanyMasterService companyMasterService;

    @PostMapping
    public ResponseEntity<KPIResponse> saveCompanyDetails(@RequestBody CompanyMasterCreateRequest masterCreateRequest) {
        KPIResponse response = companyMasterService.saveCompanyDetails(masterCreateRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<KPIResponse> updateComapnyDetails(@RequestBody CompanyMasterUpdateRequest companyMasterUpdateRequest) {
        KPIResponse response = companyMasterService.updateCompanyDetails(companyMasterUpdateRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/search")
    @PageableAsQueryParam
    public ResponseEntity<KPIResponse> findCompanyDetails(@RequestParam(required = false) Integer regionId,
                                                          @RequestParam(required = false) Integer siteId,
                                                          @RequestParam(required = false) String companyName,
                                                          @RequestParam(required = false) String statusCd,
                                                          @Parameter(hidden = true) Pageable pageable) {
        KPIResponse response = companyMasterService.findCompanyDetails(regionId, siteId, companyName, statusCd, pageable);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @GetMapping(value = "/by-comp-id")
    public ResponseEntity<CompanyMasterResponse> findCompanyDetails(@RequestParam(required = false) Integer companyId) {
        CompanyMasterResponse response = companyMasterService.findCompanyById(companyId);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    // for dropdown list


    @GetMapping(value = "/dd-region-site")
    public ResponseEntity<Object> findAllCompanyDetails(@RequestParam(required = false) Integer regionId, @RequestParam(required = false) Integer siteId) {
        return new ResponseEntity<>(companyMasterService.findAllCompanyByRegionaIdAndSiteId(regionId, siteId), HttpStatus.OK);
    }

    //for region save
    @GetMapping(value = "/dd-regions-company")
    public ResponseEntity<List<RegionDDResponse>> getDDRegionFromCompany() {
        List<RegionDDResponse> response = companyMasterService.getDDRegionFromCompany();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/dd-sites-company")
    public ResponseEntity<List<SiteDDResponse>> getDDSitesFromComany(@RequestParam(required = false) Integer regionId) {
        List<SiteDDResponse> response = companyMasterService.getDDSitesFromComany(regionId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/dd-company-company")
    public ResponseEntity<List<CompanyDDResponse>> getDDCompanyFromComany(@RequestParam(required = false) Integer regionId,
                                                                          @RequestParam(required = false) Integer siteId) {
        List<CompanyDDResponse> response = companyMasterService.getDDCompanyFromComany(regionId, siteId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}

