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
import com.futurebizops.kpi.response.KPIResponse;
import com.futurebizops.kpi.service.DepartmentService;

import com.futurebizops.kpi.utils.KPIUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public KPIResponse findDepartmentDetails(DepartmentSearchEnum searchEnum, String searchString, StatusCdEnum statusCdEnum, Pageable requestPageable, String sortParam, String pageDirection) {
        Page<DepartmentEntity> departmentEntities = null;
        Pageable pageable = KPIUtils.sort(requestPageable, sortParam, pageDirection);
        switch (searchEnum.getSearchType()) {
            case "BY_ID":
                departmentEntities = departmentRepo.findByDeptIdAndStatusCd(Integer.parseInt(searchString), statusCdEnum.getSearchType(), pageable);
                break;
            case "BY_NAME":
                departmentEntities = departmentRepo.findByDeptNameStartingWithIgnoreCaseAndStatusCd(searchString, statusCdEnum.getSearchType(), pageable);
                break;
            case "BY_STATUS":
                departmentEntities = departmentRepo.findByStatusCd(statusCdEnum.getSearchType(), pageable);
                break;
            case "ALL":
            default:
                departmentEntities = departmentRepo.findAll(pageable);
        }
        return KPIResponse.builder()
                .isSuccess(true)
                .responseData(departmentEntities)
                .responseMessage(KPIConstants.RECORD_FETCH)
                .build();
    }

    @Override
    public List<DepartmentReponse> findAllDepartmentDetails() {

        List<DepartmentEntity> departmentEntities =  departmentRepo.findAllDepartments();
        List<DepartmentReponse> departmentReponses = new ArrayList<>();
        DepartmentReponse departmentReponse = null;
        for(DepartmentEntity departmentEntity : departmentEntities){
            departmentReponse = new DepartmentReponse();

            departmentReponse.setDeptId(departmentEntity.getDeptId());
            departmentReponse.setDeptName(departmentEntity.getDeptName());
            departmentReponse.setStatusCd(departmentEntity.getStatusCd());
            departmentReponses.add(departmentReponse);
        }
        return departmentReponses;
    }

    @Override
    public List<DepartmentReponse> findAllDepartmentDetailsForEmployee() {
        List<DepartmentEntity> departmentEntities =  departmentRepo.findAllDepartmentDetailsForEmployee();
        List<DepartmentReponse> departmentReponses = new ArrayList<>();
        DepartmentReponse departmentReponse = null;
        for(DepartmentEntity departmentEntity : departmentEntities){
            departmentReponse = new DepartmentReponse();
            departmentReponse.setDeptId(departmentEntity.getDeptId());
            departmentReponse.setDeptName(departmentEntity.getDeptName());
            departmentReponse.setStatusCd(departmentEntity.getStatusCd());
            departmentReponses.add(departmentReponse);
        }
        return departmentReponses;
    }

    @Override
    public DepartmentReponse findAllDepartmentById(Integer deptId) {
        Optional<DepartmentEntity> optionalDepartmentEntity = departmentRepo.findById(deptId);
        if(optionalDepartmentEntity.isPresent()){
           return DepartmentReponse.builder()
                    .deptId(optionalDepartmentEntity.get().getDeptId())
                    .deptName(optionalDepartmentEntity.get().getDeptName())
                   .remark(optionalDepartmentEntity.get().getRemark())
                    .statusCd(optionalDepartmentEntity.get().getStatusCd())
                    .build();
        }
        return null;
    }

    private DepartmentEntity convertDepartmentCreateRequestToEntity(DepartmentCreateRequest roleCreateRequest) {
        return DepartmentEntity.departmentEntityBuilder()
                .deptName(roleCreateRequest.getDeptName())
                .remark(roleCreateRequest.getRemark())
                .statusCd(roleCreateRequest.getStatusCd())
                .createdUserId(roleCreateRequest.getEmployeeId())
                .build();
    }

    private DepartmentEntity convertDepartmentUpdateRequestToEntity(DepartmentUpdateRequest roleUpdateRequest) {
        return DepartmentEntity.departmentEntityBuilder()
                .deptId(roleUpdateRequest.getDeptId())
                .deptName(roleUpdateRequest.getDeptName())
                .remark(roleUpdateRequest.getRemark())
                .statusCd(roleUpdateRequest.getStatusCd())
                .createdUserId(roleUpdateRequest.getEmployeeId())
                .build();
    }
}
