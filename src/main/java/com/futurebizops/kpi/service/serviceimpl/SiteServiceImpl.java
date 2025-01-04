package com.futurebizops.kpi.service.serviceimpl;

import com.futurebizops.kpi.entity.SiteAudit;
import com.futurebizops.kpi.entity.SiteEntity;
import com.futurebizops.kpi.exception.KPIException;
import com.futurebizops.kpi.repository.SiteAuditRepo;
import com.futurebizops.kpi.repository.SiteRepo;
import com.futurebizops.kpi.request.SiteCreateRequest;
import com.futurebizops.kpi.request.SiteUpdateRequest;
import com.futurebizops.kpi.response.KPIResponse;
import com.futurebizops.kpi.response.SiteResponse;
import com.futurebizops.kpi.response.dropdown.RegionDDResponse;
import com.futurebizops.kpi.response.dropdown.SiteDDResponse;
import com.futurebizops.kpi.service.SiteService;
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
public class SiteServiceImpl implements SiteService {

    @Autowired
    private SiteRepo siteRepo;

    @Autowired
    private SiteAuditRepo siteAuditRepo;

    @Override
    @Retryable(include = {KPIException.class}, maxAttemptsExpression = "${retry-max-attempts}")
    public KPIResponse saveSite(SiteCreateRequest siteCreateRequest) {
        log.debug("Inside SiteServiceImpl >> saveSite() siteCreateRequest : {}", siteCreateRequest);

        KPIResponse kpiResponse = new KPIResponse();
        Optional<SiteEntity> optionalSiteEntity = siteRepo.findByRegionIdAndSiteNameEqualsIgnoreCase(siteCreateRequest.getRegionId(), siteCreateRequest.getSiteName());
        if (optionalSiteEntity.isPresent()) {
            log.error("Inside SiteServiceImpl >> saveSite() Site name already exist");
            throw new KPIException("SiteServiceImpl class", false, "Site name already exist");
        }

        SiteEntity siteEntity = convertSiteCreateRequestToEntity(siteCreateRequest);
        try {
            siteRepo.save(siteEntity);
            SiteAudit siteAudit = new SiteAudit(siteEntity);
            siteAuditRepo.save(siteAudit);

            kpiResponse.setResponseMessage("Site details added successfully");
            kpiResponse.setSuccess(true);
            return kpiResponse;
        } catch (Exception ex) {
            log.error("Inside SiteServiceImpl >> saveSite(): {}", ex);
            throw new KPIException("SiteServiceImpl >> saveSite()", false, ex.getMessage());
        }
    }

    @Transactional
    @Override
    @Retryable(include = {KPIException.class}, maxAttemptsExpression = "${retry-max-attempts}")
    public KPIResponse deleteSiteDetails(Integer siteId) {
        log.debug("Inside SiteServiceImpl >> deleteSiteDetails() siteId : {}", siteId);
        KPIResponse kpiResponse = new KPIResponse();
        try {
            siteRepo.deleteSiteDetails(siteId);
            kpiResponse.setSuccess(true);
            kpiResponse.setResponseMessage("Site details deleted Successfully");
            return kpiResponse;
        } catch (Exception ex) {
            log.error("Inside SiteServiceImpl >> deleteSiteDetails() : {}", ex);
            throw new KPIException("SiteServiceImpl >> deleteSiteDetails()", false, ex.getMessage());
        }
    }

    @Override
    @Retryable(include = {KPIException.class}, maxAttemptsExpression = "${retry-max-attempts}")
    public KPIResponse updateSite(SiteUpdateRequest siteUpdateRequest) {
        log.debug("Inside SiteServiceImpl >> updateSite() siteUpdateRequest : {}", siteUpdateRequest);

        KPIResponse kpiResponse = new KPIResponse();
        try {
            Optional<SiteEntity> optionalSiteEntity = siteRepo.findById(siteUpdateRequest.getSiteId());
            if (optionalSiteEntity.isPresent()) {
                SiteEntity siteEntity = optionalSiteEntity.get();
                siteEntity.setSiteName(siteUpdateRequest.getSiteName());
                siteEntity.setRegionId(siteUpdateRequest.getRegionId());
                siteEntity.setRemark(siteUpdateRequest.getRemark());
                siteEntity.setUpdatedUserId(siteUpdateRequest.getEmployeeId());
                siteRepo.save(siteEntity);
                SiteAudit siteAudit = new SiteAudit(siteEntity);
                siteAuditRepo.save(siteAudit);

                log.info("Inside updateSite() Site details updated Successfully for id :{}", siteUpdateRequest.getSiteId());
                kpiResponse.setSuccess(true);
                kpiResponse.setResponseMessage("Site details updated Successfully");
                return kpiResponse;
            }
        } catch (Exception ex) {
            log.error("Inside SiteServiceImpl >> updateSite() : {}", ex);
            throw new KPIException("SiteServiceImpl >> updateSite()", false, ex.getMessage());
        }
        kpiResponse.setSuccess(false);
        kpiResponse.setResponseMessage("Site details not found");
        return kpiResponse;
    }

