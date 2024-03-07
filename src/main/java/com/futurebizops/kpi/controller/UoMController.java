package com.futurebizops.kpi.controller;

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
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
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
@RequestMapping(value = "/uom")
public class UoMController {

    @Autowired
    UoMService uoMService;

    @GetMapping(value = "/search")
    @PageableAsQueryParam
    public ResponseEntity<KPIResponse> findDepartmentDetails(@RequestParam(required = false) Integer uomId,

                                                             @RequestParam(required = false) String uomName,
                                                             @RequestParam(required = false) String statusCd,
                                                             @Parameter(hidden = true) Pageable pageable) {
        KPIResponse response = uoMService.findUoMDetails(uomId, uomName, statusCd, pageable);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @PostMapping
    public ResponseEntity<KPIResponse> saveUoMDetails(@RequestBody UoMCreateRequest uoMCreateRequest) {
        KPIResponse response = uoMService.saveUoM(uoMCreateRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<KPIResponse> updateUoMDetails(@RequestBody UoMUpdateRequest uoMUpdateRequest) {
        KPIResponse response = uoMService.updateUoM(uoMUpdateRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<KPIResponse> getUoMDetails(Integer uomId) {
        KPIResponse response = uoMService.findUoMDetails(uomId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/all-uom")
    public ResponseEntity<List<UoMEntity>> getAllUoMDetails() {
        List<UoMEntity> uoMEntities = uoMService.findAllUoMDetails();
        return new ResponseEntity<>(uoMEntities, HttpStatus.OK);
    }
}
