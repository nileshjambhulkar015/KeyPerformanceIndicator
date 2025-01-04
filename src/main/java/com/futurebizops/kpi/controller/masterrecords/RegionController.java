package com.futurebizops.kpi.controller.masterrecords;

import com.futurebizops.kpi.request.RegionCreateRequest;
import com.futurebizops.kpi.request.RegionUpdateRequest;
import com.futurebizops.kpi.response.KPIResponse;
import com.futurebizops.kpi.response.RegionResponse;
import com.futurebizops.kpi.response.dropdown.RegionDDResponse;
import com.futurebizops.kpi.service.RegionService;
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
@RequestMapping(value = "/region")
@Slf4j
public class RegionController {

    @Autowired
    private RegionService regionService;

    @PostMapping
    public ResponseEntity<KPIResponse> saveRegion(@RequestBody RegionCreateRequest regionCreateRequest) {
        log.info("Inside RegionController >> saveRegion() regionCreateRequest : {}", regionCreateRequest);
        KPIResponse response = regionService.saveRegion(regionCreateRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<KPIResponse> deleteRegionDetails(@RequestParam(required = false) Integer regionId) {
        log.info("Inside RegionController >> deleteRegionDetails() regionId : {}", regionId);
        KPIResponse response = regionService.deleteRegionDetails(regionId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PutMapping
    public ResponseEntity<KPIResponse> updateRegion(@RequestBody RegionUpdateRequest regionUpdateRequest) {
        log.info("Inside RegionController >> updateRegion() regionUpdateRequest : {}", regionUpdateRequest);
        KPIResponse response = regionService.updateRegion(regionUpdateRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/search")
    @PageableAsQueryParam
    public ResponseEntity<KPIResponse> findRegionDetails(@RequestParam(required = false) Integer regionId,
                                                              @RequestParam(required = false) String regionName,
                                                              @RequestParam(required = false) String statusCd,
                                                              @Parameter(hidden = true) Pageable pageable) {
        log.info("Inside RegionController >> findRegionDetails() regionId : {}, regionName : {}", regionId, regionName);
        KPIResponse response = regionService.findRegionDetails(regionId, regionName, statusCd, pageable);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @GetMapping
    public ResponseEntity<KPIResponse> findRegionDetails(Integer regionId) {
        log.info("Inside RegionController >> findRegionDetails() regionId : {}", regionId);
        KPIResponse response = regionService.findRegionDetails(regionId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/dd-regions-regions")
    public ResponseEntity<List<RegionDDResponse>> ddRegionDetails(Integer regionId) {
        log.info("Inside RegionController >> ddRegionDetails() regionId : {}", regionId);
        List<RegionDDResponse>   response = regionService.ddRegionDetails(regionId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