    @Override
    @Retryable(include = {KPIException.class}, maxAttemptsExpression = "${retry-max-attempts}")
    public KPIResponse findSiteDetails(Integer siteId, Integer regionId, String siteName, String statusCd, Pageable requestPageable) {
        log.debug("Inside SiteServiceImpl >> findSiteDetails() siteId : {}, regionId : {}, siteName : {}", siteId, regionId, siteName);

        KPIResponse kpiResponse = new KPIResponse();
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
        log.debug("Inside findSiteDetails() pageSize: {}, pageOffset: {}, sortName: {}", pageSize, pageOffset, sortName);
        try {
            Integer totalCount = siteRepo.getSiteCount(siteId, regionId, siteName, statusCd);
            List<Object[]> siteData = siteRepo.getSiteDetail(siteId, regionId, siteName, statusCd, sortName, pageSize, pageOffset);

            List<SiteResponse> siteResponses = siteData.stream().map(SiteResponse::new).collect(Collectors.toList());

            siteResponses = siteResponses.stream()
                    .sorted(Comparator.comparing(SiteResponse::getSiteName))
                    .collect(Collectors.toList());
            kpiResponse.setResponseData(new PageImpl<>(siteResponses, requestPageable, totalCount));
            kpiResponse.setSuccess(true);
            kpiResponse.setResponseMessage("Site details fetch successfully");
            return kpiResponse;
        } catch (Exception ex) {
            log.error("Inside SiteServiceImpl >> findSiteDetails() : {}", ex);
            throw new KPIException("SiteServiceImpl >> findSiteDetails()", false, ex.getMessage());
        }
    }

    @Override
    @Retryable(include = {KPIException.class}, maxAttemptsExpression = "${retry-max-attempts}")
    public List<SiteDDResponse> ddSearchSites(Integer regionId, Integer siteId) {
        log.debug("Inside SiteServiceImpl >> ddSearchSites() regionId : {}, siteId : {}", regionId, siteId);
        try {
            List<Object[]> regionData = siteRepo.ddSiteDetails(regionId, siteId);
            return regionData.stream().map(SiteDDResponse::new).collect(Collectors.toList());
        } catch (Exception ex) {
            log.error("Inside SiteServiceImpl >> ddSearchSites() : {}", ex);
            throw new KPIException("SiteServiceImpl >> ddSearchSites()", false, ex.getMessage());
        }
    }

    @Override
    @Retryable(include = {KPIException.class}, maxAttemptsExpression = "${retry-max-attempts}")
    public SiteResponse getSitesById(Integer siteId) {
        log.debug("Inside SiteServiceImpl >> getSitesById() siteId : {}", siteId);
        try {
            List<Object[]> regionData = siteRepo.SiteByIdDetails(siteId);
            List<SiteResponse> siteResponses = regionData.stream().map(SiteResponse::new).collect(Collectors.toList());
            return siteResponses.get(0);
        } catch (Exception ex) {
            log.error("Inside SiteServiceImpl >> getSitesById() : {}", ex);
            throw new KPIException("SiteServiceImpl >> getSitesById()", false, ex.getMessage());
        }
    }

    @Override
    @Retryable(include = {KPIException.class}, maxAttemptsExpression = "${retry-max-attempts}")
    public List<RegionDDResponse> getDDRegionFromSite() {
        log.debug("Inside SiteServiceImpl >> getDDRegionFromSite()");
        try {
            List<Object[]> regionData = siteRepo.getDDRegionFromSite();
            return regionData.stream().map(RegionDDResponse::new).collect(Collectors.toList());
        } catch (Exception ex) {
            log.error("Inside SiteServiceImpl >> getDDRegionFromSite() : {}", ex);
            throw new KPIException("SiteServiceImpl >> getDDRegionFromSite()", false, ex.getMessage());
        }
    }

    private SiteEntity convertSiteCreateRequestToEntity(SiteCreateRequest siteCreateRequest) {
        log.debug("Inside SiteServiceImpl >> convertSiteCreateRequestToEntity() siteCreateRequest : {}", siteCreateRequest);
        SiteEntity siteEntity = new SiteEntity();
        siteEntity.setRegionId(siteCreateRequest.getRegionId());
        siteEntity.setSiteName(siteCreateRequest.getSiteName());
        siteEntity.setRemark(siteCreateRequest.getRemark());
        siteEntity.setStatusCd(siteCreateRequest.getStatusCd());
        siteEntity.setCreatedUserId(siteCreateRequest.getEmployeeId());
        return siteEntity;
    }


    @Override
    @Retryable(include = {KPIException.class}, maxAttemptsExpression = "${retry-max-attempts}")
    public List<SiteDDResponse> getDDAllSite() {
        log.debug("Inside SiteServiceImpl >> getDDAllSite()");
        try {
            List<SiteEntity> siteEntities = siteRepo.findAll();
            List<SiteDDResponse> siteDDResponses = new ArrayList<>();
            for (SiteEntity siteEntity : siteEntities) {
                SiteDDResponse siteDDResponse = new SiteDDResponse();
                siteDDResponse.setSiteId(siteEntity.getSiteId());
                siteDDResponse.setSiteName(siteEntity.getSiteName());
                siteDDResponses.add(siteDDResponse);
            }
            log.info("Inside getDDAllSite() Total : {}", siteDDResponses.size());
            return siteDDResponses;
        } catch (Exception ex) {
            log.error("Inside SiteServiceImpl >> getDDAllSite() : {}", ex);
            throw new KPIException("SiteServiceImpl >> getDDAllSite()", false, ex.getMessage());
        }
    }
}
