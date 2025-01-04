package com.futurebizops.kpi.service.serviceimpl;

import com.futurebizops.kpi.constants.KPIConstants;
import com.futurebizops.kpi.entity.CompanyMasterAudit;
import com.futurebizops.kpi.entity.CompanyMasterEntity;
import com.futurebizops.kpi.exception.KPIException;
import com.futurebizops.kpi.repository.CompanyMasterAuditRepo;
import com.futurebizops.kpi.repository.CompanyMasterRepo;
import com.futurebizops.kpi.request.CompanyMasterCreateRequest;
import com.futurebizops.kpi.request.CompanyMasterUpdateRequest;
import com.futurebizops.kpi.response.CompanyMasterResponse;
import com.futurebizops.kpi.response.KPIResponse;
import com.futurebizops.kpi.response.dropdown.CompanyDDResponse;
import com.futurebizops.kpi.response.dropdown.RegionDDResponse;
import com.futurebizops.kpi.response.dropdown.SiteDDResponse;
import com.futurebizops.kpi.service.CompanyMasterService;
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
public class CompanyMasterServiceImpl implements CompanyMasterService {

    @Autowired
    CompanyMasterRepo companyMasterRepo;

    @Autowired
    CompanyMasterAuditRepo companyMasterAuditRepo;


    @Override
    @Retryable(include = {KPIException.class}, maxAttemptsExpression = "${retry-max-attempts}")
    public KPIResponse saveCompanyDetails(CompanyMasterCreateRequest companyMasterRequest) {
        log.debug("Inside CompanyMasterServiceImpl >> saveCompanyDetails() masterCreateRequest : {}", companyMasterRequest);

        Optional<CompanyMasterEntity> optionalCompanyMasterEntity = companyMasterRepo.findByCompanyNameEqualsIgnoreCaseAndRegionIdAndSiteId(companyMasterRequest.getCompanyName(), companyMasterRequest.getRegionId(), companyMasterRequest.getSiteId());
        log.info("Inside CompanyMasterServiceImpl >> saveCompanyDetails() optionalCompanyMasterEntity : {}", optionalCompanyMasterEntity.isPresent());

        if (optionalCompanyMasterEntity.isPresent()) {
            log.error("Inside CompanyMasterServiceImpl >> saveCompanyDetails() Company name already exist");
            throw new KPIException("Inside CompanyMasterServiceImpl >> saveCompanyDetails()", false, "Company name already exist");
        }

        CompanyMasterEntity companyMasterEntity = convertCompanyCreateRequestToEntity(companyMasterRequest);
        try {
            companyMasterRepo.save(companyMasterEntity);
            CompanyMasterAudit partAudit = new CompanyMasterAudit(companyMasterEntity);
            companyMasterAuditRepo.save(partAudit);
            log.info("Inside CompanyMasterServiceImpl >> saveCompanyDetails() company details save successfully");
            return KPIResponse.builder()
                    .isSuccess(true)
                    .responseMessage(KPIConstants.RECORD_SUCCESS)
                    .build();
        } catch (Exception ex) {
            log.error("Inside CompanyMasterServiceImpl >> saveCompanyDetails(): {}", ex);
            throw new KPIException("CompanyMasterServiceImpl >> saveCompanyDetails()", false, ex.getMessage());
        }
    }

    @Transactional
    @Override
    @Retryable(include = {KPIException.class}, maxAttemptsExpression = "${retry-max-attempts}")
    public KPIResponse deleteCompanyDetails(Integer companyId) {
        log.debug("Inside CompanyMasterServiceImpl >> deleteCompanyDetails() companyId : {}", companyId);
        KPIResponse kpiResponse = new KPIResponse();
        try {
            companyMasterRepo.deleteCompanyDetails(companyId);
            kpiResponse.setSuccess(true);
            kpiResponse.setResponseMessage("Company details deleted Successfully");
            log.info("Inside deleteCompanyDetails() Company id {} deleted Successfully ", companyId);
            return kpiResponse;
        } catch (Exception ex) {
            log.error("Inside CompanyMasterServiceImpl >> deleteCompanyDetails() : {}", ex);
            return KPIResponse.builder()
                    .isSuccess(false)
                    .build();
        }

    }

