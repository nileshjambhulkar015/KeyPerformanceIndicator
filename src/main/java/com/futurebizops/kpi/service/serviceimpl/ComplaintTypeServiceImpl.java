package com.futurebizops.kpi.service.serviceimpl;

import com.futurebizops.kpi.constants.KPIConstants;
import com.futurebizops.kpi.entity.ComplaintTypeAudit;
import com.futurebizops.kpi.entity.ComplaintTypeEntity;
import com.futurebizops.kpi.entity.DepartmentEntity;
import com.futurebizops.kpi.exception.KPIException;
import com.futurebizops.kpi.repository.ComplaintTypeAuditRepo;
import com.futurebizops.kpi.repository.ComplaintTypeRepo;
import com.futurebizops.kpi.request.ComplaintTypeCreateRequest;
import com.futurebizops.kpi.request.ComplaintTypeUpdateRequest;
import com.futurebizops.kpi.response.ComplaintTypeReponse;
import com.futurebizops.kpi.response.KPIResponse;
import com.futurebizops.kpi.response.dropdown.ComplaintTypeDDResponse;
import com.futurebizops.kpi.response.dropdown.DepartmentDDResponse;
import com.futurebizops.kpi.service.ComplaintTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ComplaintTypeServiceImpl implements ComplaintTypeService {

    @Autowired
    ComplaintTypeRepo complaintTypeRepo;

    @Autowired
    ComplaintTypeAuditRepo complaintTypeAuditRepo;

    @Override
    @Retryable(include = {KPIException.class}, maxAttemptsExpression = "${retry-max-attempts}")
    public KPIResponse saveComplaintType(ComplaintTypeCreateRequest complaintTypeCreateRequest) {
        log.debug("Inside ComplaintTypeServiceImpl >> saveComplaintType() complaintTypeCreateRequest : {}", complaintTypeCreateRequest);

        KPIResponse kpiResponse = new KPIResponse();
        Optional<ComplaintTypeEntity> optionalComplaintType = complaintTypeRepo.findByDeptIdAndCompTypeNameEqualsIgnoreCase(complaintTypeCreateRequest.getDeptId(), complaintTypeCreateRequest.getCompTypeName());
        if (optionalComplaintType.isPresent()) {
            log.error("Inside ComplaintTypeServiceImpl >> saveComplaintType()");
            throw new KPIException("ComplaintTypeServiceImpl >> saveComplaintType()", false, "Complaint Type name already exist");
        }

        ComplaintTypeEntity complaintTypeEntity = convertComplaintTypeCreateRequestToEntity(complaintTypeCreateRequest);
        try {
            complaintTypeRepo.save(complaintTypeEntity);
            ComplaintTypeAudit complaintTypeAudit = new ComplaintTypeAudit(complaintTypeEntity);
            complaintTypeAuditRepo.save(complaintTypeAudit);
            log.info("Complaint type added successfully");
            kpiResponse.setResponseMessage("Complaint type added successfully");
            kpiResponse.setSuccess(true);
            return kpiResponse;
        } catch (Exception ex) {
            log.error("Inside ComplaintTypeServiceImpl >> saveComplaintType() : {}", ex);
            throw new KPIException("ComplaintTypeServiceImpl", false, ex.getMessage());
        }
    }


    @Transactional
    @Override
    @Retryable(include = {KPIException.class}, maxAttemptsExpression = "${retry-max-attempts}")
    public KPIResponse deleteComplaintTypeDetails(Integer compTypeId) {
        log.debug("Inside ComplaintTypeServiceImpl >> deleteComplaintTypeDetails() compTypeId :{}", compTypeId);

        KPIResponse kpiResponse = new KPIResponse();
        try {
            complaintTypeRepo.deleteComplaintTypeDetails(compTypeId);
            kpiResponse.setSuccess(true);
            kpiResponse.setResponseMessage("Complaint Type details deleted Successfully");

            log.info("Complaint Type details deleted Successfully : {}", compTypeId);
            return kpiResponse;
        } catch (Exception ex) {
            log.error("Inside ComplaintTypeServiceImpl >> deleteComplaintTypeDetails() : {}", ex);
            throw new KPIException("ComplaintTypeServiceImpl >> deleteComplaintTypeDetails()", false, ex.getMessage());
        }
    }

    @Override
    @Retryable(include = {KPIException.class}, maxAttemptsExpression = "${retry-max-attempts}")
    public KPIResponse findComplaintTypeDetails(Integer compTypeId, String compTypeName, Integer deptId, String statusCd, Pageable requestPageable) {
        log.debug("Inside ComplaintTypeServiceImpl >> findComplaintTypeDetails() compTypeId :{}, compTypeName : {}, deptId : {}", compTypeId, compTypeName, deptId);

        KPIResponse kpiResponse = new KPIResponse();
        String sortName = null;
        //  String sortDirection = null;
        Integer pageSize = requestPageable.getPageSize();
        Integer pageOffset = (int) requestPageable.getOffset();
        // pageable = KPIUtils.sort(requestPageable, sortParam, pageDirection);
        Optional<Sort.Order> order = requestPageable.getSort().get().findFirst();
        if (order.isPresent()) {
            sortName = order.get().getProperty();  //order by this field
            //sortDirection = order.get().getDirection().toString(); // Sort ASC or DESC
        }
        log.info("Inside findComplaintTypeDetails() pageSize : {}, pageOffset : {}, sortName : {}", pageSize, pageOffset, sortName);
        try {
            Integer totalCount = complaintTypeRepo.getComplaintTypeCount(compTypeId, compTypeName, deptId, statusCd);
            List<Object[]> complaintTypeData = complaintTypeRepo.getComplaintTypeDetail(compTypeId, compTypeName, deptId, statusCd, sortName, pageSize, pageOffset);

            List<ComplaintTypeReponse> complaintTypeReponses = complaintTypeData.stream().map(ComplaintTypeReponse::new).collect(Collectors.toList());
            log.info("Inside findComplaintTypeDetails() total : {}", complaintTypeReponses.size());

            if (complaintTypeReponses.size() > 0) {
                complaintTypeReponses = complaintTypeReponses.stream()
                        .sorted(Comparator.comparing(ComplaintTypeReponse::getCompTypeName))
                        .collect(Collectors.toList());
                kpiResponse.setResponseData(new PageImpl<>(complaintTypeReponses, requestPageable, totalCount));
                kpiResponse.setResponseMessage("Complaint type fetch successfully");
                kpiResponse.setSuccess(true);
                return kpiResponse;
            }
        } catch (Exception ex) {
            log.error("Inside ComplaintTypeServiceImpl >> findComplaintTypeDetails() : {}", ex);
            throw new KPIException("ComplaintTypeServiceImpl >> findComplaintTypeDetails()", false, ex.getMessage());
        }
        log.info("Inside findComplaintTypeDetails() Complaint type not found");
        kpiResponse.setSuccess(false);
        kpiResponse.setResponseMessage("Complaint type details not found");
        return kpiResponse;
    }

    @Override
    @Retryable(include = {KPIException.class}, maxAttemptsExpression = "${retry-max-attempts}")
    public ComplaintTypeReponse findAllComplaintTypeById(Integer compTypeId) {
        log.debug("Inside ComplaintTypeServiceImpl >> findAllComplaintTypeById() compTypeId : {}", compTypeId);
        try{
        List<Object[]> complaintTypeData = complaintTypeRepo.getComplaintTypeByIdDetail(compTypeId);
        List<ComplaintTypeReponse> designationReponses = complaintTypeData.stream().map(ComplaintTypeReponse::new).collect(Collectors.toList());
        return designationReponses.get(0);
        } catch (Exception ex) {
            log.error("Inside ComplaintTypeServiceImpl >> findAllComplaintTypeById() : {}", ex);
            throw new KPIException("ComplaintTypeServiceImpl >> findAllComplaintTypeById()", false, ex.getMessage());
        }
    }

    @Override
    @Retryable(include = {KPIException.class}, maxAttemptsExpression = "${retry-max-attempts}")
    public KPIResponse updateComplaintType(ComplaintTypeUpdateRequest complaintTypeUpdateRequest) {
        log.debug("Inside ComplaintTypeServiceImpl >> updateComplaintType() complaintTypeUpdateRequest : {}", complaintTypeUpdateRequest);

        KPIResponse kpiResponse = new KPIResponse();
        try {
            Optional<ComplaintTypeEntity> optionalComplaintType = complaintTypeRepo.findById(complaintTypeUpdateRequest.getCompTypeId());
            if (optionalComplaintType.isPresent()) {
                ComplaintTypeEntity complaintTypeEntity = optionalComplaintType.get();
                complaintTypeEntity.setCompTypeName(complaintTypeUpdateRequest.getCompTypeName());
                complaintTypeEntity.setRemark(complaintTypeUpdateRequest.getRemark());
                complaintTypeEntity.setUpdatedUserId(complaintTypeUpdateRequest.getEmployeeId());
                complaintTypeRepo.save(complaintTypeEntity);
                ComplaintTypeAudit departmentAudit = new ComplaintTypeAudit(complaintTypeEntity);
                complaintTypeAuditRepo.save(departmentAudit);
                kpiResponse.setResponseMessage("Complaint type updated successfully");
                kpiResponse.setSuccess(true);
                return kpiResponse;
            }
        } catch (Exception ex) {
            log.error("Inside ComplaintTypeServiceImpl >> updateComplaintType() : {}", ex);
            throw new KPIException("Inside ComplaintTypeServiceImpl >> updateComplaintType()", false, ex.getMessage());
        }
        log.info("Inside  updateComplaintType() Complaint type details not found");
        kpiResponse.setSuccess(false);
        kpiResponse.setResponseMessage("Complaint type details not found");
        return kpiResponse;
    }

    @Override
    @Retryable(include = {KPIException.class}, maxAttemptsExpression = "${retry-max-attempts}")
    public List<DepartmentDDResponse> findAllDepartmentFromComplaintType() {
        log.debug("Inside ComplaintTypeServiceImpl >> findAllDepartmentFromComplaintType()");
        try{
        List<Object[]> departmentData = complaintTypeRepo.findAllDepartmentFromComplaintType();
        List<DepartmentDDResponse> departmentDDResponses = new ArrayList<>();
        if (departmentData.size() > 0) {
            departmentDDResponses = departmentData.stream().map(DepartmentDDResponse::new).collect(Collectors.toList());
        }
        return departmentDDResponses;
        } catch (Exception ex) {
            log.error("Inside ComplaintTypeServiceImpl >> findAllDepartmentFromComplaintType() : {}", ex);
            throw new KPIException("Inside ComplaintTypeServiceImpl >> findAllDepartmentFromComplaintType()", false, ex.getMessage());
        }
    }

    @Override
    @Retryable(include = {KPIException.class}, maxAttemptsExpression = "${retry-max-attempts}")
    public List<ComplaintTypeDDResponse> findAllComplaintTypeByDeptId(Integer deptId) {
        log.debug("Inside ComplaintTypeServiceImpl >> findAllComplaintTypeByDeptId() deptId : {}", deptId);

        try{
        List<Object[]> complaintData = complaintTypeRepo.findAllComplaintTypeByDeptId(deptId);
        List<ComplaintTypeDDResponse> departmentDDResponses = new ArrayList<>();
        if (complaintData.size() > 0) {
            departmentDDResponses = complaintData.stream().map(ComplaintTypeDDResponse::new).collect(Collectors.toList());
        }
        return departmentDDResponses;
        } catch (Exception ex) {
            log.error("Inside ComplaintTypeServiceImpl >> findAllComplaintTypeByDeptId() : {}", ex);
            throw new KPIException("Inside ComplaintTypeServiceImpl >> findAllComplaintTypeByDeptId()", false, ex.getMessage());
        }
    }

    private ComplaintTypeEntity convertComplaintTypeCreateRequestToEntity(ComplaintTypeCreateRequest compTypeCreateRequest) {
       log.debug("Inside ComplaintTypeServiceImpl >> convertComplaintTypeCreateRequestToEntity() compTypeCreateRequest : {}", compTypeCreateRequest);

        ComplaintTypeEntity complaintTypeEntity = new ComplaintTypeEntity();
        complaintTypeEntity.setDeptId(compTypeCreateRequest.getDeptId());

        complaintTypeEntity.setCompTypeName(compTypeCreateRequest.getCompTypeName());
        complaintTypeEntity.setRemark(compTypeCreateRequest.getRemark());
        complaintTypeEntity.setStatusCd(compTypeCreateRequest.getStatusCd());
        complaintTypeEntity.setCreatedUserId(compTypeCreateRequest.getEmployeeId());
        return complaintTypeEntity;
    }
}
