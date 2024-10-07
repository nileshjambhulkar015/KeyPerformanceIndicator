package com.futurebizops.kpi.controller;

import com.futurebizops.kpi.enums.StatusCdEnum;
import com.futurebizops.kpi.model.KppAdvanceSearchModel;
import com.futurebizops.kpi.request.EmpKPPMasterUpdateRequest;
import com.futurebizops.kpi.request.EmployeeKeyPerfParamCreateRequest;
import com.futurebizops.kpi.request.advsearch.KPPAdvanceSearchRequest;
import com.futurebizops.kpi.response.KPIResponse;
import com.futurebizops.kpi.response.KPPResponse;
import com.futurebizops.kpi.response.dropdown.CompanyDDResponse;
import com.futurebizops.kpi.response.dropdown.DepartmentDDResponse;
import com.futurebizops.kpi.response.dropdown.DesignationDDResponse;
import com.futurebizops.kpi.response.dropdown.RegionDDResponse;
import com.futurebizops.kpi.response.dropdown.RoleDDResponse;
import com.futurebizops.kpi.response.dropdown.SiteDDResponse;
import com.futurebizops.kpi.service.EmployeeKeyPerfParamService;
import io.swagger.v3.oas.annotations.Parameter;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/employee-kpp")
public class EmployeeKppController {

    @Autowired
    private EmployeeKeyPerfParamService employeeKeyPerfParamService;

