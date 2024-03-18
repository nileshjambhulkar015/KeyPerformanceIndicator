package com.futurebizops.kpi.controller;

import com.futurebizops.kpi.enums.PageDirection;
import com.futurebizops.kpi.enums.RoleSearchEnum;
import com.futurebizops.kpi.enums.StatusCdEnum;
import com.futurebizops.kpi.request.RoleCreateRequest;
import com.futurebizops.kpi.request.RoleUpdateRequest;
import com.futurebizops.kpi.response.KPIResponse;
import com.futurebizops.kpi.service.RoleService;
import com.futurebizops.kpi.utils.KPIUtils;
import io.swagger.v3.oas.annotations.Parameter;
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

@CrossOrigin
@RestController
@RequestMapping(value = "/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @PostMapping
    public ResponseEntity<KPIResponse> saveRoleDetails(@RequestBody RoleCreateRequest roleCreateRequest) {
        KPIResponse response = roleService.saveRole(roleCreateRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<KPIResponse> updateRoleDetails(@RequestBody RoleUpdateRequest roleUpdateRequest) {
        KPIResponse response = roleService.updateRole(roleUpdateRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/search")
    @PageableAsQueryParam
    public ResponseEntity<KPIResponse> findRoleDetails(@RequestParam(required = false) RoleSearchEnum searchEnum,
                                                      @RequestParam(required = false) String searchString,
                                                      @RequestParam(required = false) StatusCdEnum statusCdEnum,
                                                      @Parameter(hidden = true) Pageable pageable,
                                                      @Parameter(hidden = true) PageDirection pageDirection,
                                                      @Parameter(hidden = true) String sortParam) {
        KPIResponse response = roleService.findRoleDetails(searchEnum, searchString, statusCdEnum, pageable, sortParam, KPIUtils.getDirection(pageDirection));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Object> findAllRoleDetails() {
        return new ResponseEntity<>(roleService.findAllRolesDetails(), HttpStatus.OK);
    }

    //for Department
    @GetMapping(value = "/{roleId}")
    public ResponseEntity<Object> findAllRoleById(@PathVariable Integer roleId) {
        return new ResponseEntity<>(roleService.findAllRoleById(roleId), HttpStatus.OK);
    }

    //for Role which is present in department table
    @GetMapping(value = "department/role")
    public ResponseEntity<Object> findAllRole() {
        return new ResponseEntity<>(roleService.getAllRoleFromDeptId(), HttpStatus.OK);
    }

    //for Key perform indicator
    @GetMapping(value = "/designation/roles")
    public ResponseEntity<Object> findAllRoleForKpp() {
        return new ResponseEntity<>(roleService.findAllRoleForKpp(), HttpStatus.OK);
    }
}
