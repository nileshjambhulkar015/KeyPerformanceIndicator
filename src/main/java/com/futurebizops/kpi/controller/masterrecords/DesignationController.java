package com.futurebizops.kpi.controller.masterrecords;

import com.futurebizops.kpi.request.DesignationCreateRequest;
import com.futurebizops.kpi.request.DesignationUpdateRequest;
import com.futurebizops.kpi.response.DesignationReponse;
import com.futurebizops.kpi.response.KPIResponse;
import com.futurebizops.kpi.service.DesignationService;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
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

@CrossOrigin
@RestController
@RequestMapping(value = "/designation")
@Slf4j
public class DesignationController {

    @Autowired
    private DesignationService designationService;

    @PostMapping
    public ResponseEntity<KPIResponse> saveDesignation(@RequestBody DesignationCreateRequest designationCreateRequest) {
        log.info("Inside DesignationController >> saveDesignation() designationCreateRequest : {}", designationCreateRequest);

        KPIResponse response = designationService.saveDesignation(designationCreateRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<KPIResponse> deleteDesignationDetails(@RequestParam(required = false) Integer desigId) {
        log.info("Inside DesignationController >> deleteDesignationDetails() desigId : {}", desigId);
        KPIResponse response = designationService.deleteDesignationDetails(desigId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<KPIResponse> updateDesignationDetails(@RequestBody DesignationUpdateRequest designationUpdateRequest) {
        log.info("Inside DesignationController >> updateDesignationDetails() designationUpdateRequest : {}", designationUpdateRequest);
        KPIResponse response = designationService.updateDesignation(designationUpdateRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/search")
    @PageableAsQueryParam
    public ResponseEntity<KPIResponse> findDesignationDetails(@RequestParam(required = false) Integer deptId,
                                                              @RequestParam(required = false) String desigName,
                                                              @RequestParam(required = false) String statusCd,
                                                              @Parameter(hidden = true) Pageable pageable) {
        log.info("Inside DesignationController >> findDesignationDetails() deptId : {}, desigName : {}", deptId, desigName);
        KPIResponse response = designationService.findDesignationDetails(deptId, desigName, statusCd, pageable);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @GetMapping(value = "/by-desig-id")
    public ResponseEntity<DesignationReponse> findDesignationById(@RequestParam(required = false) Integer desigId) {
        log.info("Inside DesignationController >> findDesignationById() desigId : {}", desigId);
        DesignationReponse response = designationService.findDesignationById(desigId);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    // for dropdown list
    @GetMapping(value = "/by-desig-dept")
    public ResponseEntity<Object> findAllDesignationByDeptId(@RequestParam(required = false) Integer deptId) {
        log.info("Inside DesignationController >> findAllDesignationByDeptId() deptId : {}", deptId);
        return new ResponseEntity<>(designationService.findAllDesignationByDeptId(deptId), HttpStatus.OK);
    }

    @GetMapping(value = "/department")
    public ResponseEntity<Object> getAllDepartmentFromDesig(@RequestParam(required = false) Integer deptId) {
        log.info("Inside DesignationController >> getAllDepartmentFromDesig() deptId : {}", deptId);
        return new ResponseEntity<>(designationService.getAllDepartmentFromDesig(deptId), HttpStatus.OK);
    }

    @PostMapping (value = "/upload-designation")
    public void uploadDesigExcelFile(@RequestParam("file") MultipartFile file) throws IOException {
        log.info("Inside DesignationController >> uploadDesigExcelFile()");
        designationService.uploadDesigExcelFile(file);

    }
}
