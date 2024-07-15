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
import org.springframework.stereotype.Service;

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
    public KPIResponse saveComplaintType(ComplaintTypeCreateRequest complaintCreateRequest) {
        Optional<ComplaintTypeEntity> optionalComplaintType = complaintTypeRepo.findByDeptIdAndCompTypeNameEqualsIgnoreCase(complaintCreateRequest.getDeptId(), complaintCreateRequest.getCompTypeName() );
        if(optionalComplaintType.isPresent()){
            log.error("Inside ComplaintTypeServiceImpl >> saveDepartment()");
            throw new KPIException("ComplaintTypeServiceImpl Class", false, "Complaint Type name already exist");
        }

        ComplaintTypeEntity complaintTypeEntity = convertComplaintTypeCreateRequestToEntity(complaintCreateRequest);
        try {
            complaintTypeRepo.save(complaintTypeEntity);
            ComplaintTypeAudit complaintTypeAudit = new ComplaintTypeAudit(complaintTypeEntity);
            complaintTypeAuditRepo.save(complaintTypeAudit);
            return KPIResponse.builder()
                    .isSuccess(true)
                    .responseMessage(KPIConstants.RECORD_SUCCESS)
                    .build();
        } catch (Exception ex) {
            log.error("Inside ComplaintTypeServiceImpl >> saveComplaintType()");
            throw new KPIException("ComplaintTypeServiceImpl", false, ex.getMessage());
        }
    }

    @Override
    public KPIResponse findComplaintTypeDetails(Integer compTypeId, String compTypeName, String statusCd, Pageable requestPageable) {
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

        Integer totalCount = complaintTypeRepo.getComplaintTypeCount(compTypeId, compTypeName, statusCd);
        List<Object[]> departmentData = complaintTypeRepo.getComplaintTypeDetail(compTypeId, compTypeName, statusCd, sortName, pageSize, pageOffset);

        List<ComplaintTypeReponse> complaintTypeReponses = departmentData.stream().map(ComplaintTypeReponse::new).collect(Collectors.toList());

        complaintTypeReponses= complaintTypeReponses.stream()
                .sorted(Comparator.comparing(ComplaintTypeReponse::getCompTypeName))
                .collect(Collectors.toList());

        return KPIResponse.builder()
                .isSuccess(true)
                .responseData(new PageImpl<>(complaintTypeReponses, requestPageable, totalCount))
                .responseMessage(KPIConstants.RECORD_FETCH)
                .build();
    }

    @Override
    public ComplaintTypeReponse findAllComplaintTypeById(Integer compTypeId) {
        List<Object[]> complaintTypeData = complaintTypeRepo.getComplaintTypeByIdDetail(compTypeId);
        List<ComplaintTypeReponse> designationReponses = complaintTypeData.stream().map(ComplaintTypeReponse::new).collect(Collectors.toList());
        return  designationReponses.get(0);
    }

    @Override
    public KPIResponse updateComplaintType(ComplaintTypeUpdateRequest complaintTypeUpdateRequest) {
        ComplaintTypeEntity complaintTypeEntity = convertComplaintTypeUpdateRequestToEntity(complaintTypeUpdateRequest
        );
        try {
            complaintTypeRepo.save(complaintTypeEntity);
            ComplaintTypeAudit departmentAudit = new ComplaintTypeAudit(complaintTypeEntity);
            complaintTypeAuditRepo.save(departmentAudit);
            return KPIResponse.builder()
                    .isSuccess(true)
                    .responseMessage(KPIConstants.RECORD_UPDATE)
                    .build();
        } catch (Exception ex) {
            log.error("Inside ComplaintTypeServiceImpl >> updateComplaintType()");
            throw new KPIException("ComplaintTypeServiceImpl", false, ex.getMessage());
        }
    }

    @Override
    public List<DepartmentDDResponse> findAllDepartmentFromComplaintType() {
        List<Object[]> complaintData = complaintTypeRepo.findAllDepartmentFromComplaintType();
        List<DepartmentDDResponse> departmentDDResponses = null;
        if (complaintData.size() > 0) {
            departmentDDResponses = complaintData.stream().map(DepartmentDDResponse::new).collect(Collectors.toList());
        }
        return departmentDDResponses;
    }

    @Override
    public List<ComplaintTypeDDResponse> findAllComplaintTypeByDeptId(Integer deptId) {
        List<Object[]> complaintData = complaintTypeRepo.findAllComplaintTypeByDeptId(deptId);
        List<ComplaintTypeDDResponse> departmentDDResponses = null;
        if (complaintData.size() > 0) {
            departmentDDResponses = complaintData.stream().map(ComplaintTypeDDResponse::new).collect(Collectors.toList());
        }
        return departmentDDResponses;
    }

    private ComplaintTypeEntity convertComplaintTypeCreateRequestToEntity(ComplaintTypeCreateRequest compTypeCreateRequest) {
        ComplaintTypeEntity complaintTypeEntity = new ComplaintTypeEntity();
        complaintTypeEntity.setDeptId(compTypeCreateRequest.getDeptId());

        complaintTypeEntity.setCompTypeName(compTypeCreateRequest.getCompTypeName());
        complaintTypeEntity.setRemark(compTypeCreateRequest.getRemark());
        complaintTypeEntity.setStatusCd(compTypeCreateRequest.getStatusCd());
        complaintTypeEntity.setCreatedUserId(compTypeCreateRequest.getEmployeeId());
        return  complaintTypeEntity;
    }

    private ComplaintTypeEntity convertComplaintTypeUpdateRequestToEntity(ComplaintTypeUpdateRequest complaintTypeUpdateRequest) {
        ComplaintTypeEntity complaintTypeEntity = new ComplaintTypeEntity();
        complaintTypeEntity.setCompTypeId(complaintTypeUpdateRequest.getCompTypeId());
        complaintTypeEntity.setDeptId(complaintTypeEntity.getDeptId());

        complaintTypeEntity.setCompTypeName(complaintTypeUpdateRequest.getCompTypeName());
        complaintTypeEntity.setRemark(complaintTypeUpdateRequest.getRemark());
        complaintTypeEntity.setStatusCd(complaintTypeUpdateRequest.getStatusCd());
        complaintTypeEntity.setCreatedUserId(complaintTypeUpdateRequest.getEmployeeId());
        return  complaintTypeEntity;
    }
}