    @Override
    @Retryable(include = {KPIException.class}, maxAttemptsExpression = "${retry-max-attempts}")
    public KPIResponse updateCompanyDetails(CompanyMasterUpdateRequest companyMasterUpdateRequest) {
        log.debug("Inside CompanyMasterServiceImpl >> updateCompanyDetails() companyMasterUpdateRequest : {}", companyMasterUpdateRequest);
        KPIResponse kpiResponse = new KPIResponse();
        try {

            Optional<CompanyMasterEntity> optionalCompanyMasterEntity = companyMasterRepo.findById(companyMasterUpdateRequest.getCompanyId());

            log.info("Inside updateCompanyDetails() : {}", optionalCompanyMasterEntity.isPresent());
            if (optionalCompanyMasterEntity.isPresent()) {
                CompanyMasterEntity companyMasterEntity = optionalCompanyMasterEntity.get();
                companyMasterEntity.setSiteId(companyMasterUpdateRequest.getSiteId());
                companyMasterEntity.setRegionId(companyMasterUpdateRequest.getRegionId());
                companyMasterEntity.setCompanyAddress(companyMasterUpdateRequest.getCompanyAddress());
                companyMasterEntity.setCompanyName(companyMasterUpdateRequest.getCompanyName());
                companyMasterEntity.setCompanyFinYear(companyMasterUpdateRequest.getCompanyFinYear());
                companyMasterEntity.setCompanyMbNo(companyMasterUpdateRequest.getCompanyMbNo());
                companyMasterEntity.setRemark(companyMasterUpdateRequest.getRemark());
                companyMasterEntity.setUpdatedUserId(companyMasterUpdateRequest.getEmployeeId());
                companyMasterRepo.save(companyMasterEntity);

                log.info("Company id {} updated successfully", companyMasterUpdateRequest.getCompanyId());
                kpiResponse.setSuccess(true);
                kpiResponse.setResponseMessage("Company details updated successfully");
                return kpiResponse;
            }
        } catch (Exception ex) {
            log.error("Inside CompanyMasterServiceImpl >> updateCompanyDetails() : {}", ex);
            throw new KPIException("Inside CompanyMasterServiceImpl >> updateCompanyDetails()", false, ex.getMessage());
        }
        kpiResponse.setSuccess(false);
        kpiResponse.setResponseMessage("Company id " + companyMasterUpdateRequest.getCompanyId() + " not present");
        return kpiResponse;
    }

    @Override
    @Retryable(include = {KPIException.class}, maxAttemptsExpression = "${retry-max-attempts}")
    public KPIResponse findCompanyDetails(Integer regionId, Integer siteId, String companyName, String statusCd, Pageable requestPageable) {
        log.debug("Inside CompanyMasterServiceImpl >> findCompanyDetails() regionId :{}, siteId : {}, companyName : {}", regionId, siteId, companyName);

        KPIResponse kpiResponse = new KPIResponse();
        String sortName = null;
        //String sortDirection = null;
        Integer pageSize = requestPageable.getPageSize();
        Integer pageOffset = (int) requestPageable.getOffset();
        // pageable = KPIUtils.sort(requestPageable, sortParam, pageDirection);
        Optional<Sort.Order> order = requestPageable.getSort().get().findFirst();
        if (order.isPresent()) {
            sortName = order.get().getProperty();  //order by this field
            // sortDirection = order.get().getDirection().toString(); // Sort ASC or DESC
        }

        log.info("Inside findCompanyDetails() sortName : {}, pageSize : {}, pageOffset : {}", sortName, pageSize, pageOffset);
        try {
            Integer totalCount = companyMasterRepo.getCompanyCount(regionId, siteId, companyName, statusCd);
            List<Object[]> companyData = companyMasterRepo.getCompanyDetail(regionId, siteId, companyName, statusCd, sortName, pageSize, pageOffset);

            List<CompanyMasterResponse> companyMasterResponses = companyData.stream().map(CompanyMasterResponse::new).collect(Collectors.toList());

            log.info("Inside findCompanyDetails() total : {}", companyMasterResponses.size());
            companyMasterResponses = companyMasterResponses.stream()
                    .sorted(Comparator.comparing(CompanyMasterResponse::getCompanyName))
                    .collect(Collectors.toList());
            kpiResponse.setSuccess(true);
            kpiResponse.setResponseData(new PageImpl<>(companyMasterResponses, requestPageable, totalCount));
            kpiResponse.setResponseMessage("Company details fetch successfully");
            return kpiResponse;
        } catch (Exception ex) {
            log.error("Inside CompanyMasterServiceImpl >> findCompanyDetails() : {}", ex);
            throw new KPIException("Inside CompanyMasterServiceImpl >> findCompanyDetails()", false, ex.getMessage());
        }
    }

