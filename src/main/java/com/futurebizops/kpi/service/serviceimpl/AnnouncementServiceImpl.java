package com.futurebizops.kpi.service.serviceimpl;

import com.futurebizops.kpi.constants.KPIConstants;
import com.futurebizops.kpi.entity.AnnouncementAudit;
import com.futurebizops.kpi.entity.AnnouncementEntity;
import com.futurebizops.kpi.entity.AnnouncementTypeEntity;
import com.futurebizops.kpi.exception.KPIException;
import com.futurebizops.kpi.repository.AnnouncementAuditRepo;
import com.futurebizops.kpi.repository.AnnouncementRepo;
import com.futurebizops.kpi.repository.AnnouncementTypeRepo;
import com.futurebizops.kpi.request.AnnouncementCreateRequest;
import com.futurebizops.kpi.request.AnnouncementUpdateRequest;
import com.futurebizops.kpi.request.advsearch.AnnouncementAdvSearch;
import com.futurebizops.kpi.response.AnnouncementReponse;
import com.futurebizops.kpi.response.AnnouncementTypeResponse;
import com.futurebizops.kpi.response.KPIResponse;
import com.futurebizops.kpi.service.AnnouncementService;
import com.futurebizops.kpi.utils.DateTimeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AnnouncementServiceImpl implements AnnouncementService {

    @Autowired
    AnnouncementRepo announcementRepo;

    @Autowired
    AnnouncementAuditRepo announcementAuditRepo;

    @Autowired
    AnnouncementTypeRepo announcementTypeRepo;

    @Override
    public KPIResponse saveAnnouncement(AnnouncementCreateRequest announcementCreateRequest) {
        System.out.println(announcementCreateRequest);
        AnnouncementEntity announcementEntity = convertAnnouncementCreateRequestToEntity(announcementCreateRequest);
        try {
            announcementRepo.save(announcementEntity);
            AnnouncementAudit partAudit = new AnnouncementAudit(announcementEntity);
            announcementAuditRepo.save(partAudit);
            return KPIResponse.builder()
                    .isSuccess(true)
                    .responseMessage(KPIConstants.RECORD_SUCCESS)
                    .build();
        } catch (Exception ex) {
            log.error("Inside AnnouncementServiceImpl >> saveAnnouncement()");
            throw new KPIException("AnnouncementServiceImpl", false, ex.getMessage());
        }
    }

    @Transactional
    @Override
    public KPIResponse cancelAnnouncement(AnnouncementUpdateRequest announcementUpdateRequest) {
        try {
            announcementRepo.cancelAnnouncement(announcementUpdateRequest.getAnnounId(),announcementUpdateRequest.getAnnounStatus(), announcementUpdateRequest.getStatusCd());
            return KPIResponse.builder()
                    .isSuccess(true)
                    .responseMessage(KPIConstants.RECORD_UPDATE)
                    .build();
        } catch (Exception ex) {
            log.error("Inside AnnouncementServiceImpl >> cancelAnnouncement() :{}", ex);
            throw new KPIException("AnnouncementServiceImpl", false, ex.getMessage());
        }
    }

    @Override
    public KPIResponse findAllAnnouncements(String announFromDate, String announToDate,Integer announTypeId,String statusCd,Pageable requestPageable) {
        String sortName = null;
        //  String sortDirection = null;

        announFromDate = StringUtils.isNotEmpty(announFromDate) ? announFromDate : null;
        announTypeId = (null!=announTypeId)?announTypeId:null;

        announToDate = StringUtils.isNotEmpty(announToDate) ? announToDate : null;
        Integer pageSize = requestPageable.getPageSize();
        Integer pageOffset = (int) requestPageable.getOffset();
        // pageable = KPIUtils.sort(requestPageable, sortParam, pageDirection);
        Optional<Sort.Order> order = requestPageable.getSort().get().findFirst();
        if (order.isPresent()) {
            sortName = order.get().getProperty();  //order by this field
            //sortDirection = order.get().getDirection().toString(); // Sort ASC or DESC
        }

        Integer totalCount = announcementRepo.getAnnouncementCount(announFromDate,announToDate,announTypeId,statusCd);
        List<Object[]> departmentData = announcementRepo.getAnnouncementDetail(announFromDate,announToDate,announTypeId,statusCd,pageSize, pageOffset);

        List<AnnouncementReponse> announcementReponses = departmentData.stream().map(AnnouncementReponse::new).collect(Collectors.toList());


        return KPIResponse.builder()
                .isSuccess(true)
                .responseData(new PageImpl<>(announcementReponses, requestPageable, totalCount))
                .responseMessage(KPIConstants.RECORD_FETCH)
                .build();
    }

    @Override
    public KPIResponse advSearchAnnouncementDetails(AnnouncementAdvSearch announcementAdvSearch, Pageable requestPageable) {
        String statusCd=null;
        String announFromDate = StringUtils.isNotEmpty(announcementAdvSearch.getAsAnnounFromDate()) ? announcementAdvSearch.getAsAnnounFromDate() : null;

        System.out.println("announFromDate : "+announFromDate);

        String announToDate = StringUtils.isNotEmpty(announcementAdvSearch.getAsAnnounToDate()) ? announcementAdvSearch.getAsAnnounToDate() : null;

        System.out.println("announToDate : "+announToDate);
        String asAnnounStatus = StringUtils.isNotEmpty(announcementAdvSearch.getAsAnnounStatus()) ? announcementAdvSearch.getAsAnnounStatus() : null;

        Integer asAnnounTypeId = (null != announcementAdvSearch.getAsAnnounTypeId() ? announcementAdvSearch.getAsAnnounTypeId() : null);

        if(null != asAnnounStatus && asAnnounStatus.equalsIgnoreCase("Cancel")){
            statusCd = "I";
        } else {
            statusCd = "A";
        }

        String sortName = null;
        //  String sortDirection = null;
        Integer pageSize = requestPageable.getPageSize();
        Integer pageOffset = (int) requestPageable.getOffset();
        // pageable = KPIUtils.sort(requestPageable, sortParam, pageDirection);
        Optional<Sort.Order> order = requestPageable.getSort().get().findFirst();
        if (order.isPresent()) {
            sortName = order.get().getProperty();  //order by this field
            //sortDirection = order.get().getDirection().toString(); // Sort ASC or DESC
        }

        Integer totalCount = announcementRepo.getAdvanceSearcheAnnouncementCount(announFromDate,announToDate, asAnnounStatus,asAnnounTypeId,statusCd);
        List<Object[]> complaintData = announcementRepo.getAdvanceSearchAnnouncementDetail(announFromDate,announToDate, asAnnounStatus,asAnnounTypeId,statusCd,+ pageSize, pageOffset);

        List<AnnouncementReponse> announcementReponses = complaintData.stream().map(AnnouncementReponse::new).collect(Collectors.toList());
        announcementReponses = announcementReponses.stream()
                // .sorted(Comparator.comparing(EmployeeComplaintResponse::getCompId))
                .sorted((o1, o2)->o2.getAnnounStartDate().
                        compareTo(o1.getAnnounStartDate()))
                .collect(Collectors.toList());
        if(announcementReponses.size()>0) {
            return KPIResponse.builder()
                    .isSuccess(true)
                    .responseData(new PageImpl<>(announcementReponses, requestPageable, totalCount))
                    .responseMessage(KPIConstants.RECORD_FETCH)
                    .build();
        }
        return KPIResponse.builder()
                .isSuccess(false)
                .responseData(null)
                .responseMessage(KPIConstants.RECORD_NOT_FOUND)
                .build();
    }

    @Override
    public AnnouncementReponse findAnnouncementById(Integer announId, String statusCd) {
        List<Object[]> announcementData = announcementRepo.getAnnouncementByAnnounId(announId, statusCd);
        if (announcementData.size() > 0) {
            List<AnnouncementReponse> announcementReponses = announcementData.stream().map(AnnouncementReponse::new).collect(Collectors.toList());
            return announcementReponses.get(0);
        }
        return null;
    }

    @Override
    public KPIResponse findAllAnnouncement(Integer announId, String statusCd) {
        KPIResponse kpiResponse = new KPIResponse();
        List<Object[]> announcementData = announcementRepo.getAnnouncementByAnnounId(announId, statusCd);
        if (announcementData.size() > 0) {
            List<AnnouncementReponse> announcementReponses = announcementData.stream().map(AnnouncementReponse::new).collect(Collectors.toList());
            kpiResponse.setSuccess(true);
            kpiResponse.setResponseData(announcementReponses);
            return kpiResponse;

        }
        kpiResponse.setSuccess(false);
        return kpiResponse;
    }

    @Override
    public List<AnnouncementTypeResponse> ddAllAnnouncementType(String statusCd) {
        KPIResponse kpiResponse = new KPIResponse();
        List<Object[]> announcementData = announcementRepo.getDDAnnouncementByAnnounId(statusCd);
        if (announcementData.size() > 0) {
            List<AnnouncementTypeResponse> announcementReponses = announcementData.stream().map(AnnouncementTypeResponse::new).collect(Collectors.toList());

            return announcementReponses;

        }

        return null;
    }


    private String getAnnounceTypeName(Integer announTypeId){
        Optional<AnnouncementTypeEntity> optionalAnnouncementTypeEntity = announcementTypeRepo.findById(announTypeId);
        if(optionalAnnouncementTypeEntity.isPresent()){
            return optionalAnnouncementTypeEntity.get().getAnnounTypeName();
        }
        return null;
    }

    private AnnouncementEntity convertAnnouncementCreateRequestToEntity(AnnouncementCreateRequest announcementCreateRequest) {
        Instant annountartDateTime = StringUtils.isNotEmpty(announcementCreateRequest.getAnnounStartDate())? DateTimeUtils.convertResolveDateStringToInstant(announcementCreateRequest.getAnnounStartDate()):null;
        Instant announEndDateTime = StringUtils.isNotEmpty(announcementCreateRequest.getAnnounEndDate())? DateTimeUtils.convertResolveDateStringToInstant(announcementCreateRequest.getAnnounEndDate()):null;


        AnnouncementEntity announcementEntity = new AnnouncementEntity();
announcementEntity.setAnnounTypeId(announcementCreateRequest.getAnnounTypeId());
      announcementEntity.setAnnounTypeName(getAnnounceTypeName(announcementCreateRequest.getAnnounTypeId()));
        announcementEntity.setAnnounStartDate(annountartDateTime);
        announcementEntity.setAnnounEndDate(announEndDateTime);
        announcementEntity.setAnnounCreatedByEmpId(announcementCreateRequest.getAnnounCreatedByEmpId());
        announcementEntity.setAnnounCreatedByEmpEId(announcementCreateRequest.getAnnounCreatedByEmpEId());
        announcementEntity.setAnnounCreatedByEmpName(announcementCreateRequest.getAnnounCreatedByEmpName());
        announcementEntity.setAnnounCreatedByRoleId(announcementCreateRequest.getAnnounCreatedByRoleId());
        announcementEntity.setAnnounCreatedByRoleName(announcementCreateRequest.getAnnounCreatedByRoleName());
        announcementEntity.setAnnounCreatedByDeptId(announcementCreateRequest.getAnnounCreatedByDeptId());
        announcementEntity.setAnnounCreatedByDeptName(announcementCreateRequest.getAnnounCreatedByDeptName());
        announcementEntity.setAnnounCreatedByDesigId(announcementCreateRequest.getAnnounCreatedByDesigId());
        announcementEntity.setAnnounCreatedByDesigName(announcementCreateRequest.getAnnounCreatedByDesigName());
        announcementEntity.setAnnounVenue(announcementCreateRequest.getAnnounVenue());
        announcementEntity.setAnnounTitle(announcementCreateRequest.getAnnounTitle());
        announcementEntity.setAnnounDescription(announcementCreateRequest.getAnnounDescription());
        announcementEntity.setAnnounStatus(announcementCreateRequest.getAnnounStatus());
        announcementEntity.setRemark(announcementCreateRequest.getRemark());
        announcementEntity.setStatusCd(announcementCreateRequest.getStatusCd());
        announcementEntity.setCreatedUserId(announcementCreateRequest.getEmployeeId());
        return announcementEntity;
    }

}
