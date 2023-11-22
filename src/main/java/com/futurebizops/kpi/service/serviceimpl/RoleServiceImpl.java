package com.futurebizops.kpi.service.serviceimpl;

import com.futurebizops.kpi.constants.KPIConstants;
import com.futurebizops.kpi.entity.RoleAudit;
import com.futurebizops.kpi.entity.RoleEntity;
import com.futurebizops.kpi.enums.RoleSearchEnum;
import com.futurebizops.kpi.enums.StatusCdEnum;
import com.futurebizops.kpi.exception.KPIException;
import com.futurebizops.kpi.repository.RoleAuditRepo;
import com.futurebizops.kpi.repository.RoleRepo;
import com.futurebizops.kpi.request.RoleCreateRequest;
import com.futurebizops.kpi.request.RoleUpdateRequest;
import com.futurebizops.kpi.response.KPIResponse;
import com.futurebizops.kpi.response.RoleResponse;
import com.futurebizops.kpi.service.RoleService;
import com.futurebizops.kpi.utils.KPIUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private RoleAuditRepo roleAuditRepo;

    @Override
    public KPIResponse saveRole(RoleCreateRequest roleCreateRequest) {
        Optional<RoleEntity> optionalRoleEntity = roleRepo.findByRoleNameEqualsIgnoreCase(roleCreateRequest.getRoleName());
        if(optionalRoleEntity.isPresent()){
            log.error("Inside RoleServiceImpl >> saveRole()");
            throw new KPIException("RoleServiceImpl Class", false, "Role name already exist");
        }

        RoleEntity roleEntity = convertRoleCreateRequestToEntity(roleCreateRequest);
        try {
            roleRepo.save(roleEntity);
            RoleAudit roleAudit = new RoleAudit(roleEntity);
            roleAuditRepo.save(roleAudit);
            return KPIResponse.builder()
                    .isSuccess(true)
                    .responseMessage(KPIConstants.RECORD_SUCCESS)
                    .build();
        } catch (Exception ex) {
            log.error("Inside RoleServiceImpl >> saveRole()");
            throw new KPIException("RoleServiceImpl", false, ex.getMessage());
        }
    }

    @Override
    public KPIResponse updateRole(RoleUpdateRequest roleUpdateRequest) {
        RoleEntity roleEntity = convertRoleUpdateRequestToEntity(roleUpdateRequest);
        try {
            roleRepo.save(roleEntity);
            RoleAudit partAudit = new RoleAudit(roleEntity);
            roleAuditRepo.save(partAudit);
            return KPIResponse.builder()
                    .isSuccess(true)
                    .responseMessage(KPIConstants.RECORD_UPDATE)
                    .build();
        } catch (Exception ex) {
            log.error("Inside RoleServiceImpl >> updateRole()");
            throw new KPIException("RoleServiceImpl", false, ex.getMessage());
        }
    }

    @Override
    public KPIResponse findRoleDetails(RoleSearchEnum searchEnum, String searchString, StatusCdEnum statusCdEnum, Pageable requestPageable, String sortParam, String pageDirection) {
        Page<RoleEntity> roleEntities = null;
        Pageable pageable = KPIUtils.sort(requestPageable, sortParam, pageDirection);
        switch (searchEnum.getSearchType()) {
            case "BY_ID":
                roleEntities = roleRepo.findByRoleIdAndStatusCd(Integer.parseInt(searchString), statusCdEnum.getSearchType(), pageable);
                break;
            case "BY_NAME":
                roleEntities = roleRepo.findByRoleNameContainingIgnoreCaseAndStatusCd(searchString, statusCdEnum.getSearchType(), pageable);
                break;
            case "BY_STATUS":
                roleEntities = roleRepo.findByStatusCd(statusCdEnum.getSearchType(), pageable);
                break;
            case "ALL":
            default:
                roleEntities = roleRepo.findAll(pageable);
        }
        return KPIResponse.builder()
                .isSuccess(true)
                .responseData(roleEntities)
                .responseMessage(KPIConstants.RECORD_FETCH)
                .build();
    }

    @Override
    public List<RoleResponse> findAllRolesDetails() {
        List<RoleEntity> roleEntities =  roleRepo.findAllRolesDetails();
        List<RoleResponse> roleResponses = new ArrayList<>();
        RoleResponse roleResponse = null;
        for(RoleEntity departmentEntity : roleEntities){
            roleResponse = new RoleResponse();

            roleResponse.setRoleId(departmentEntity.getRoleId());
            roleResponse.setRoleName(departmentEntity.getRoleName());
            roleResponse.setStatusCd(departmentEntity.getStatusCd());
            roleResponses.add(roleResponse);
        }
        return roleResponses;
    }

    @Override
    public RoleResponse findAllRoleById(Integer roleId) {
        Optional<RoleEntity> optionalRoleEntity = roleRepo.findById(roleId);
        if(optionalRoleEntity.isPresent()){
            return RoleResponse.builder()
                    .roleId(optionalRoleEntity.get().getRoleId())
                    .roleName(optionalRoleEntity.get().getRoleName())
                    .remark(optionalRoleEntity.get().getRemark())
                    .statusCd(optionalRoleEntity.get().getStatusCd())
                    .build();
        }
        return null;
    }

    //for roles from department table
    @Override
    public List<RoleResponse> getAllRoleFromDeptId() {
        List<Object[]> roleData = roleRepo.getAllRoleFromDept();
        return roleData.stream().map(RoleResponse::new).collect(Collectors.toList());
    }
    @Override
    public List<RoleResponse> findAllRoleForKpp() {
        List<Object[]> roleData = roleRepo.getAllRoleFromDesignation();
        return roleData.stream().map(RoleResponse::new).collect(Collectors.toList());
    }

    private RoleEntity convertRoleCreateRequestToEntity(RoleCreateRequest roleCreateRequest) {
        return RoleEntity.roleEntityBuilder()
                .roleName(roleCreateRequest.getRoleName())
                .remark(roleCreateRequest.getRemark())
                .statusCd(roleCreateRequest.getStatusCd())
                .createdUserId(roleCreateRequest.getEmployeeId())
                .build();
    }

    private RoleEntity convertRoleUpdateRequestToEntity(RoleUpdateRequest roleUpdateRequest) {
        return RoleEntity.roleEntityBuilder()
                .roleId(roleUpdateRequest.getRoleId())
                .roleName(roleUpdateRequest.getRoleName())
                .remark(roleUpdateRequest.getRemark())
                .statusCd(roleUpdateRequest.getStatusCd())
                .createdUserId(roleUpdateRequest.getEmployeeId())
                .build();
    }


}
