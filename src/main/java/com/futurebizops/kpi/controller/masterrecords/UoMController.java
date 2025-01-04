package com.futurebizops.kpi.controller.masterrecords;

import com.futurebizops.kpi.entity.UoMEntity;
import com.futurebizops.kpi.enums.PageDirection;
import com.futurebizops.kpi.enums.RoleSearchEnum;
import com.futurebizops.kpi.enums.StatusCdEnum;
import com.futurebizops.kpi.request.RoleCreateRequest;
import com.futurebizops.kpi.request.RoleUpdateRequest;
import com.futurebizops.kpi.request.UoMCreateRequest;
import com.futurebizops.kpi.request.UoMUpdateRequest;
import com.futurebizops.kpi.response.KPIResponse;
import com.futurebizops.kpi.service.UoMService;
import com.futurebizops.kpi.utils.KPIUtils;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/uom")
@Slf4j
public class UoMController {

    @Autowired
    UoMService uoMService;

    @GetMapping(value = "/search")
    @PageableAsQueryParam
    public ResponseEntity<KPIResponse> findUoMDetails(@RequestParam(required = false) Integer uomId,
                                                             @RequestParam(required = false) String uomName,
                                                             @RequestParam(required = false) String statusCd,
                                                             @Parameter(hidden = true) Pageable pageable) {
        log.info("Inside SiteController >> findUoMDetails() uomId : {},uomName : {}", uomId, uomName);
        KPIResponse response = uoMService.findUoMDetails(uomId, uomName, statusCd, pageable);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @DeleteMapping
    public ResponseEntity<KPIResponse> deleteUOMDetails(@RequestParam(required = false) Integer uomId) {
        log.info("Inside SiteController >> deleteUOMDetails() uomId : {}", uomId);
        KPIResponse response = uoMService.deleteUOMDetails(uomId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<KPIResponse> saveUoM(@RequestBody UoMCreateRequest uoMCreateRequest) {
        log.info("Inside SiteController >> saveUoM() uoMCreateRequest : {}", uoMCreateRequest);
        KPIResponse response = uoMService.saveUoM(uoMCreateRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<KPIResponse> updateUoM(@RequestBody UoMUpdateRequest uoMUpdateRequest) {
        log.info("Inside SiteController >> updateUoM() uoMUpdateRequest : {}", uoMUpdateRequest);
        KPIResponse response = uoMService.updateUoM(uoMUpdateRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<KPIResponse> findUoMDetails(Integer uomId) {
        log.info("Inside SiteController >> findUoMDetails() uomId : {}", uomId);
        KPIResponse response = uoMService.findUoMDetails(uomId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/all-uom")
    public ResponseEntity<List<UoMEntity>> findAllUoMDetails() {
        log.info("Inside SiteController >> findAllUoMDetails()");
        List<UoMEntity> uoMEntities = uoMService.findAllUoMDetails();
        return new ResponseEntity<>(uoMEntities, HttpStatus.OK);
    }

}
