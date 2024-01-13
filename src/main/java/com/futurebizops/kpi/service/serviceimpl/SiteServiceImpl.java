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
import com.futurebizops.kpi.response.KPIResponse;
import com.futurebizops.kpi.service.SiteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
        return null;
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
