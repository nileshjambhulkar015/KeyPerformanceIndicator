package com.futurebizops.kpi.controller;


import com.futurebizops.kpi.request.AnnouncementCreateRequest;
import com.futurebizops.kpi.request.AnnouncementUpdateRequest;
import com.futurebizops.kpi.request.advsearch.AnnouncementAdvSearch;
import com.futurebizops.kpi.response.AnnouncementReponse;
import com.futurebizops.kpi.response.AnnouncementTypeResponse;
import com.futurebizops.kpi.response.KPIResponse;
import com.futurebizops.kpi.service.AnnouncementService;
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
@RequestMapping(value = "/announcement")
public class AnnouncementController {

    @Autowired
    private AnnouncementService announcementService;

    @PostMapping
    public ResponseEntity<KPIResponse> saveAnnouncement(@RequestBody AnnouncementCreateRequest announcementCreateRequest) {
        KPIResponse response = announcementService.saveAnnouncement(announcementCreateRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @PutMapping(value = "/cancel-announ")
    public ResponseEntity<KPIResponse> cancelAnnouncement(@RequestBody AnnouncementUpdateRequest announcementUpdateRequest) {
        System.out.println(announcementUpdateRequest);
        KPIResponse response = announcementService.cancelAnnouncement(announcementUpdateRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/search")
    @PageableAsQueryParam
    public ResponseEntity<KPIResponse> findAllAnnouncements(
            @RequestParam(required = false) String announFromDate,
            @RequestParam(required = false) String announToDate,
            @RequestParam(required = false) Integer announTypeId,
            @RequestParam(required = false) String statusCd,
            @Parameter(hidden = true) Pageable pageable) {
        KPIResponse response = announcementService.findAllAnnouncements(announFromDate,announToDate,announTypeId,statusCd, pageable);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @PostMapping(value = "/announ-adv-search")
    @PageableAsQueryParam
    public ResponseEntity<KPIResponse> advSearchEmployeeComplaintDetails(@RequestBody AnnouncementAdvSearch announcementAdvSearch, @Parameter(hidden = true) Pageable pageable) {
        System.out.println("Advance Search Request : "+announcementAdvSearch);
        KPIResponse response = announcementService.advSearchAnnouncementDetails(announcementAdvSearch, pageable);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @GetMapping(value = "/by-announ-id")
    public ResponseEntity<AnnouncementReponse> findAnnouncementById(
            @RequestParam(required = false) Integer announId,
            @RequestParam(required = false) String statusCd) {
        AnnouncementReponse response = announcementService.findAnnouncementById(announId,statusCd);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }
    @GetMapping(value = "/all-announ-id")
    public ResponseEntity<KPIResponse> findAllAnnouncement(
            @RequestParam(required = false) Integer announId,
            @RequestParam(required = false) Integer announTypeId,
            @RequestParam(required = false) String statusCd) {
        KPIResponse response = announcementService.findAllAnnouncement(announId,announTypeId,statusCd);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @GetMapping(value = "/dd-announ-all")
    public ResponseEntity<List<AnnouncementTypeResponse>> ddAllAnouncementType(
            @RequestParam(required = false) String statusCd) {
        List<AnnouncementTypeResponse> response = announcementService.ddAllAnnouncementType(statusCd);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

}