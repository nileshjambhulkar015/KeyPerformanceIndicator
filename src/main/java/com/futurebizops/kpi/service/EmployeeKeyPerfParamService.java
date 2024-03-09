package com.futurebizops.kpi.service;

import com.futurebizops.kpi.request.EmpKPPMasterUpdateRequest;
import com.futurebizops.kpi.request.EmployeeKeyPerfParamCreateRequest;
import com.futurebizops.kpi.request.GMUpdateMasterEmployeeRatingReq;
import com.futurebizops.kpi.request.HODUpdateMasterEmployeeRatingReq;
import com.futurebizops.kpi.response.EmpKppStatusResponse;
import com.futurebizops.kpi.response.HodEmploeeKppResponse;
import com.futurebizops.kpi.response.KPIResponse;
import com.futurebizops.kpi.response.KPPResponse;
import com.futurebizops.kpi.response.dropdown.CompanyDDResponse;
import com.futurebizops.kpi.response.dropdown.RegionDDResponse;
import com.futurebizops.kpi.response.dropdown.SiteDDResponse;

import java.util.List;

public interface EmployeeKeyPerfParamService {

    public KPIResponse saveEmployeeKeyPerfParamDetails(EmployeeKeyPerfParamCreateRequest keyPerfParamCreateRequest);

    public KPIResponse updateEmployeeKeyPerfParamDetails(EmpKPPMasterUpdateRequest empKPPMasterUpdateRequest);

    public KPIResponse updateHoDApprovalRequest(HODUpdateMasterEmployeeRatingReq keyPerfParamCreateRequest);

    public KPIResponse updateGMApprovalRequest(GMUpdateMasterEmployeeRatingReq empKPPMasterUpdateRequest);

    public List<KPPResponse> getKeyPerfomanceParameter(Integer roleId, Integer deptId, Integer desigId, String statusCd);

    public List<HodEmploeeKppResponse> getEmployeeForHodRatings(Integer empId, String empEId, String statusCd);



    public KPIResponse generateEmployeeKppReport(Integer empId, String statusCd);

    public List<RegionDDResponse> getDDRegionFromCompany();

    public List<SiteDDResponse> getDDSitesFromComany(Integer regionId);

    public List<CompanyDDResponse> getDDCompanyFromComany(Integer regionId, Integer siteId);
}
