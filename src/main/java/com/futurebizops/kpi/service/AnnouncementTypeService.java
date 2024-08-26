package com.futurebizops.kpi.service;

import com.futurebizops.kpi.request.AnnouncementTypeCreateRequest;
import com.futurebizops.kpi.request.AnnouncementTypeUpdateRequest;
import com.futurebizops.kpi.request.DepartmentCreateRequest;
import com.futurebizops.kpi.request.DepartmentUpdateRequest;
import com.futurebizops.kpi.response.AnnouncementTypeResponse;
import com.futurebizops.kpi.response.DepartmentReponse;
import com.futurebizops.kpi.response.KPIResponse;
import com.futurebizops.kpi.response.dropdown.DepartmentDDResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface AnnouncementTypeService {

    public KPIResponse saveAnnouncementTypeDetails(AnnouncementTypeCreateRequest departmentCreateRequest);

    public KPIResponse updateAnnouncementTypeDetails(AnnouncementTypeUpdateRequest announcementTypeUpdateRequest);

    public KPIResponse findAnnouncementTypeSearch(Integer announTypeId, String announTypeName, String statusCd, Pageable pageable);

    public List<AnnouncementTypeResponse> getAllAnnouncementType();

    public AnnouncementTypeResponse findAnnouncementTypeById(Integer deptId);
}