    @Override
    @Retryable(include = {KPIException.class}, maxAttemptsExpression = "${retry-max-attempts}")
    public CompanyMasterResponse findCompanyById(Integer companyId) {
        log.debug("Inside CompanyMasterServiceImpl >> findCompanyById() companyId : {}", companyId);

        List<Object[]> companyData = companyMasterRepo.getAllCompanyByCompId(companyId);
        try {
            List<CompanyMasterResponse> companyMasterResponse = companyData.stream().map(CompanyMasterResponse::new).collect(Collectors.toList());
            log.info("Inside findCompanyById() total :{}", companyMasterResponse.size());
            if (companyMasterResponse.size() > 0) {
                return companyMasterResponse.get(0);
            }
        } catch (Exception ex) {
            log.error("Inside CompanyMasterServiceImpl >> findCompanyById() : {}", ex);
            throw new KPIException("Inside CompanyMasterServiceImpl >> findCompanyById()", false, ex.getMessage());
        }
        log.info("Inside  CompanyMasterServiceImpl >> findCompanyById() company id {} not found", companyId);
        return new CompanyMasterResponse();
    }

    @Override
    @Retryable(include = {KPIException.class}, maxAttemptsExpression = "${retry-max-attempts}")
    public List<CompanyMasterResponse> findAllCompanyByRegionaIdAndSiteId(Integer regionId, Integer siteId) {
        log.debug("Inside CompanyMasterServiceImpl >> findAllCompanyByRegionaIdAndSiteId() regionId : {}, siteId :{}", regionId, siteId);
        try {
            List<Object[]> companyMasterData = companyMasterRepo.getAllCompanyByRegionIdAndSiteId(regionId, siteId);
            return companyMasterData.stream().map(CompanyMasterResponse::new).collect(Collectors.toList());
        } catch (Exception ex) {
            log.error("Inside CompanyMasterServiceImpl >> findAllCompanyByRegionaIdAndSiteId() : {}", ex);
            throw new KPIException("Inside CompanyMasterServiceImpl >> findAllCompanyByRegionaIdAndSiteId()", false, ex.getMessage());
        }
    }

    @Override
    @Retryable(include = {KPIException.class}, maxAttemptsExpression = "${retry-max-attempts}")
    public List<RegionDDResponse> getDDRegionFromCompany() {
        log.debug("Inside CompanyMasterServiceImpl >> getDDRegionFromCompany()");
        try {
            List<Object[]> regionData = companyMasterRepo.getDDRegionFromCompany();
            return regionData.stream().map(RegionDDResponse::new).collect(Collectors.toList());
        } catch (Exception ex) {
            log.error("Inside CompanyMasterServiceImpl >> getDDRegionFromCompany() : {}", ex);
            throw new KPIException("Inside CompanyMasterServiceImpl >> getDDRegionFromCompany()", false, ex.getMessage());
        }
    }

