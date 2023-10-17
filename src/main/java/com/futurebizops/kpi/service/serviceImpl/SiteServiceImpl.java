package com.futurebizops.kpi.service.serviceImpl;

import com.futurebizops.kpi.constants.KPIConstants;
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
            throw new KPIException("SiteServiceImpl", false, "Site name already exist");
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
        return SiteEntity.siteEntityBuilder()
                .regionId(siteCreateRequest.getRegionId())
                .siteName(siteCreateRequest.getSiteName())
                .remark(siteCreateRequest.getRemark())
                .statusCd(siteCreateRequest.getStatusCd())
                .createdUserId(siteCreateRequest.getEmployeeId())
                .build();
    }

    private SiteEntity convertSiteUpdateRequestToEntity(SiteUpdateRequest siteUpdateRequest) {
        return SiteEntity.siteEntityBuilder()
                .siteId(siteUpdateRequest.getSiteId())
                .regionId(siteUpdateRequest.getRegionId())
                .siteName(siteUpdateRequest.getSiteName())
                .remark(siteUpdateRequest.getRemark())
                .statusCd(siteUpdateRequest.getStatusCd())
                .createdUserId(siteUpdateRequest.getEmployeeId())
                .build();
    }
}
