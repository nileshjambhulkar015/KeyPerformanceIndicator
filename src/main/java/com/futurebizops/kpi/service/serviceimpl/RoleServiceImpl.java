package com.futurebizops.kpi.service.serviceimpl;

import com.futurebizops.kpi.constants.KPIConstants;
import com.futurebizops.kpi.entity.RegionEntity;
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
import com.futurebizops.kpi.response.RegionResponse;
import com.futurebizops.kpi.response.RoleResponse;
import com.futurebizops.kpi.response.dropdown.RoleDDResponse;
import com.futurebizops.kpi.service.RoleService;
import com.futurebizops.kpi.utils.KPIUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.retry.annotation.Retryable;
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
    @Retryable(include = {KPIException.class}, maxAttemptsExpression = "${retry-max-attempts}")
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
    @Retryable(include = {KPIException.class}, maxAttemptsExpression = "${retry-max-attempts}")
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
    @Retryable(include = {KPIException.class}, maxAttemptsExpression = "${retry-max-attempts}")
    public KPIResponse findRoleDetails(Integer roleId, String roleName, Pageable requestPageable, String sortParam, String pageDirection) {
        String sortName = null;
        // String sortDirection = null;
        Integer pageSize = requestPageable.getPageSize();
        Integer pageOffset = (int) requestPageable.getOffset();

        Optional<Sort.Order> order = requestPageable.getSort().get().findFirst();
        if (order.isPresent()) {
            sortName = order.get().getProperty();  //order by this field
            // sortDirection = order.get().getDirection().toString(); // Sort ASC or DESC
        }

        Integer totalCount = roleRepo.getRoleDetailsCount(roleId, roleName);
        List<Object[]> regionData = roleRepo.getRoleDetails(roleId, roleName, sortName, pageSize, pageOffset);

        List<RoleResponse> roleResponses = regionData.stream().map(RoleResponse::new).collect(Collectors.toList());

        return KPIResponse.builder()
                .isSuccess(true)
                .responseData(new PageImpl(roleResponses, requestPageable, totalCount))
                .responseMessage(KPIConstants.RECORD_FETCH)
                .build();
    }

    @Override
    @Retryable(include = {KPIException.class}, maxAttemptsExpression = "${retry-max-attempts}")
    public List<RoleResponse> findAllRolesDetails() {
        List<RoleEntity> roleEntities =  roleRepo.findAllRolesDetails();
        List<RoleResponse> roleResponses = new ArrayList<>();
        RoleResponse roleResponse = null;
        for(RoleEntity departmentEntity : roleEntities){
            roleResponse = new RoleResponse();


            roleResponse.setRoleName(departmentEntity.getRoleName());
            //roleResponse.setStatusCd(departmentEntity.getStatusCd());
            roleResponses.add(roleResponse);
        }
        return roleResponses;
    }

    @Override
    @Retryable(include = {KPIException.class}, maxAttemptsExpression = "${retry-max-attempts}")
    public RoleResponse findAllRoleById(Integer roleId) {
        Optional<RoleEntity> optionalRoleEntity = roleRepo.findById(roleId);
        if(optionalRoleEntity.isPresent()){
            return RoleResponse.builder()

                    .roleName(optionalRoleEntity.get().getRoleName())
                    .remark(optionalRoleEntity.get().getRemark())
                 //   .statusCd(optionalRoleEntity.get().getStatusCd())
                    .build();
        }
        return null;
    }




    private RoleEntity convertRoleCreateRequestToEntity(RoleCreateRequest roleCreateRequest) {
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setRoleName(roleCreateRequest.getRoleName());
        roleEntity.setRemark(roleCreateRequest.getRemark());
        roleEntity.setStatusCd(roleCreateRequest.getStatusCd());
        roleEntity.setCreatedUserId(roleCreateRequest.getEmployeeId());
        return roleEntity;
    }

    private RoleEntity convertRoleUpdateRequestToEntity(RoleUpdateRequest roleUpdateRequest) {
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setRoleId(roleUpdateRequest.getRoleId());
        roleEntity.setRoleName(roleUpdateRequest.getRoleName());
        roleEntity.setRemark(roleUpdateRequest.getRemark());
        roleEntity.setStatusCd(roleUpdateRequest.getStatusCd());
        roleEntity.setUpdatedUserId(roleUpdateRequest.getEmployeeId());
        return roleEntity;
    }

    @Override
    @Retryable(include = {KPIException.class}, maxAttemptsExpression = "${retry-max-attempts}")
    public List<RoleDDResponse> ddEmployeeRoleExceptGM() {
        List<Object[]> roleData = roleRepo.ddEmployeeRoleExceptGM();
        return roleData.stream().map(RoleDDResponse::new).collect(Collectors.toList());
    }


}
