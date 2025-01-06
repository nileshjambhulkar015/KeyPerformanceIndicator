package com.futurebizops.kpi.service.serviceimpl;

import com.futurebizops.kpi.constants.KPIConstants;
import com.futurebizops.kpi.entity.AnnouncementTypeAudit;
import com.futurebizops.kpi.entity.AnnouncementTypeEntity;
import com.futurebizops.kpi.exception.KPIException;
import com.futurebizops.kpi.repository.AnnouncementTypeAuditRepo;
import com.futurebizops.kpi.repository.AnnouncementTypeRepo;
import com.futurebizops.kpi.repository.RoleRepo;
import com.futurebizops.kpi.request.AnnouncementTypeCreateRequest;
import com.futurebizops.kpi.request.AnnouncementTypeUpdateRequest;
import com.futurebizops.kpi.response.AnnouncementTypeResponse;
import com.futurebizops.kpi.response.KPIResponse;
import com.futurebizops.kpi.service.AnnouncementTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AnnouncementTypeServiceImpl implements AnnouncementTypeService {

    @Autowired
    private AnnouncementTypeRepo announcementTypeRepo;

    @Autowired
    private AnnouncementTypeAuditRepo announcementTypeAuditRepo;

    @Override
    @Retryable(include = {KPIException.class}, maxAttemptsExpression = "${retry-max-attempts}")
    public KPIResponse saveAnnouncementTypeDetails(AnnouncementTypeCreateRequest announcementTypeCreateRequest) {
        log.debug("Inside AnnouncementTypeServiceImpl >> saveAnnouncementTypeDetails() announcementTypeCreateRequest : {}", announcementTypeCreateRequest);
        Optional<AnnouncementTypeEntity> announcementTypeEntity = announcementTypeRepo.findByAnnounTypeNameEqualsIgnoreCase(announcementTypeCreateRequest.getAnnounTypeName());
        if (announcementTypeEntity.isPresent()) {
            return KPIResponse.builder()
                    .isSuccess(false)
                    .responseMessage("Announcement Type name already exist")
                    .build();
        }

        AnnouncementTypeEntity announcementType = convertAnnouncementTypeCreateRequestToEntity(announcementTypeCreateRequest);
        try {
            announcementTypeRepo.save(announcementType);
            AnnouncementTypeAudit announcementTypeAudit = new AnnouncementTypeAudit(announcementType);
            announcementTypeAuditRepo.save(announcementTypeAudit);
            log.info("Announcement Type save successfully");
            return KPIResponse.builder()
                    .isSuccess(true)
                    .responseMessage(KPIConstants.RECORD_SUCCESS)
                    .build();
        } catch (Exception ex) {
            log.error("Inside AnnouncementTypeServiceImpl >> saveAnnouncementTypeDetails() : {}", ex);
            throw new KPIException("AnnouncementTypeServiceImpl", false, ex.getMessage());
        }
    }

    @Transactional
    @Override
    @Retryable(include = {KPIException.class}, maxAttemptsExpression = "${retry-max-attempts}")
    public KPIResponse deleteAnnouncementTypeDetails(Integer announTypeId) {
        log.debug("Inside deleteAnnouncementTypeDetails() announTypeId : {}", announTypeId);
        KPIResponse kpiResponse = new KPIResponse();
        try {
            announcementTypeRepo.deleteAnnouncementTypeDetails(announTypeId);
            kpiResponse.setSuccess(true);
            kpiResponse.setResponseMessage("Announcement Type details deleted Successfully");
            log.info("Announcement Type details deleted Successfully");
            return kpiResponse;
        } catch (Exception ex) {
            log.error("Inside AnnouncementTypeServiceImpl >> deleteAnnouncementTypeDetails() : {}", ex);
            return KPIResponse.builder()
                    .isSuccess(false)
                    .build();
        }
    }

    @Override
    @Retryable(include = {KPIException.class}, maxAttemptsExpression = "${retry-max-attempts}")
    public KPIResponse updateAnnouncementTypeDetails(AnnouncementTypeUpdateRequest announcementTypeUpdateRequest) {
        log.debug("Inside AnnouncementTypeServiceImpl >> updateAnnouncementTypeDetails() : {}", announcementTypeUpdateRequest);
        Optional<AnnouncementTypeEntity> optionalAnnouncementTypeEntity = announcementTypeRepo.findById(announcementTypeUpdateRequest.getAnnounTypeId());
        try {
            if (optionalAnnouncementTypeEntity.isPresent()) {
                AnnouncementTypeEntity announcementTypeEntity = optionalAnnouncementTypeEntity.get();
                announcementTypeEntity.setAnnounTypeName(announcementTypeUpdateRequest.getAnnounTypeName());
                announcementTypeEntity.setRemark(announcementTypeUpdateRequest.getRemark());
                announcementTypeRepo.save(announcementTypeEntity);
                AnnouncementTypeAudit announcementTypeAudit = new AnnouncementTypeAudit(announcementTypeEntity);
                announcementTypeAuditRepo.save(announcementTypeAudit);

                log.info("Announcement Type updated successfully");
                return KPIResponse.builder()
                        .isSuccess(true)
                        .responseMessage(KPIConstants.RECORD_UPDATE)
                        .build();
            } else {
                log.info("Announcement type id: {} is not present", announcementTypeUpdateRequest.getAnnounTypeId());
            }
        } catch (Exception ex) {
            log.error("Inside AnnouncementTypeServiceImpl >> updateAnnouncementTypeDetails() : {}", ex);
            throw new KPIException("AnnouncementTypeServiceImpl >> updateAnnouncementTypeDetails()", false, ex.getMessage());
        }
        return KPIResponse.builder()
                .isSuccess(true)
                .responseMessage("Announcement type id : " + announcementTypeUpdateRequest.getAnnounTypeId() + " is not present")
                .build();
    }

    @Override
    @Retryable(include = {KPIException.class}, maxAttemptsExpression = "${retry-max-attempts}")
    public KPIResponse findAnnouncementTypeSearch(Integer announTypeId, String announTypeName, String statusCd, Pageable requestPageable) {
        log.debug("Inside AnnouncementTypeServiceImpl >> findAnnouncementTypeSearch() : announTypeId : {}, announTypeName : {}", announTypeId, announTypeName);

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
        try {
            Integer totalCount = announcementTypeRepo.getAnnouncementTypeCount(announTypeId, announTypeName, statusCd);
            List<Object[]> announcementTypeData = announcementTypeRepo.getAnnouncementTypeDetail(announTypeId, announTypeName, statusCd, sortName, pageSize, pageOffset);

            List<AnnouncementTypeResponse> announcementTypeResponses = announcementTypeData.stream().map(AnnouncementTypeResponse::new).collect(Collectors.toList());
            log.info("Total Annoncement type size : {}", announcementTypeResponses.size());
            announcementTypeResponses = announcementTypeResponses.stream()
                    .sorted(Comparator.comparing(AnnouncementTypeResponse::getAnnounTypeName))
                    .collect(Collectors.toList());

            log.info("Inside findAnnouncementTypeSearch() record fetch successfully");
            return KPIResponse.builder()
                    .isSuccess(true)
                    .responseData(new PageImpl<>(announcementTypeResponses, requestPageable, totalCount))
                    .responseMessage(KPIConstants.RECORD_FETCH)
                    .build();
        } catch (Exception ex) {
            log.error("Inside AnnouncementTypeServiceImpl >> findAnnouncementTypeSearch() : {}", ex);
            throw new KPIException("AnnouncementTypeServiceImpl >> findAnnouncementTypeSearch()", false, ex.getMessage());
        }
    }

    @Override
    @Retryable(include = {KPIException.class}, maxAttemptsExpression = "${retry-max-attempts}")
    public List<AnnouncementTypeResponse> getAllAnnouncementType() {
        log.debug("Inside AnnouncementTypeServiceImpl >> getAllAnnouncementType()");

        List<AnnouncementTypeEntity> announcementTypeEntities = announcementTypeRepo.findAll();
        log.info("Inside AnnouncementTypeServiceImpl >> getAllAnnouncementType() announcementTypeEntities size:{}", announcementTypeEntities.size());
        List<AnnouncementTypeResponse> announcementTypeResponses = new ArrayList<>();
        AnnouncementTypeResponse announcementTypeResponse = null;
        for (AnnouncementTypeEntity announcementTypeEntity : announcementTypeEntities) {
            announcementTypeResponse = new AnnouncementTypeResponse();
            if ("A".equalsIgnoreCase(announcementTypeEntity.getStatusCd())) {
                announcementTypeResponse.setAnnounTypeId(announcementTypeEntity.getAnnounTypeId());
                announcementTypeResponse.setAnnounTypeName(announcementTypeEntity.getAnnounTypeName());
                announcementTypeResponse.setRemark(announcementTypeEntity.getRemark());
                announcementTypeResponse.setStatusCd(announcementTypeEntity.getStatusCd());
                announcementTypeResponses.add(announcementTypeResponse);
            }
        }
        log.info("Inside getAllAnnouncementType() fetch record successfully");
        return announcementTypeResponses;
    }

    @Override
    @Retryable(include = {KPIException.class}, maxAttemptsExpression = "${retry-max-attempts}")
    public AnnouncementTypeResponse findAnnouncementTypeById(Integer annonTypeId) {
        log.debug("Inside AnnouncementTypeServiceImpl >> findAnnouncementTypeById() annonTypeId : {}", annonTypeId);

        AnnouncementTypeResponse announcementTypeResponse = null;
        try {
            Optional<AnnouncementTypeEntity> optionalAnnouncementTypeEntity = announcementTypeRepo.findById(annonTypeId);

            log.info("Inside findAnnouncementTypeById : {}", optionalAnnouncementTypeEntity.isPresent());
            if (optionalAnnouncementTypeEntity.isPresent()) {
                AnnouncementTypeEntity announcementType = optionalAnnouncementTypeEntity.get();

                announcementTypeResponse = new AnnouncementTypeResponse();
                announcementTypeResponse.setAnnounTypeId(announcementType.getAnnounTypeId());
                announcementTypeResponse.setAnnounTypeName(announcementType.getAnnounTypeName());
                announcementTypeResponse.setRemark(announcementType.getRemark());
                announcementTypeResponse.setStatusCd(announcementType.getStatusCd());
            }

            log.info("Inside findAnnouncementTypeById() fetch record successfully");
            return announcementTypeResponse;
        } catch (Exception ex) {
            log.error("Inside AnnouncementTypeServiceImpl >> findAnnouncementTypeById() :{}", ex);
            throw new KPIException("DepartmentServiceImpl >> findAnnouncementTypeById()", false, ex.getMessage());
        }
    }


    private AnnouncementTypeEntity convertAnnouncementTypeCreateRequestToEntity(AnnouncementTypeCreateRequest announcementTypeCreateRequest) {
        log.info("Inside convertAnnouncementTypeCreateRequestToEntity()");
        AnnouncementTypeEntity announcementTypeEntity = new AnnouncementTypeEntity();

        announcementTypeEntity.setAnnounTypeName(announcementTypeCreateRequest.getAnnounTypeName());
        announcementTypeEntity.setRemark(announcementTypeCreateRequest.getRemark());
        announcementTypeEntity.setStatusCd(announcementTypeCreateRequest.getStatusCd());
        announcementTypeEntity.setCreatedUserId(announcementTypeCreateRequest.getEmployeeId());
        return announcementTypeEntity;
    }
}
