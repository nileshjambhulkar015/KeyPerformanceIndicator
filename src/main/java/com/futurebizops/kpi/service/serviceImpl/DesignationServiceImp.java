package com.futurebizops.kpi.service.serviceImpl;

import com.futurebizops.kpi.constants.KPIConstants;
import com.futurebizops.kpi.entity.DepartmentEntity;
import com.futurebizops.kpi.entity.DesignationAudit;
import com.futurebizops.kpi.entity.DesignationEntity;
import com.futurebizops.kpi.exception.KPIException;
import com.futurebizops.kpi.repository.DepartmentRepo;
import com.futurebizops.kpi.repository.DesignationAuditRepo;
import com.futurebizops.kpi.repository.DesignationRepo;
import com.futurebizops.kpi.request.DesignationCreateRequest;
import com.futurebizops.kpi.request.DesignationUpdateRequest;
import com.futurebizops.kpi.response.DepartmentReponse;
import com.futurebizops.kpi.response.DesignationReponse;
import com.futurebizops.kpi.response.KPIResponse;
import com.futurebizops.kpi.service.DepartmentService;
import com.futurebizops.kpi.service.DesignationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DesignationServiceImp implements DesignationService {

    @Autowired
    private DesignationRepo designationRepo;

    @Autowired
    private DesignationAuditRepo designationAuditRepo;

    @Autowired
    private DepartmentRepo departmentRepo;

    @Autowired
    private DepartmentService departmentService;
    @Override
    public KPIResponse saveDesignation(DesignationCreateRequest designationCreateRequest) {
        Optional<DesignationEntity> designationEntities = designationRepo.findByDeptIdAndDesigNameEqualsIgnoreCase(designationCreateRequest.getDeptId(), designationCreateRequest.getDesigName());
        if (designationEntities.isPresent()) {
            log.error("Inside DesignationServiceImp >> saveDesignation()");
            throw new KPIException("DesignationServiceImp", false, "With Department name Designation name already exist");
        }

        DesignationEntity designationEntity = convertDesignationCreateRequestToEntity(designationCreateRequest);
        try {
            designationRepo.save(designationEntity);
            DesignationAudit designationAudit = new DesignationAudit(designationEntity);
            designationAuditRepo.save(designationAudit);
            return KPIResponse.builder()
                    .isSuccess(true)
                    .responseMessage(KPIConstants.RECORD_SUCCESS)
                    .build();
        } catch (Exception ex) {
            log.error("Inside DesignationServiceImp >> saveDesignation()");
            throw new KPIException("DesignationServiceImp", false, ex.getMessage());
        }
    }

    @Override
    public KPIResponse updateDesignation(DesignationUpdateRequest departmentUpdateRequest) {
        DesignationEntity designationEntity = convertDesignationUpdateRequestToEntity(departmentUpdateRequest);
        try {
            designationRepo.save(designationEntity);
            DesignationAudit designationAudit = new DesignationAudit(designationEntity);
            designationAuditRepo.save(designationAudit);
            return KPIResponse.builder()
                    .isSuccess(true)
                    .responseMessage(KPIConstants.RECORD_UPDATE)
                    .build();
        } catch (Exception ex) {
            log.error("Inside DesignationServiceImp >> updateDesignation() : {}",ex);
            throw new KPIException("DesignationServiceImp", false, ex.getMessage());
        }
    }

    @Override
    public KPIResponse findDesignationDetails( Integer roleId, Integer deptId, String desigName, String statusCd, Pageable requestPageable) {

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

        Integer totalCount = designationRepo.getDesignationCount(roleId,deptId, desigName, statusCd);
        List<Object[]> designationData = designationRepo.getDesignationDetail(roleId,deptId, desigName, statusCd, sortName, pageSize, pageOffset);

        List<DesignationReponse> designationReponses = designationData.stream().map(DesignationReponse::new).collect(Collectors.toList());

        designationReponses= designationReponses.stream()
                .sorted(Comparator.comparing(DesignationReponse::getDeptName))
                .collect(Collectors.toList());

        return KPIResponse.builder()
                .isSuccess(true)
                .responseData(new PageImpl(designationReponses, requestPageable, totalCount))
                .responseMessage(KPIConstants.RECORD_FETCH)
                .build();
    }

    @Override
    public DesignationReponse findDesignationById(Integer desigId) {
        List<Object[]> designationData = designationRepo.getDesignationByDesigId(desigId);
        List<DesignationReponse> designationReponses = designationData.stream().map(DesignationReponse::new).collect(Collectors.toList());
        return  designationReponses.get(0);
    }

    @Override
    public List<DesignationReponse> findAllDesignationByDeptId(Integer deptId) {

        List<Object[]> designationData = designationRepo.getAllDesigByDeptId(deptId);
        List<DesignationReponse> designationReponses = designationData.stream().map(DesignationReponse::new).collect(Collectors.toList());
        return  designationReponses;
    }

    @Override
    public List<DepartmentReponse> getAllDepartmentFromDesig(Integer deptId) {
        List<Object[]> designationData = designationRepo.getDeptInDesigById(deptId);
        List<DepartmentReponse> designationReponses = designationData.stream().map(DepartmentReponse::new).collect(Collectors.toList());
        return  designationReponses;
    }



    private String getDepartmentName(Integer deptId) {
        Optional<DepartmentEntity> departmentEntity = departmentRepo.findById(deptId);
        if (departmentEntity.isPresent()) {
            return departmentEntity.get().getDeptName();
        }
        return null;
    }

    private DesignationEntity convertDesignationCreateRequestToEntity(DesignationCreateRequest designationCreateRequest) {
        return DesignationEntity.designationEntityBuilder()
                .roleId(designationCreateRequest.getRoleId())
                .deptId(designationCreateRequest.getDeptId())
                .desigName(designationCreateRequest.getDesigName())
                .remark(designationCreateRequest.getRemark())
                .statusCd(designationCreateRequest.getStatusCd())
                .createdUserId(designationCreateRequest.getEmployeeId())
                .build();
    }

    private DesignationEntity convertDesignationUpdateRequestToEntity(DesignationUpdateRequest designationUpdateRequest) {
        return DesignationEntity.designationEntityBuilder()
                .desigId(designationUpdateRequest.getDesigId())
                .roleId(designationUpdateRequest.getRoleId())
                .deptId(designationUpdateRequest.getDeptId())
                .desigName(designationUpdateRequest.getDesigName())
                .remark(designationUpdateRequest.getRemark())
                .statusCd(designationUpdateRequest.getStatusCd())
                .createdUserId(designationUpdateRequest.getEmployeeId())
                .build();
    }
}
