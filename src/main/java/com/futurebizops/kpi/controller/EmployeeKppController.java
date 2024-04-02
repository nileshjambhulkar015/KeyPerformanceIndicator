package com.futurebizops.kpi.controller;

import com.futurebizops.kpi.enums.StatusCdEnum;
import com.futurebizops.kpi.request.EmpKPPMasterUpdateRequest;
import com.futurebizops.kpi.request.EmployeeKeyPerfParamCreateRequest;
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

    @PostMapping(value = "/assign-kpp")
    public ResponseEntity<KPIResponse> saveEmployeeKeyPerfomanceParamDetails(@RequestBody EmployeeKeyPerfParamCreateRequest keyPerfParamCreateRequest) {
        KPIResponse response = employeeKeyPerfParamService.saveEmployeeKeyPerfParamDetails(keyPerfParamCreateRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping(value = "/assign-kpp")
    public ResponseEntity<KPIResponse> deleteEmployeeKeyPerfomanceParamDetails(@RequestParam(required = false) Integer empId,
                                                                               @RequestParam(required = false) Integer kppId) {
        KPIResponse response = employeeKeyPerfParamService.deleteEmployeeKeyPerfParamDetails(empId,kppId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<KPIResponse> updateEmployeeKeyPerfomanceParamDetails(@RequestBody EmpKPPMasterUpdateRequest empKPPMasterUpdateRequest) {
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
                                                               @RequestParam(required = false) Integer roleId,
                                                               @RequestParam(required = false) Integer deptId,
                                                               @RequestParam(required = false) Integer desigId,
                                                               @Parameter(hidden = true) Pageable pageable) {
        KPIResponse response = employeeKeyPerfParamService.assignEmployeeKppSearch(empId,roleId, deptId, desigId,pageable);
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
