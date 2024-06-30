package com.futurebizops.kpi.service.serviceimpl;

import com.futurebizops.kpi.constants.KPIConstants;
import com.futurebizops.kpi.entity.ComplaintAudit;
import com.futurebizops.kpi.entity.ComplaintEntity;
import com.futurebizops.kpi.entity.DepartmentAudit;
import com.futurebizops.kpi.entity.DepartmentEntity;
import com.futurebizops.kpi.exception.KPIException;
import com.futurebizops.kpi.repository.ComplaintAuditRepo;
import com.futurebizops.kpi.repository.ComplaintRepo;
import com.futurebizops.kpi.request.ComplaintCreateRequest;
import com.futurebizops.kpi.request.DepartmentCreateRequest;
import com.futurebizops.kpi.request.DepartmentUpdateRequest;
import com.futurebizops.kpi.response.KPIResponse;
import com.futurebizops.kpi.service.ComplaintService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Slf4j
@Service
public class ComplaintServiceImpl implements ComplaintService {

@Autowired
    ComplaintRepo complaintRepo;

@Autowired
    ComplaintAuditRepo complaintAuditRepo;

    @Override
    public KPIResponse saveComplaint(ComplaintCreateRequest complaintCreateRequest) {
        Optional<ComplaintEntity> complaintEntityOptional = complaintRepo.findByCompTypeIdAndCompStatusEqualsIgnoreCase(complaintCreateRequest.getCompTypeId(),"Pending");
        if(complaintEntityOptional.isPresent()){
            log.error("Inside ComplaintServiceImpl >> saveComplaint()");
            throw new KPIException("ComplaintServiceImpl Class", false, "Complaint name already exist");
        }

        ComplaintEntity complaintEntity = convertComplaintCreateRequestToEntity(complaintCreateRequest);
        try {
            complaintRepo.save(complaintEntity);
            ComplaintAudit complaintAudit = new ComplaintAudit(complaintEntity);
            complaintAuditRepo.save(complaintAudit);
            return KPIResponse.builder()
                    .isSuccess(true)
                    .responseMessage(KPIConstants.RECORD_SUCCESS)
                    .build();
        } catch (Exception ex) {
            log.error("Inside ComplaintServiceImpl >> saveComplaint()");
            throw new KPIException("ComplaintServiceImpl", false, ex.getMessage());
        }

    }


    private ComplaintEntity convertComplaintCreateRequestToEntity(ComplaintCreateRequest complaintCreateRequest) {
        ComplaintEntity complaintEntity = new ComplaintEntity();
        complaintEntity.setEmpId(complaintCreateRequest.getEmpId());
        complaintEntity.setEmpEId(complaintCreateRequest.getEmpEId());
        complaintEntity.setRoleId(complaintCreateRequest.getRoleId());
        complaintEntity.setDeptId(complaintCreateRequest.getDeptId());
        complaintEntity.setDesigId(complaintCreateRequest.getDesigId());
        complaintEntity.setCompDate(Instant.now());
        complaintEntity.setCompDesc(complaintCreateRequest.getCompDesc());
        complaintEntity.setCompTypeId(complaintCreateRequest.getCompTypeId());
        complaintEntity.setCompStatus("Pending");
        complaintEntity.setRemark(complaintCreateRequest.getRemark());
        complaintEntity.setStatusCd(complaintCreateRequest.getStatusCd());
        complaintEntity.setCreatedUserId(complaintCreateRequest.getEmployeeId());
        return  complaintEntity;
    }

    @Override
    public KPIResponse updateComplaint(DepartmentUpdateRequest departmentUpdateRequest) {
        return null;
    }

    @Override
    public KPIResponse findComplaintDetails(Integer empId, String empEId, Integer roleId, Integer deptId, Integer desigId, String statusCd, Pageable pageable) {
        return null;
    }
}
