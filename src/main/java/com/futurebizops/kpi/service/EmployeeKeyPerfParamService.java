package com.futurebizops.kpi.service;

import com.futurebizops.kpi.model.KppAdvanceSearchModel;
import com.futurebizops.kpi.request.EmpKPPMasterUpdateRequest;
import com.futurebizops.kpi.request.EmployeeKeyPerfParamCreateRequest;
import com.futurebizops.kpi.request.GMUpdateMasterEmployeeRatingReq;
import com.futurebizops.kpi.request.HODUpdateMasterEmployeeRatingReq;
import com.futurebizops.kpi.request.advsearch.KPPAdvanceSearchRequest;
import com.futurebizops.kpi.response.HodEmploeeKppResponse;
import com.futurebizops.kpi.response.KPIResponse;
import com.futurebizops.kpi.response.KPPResponse;
import com.futurebizops.kpi.response.dropdown.CompanyDDResponse;
import com.futurebizops.kpi.response.dropdown.DepartmentDDResponse;
import com.futurebizops.kpi.response.dropdown.DesignationDDResponse;
import com.futurebizops.kpi.response.dropdown.RegionDDResponse;
import com.futurebizops.kpi.response.dropdown.RoleDDResponse;
import com.futurebizops.kpi.response.dropdown.SiteDDResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EmployeeKeyPerfParamService {

    public KPIResponse saveEmployeeKeyPerfParamDetails(EmployeeKeyPerfParamCreateRequest keyPerfParamCreateRequest);

    public KPIResponse deleteEmployeeKeyPerfParamDetails(Integer empId,Integer kppId,String kppOverallTarget,String kppOverallWeightage);

    public KPIResponse updateEmployeeKeyPerfParamDetails(EmpKPPMasterUpdateRequest empKPPMasterUpdateRequest);

    public KPIResponse updateHoDApprovalRequest(HODUpdateMasterEmployeeRatingReq keyPerfParamCreateRequest);

    public KPIResponse updateGMApprovalRequest(GMUpdateMasterEmployeeRatingReq empKPPMasterUpdateRequest);

    public List<KPPResponse> getKeyPerfomanceParameter(Integer roleId, Integer deptId, Integer desigId, String statusCd);

    public List<HodEmploeeKppResponse> getEmployeeForHodRatings(Integer empId, String empEId, String statusCd);



    public KPIResponse generateEmployeeKppReport(Integer empId, String statusCd);



    public List<RegionDDResponse> getDDRegionFromEmployee();

    public List<SiteDDResponse> getDDSitesFromEmployee(Integer regionId);

    public List<CompanyDDResponse> getDDCompanyFromEmployee(Integer regionId, Integer siteId);

    public List<RoleDDResponse> getDDRolesFromEmployee(Integer regionId, Integer siteId, Integer companyId);

    public List<DepartmentDDResponse> getDDDeptFromEmployee(Integer regionId, Integer siteId, Integer companyId, Integer roleId);

    public List<DesignationDDResponse> getDDDesigFromEmployee(Integer regionId, Integer siteId, Integer companyId, Integer roleId, Integer deptId);

    public KPIResponse viewEmployeeKpp(Integer empId, Integer roleId, Integer deptId, Integer desigId, Pageable pageable);

    public KPIResponse assignEmployeeKppSearch(Integer empId,Pageable pageable);

    public KPIResponse assignEmployeeKppAdvanceSearch(Integer empId, KppAdvanceSearchModel employeeAdvSearchModel, Pageable pageable);
}