    //for employee master page to get details of employee
    @GetMapping
    @PageableAsQueryParam
    public ResponseEntity<KPIResponse> getAllEmployee(@RequestParam(required = false) Integer empId,
                                                      @RequestParam(required = false) String empEId,
                                                      @RequestParam(required = false) Integer roleId,
                                                      @RequestParam(required = false) Integer deptId,
                                                      @RequestParam(required = false) Integer desigId,
                                                      @RequestParam(required = false) String empFirstName,
                                                      @RequestParam(required = false) String empMiddleName,
                                                      @RequestParam(required = false) String empLastName,
                                                      @RequestParam(required = false) String empMobileNo,
                                                      @RequestParam(required = false) String emailId,
                                                      @RequestParam(required = false) String statusCd,
                                                      @RequestParam(required = false) Integer empTypeId,
                                                      @RequestParam(required = false) Integer companyId,
                                                      @RequestParam(required = false)  Integer reportingEmpId,
                                                      @Parameter(hidden = true) Pageable pageable) {

        KPIResponse response = employeeKeyPerfParamService.getAllEmployeeKPPDetails(empId, empEId,roleId, deptId, desigId, empFirstName, empMiddleName, empLastName, empMobileNo, emailId, statusCd,empTypeId,companyId,reportingEmpId, pageable);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = "/assign-kpp")
    public ResponseEntity<KPIResponse> saveEmployeeKeyPerfomanceParamDetails(@RequestBody EmployeeKeyPerfParamCreateRequest keyPerfParamCreateRequest) {
        KPIResponse response = employeeKeyPerfParamService.saveEmployeeKeyPerfParamDetails(keyPerfParamCreateRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping(value = "/assign-kpp")
    public ResponseEntity<KPIResponse> deleteEmployeeKeyPerfomanceParamDetails(@RequestParam(required = false) Integer empId,
                                                                               @RequestParam(required = false) Integer kppId,
                                                                               @RequestParam(required = false) String kppOverallTarget,
                                                                               @RequestParam(required = false) String kppOverallWeightage) {
        KPIResponse response = employeeKeyPerfParamService.deleteEmployeeKeyPerfParamDetails(empId,kppId,kppOverallTarget,kppOverallWeightage);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<KPIResponse> updateEmployeeKeyPerfomanceParamDetails(@RequestBody EmpKPPMasterUpdateRequest empKPPMasterUpdateRequest) {
        System.out.println("empKPPMasterUpdateRequest : "+empKPPMasterUpdateRequest);
        KPIResponse response = employeeKeyPerfParamService.updateEmployeeKeyPerfParamDetails(empKPPMasterUpdateRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    //load kpp details as per role , dept and designation of employee
    @GetMapping(value = "/kpp")
    public ResponseEntity<List<KPPResponse>> findAllKeyPerfomanceParam(@RequestParam(required = false) Integer roleId,
                                                                       @RequestParam(required = false) Integer deptId,
                                                                       @RequestParam(required = false) Integer desigId,
                                                                       @RequestParam(required = false) StatusCdEnum statusCdEnum
    ) {
        List<KPPResponse> response = employeeKeyPerfParamService.getKeyPerfomanceParameter(roleId, deptId, desigId, statusCdEnum.getSearchType());
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    //for region save
    @GetMapping(value = "/dd-regions-employee")
    public ResponseEntity<List<RegionDDResponse>> getDDRegionFromEmployee() {
        List<RegionDDResponse>   response = employeeKeyPerfParamService.getDDRegionFromEmployee();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/dd-sites-employee")
    public ResponseEntity<List<SiteDDResponse>> getDDSitesFromEmployee(@RequestParam(required = false) Integer regionId) {
        List<SiteDDResponse>   response = employeeKeyPerfParamService.getDDSitesFromEmployee(regionId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/dd-company-employee")
    public ResponseEntity<List<CompanyDDResponse>> getDDCompanyFromEmployee(@RequestParam(required = false) Integer regionId,
                                                                          @RequestParam(required = false) Integer siteId) {
        List<CompanyDDResponse>  response = employeeKeyPerfParamService.getDDCompanyFromEmployee(regionId, siteId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/dd-roles-employee")
    public ResponseEntity<List<RoleDDResponse>> getDDRolesFromEmployee(@RequestParam(required = false) Integer regionId,
                                                                       @RequestParam(required = false) Integer siteId,
                                                                     @RequestParam(required = false) Integer companyId) {
        List<RoleDDResponse>  response = employeeKeyPerfParamService.getDDRolesFromEmployee(regionId, siteId, companyId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/dd-dept-employee")
    public ResponseEntity<List<DepartmentDDResponse>> getDDDeptFromEmployee(@RequestParam(required = false) Integer regionId,
                                                                     @RequestParam(required = false) Integer siteId,
                                                                     @RequestParam(required = false) Integer companyId,
                                                                      @RequestParam(required = false) Integer roleId) {
        List<DepartmentDDResponse>  response = employeeKeyPerfParamService.getDDDeptFromEmployee(regionId, siteId, companyId, roleId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @GetMapping(value = "/dd-desig-employee")
    public ResponseEntity<List<DesignationDDResponse>> getDDDeptFromEmployee(@RequestParam(required = false) Integer regionId,
                                                                             @RequestParam(required = false) Integer siteId,
                                                                             @RequestParam(required = false) Integer companyId,
                                                                             @RequestParam(required = false) Integer roleId,
                                                                             @RequestParam(required = false) Integer deptId) {
        List<DesignationDDResponse>  response = employeeKeyPerfParamService.getDDDesigFromEmployee(regionId, siteId, companyId, roleId, deptId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //view employee assign kpp on AssignEmployeeKppComponent
    @GetMapping(value = "/assign-employee-kpp-search")
    @PageableAsQueryParam
    public ResponseEntity<KPIResponse> assignEmployeeKppSearch(@RequestParam(required = false) Integer empId,
                                                               @Parameter(hidden = true) Pageable pageable) {
        KPIResponse response = employeeKeyPerfParamService.assignEmployeeKppSearch(empId,pageable);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @PostMapping(value = "/assign-employee-kpp-advance-search")
    @PageableAsQueryParam
    public ResponseEntity<KPIResponse> assignEmployeeKppAdvanceSearch(@RequestParam(required = false) Integer empId,
                                                                      @RequestBody KPPAdvanceSearchRequest kppAdvanceSearchRequest, @Parameter(hidden = true) Pageable pageable) {
        KppAdvanceSearchModel employeeAdvSearchModel = KppAdvanceSearchModel.builder()
                .kppObjectiveNo(kppAdvanceSearchRequest.getKppObjectiveNo())
                .kppObjective(kppAdvanceSearchRequest.getKppObjective())
                .kppPerformanceIndica(kppAdvanceSearchRequest.getKppPerformanceIndica())

                .pageable(pageable)
                .build();

        KPIResponse response = employeeKeyPerfParamService.assignEmployeeKppAdvanceSearch(empId,employeeAdvSearchModel, pageable);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }


    //view employee assign kpp on AssignEmployeeKppComponent
    @GetMapping(value = "/assign-employee-kpp-view")
    @PageableAsQueryParam
    public ResponseEntity<KPIResponse> viewEmployeeKpp(@RequestParam(required = false) Integer empId,
                                                       @RequestParam(required = false) Integer roleId,
                                                       @RequestParam(required = false) Integer deptId,
                                                       @RequestParam(required = false) Integer desigId,
                                                       @Parameter(hidden = true) Pageable pageable) {
        KPIResponse response = employeeKeyPerfParamService.viewEmployeeKpp(empId,roleId, deptId, desigId,pageable);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }


}
