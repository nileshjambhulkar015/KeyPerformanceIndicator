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
import com.futurebizops.kpi.response.DesignationReponse;
import com.futurebizops.kpi.response.KPIResponse;
import com.futurebizops.kpi.service.DesignationService;
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
public class DesignationServiceImp implements DesignationService {

    @Autowired
    private DesignationRepo designationRepo;

    @Autowired
    private DesignationAuditRepo designationAuditRepo;

    @Autowired
    private DepartmentRepo departmentRepo;

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
            log.error("Inside DesignationServiceImp >> updateDesignation()");
            throw new KPIException("DesignationServiceImp", false, ex.getMessage());
        }
    }

    @Override
    public KPIResponse findDesignationDetails(Integer deptId, String desigName, String statusCd, Pageable requestPageable) {
        Page<DesignationEntity> departmentEntities = null;

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

        Integer totalCount = designationRepo.getDesignationCount(deptId, desigName, statusCd);
        List<Object[]> designationData = designationRepo.getDesignationDetail(deptId, desigName, statusCd, sortName, pageSize, pageOffset);

        List<DesignationReponse> designationReponses = designationData.stream().map(DesignationReponse::new).collect(Collectors.toList());

        return KPIResponse.builder()
                .isSuccess(true)
                .responseData(new PageImpl(designationReponses, requestPageable, totalCount))
                .responseMessage(KPIConstants.RECORD_FETCH)
                .build();
    }

    @Override
    public List<DesignationReponse> findAllDesignationDetails(Integer deptId) {

        List<DesignationEntity> designationEntities = null;
        if (null == deptId) {
            designationEntities = designationRepo.findAll();
        } else {
            designationEntities = designationRepo.findAllDesignation(deptId);
        }
        List<DesignationReponse> designationReponses = new ArrayList<>();
        DesignationReponse designationReponse = null;
        for (DesignationEntity designationEntity : designationEntities) {
            designationReponse = new DesignationReponse();
            designationReponse.setDesigId(designationEntity.getDesigId());
            designationReponse.setDeptId(designationEntity.getDeptId());
            designationReponse.setDeptName(getDepartmentName(designationEntity.getDeptId()));
            designationReponse.setDesigName(designationEntity.getDesigName());
            designationReponse.setStatusCd(designationEntity.getStatusCd());
            designationReponses.add(designationReponse);
        }
        return designationReponses;
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
                .deptId(designationUpdateRequest.getDeptId())
                .desigName(designationUpdateRequest.getDesigName())
                .remark(designationUpdateRequest.getRemark())
                .statusCd(designationUpdateRequest.getStatusCd())
                .createdUserId(designationUpdateRequest.getEmployeeId())
                .build();
    }
}
