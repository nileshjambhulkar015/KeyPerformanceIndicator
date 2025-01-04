package com.futurebizops.kpi.controller.masterrecords;


import com.futurebizops.kpi.request.AnnouncementTypeCreateRequest;
import com.futurebizops.kpi.request.AnnouncementTypeUpdateRequest;
import com.futurebizops.kpi.response.AnnouncementTypeResponse;
import com.futurebizops.kpi.response.DepartmentReponse;
import com.futurebizops.kpi.response.KPIResponse;
import com.futurebizops.kpi.service.AnnouncementTypeService;
import com.futurebizops.kpi.service.DepartmentService;
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
@RequestMapping(value = "/announcement-type")
@Slf4j
public class AnnouncementTypeController {

    @Autowired
    private AnnouncementTypeService announcementTypeService;

    @PostMapping
    public ResponseEntity<KPIResponse> saveAnnouncementTypeDetails(@RequestBody AnnouncementTypeCreateRequest announcementTypeCreateRequest) {
        log.info("Inside AnnouncementTypeController >> saveAnnouncementTypeDetails() request : {}", announcementTypeCreateRequest);
        KPIResponse response = announcementTypeService.saveAnnouncementTypeDetails(announcementTypeCreateRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<KPIResponse> deleteAnnouncementTypeDetails(@RequestParam(required = false) Integer announTypeId) {
        log.info("Inside AnnouncementTypeController >> deleteAnnouncementTypeDetails() announTypeId : {}", announTypeId);
        KPIResponse response = announcementTypeService.deleteAnnouncementTypeDetails(announTypeId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<KPIResponse> updateAnnouncementTypeDetails(@RequestBody AnnouncementTypeUpdateRequest announcementTypeUpdateRequest) {
        log.info("Inside AnnouncementTypeController >> updateAnnouncementTypeDetails() announcementTypeUpdateRequest : {}", announcementTypeUpdateRequest);
        KPIResponse response = announcementTypeService.updateAnnouncementTypeDetails(announcementTypeUpdateRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/search")
    @PageableAsQueryParam
    public ResponseEntity<KPIResponse> findAnnouncementTypeSearch(
            @RequestParam(required = false) Integer announTypeId,
            @RequestParam(required = false) String announTypeName,
            @RequestParam(required = false) String statusCd,
            @Parameter(hidden = true) Pageable pageable) {
        log.info("Inside AnnouncementTypeController >> findAnnouncementTypeSearch() announTypeId : {}, announTypeName : {}, statusCd :{}", announTypeId, announTypeName,statusCd);
        KPIResponse response = announcementTypeService.findAnnouncementTypeSearch(announTypeId, announTypeName, statusCd, pageable);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @GetMapping
    public ResponseEntity<List<AnnouncementTypeResponse>> getAllAnnouncementType() {
        log.info("Inside AnnouncementTypeController >> getAllAnnouncementType()");
        List<AnnouncementTypeResponse> response = announcementTypeService.getAllAnnouncementType();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/by-announcement-type-id")
    public ResponseEntity<AnnouncementTypeResponse> findAllDepartmentById(@RequestParam(required = false) Integer announTypeId) {
        log.info("Inside AnnouncementTypeController >> findAllDepartmentById() announTypeId : {}",announTypeId);
        return new ResponseEntity<>(announcementTypeService.findAnnouncementTypeById(announTypeId), HttpStatus.OK);
    }

}