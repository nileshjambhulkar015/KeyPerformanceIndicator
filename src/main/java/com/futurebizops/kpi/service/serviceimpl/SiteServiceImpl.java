package com.futurebizops.kpi.service.serviceimpl;

import com.futurebizops.kpi.constants.KPIConstants;
import com.futurebizops.kpi.entity.RoleEntity;
import com.futurebizops.kpi.entity.SiteAudit;
import com.futurebizops.kpi.entity.SiteEntity;
import com.futurebizops.kpi.exception.KPIException;
import com.futurebizops.kpi.repository.SiteAuditRepo;
import com.futurebizops.kpi.repository.SiteRepo;
import com.futurebizops.kpi.request.SiteCreateRequest;
import com.futurebizops.kpi.request.SiteUpdateRequest;
import com.futurebizops.kpi.response.DepartmentReponse;
import com.futurebizops.kpi.response.KPIResponse;
import com.futurebizops.kpi.response.SiteResponse;
import com.futurebizops.kpi.service.SiteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SiteServiceImpl implements SiteService {

    @Autowired
    private SiteRepo siteRepo;

    @Autowired
    private SiteAuditRepo siteAuditRepo;

    @Override
    public KPIResponse saveSite(SiteCreateRequest siteCreateRequest) {
        Optional<SiteEntity> designationEntities = siteRepo.findByRegionIdAndSiteNameEqualsIgnoreCase(siteCreateRequest.getRegionId(), siteCreateRequest.getSiteName());
        if (designationEntities.isPresent()) {
            log.error("Inside SiteServiceImpl >> saveSite()");
            throw new KPIException("SiteServiceImpl class", false, "Site name already exist");
        }

        SiteEntity siteEntity = convertSiteCreateRequestToEntity(siteCreateRequest);
        try {
            siteRepo.save(siteEntity);
            SiteAudit siteAudit = new SiteAudit(siteEntity);
            siteAuditRepo.save(siteAudit);
            return KPIResponse.builder()
                    .isSuccess(true)
                    .responseMessage(KPIConstants.RECORD_SUCCESS)
                    .build();
        } catch (Exception ex) {
            log.error("Inside SiteServiceImpl >> saveSite()");
            throw new KPIException("SiteServiceImpl", false, ex.getMessage());
        }
    }

    @Override
    public KPIResponse updateSite(SiteUpdateRequest siteUpdateRequest) {
        SiteEntity siteEntity = convertSiteUpdateRequestToEntity(siteUpdateRequest);
        try {
            siteRepo.save(siteEntity);
            SiteAudit siteAudit = new SiteAudit(siteEntity);
            siteAuditRepo.save(siteAudit);
            return KPIResponse.builder()
                    .isSuccess(true)
                    .responseMessage(KPIConstants.RECORD_UPDATE)
                    .build();
        } catch (Exception ex) {
            log.error("Inside SiteServiceImpl >> updateSite()");
            throw new KPIException("SiteServiceImpl", false, ex.getMessage());
        }
    }

    @Override
    public KPIResponse findSiteDetails(Integer siteId, Integer regionId, String siteName, String statusCd, Pageable requestPageable) {
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

        Integer totalCount = siteRepo.getSiteCount(siteId, regionId, siteName, statusCd);
        List<Object[]> siteData = siteRepo.getSiteDetail(siteId, regionId, siteName, statusCd, sortName, pageSize, pageOffset);

        List<SiteResponse> siteResponses = siteData.stream().map(SiteResponse::new).collect(Collectors.toList());

        siteResponses= siteResponses.stream()
                .sorted(Comparator.comparing(SiteResponse::getSiteName))
                .collect(Collectors.toList());

        return KPIResponse.builder()
                .isSuccess(true)
                .responseData(new PageImpl<>(siteResponses, requestPageable, totalCount))
                .responseMessage(KPIConstants.RECORD_FETCH)
                .build();
    }

    private SiteEntity convertSiteCreateRequestToEntity(SiteCreateRequest siteCreateRequest) {
        SiteEntity siteEntity = new SiteEntity();
        siteEntity.setRegionId(siteCreateRequest.getRegionId());
        siteEntity.setSiteName(siteCreateRequest.getSiteName());
        siteEntity.setRemark(siteCreateRequest.getRemark());
        siteEntity.setStatusCd(siteCreateRequest.getStatusCd());
        siteEntity.setCreatedUserId(siteCreateRequest.getEmployeeId());
        return siteEntity;
    }

    private SiteEntity convertSiteUpdateRequestToEntity(SiteUpdateRequest siteUpdateRequest) {
        SiteEntity siteEntity = new SiteEntity();
        siteEntity.setSiteId(siteEntity.getSiteId());
        siteEntity.setRegionId(siteUpdateRequest.getRegionId());
        siteEntity.setSiteName(siteUpdateRequest.getSiteName());
        siteEntity.setRemark(siteUpdateRequest.getRemark());
        siteEntity.setStatusCd(siteUpdateRequest.getStatusCd());
        siteEntity.setUpdatedUserId(siteUpdateRequest.getEmployeeId());
        return siteEntity;
    }
}
