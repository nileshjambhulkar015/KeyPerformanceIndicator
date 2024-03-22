package com.futurebizops.kpi.service.serviceimpl;

import com.futurebizops.kpi.constants.KPIConstants;
import com.futurebizops.kpi.entity.CompanyMasterAudit;
import com.futurebizops.kpi.entity.CompanyMasterEntity;
import com.futurebizops.kpi.entity.DesignationAudit;
import com.futurebizops.kpi.entity.DesignationEntity;
import com.futurebizops.kpi.entity.SiteEntity;
import com.futurebizops.kpi.exception.KPIException;
import com.futurebizops.kpi.repository.CompanyMasterAuditRepo;
import com.futurebizops.kpi.repository.CompanyMasterRepo;
import com.futurebizops.kpi.request.CompanyMasterCreateRequest;
import com.futurebizops.kpi.request.CompanyMasterUpdateRequest;
import com.futurebizops.kpi.request.DesignationUpdateRequest;
import com.futurebizops.kpi.response.CompanyMasterResponse;
import com.futurebizops.kpi.response.DesignationReponse;
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
import org.springframework.stereotype.Service;

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
    public KPIResponse saveCompanyDetails(CompanyMasterCreateRequest companyMasterRequest) {

        Optional<CompanyMasterEntity> companyMasterOptional = companyMasterRepo.findByCompanyNameEqualsIgnoreCaseAndRegionIdAndSiteId(companyMasterRequest.getCompanyName(),companyMasterRequest.getRegionId(), companyMasterRequest.getSiteId() );
        if(companyMasterOptional.isPresent()){
            log.error("Inside CompanyMasterServiceImpl >> saveCompanyDetails()");
            throw new KPIException("CompanyMasterServiceImpl Class", false, "Company name already exist");
        }

        CompanyMasterEntity companyMasterEntity = convertCompanyCreateRequestToEntity(companyMasterRequest);
        try {
            companyMasterRepo.save(companyMasterEntity);
            CompanyMasterAudit partAudit = new CompanyMasterAudit(companyMasterEntity);
            companyMasterAuditRepo.save(partAudit);
            return KPIResponse.builder()
                    .isSuccess(true)
                    .responseMessage(KPIConstants.RECORD_SUCCESS)
                    .build();
        } catch (Exception ex) {
            log.error("Inside CompanyMasterServiceImpl >> saveCompanyDetails()");
            throw new KPIException("CompanyMasterServiceImpl", false, ex.getMessage());
        }
    }


    private CompanyMasterEntity convertCompanyCreateRequestToEntity(CompanyMasterCreateRequest companyMasterRequest) {
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

    private CompanyMasterEntity convertCompanyUpdateRequestToEntity(CompanyMasterUpdateRequest companyMasterUpdateReq) {

        CompanyMasterEntity companyMasterEntity = new CompanyMasterEntity();
        companyMasterEntity.setCompanyId(companyMasterUpdateReq.getCompanyId());
        companyMasterEntity.setRegionId(companyMasterUpdateReq.getRegionId());
        companyMasterEntity.setSiteId(companyMasterUpdateReq.getSiteId());
        companyMasterEntity.setCompanyName(companyMasterUpdateReq.getCompanyName());

        companyMasterEntity.setCompanyAddress(companyMasterUpdateReq.getCompanyAddress());
        companyMasterEntity.setCompanyMbNo(companyMasterUpdateReq.getCompanyMbNo());
        companyMasterEntity.setCompanyFinYear(companyMasterUpdateReq.getCompanyFinYear());
        companyMasterEntity.setRemark(companyMasterUpdateReq.getRemark());
        companyMasterEntity.setStatusCd(companyMasterUpdateReq.getStatusCd());
        companyMasterEntity.setCreatedUserId(companyMasterUpdateReq.getEmployeeId());
        return companyMasterEntity;
    }


    @Override
    public KPIResponse updateCompanyDetails(CompanyMasterUpdateRequest companyMasterUpdateRequest) {

        CompanyMasterEntity companyMasterEntity = convertCompanyUpdateRequestToEntity(companyMasterUpdateRequest);
        try {
            companyMasterRepo.save(companyMasterEntity);
            CompanyMasterAudit companyMasterAudit = new CompanyMasterAudit(companyMasterEntity);
            companyMasterAuditRepo.save(companyMasterAudit);
            return KPIResponse.builder()
                    .isSuccess(true)
                    .responseMessage(KPIConstants.RECORD_UPDATE)
                    .build();
        } catch (Exception ex) {
            log.error("Inside CompanyMasterServiceImpl >> updateCompanyDetails() : {}", ex);
            throw new KPIException("CompanyMasterServiceImpl", false, ex.getMessage());
        }
    }

    @Override
    public KPIResponse findCompanyDetails(Integer regionId, Integer siteId, String companyName, String statusCd, Pageable requestPageable) {
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

        Integer totalCount = companyMasterRepo.getCompanyCount(regionId, siteId, companyName, statusCd);
        List<Object[]> companyData = companyMasterRepo.getCompanyDetail(regionId, siteId, companyName, statusCd, sortName, pageSize, pageOffset);

        List<CompanyMasterResponse> companyMasterResponses = companyData.stream().map(CompanyMasterResponse::new).collect(Collectors.toList());

        companyMasterResponses = companyMasterResponses.stream()
                .sorted(Comparator.comparing(CompanyMasterResponse::getCompanyName))
                .collect(Collectors.toList());

        return KPIResponse.builder()
                .isSuccess(true)
                .responseData(new PageImpl<>(companyMasterResponses, requestPageable, totalCount))
                .responseMessage(KPIConstants.RECORD_FETCH)
                .build();
    }

    @Override
    public CompanyMasterResponse findCompanyById(Integer companyId) {
        List<Object[]> companyData = companyMasterRepo.getAllCompanyByCompId(companyId);

        List<CompanyMasterResponse> companyMasterResponse = companyData.stream().map(CompanyMasterResponse::new).collect(Collectors.toList());
       return companyMasterResponse.get(0);
    }

    @Override
    public List<CompanyMasterResponse> findAllCompanyByRegionaIdAndSiteId(Integer regionId, Integer siteId) {
        List<Object[]> designationData = companyMasterRepo.getAllCompanyByRegionIdAndSiteId(regionId, siteId);

        return designationData.stream().map(CompanyMasterResponse::new).collect(Collectors.toList());
    }

    @Override
    public List<RegionDDResponse> getDDRegionFromCompany() {
        List<Object[]> regionData = companyMasterRepo.getDDRegionFromCompany();
        return regionData.stream().map(RegionDDResponse::new).collect(Collectors.toList());
    }

    @Override
    public List<SiteDDResponse> getDDSitesFromComany(Integer regionId) {
        List<Object[]> siteData = companyMasterRepo.getDDSiteFromCompany(regionId);
        return siteData.stream().map(SiteDDResponse::new).collect(Collectors.toList());
    }

    @Override
    public List<CompanyDDResponse> getDDCompanyFromComany(Integer regionId, Integer siteId) {
        List<Object[]> siteData = companyMasterRepo.getDDCompanyFromCompany(regionId, siteId);
        return siteData.stream().map(CompanyDDResponse::new).collect(Collectors.toList());
    }

    @Override
    public List<CompanyMasterResponse> getAllCompanyByCompanyId(Integer companyId) {
        return null;
    }

    @Override
    public  List<CompanyDDResponse> getDDAllCompany(){
        List<CompanyMasterEntity> companyMasterEntities = companyMasterRepo.findAll();
        List<CompanyDDResponse> companyDDResponses = new ArrayList<>();

        for(CompanyMasterEntity companyMasterEntity : companyMasterEntities){
            CompanyDDResponse companyDDResponse = new CompanyDDResponse();
            companyDDResponse.setCompanyId(companyMasterEntity.getCompanyId());
            companyDDResponse.setCompanyName(companyMasterEntity.getCompanyName());
            companyDDResponse.setStatusCd(companyMasterEntity.getStatusCd());

            companyDDResponses.add(companyDDResponse);
        }
        return companyDDResponses;
    }
}
