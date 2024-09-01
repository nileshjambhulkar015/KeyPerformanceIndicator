package com.futurebizops.kpi.service;

import com.futurebizops.kpi.request.AnnouncementCreateRequest;
import com.futurebizops.kpi.request.AnnouncementUpdateRequest;
import com.futurebizops.kpi.request.EmployeeMeetingCreateRequest;
import com.futurebizops.kpi.request.EmployeeMeetingUpdateRequest;
import com.futurebizops.kpi.request.advsearch.AnnouncementAdvSearch;
import com.futurebizops.kpi.request.advsearch.MeetingAdvSearch;
import com.futurebizops.kpi.response.AnnouncementReponse;
import com.futurebizops.kpi.response.AnnouncementTypeResponse;
import com.futurebizops.kpi.response.EmployeeMeetingReponse;
import com.futurebizops.kpi.response.KPIResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AnnouncementService {
    public KPIResponse saveAnnouncement(AnnouncementCreateRequest announcementCreateRequest);

    public KPIResponse cancelAnnouncement(AnnouncementUpdateRequest announcementUpdateRequest);

    public KPIResponse findAllAnnouncements(String announFromDate,String announToDate,Integer announTypeId,String statusCd,Pageable pageable);

    public KPIResponse advSearchAnnouncementDetails(AnnouncementAdvSearch announcementAdvSearch, Pageable pageable);

    public AnnouncementReponse findAnnouncementById(Integer announId, String statusCd);

    public KPIResponse findAllAnnouncement(Integer announId, Integer announTypeId,String statusCd);

    public List<AnnouncementTypeResponse> ddAllAnnouncementType(String statusCd);
}
