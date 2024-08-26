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
import org.springframework.stereotype.Service;

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

    @Autowired
    RoleRepo roleRepo;


    @Override
    public KPIResponse saveAnnouncementTypeDetails(AnnouncementTypeCreateRequest announcementTypeCreateRequest) {
        Optional<AnnouncementTypeEntity> announcementTypeEntity = announcementTypeRepo.findByAnnounTypeNameEqualsIgnoreCase(announcementTypeCreateRequest.getAnnounTypeName() );
        if(announcementTypeEntity.isPresent()){
            log.error("Inside AnnouncementTypeServiceImpl >> saveAnnouncementTypeDetails()");

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
            return KPIResponse.builder()
                    .isSuccess(true)
                    .responseMessage(KPIConstants.RECORD_SUCCESS)
                    .build();
            } catch (Exception ex) {
            log.error("Inside AnnouncementTypeServiceImpl >> saveAnnouncementTypeDetails()");
            throw new KPIException("AnnouncementTypeServiceImpl", false, ex.getMessage());
        }
    }
   //

    @Override
    public KPIResponse updateAnnouncementTypeDetails(AnnouncementTypeUpdateRequest announcementTypeUpdateRequest) {
        AnnouncementTypeEntity announcementTypeEntity = convertAnnouncementTypeUpdateRequestToEntity(announcementTypeUpdateRequest);
        try {
            announcementTypeRepo.save(announcementTypeEntity);
            AnnouncementTypeAudit departmentAudit = new AnnouncementTypeAudit(announcementTypeEntity);
            announcementTypeAuditRepo.save(departmentAudit);
            return KPIResponse.builder()
                    .isSuccess(true)
                    .responseMessage(KPIConstants.RECORD_UPDATE)
                    .build();
        } catch (Exception ex) {
            log.error("Inside AnnouncementTypeServiceImpl >> updateAnnouncementTypeDetails()");
            throw new KPIException("AnnouncementTypeServiceImpl", false, ex.getMessage());
        }

    }

    @Override
    public KPIResponse findAnnouncementTypeSearch(Integer announTypeId, String announTypeName, String statusCd, Pageable requestPageable) {
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

        Integer totalCount = announcementTypeRepo.getAnnouncementTypeCount(announTypeId, announTypeName, statusCd);
        List<Object[]> departmentData = announcementTypeRepo.getAnnouncementTypeDetail(announTypeId, announTypeName, statusCd, sortName, pageSize, pageOffset);

        List<AnnouncementTypeResponse> announcementTypeResponses = departmentData.stream().map(AnnouncementTypeResponse::new).collect(Collectors.toList());

        announcementTypeResponses= announcementTypeResponses.stream()
                .sorted(Comparator.comparing(AnnouncementTypeResponse::getAnnounTypeName))
                .collect(Collectors.toList());

        return KPIResponse.builder()
                .isSuccess(true)
                .responseData(new PageImpl<>(announcementTypeResponses, requestPageable, totalCount))
                .responseMessage(KPIConstants.RECORD_FETCH)
                .build();
    }

    @Override
    public List<AnnouncementTypeResponse> getAllAnnouncementType() {
        List<AnnouncementTypeEntity> announcementTypeEntities =  announcementTypeRepo.findAll();
        List<AnnouncementTypeResponse> announcementTypeResponses = new ArrayList<>();
        AnnouncementTypeResponse announcementTypeResponse = null;
        for(AnnouncementTypeEntity announcementTypeEntity : announcementTypeEntities){
            announcementTypeResponse = new AnnouncementTypeResponse();
            if("A".equalsIgnoreCase(announcementTypeEntity.getStatusCd())) {
                announcementTypeResponse.setAnnonTypeId(announcementTypeEntity.getAnnounTypeId());
                announcementTypeResponse.setAnnounTypeName(announcementTypeEntity.getAnnounTypeName());
                announcementTypeResponse.setRemark(announcementTypeEntity.getRemark());
                announcementTypeResponse.setStatusCd(announcementTypeEntity.getStatusCd());
                announcementTypeResponses.add(announcementTypeResponse);
            }
        }
        return announcementTypeResponses;
    }

    @Override
    public AnnouncementTypeResponse findAnnouncementTypeById(Integer annonTypeId) {
        AnnouncementTypeResponse  announcementTypeResponse = null;
        try {
            Optional<AnnouncementTypeEntity> optionalAnnouncementTypeEntity = announcementTypeRepo.findById(annonTypeId);
            if(optionalAnnouncementTypeEntity.isPresent()){
                AnnouncementTypeEntity announcementType = optionalAnnouncementTypeEntity.get();

                 announcementTypeResponse = new AnnouncementTypeResponse();
                announcementTypeResponse.setAnnonTypeId(announcementType.getAnnounTypeId());
                announcementTypeResponse.setAnnounTypeName(announcementType.getAnnounTypeName());
                announcementTypeResponse.setRemark(announcementType.getRemark());
                announcementTypeResponse.setStatusCd(announcementType.getStatusCd());
            }

            return announcementTypeResponse;
        } catch (Exception ex) {
            log.error("AnnouncementTypeServiceImpl >>findAnnouncementTypeById :{}", ex);
            throw new KPIException("DepartmentServiceImpl", false, ex.getMessage());
        }
    }




    private AnnouncementTypeEntity convertAnnouncementTypeCreateRequestToEntity(AnnouncementTypeCreateRequest announcementTypeCreateRequest) {
        AnnouncementTypeEntity announcementTypeEntity = new AnnouncementTypeEntity();

        announcementTypeEntity.setAnnounTypeName(announcementTypeCreateRequest.getAnnounTypeName());
        announcementTypeEntity.setRemark(announcementTypeCreateRequest.getRemark());
        announcementTypeEntity.setStatusCd(announcementTypeCreateRequest.getStatusCd());
        announcementTypeEntity.setCreatedUserId(announcementTypeCreateRequest.getEmployeeId());
        return  announcementTypeEntity;
    }

    private AnnouncementTypeEntity convertAnnouncementTypeUpdateRequestToEntity(AnnouncementTypeUpdateRequest announcementTypeUpdateRequest) {
        AnnouncementTypeEntity announcementType = new AnnouncementTypeEntity();
        announcementType.setAnnounTypeId(announcementTypeUpdateRequest.getAnnounTypeId());
        announcementType.setAnnounTypeName(announcementTypeUpdateRequest.getAnnounTypeName());
        announcementType.setRemark(announcementTypeUpdateRequest.getRemark());
        announcementType.setStatusCd(announcementTypeUpdateRequest.getStatusCd());
        announcementType.setCreatedUserId(announcementTypeUpdateRequest.getEmployeeId());
        return  announcementType;
    }
}
