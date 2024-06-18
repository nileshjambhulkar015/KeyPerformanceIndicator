package com.futurebizops.kpi.controller;

import com.futurebizops.kpi.model.EmployeeAdvSearchModel;
import com.futurebizops.kpi.model.KppAdvanceSearchModel;
import com.futurebizops.kpi.request.KeyPerfParamCreateRequest;
import com.futurebizops.kpi.request.KeyPerfParamUpdateRequest;
import com.futurebizops.kpi.request.advsearch.EmployeeAdvSearchRequest;
import com.futurebizops.kpi.request.advsearch.KPPAdvanceSearchRequest;
import com.futurebizops.kpi.response.DepartmentReponse;
import com.futurebizops.kpi.response.KPIResponse;
import com.futurebizops.kpi.response.KPPResponse;
import com.futurebizops.kpi.response.dropdown.DepartmentDDResponse;
import com.futurebizops.kpi.service.KeyPerfParameterService;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/key-perform-parameter")
@Slf4j
public class KeyPerfParameterController {

    @Autowired
    private KeyPerfParameterService keyPerfParameterService;

    @PostMapping
    public ResponseEntity<KPIResponse> saveKeyPerfomanceParamDetails(@RequestBody KeyPerfParamCreateRequest keyPerfParamCreateRequest) {
        KPIResponse response = keyPerfParameterService.saveKeyPerfomanceParameter(keyPerfParamCreateRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<KPIResponse> updateKeyPerfomanceParamDetails(@RequestBody KeyPerfParamUpdateRequest keyPerfParamUpdateRequest) {
        KPIResponse response = keyPerfParameterService.updateKeyPerfomanceParameter(keyPerfParamUpdateRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/search")
    @PageableAsQueryParam
    public ResponseEntity<KPIResponse> findKeyPerfomanceParam(@RequestParam(required = false) Integer kppId,
                                                              @RequestParam(required = false) String kppObjectiveNo,
                                                              @RequestParam(required = false) String kppObjective,
                                                              @RequestParam(required = false) String statusCd,
                                                              @Parameter(hidden = true) Pageable pageable) {
        log.info("reqiuest for Employee KPP search");
        KPIResponse response = keyPerfParameterService.findKeyPerfomanceParameterDetails(kppId, kppObjectiveNo,kppObjective, statusCd, pageable);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @PostMapping(value = "/adv-search")
    @PageableAsQueryParam
    public ResponseEntity<KPIResponse>  empAdvanceSearch(@RequestBody KPPAdvanceSearchRequest kppAdvanceSearchRequest, @Parameter(hidden = true) Pageable pageable) {
        log.info("reqiuest for Employee search");
        KppAdvanceSearchModel employeeAdvSearchModel = KppAdvanceSearchModel.builder()
                .kppObjectiveNo(kppAdvanceSearchRequest.getKppObjectiveNo())
                .kppObjective(kppAdvanceSearchRequest.getKppObjective())
                .kppPerformanceIndi(kppAdvanceSearchRequest.getKppPerformanceIndi())
                .kppTargetPeriod(kppAdvanceSearchRequest.getKppTargetPeriod())
                .pageable(pageable)
                .build();

        log.info("reqiuest for Employee search");
       // KPIResponse response = employeeService.getAllEmployeeAdvanceSearch(employeeAdvSearchModel.getRoleId(), employeeAdvSearchModel.getDeptId(),employeeAdvSearchModel.getDesigId(),employeeAdvSearchModel.getRegionId(),employeeAdvSearchModel.getSiteId(),employeeAdvSearchModel.getCompanyId(),employeeAdvSearchModel.getEmpTypeId(), employeeAdvSearchModel.getPageable());
        //return new ResponseEntity<>(response, HttpStatus.OK);

        //KPIResponse response = employeeService.getAllEmployeeDetails(empId, roleId, deptId, desigId, empFirstName, empMiddleName, empLastName, empMobileNo, emailId, statusCd, pageable);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }



    @GetMapping(value = "/kppId")
    public ResponseEntity<KPPResponse> findKeyPerfomanceParamById(@RequestParam(required = false) Integer kppId) {
        KPPResponse response = keyPerfParameterService.findKeyPerfomanceParameterDetailById(kppId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //upload excel
        @PostMapping (value = "/upload-kpp")
    public void uploadKppExcelFile(@RequestParam("file") MultipartFile file) throws IOException {
        keyPerfParameterService.uploadKppExcelFile(file);
    }


}
