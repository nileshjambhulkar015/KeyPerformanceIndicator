package com.futurebizops.kpi.controller.masterrecords;

import com.futurebizops.kpi.request.RoleCreateRequest;
import com.futurebizops.kpi.request.RoleUpdateRequest;
import com.futurebizops.kpi.response.KPIResponse;
import com.futurebizops.kpi.response.dropdown.RoleDDResponse;
import com.futurebizops.kpi.service.RoleService;
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

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/roles")
@Slf4j
public class RoleController {

    @Autowired
    private RoleService roleService;

    @PostMapping
    public ResponseEntity<KPIResponse> saveRole(@RequestBody RoleCreateRequest roleCreateRequest) {
        log.info("Inside RoleController >> saveRole() roleCreateRequest : {}", roleCreateRequest);
        KPIResponse response = roleService.saveRole(roleCreateRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<KPIResponse> updateRole(@RequestBody RoleUpdateRequest roleUpdateRequest) {
        log.info("Inside RoleController >> updateRole() roleUpdateRequest : {}", roleUpdateRequest);
        KPIResponse response = roleService.updateRole(roleUpdateRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/search")
    @PageableAsQueryParam
    public ResponseEntity<KPIResponse> findRoleDetails(@RequestParam(required = false) Integer roleId,
                                                       @RequestParam(required = false) String roleName,
                                                       @Parameter(hidden = true) Pageable pageable) {
        log.info("Inside RoleController >> findRoleDetails() roleId : {}, roleName : {}", roleId, roleName);
        KPIResponse response = roleService.findRoleDetails(roleId, roleName, pageable);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Object> findAllRolesDetails() {
        log.info("Inside RoleController >> findAllRolesDetails()");
        return new ResponseEntity<>(roleService.findAllRolesDetails(), HttpStatus.OK);
    }

    //for Department
    @GetMapping(value = "/{roleId}")
    public ResponseEntity<Object> findAllRoleById(@PathVariable Integer roleId) {
        log.info("Inside RoleController >> findAllRoleById() roleId : {}", roleId);
        return new ResponseEntity<>(roleService.findAllRoleById(roleId), HttpStatus.OK);
    }


    //for roles except GM
    @GetMapping(value = "/dd-role-except-gm-role")
    public ResponseEntity<List<RoleDDResponse>> ddEmployeeRoleExceptGM() {
        log.info("Inside RoleController >> ddEmployeeRoleExceptGM()");
        List<RoleDDResponse> response = roleService.ddEmployeeRoleExceptGM();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
