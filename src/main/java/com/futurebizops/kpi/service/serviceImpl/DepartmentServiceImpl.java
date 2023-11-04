package com.futurebizops.kpi.service.serviceImpl;

import com.futurebizops.kpi.constants.KPIConstants;
import com.futurebizops.kpi.entity.DepartmentAudit;
import com.futurebizops.kpi.entity.DepartmentEntity;
import com.futurebizops.kpi.enums.DepartmentSearchEnum;
import com.futurebizops.kpi.enums.StatusCdEnum;
import com.futurebizops.kpi.exception.KPIException;
import com.futurebizops.kpi.repository.DepartmentAuditRepo;
import com.futurebizops.kpi.repository.DepartmentRepo;
import com.futurebizops.kpi.request.DepartmentCreateRequest;
import com.futurebizops.kpi.request.DepartmentUpdateRequest;
import com.futurebizops.kpi.response.DepartmentReponse;
import com.futurebizops.kpi.response.DesignationReponse;
import com.futurebizops.kpi.response.KPIResponse;
import com.futurebizops.kpi.response.RoleResponse;
import com.futurebizops.kpi.service.DepartmentService;

import com.futurebizops.kpi.utils.KPIUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentRepo departmentRepo;

    @Autowired
    private DepartmentAuditRepo departmentAuditRepo;

    @Override
    public KPIResponse saveDepartment(DepartmentCreateRequest departmentCreateRequest) {

        Optional<DepartmentEntity> optionalDepartmentEntity = departmentRepo.findByDeptNameEqualsIgnoreCase(departmentCreateRequest.getDeptName());
        if(optionalDepartmentEntity.isPresent()){
            log.error("Inside DepartmentServiceImpl >> saveDepartment()");
            throw new KPIException("DepartmentServiceImpl", false, "Department name already exist");
        }

        DepartmentEntity departmentEntity = convertDepartmentCreateRequestToEntity(departmentCreateRequest);
        try {
            departmentRepo.save(departmentEntity);
            DepartmentAudit partAudit = new DepartmentAudit(departmentEntity);
            departmentAuditRepo.save(partAudit);
            return KPIResponse.builder()
                    .isSuccess(true)
                    .responseMessage(KPIConstants.RECORD_SUCCESS)
                    .build();
        } catch (Exception ex) {
            log.error("Inside DepartmentServiceImpl >> saveDepartment()");
            throw new KPIException("DepartmentServiceImpl", false, ex.getMessage());
        }
    }

    @Override
    public KPIResponse updateDepartment(DepartmentUpdateRequest departmentUpdateRequest) {
        DepartmentEntity departmentEntity = convertDepartmentUpdateRequestToEntity(departmentUpdateRequest);
        try {
            departmentRepo.save(departmentEntity);
            DepartmentAudit departmentAudit = new DepartmentAudit(departmentEntity);
            departmentAuditRepo.save(departmentAudit);
            return KPIResponse.builder()
                    .isSuccess(true)
                    .responseMessage(KPIConstants.RECORD_UPDATE)
                    .build();
        } catch (Exception ex) {
            log.error("Inside DepartmentServiceImpl >> updateDepartment()");
            throw new KPIException("DepartmentServiceImpl", false, ex.getMessage());
        }
    }

    @Override
    public KPIResponse findDepartmentDetails(Integer roleId, Integer deptId, String deptName, String statusCd, Pageable requestPageable) {
        String sortName = null;
        String sortDirection = null;
        Integer pageSize = requestPageable.getPageSize();
        Integer pageOffset = (int) requestPageable.getOffset();
        // pageable = KPIUtils.sort(requestPageable, sortParam, pageDirection);
        Optional<Sort.Order> order = requestPageable.getSort().get().findFirst();
        if (order.isPresent()) {
            sortName = order.get().getProperty();  //order by this field
            sortDirection = order.get().getDirection().toString(); // Sort ASC or DESC
        }

        Integer totalCount = departmentRepo.getDepartmentCount(roleId, deptId, deptName, statusCd);
        List<Object[]> departmentData = departmentRepo.getDepartmentDetail(roleId, deptId, deptName, statusCd, sortName, pageSize, pageOffset);

        List<DepartmentReponse> designationReponses = departmentData.stream().map(DepartmentReponse::new).collect(Collectors.toList());

        return KPIResponse.builder()
                .isSuccess(true)
                .responseData(new PageImpl(designationReponses, requestPageable, totalCount))
                .responseMessage(KPIConstants.RECORD_FETCH)
                .build();
    }



    @Override
    public List<DepartmentReponse> findAllDepartmentDetails() {
        List<DepartmentEntity> departmentEntities =  departmentRepo.findAllDepartmentDetailsForEmployee();
        List<DepartmentReponse> departmentReponses = new ArrayList<>();
        DepartmentReponse departmentReponse = null;
        for(DepartmentEntity departmentEntity : departmentEntities){
            departmentReponse = new DepartmentReponse();
            departmentReponse.setDeptId(departmentEntity.getDeptId());
            departmentReponse.setDeptName(departmentEntity.getDeptName());
            departmentReponse.setRemark(departmentEntity.getRemark());
            departmentReponse.setStatusCd(departmentEntity.getStatusCd());
            departmentReponses.add(departmentReponse);
        }
        return departmentReponses;
    }

    @Override
    public DepartmentReponse findAllDepartmentById(Integer deptId) {
        List<Object[]> designationData = departmentRepo.getDepartmentByIdDetail(deptId);
        List<DepartmentReponse> designationReponses = designationData.stream().map(DepartmentReponse::new).collect(Collectors.toList());
        return  designationReponses.get(0);
    }

    @Override
    public List<RoleResponse> getAllRoleFromDeptId() {
        List<Object[]> roleData = departmentRepo.getAllRoleFromDept();
        List<RoleResponse> roleResponses = roleData.stream().map(RoleResponse::new).collect(Collectors.toList());
        return  roleResponses;
    }

    @Override
    public List<DepartmentReponse> getAllDepartmentByRoleId(Integer roleId) {
        List<Object[]> deptData = departmentRepo.getAllDepartByRoleId(roleId);
        List<DepartmentReponse> departmentReponses = deptData.stream().map(DepartmentReponse::new).collect(Collectors.toList());
        return  departmentReponses;
    }

    private DepartmentEntity convertDepartmentCreateRequestToEntity(DepartmentCreateRequest departmentCreateRequest) {
        return DepartmentEntity.departmentEntityBuilder()
                .roleId(departmentCreateRequest.getRoleId())
                .deptName(departmentCreateRequest.getDeptName())
                .remark(departmentCreateRequest.getRemark())
                .statusCd(departmentCreateRequest.getStatusCd())
                .createdUserId(departmentCreateRequest.getEmployeeId())
                .build();
    }

    private DepartmentEntity convertDepartmentUpdateRequestToEntity(DepartmentUpdateRequest departmentUpdateRequest) {
        return DepartmentEntity.departmentEntityBuilder()
                .roleId(departmentUpdateRequest.getRoleId())
                .deptId(departmentUpdateRequest.getDeptId())
                .deptName(departmentUpdateRequest.getDeptName())
                .remark(departmentUpdateRequest.getRemark())
                .statusCd(departmentUpdateRequest.getStatusCd())
                .createdUserId(departmentUpdateRequest.getEmployeeId())
                .build();
    }
}