    @Override
    @Retryable(include = {KPIException.class}, maxAttemptsExpression = "${retry-max-attempts}")
    public List<SiteDDResponse> getDDSitesFromComany(Integer regionId) {
        log.debug("Inside CompanyMasterServiceImpl >> getDDSitesFromComany() regionId : {}", regionId);
        try {
            List<Object[]> siteData = companyMasterRepo.getDDSiteFromCompany(regionId);
            return siteData.stream().map(SiteDDResponse::new).collect(Collectors.toList());
        } catch (Exception ex) {
            log.error("Inside CompanyMasterServiceImpl >> getDDSitesFromComany() : {}", ex);
            throw new KPIException("Inside CompanyMasterServiceImpl >> getDDSitesFromComany()", false, ex.getMessage());
        }
    }

    @Override
    @Retryable(include = {KPIException.class}, maxAttemptsExpression = "${retry-max-attempts}")
    public List<CompanyDDResponse> getDDCompanyFromComany(Integer regionId, Integer siteId) {
        log.debug("Inside CompanyMasterServiceImpl >> getDDCompanyFromComany() regionId : {}, siteId : {}", regionId, siteId);
        try {
            List<Object[]> companyData = companyMasterRepo.getDDCompanyFromCompany(regionId, siteId);
            return companyData.stream().map(CompanyDDResponse::new).collect(Collectors.toList());
        } catch (Exception ex) {
            log.error("Inside CompanyMasterServiceImpl >> getDDCompanyFromComany() : {}", ex);
            throw new KPIException("Inside CompanyMasterServiceImpl >> getDDCompanyFromComany()", false, ex.getMessage());
        }
    }


    @Override
    @Retryable(include = {KPIException.class}, maxAttemptsExpression = "${retry-max-attempts}")
    public List<CompanyDDResponse> getDDAllCompany() {
        log.debug("Inside CompanyMasterServiceImpl >> getDDAllCompany()");
        try {
            List<CompanyMasterEntity> companyMasterEntities = companyMasterRepo.findAll();

            log.info("Inside getDDAllCompany() total :{}", companyMasterEntities.size());
            List<CompanyDDResponse> companyDDResponses = new ArrayList<>();

            for (CompanyMasterEntity companyMasterEntity : companyMasterEntities) {
                CompanyDDResponse companyDDResponse = new CompanyDDResponse();
                companyDDResponse.setCompanyId(companyMasterEntity.getCompanyId());
                companyDDResponse.setCompanyName(companyMasterEntity.getCompanyName());

                companyDDResponses.add(companyDDResponse);
            }
            log.info("Inside getDDAllCompany() details fetch successfully");
            return companyDDResponses;
        } catch (Exception ex) {
            log.error("Inside CompanyMasterServiceImpl >> getDDAllCompany() : {}", ex);
            throw new KPIException("Inside CompanyMasterServiceImpl >> getDDAllCompany()", false, ex.getMessage());
        }
    }

    private CompanyMasterEntity convertCompanyCreateRequestToEntity(CompanyMasterCreateRequest companyMasterRequest) {
        log.info("Inside convertCompanyCreateRequestToEntity()");

        CompanyMasterEntity companyMasterEntity = new CompanyMasterEntity();
        companyMasterEntity.setRegionId(companyMasterRequest.getRegionId());
        companyMasterEntity.setSiteId(companyMasterRequest.getSiteId());
        companyMasterEntity.setCompanyName(companyMasterRequest.getCompanyName());
        companyMasterEntity.setCompanyAddress(companyMasterRequest.getCompanyAddress());
        companyMasterEntity.setCompanyMbNo(companyMasterRequest.getCompanyMbNo());
        companyMasterEntity.setCompanyFinYear(companyMasterRequest.getCompanyFinYear());
        companyMasterEntity.setRemark(companyMasterRequest.getRemark());
        companyMasterEntity.setStatusCd(companyMasterRequest.getStatusCd());
        companyMasterEntity.setCreatedUserId(companyMasterRequest.getEmployeeId());
        return companyMasterEntity;
    }
}
