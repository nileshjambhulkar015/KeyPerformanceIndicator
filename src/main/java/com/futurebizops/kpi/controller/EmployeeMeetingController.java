package com.futurebizops.kpi.controller;


import com.futurebizops.kpi.request.DepartmentCreateRequest;
import com.futurebizops.kpi.request.DepartmentUpdateRequest;
import com.futurebizops.kpi.request.EmployeeMeetingCreateRequest;
import com.futurebizops.kpi.request.EmployeeMeetingUpdateRequest;
import com.futurebizops.kpi.request.advsearch.ComplaintAdvSearch;
import com.futurebizops.kpi.request.advsearch.MeetingAdvSearch;
import com.futurebizops.kpi.response.DepartmentReponse;
import com.futurebizops.kpi.response.EmployeeMeetingReponse;
import com.futurebizops.kpi.response.KPIResponse;
import com.futurebizops.kpi.response.dropdown.DepartmentDDResponse;
import com.futurebizops.kpi.service.DepartmentService;
import com.futurebizops.kpi.service.EmployeeMeetingService;
import io.swagger.v3.oas.annotations.Parameter;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/employee-meeting")
public class EmployeeMeetingController {

    @Autowired
    private EmployeeMeetingService employeeMeetingService;

    @PostMapping
    public ResponseEntity<KPIResponse> saveEmployeeMeeting(@RequestBody EmployeeMeetingCreateRequest employeeMeetingCreateRequest) {
        KPIResponse response = employeeMeetingService.saveEmployeeMeeting(employeeMeetingCreateRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //Cancle the meeting
    @PutMapping(value = "/cancel-meeting")
    public ResponseEntity<KPIResponse> deleteEmployeeMeeting(@RequestBody EmployeeMeetingUpdateRequest employeeMeetingUpdateRequest) {
        System.out.println(employeeMeetingUpdateRequest);
        KPIResponse response = employeeMeetingService.cancelEmployeeMeeting(employeeMeetingUpdateRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/search")
    @PageableAsQueryParam
    public ResponseEntity<KPIResponse> findAllMeetings(
            @RequestParam(required = false) String meetFromDate,
            @RequestParam(required = false) String meetToDate,
            @Parameter(hidden = true) Pageable pageable) {
        KPIResponse response = employeeMeetingService.findAllMeetings(meetFromDate,meetToDate, pageable);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @PostMapping(value = "/meeting-adv-search")
    @PageableAsQueryParam
    public ResponseEntity<KPIResponse> advSearchEmployeeComplaintDetails(@RequestBody MeetingAdvSearch meetingAdvSearch, @Parameter(hidden = true) Pageable pageable) {
        System.out.println("Advance Search Request : "+meetingAdvSearch);
        KPIResponse response = employeeMeetingService.advSearchMeetingDetails(meetingAdvSearch, pageable);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @GetMapping(value = "/by-meeting-id")
    public ResponseEntity<EmployeeMeetingReponse> findAllMeetingsById(
            @RequestParam(required = false) Integer meetingId,
            @RequestParam(required = false) String statusCd) {
        EmployeeMeetingReponse response = employeeMeetingService.findMeetingById(meetingId,statusCd);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }
    @GetMapping(value = "/all-meeting-id")
    public ResponseEntity<KPIResponse> findAllMeetings(
            @RequestParam(required = false) Integer meetingId,
            @RequestParam(required = false) String statusCd) {
        KPIResponse response = employeeMeetingService.findAllMeeting(meetingId,statusCd);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

}