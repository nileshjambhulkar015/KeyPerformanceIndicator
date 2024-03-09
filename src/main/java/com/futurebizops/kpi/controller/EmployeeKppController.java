package com.futurebizops.kpi.controller;

import com.futurebizops.kpi.enums.StatusCdEnum;
import com.futurebizops.kpi.request.EmpKPPMasterUpdateRequest;
import com.futurebizops.kpi.request.EmployeeKeyPerfParamCreateRequest;
import com.futurebizops.kpi.response.KPIResponse;
import com.futurebizops.kpi.response.KPPResponse;
import com.futurebizops.kpi.response.dropdown.CompanyDDResponse;
import com.futurebizops.kpi.response.dropdown.RegionDDResponse;
import com.futurebizops.kpi.response.dropdown.SiteDDResponse;
import com.futurebizops.kpi.service.EmployeeKeyPerfParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
    public ResponseEntity<List<RegionDDResponse>> getDDRegionFromCompany() {
        List<RegionDDResponse>   response = employeeKeyPerfParamService.getDDRegionFromCompany();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/dd-sites-employee")
    public ResponseEntity<List<SiteDDResponse>> getDDSitesFromComany(@RequestParam(required = false) Integer regionId) {
        List<SiteDDResponse>   response = employeeKeyPerfParamService.getDDSitesFromComany(regionId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/dd-company-employee")
    public ResponseEntity<List<CompanyDDResponse>> getDDCompanyFromComany(@RequestParam(required = false) Integer regionId,
                                                                          @RequestParam(required = false) Integer siteId) {
        List<CompanyDDResponse>  response = employeeKeyPerfParamService.getDDCompanyFromComany(regionId, siteId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
