package com.futurebizops.kpi.service;

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
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface EmployeeMeetingService {
    public KPIResponse saveEmployeeMeeting(EmployeeMeetingCreateRequest employeeMeetingCreateRequest);

    public KPIResponse cancelEmployeeMeeting(EmployeeMeetingUpdateRequest employeeMeetingUpdateRequest);
    public KPIResponse findAllMeetings(String meetFromDate,String meetToDate,Pageable pageable);

    public KPIResponse advSearchMeetingDetails(MeetingAdvSearch meetingAdvSearch, Pageable pageable);

    public EmployeeMeetingReponse findMeetingById(Integer meetingId, String statusCd);

    public KPIResponse findAllMeeting(Integer meetingId, String statusCd);
}
