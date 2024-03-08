package com.futurebizops.kpi.service;

import com.futurebizops.kpi.request.CompanyMasterCreateRequest;
import com.futurebizops.kpi.request.CompanyMasterUpdateRequest;
import com.futurebizops.kpi.request.DesignationUpdateRequest;
import com.futurebizops.kpi.response.CompanyMasterResponse;
import com.futurebizops.kpi.response.KPIResponse;
import com.futurebizops.kpi.response.dropdown.CompanyDDResponse;
import com.futurebizops.kpi.response.dropdown.RegionDDResponse;
import com.futurebizops.kpi.response.dropdown.SiteDDResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CompanyMasterService {

    public KPIResponse saveCompanyDetails(CompanyMasterCreateRequest departmentCreateRequest);

    public KPIResponse updateCompanyDetails(CompanyMasterUpdateRequest companyMasterUpdateRequest);


    public KPIResponse findCompanyDetails( Integer regionId, Integer siteId, String companyName, String statusCd, Pageable pageable);

    public CompanyMasterResponse findCompanyById(Integer companyId);



    public List<CompanyMasterResponse> findAllCompanyByRegionaIdAndSiteId(Integer regionId, Integer siteId);

    public List<RegionDDResponse> getDDRegionFromCompany();

    public List<SiteDDResponse> getDDSitesFromComany(Integer regionId);
    public List<CompanyMasterResponse> getAllCompanyByCompanyId(Integer companyId);

    public List<CompanyDDResponse> getDDCompanyFromComany(Integer regionId, Integer siteId);

   // public void uploadDesigExcelFile(MultipartFile file) throws IOException;

}
