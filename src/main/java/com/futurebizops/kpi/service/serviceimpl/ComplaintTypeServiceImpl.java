package com.futurebizops.kpi.service.serviceimpl;

import com.futurebizops.kpi.constants.KPIConstants;
import com.futurebizops.kpi.entity.ComplaintTypeAudit;
import com.futurebizops.kpi.entity.ComplaintTypeEntity;
import com.futurebizops.kpi.entity.DepartmentAudit;
import com.futurebizops.kpi.entity.DepartmentEntity;
import com.futurebizops.kpi.exception.KPIException;
import com.futurebizops.kpi.repository.ComplaintTypeAuditRepo;
import com.futurebizops.kpi.repository.ComplaintTypeRepo;
import com.futurebizops.kpi.request.ComplaintCreateRequest;
import com.futurebizops.kpi.request.ComplaintTypeCreateRequest;
import com.futurebizops.kpi.request.ComplaintTypeUpdateRequest;
import com.futurebizops.kpi.request.DepartmentCreateRequest;
import com.futurebizops.kpi.request.DepartmentUpdateRequest;
import com.futurebizops.kpi.response.ComplaintTypeReponse;
import com.futurebizops.kpi.response.DepartmentReponse;
import com.futurebizops.kpi.response.KPIResponse;
import com.futurebizops.kpi.service.ComplaintService;
import com.futurebizops.kpi.service.ComplaintTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

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
        Optional<ComplaintTypeEntity> optionalComplaintType = complaintTypeRepo.findByCompTypeNameEqualsIgnoreCase(complaintCreateRequest.getCompTypeName() );
        if(optionalComplaintType.isPresent()){
            log.error("Inside ComplaintTypeServiceImpl >> saveDepartment()");
            throw new KPIException("ComplaintTypeServiceImpl Class", false, "Complaint Type name already exist");
        }

        ComplaintTypeEntity complaintTypeEntity = convertComplaintTypeCreateRequestToEntity(complaintCreateRequest);
        try {
            complaintTypeRepo.save(complaintTypeEntity);
            ComplaintTypeAudit partAudit = new ComplaintTypeAudit(complaintTypeEntity);
            complaintTypeAuditRepo.save(partAudit);
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


    private ComplaintTypeEntity convertComplaintTypeCreateRequestToEntity(ComplaintTypeCreateRequest compTypeCreateRequest) {
        ComplaintTypeEntity complaintTypeEntity = new ComplaintTypeEntity();

        complaintTypeEntity.setCompTypeName(compTypeCreateRequest.getCompTypeName());
        complaintTypeEntity.setRemark(compTypeCreateRequest.getRemark());
        complaintTypeEntity.setStatusCd(compTypeCreateRequest.getStatusCd());
        complaintTypeEntity.setCreatedUserId(compTypeCreateRequest.getEmployeeId());
        return  complaintTypeEntity;
    }

    private ComplaintTypeEntity convertComplaintTypeUpdateRequestToEntity(ComplaintTypeUpdateRequest complaintTypeUpdateRequest) {
        ComplaintTypeEntity complaintTypeEntity = new ComplaintTypeEntity();
        complaintTypeEntity.setCompTypeId(complaintTypeUpdateRequest.getCompTypeId());
        complaintTypeEntity.setCompTypeName(complaintTypeUpdateRequest.getCompTypeName());
        complaintTypeEntity.setRemark(complaintTypeUpdateRequest.getRemark());
        complaintTypeEntity.setStatusCd(complaintTypeUpdateRequest.getStatusCd());
        complaintTypeEntity.setCreatedUserId(complaintTypeUpdateRequest.getEmployeeId());
        return  complaintTypeEntity;
    }
}
