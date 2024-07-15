package com.futurebizops.kpi.service.serviceimpl;

import com.futurebizops.kpi.constants.KPIConstants;
import com.futurebizops.kpi.entity.ComplaintAudit;
import com.futurebizops.kpi.entity.ComplaintEntity;
import com.futurebizops.kpi.exception.KPIException;
import com.futurebizops.kpi.repository.ComplaintAuditRepo;
import com.futurebizops.kpi.repository.ComplaintRepo;
import com.futurebizops.kpi.repository.ComplaintTypeRepo;
import com.futurebizops.kpi.request.ComplaintCreateRequest;
import com.futurebizops.kpi.request.EmployeeComplaintUpdateRequest;
import com.futurebizops.kpi.response.EmployeeComplaintResponse;
import com.futurebizops.kpi.response.KPIResponse;
import com.futurebizops.kpi.response.dropdown.DepartmentDDResponse;
import com.futurebizops.kpi.service.ComplaintService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ComplaintServiceImpl implements ComplaintService {

    @Autowired
    ComplaintRepo complaintRepo;

    @Autowired
    ComplaintAuditRepo complaintAuditRepo;

    @Autowired
    ComplaintTypeRepo complaintTypeRepo;


    @Override
    public KPIResponse saveComplaint(ComplaintCreateRequest complaintCreateRequest) {
        String complaintId = "COMP00" + getRandomNumber();
        Optional<ComplaintEntity> complaintEntityOptional = complaintRepo.findByCompIdAndCompStatusEqualsIgnoreCase(complaintId, "Pending");
        if (complaintEntityOptional.isPresent()) {
            log.error("Inside ComplaintServiceImpl >> saveComplaint()");
            throw new KPIException("ComplaintServiceImpl Class", false, "Complaint Id already exist");
        }

        ComplaintEntity complaintEntity = convertComplaintCreateRequestToEntity(complaintCreateRequest);
        complaintEntity.setCompId(complaintId);

        try {
            complaintRepo.save(complaintEntity);
            ComplaintAudit complaintAudit = new ComplaintAudit(complaintEntity);
            complaintAuditRepo.save(complaintAudit);
            return KPIResponse.builder()
                    .isSuccess(true)
                    .responseMessage(KPIConstants.RECORD_SUCCESS + " With complaint id : " + complaintId)
                    .build();
        } catch (Exception ex) {
            log.error("Inside ComplaintServiceImpl >> saveComplaint()");
            throw new KPIException("ComplaintServiceImpl", false, ex.getMessage());
        }

    }

    @Transactional
    @Override
    public KPIResponse updateEmployeeComplaint(EmployeeComplaintUpdateRequest complaintUpdateRequest) {
        //ComplaintEntity complaintEntity = convertEmployeeComplaintUpdateRequestToEntity(complaintUpdateRequest);
        try {
            complaintRepo.updateEmployeeComplaintDescription(complaintUpdateRequest.getEmpCompId(), complaintUpdateRequest.getCompDesc());
            return KPIResponse.builder()
                    .isSuccess(true)
                    .responseMessage(KPIConstants.RECORD_UPDATE)
                    .build();
        } catch (Exception ex) {
            log.error("Inside ComplaintServiceImpl >> updateEmployeeComplaint() :{}", ex);
            throw new KPIException("ComplaintServiceImpl", false, ex.getMessage());
        }
    }

    @Transactional
    @Override
    public KPIResponse updateAdminHandleComplaint(EmployeeComplaintUpdateRequest complaintUpdateRequest) {
        try {
            Instant complaintResolveDate = Instant.now();
            complaintRepo.updateAdminHandleComplaintDescription(complaintUpdateRequest.getEmpCompId(), complaintUpdateRequest.getCompStatus(), complaintResolveDate, complaintUpdateRequest.getRemark());
            return KPIResponse.builder()
                    .isSuccess(true)
                    .responseMessage(KPIConstants.RECORD_UPDATE)
                    .build();
        } catch (Exception ex) {
            log.error("Inside ComplaintServiceImpl >> updateEmployeeComplaint() :{}", ex);
            throw new KPIException("ComplaintServiceImpl", false, ex.getMessage());
        }
    }

    private ComplaintEntity convertEmployeeComplaintUpdateRequestToEntity(EmployeeComplaintUpdateRequest complaintUpdateRequest) {
        ComplaintEntity complaintEntity = new ComplaintEntity();
        complaintEntity.setEmpCompId(complaintUpdateRequest.getEmpCompId());
        complaintEntity.setEmpId(complaintUpdateRequest.getEmpId());
        complaintEntity.setEmpEId(complaintUpdateRequest.getEmpEId());
        complaintEntity.setRoleId(complaintUpdateRequest.getRoleId());
        complaintEntity.setDeptId(complaintUpdateRequest.getDeptId());
        complaintEntity.setDesigId(complaintUpdateRequest.getDesigId());
        complaintEntity.setCompDate(Instant.now());
        complaintEntity.setCompDesc(complaintUpdateRequest.getCompDesc());


        complaintEntity.setCompTypeDeptId(complaintUpdateRequest.getCompTypeDeptId());
        complaintEntity.setCompTypeId(complaintUpdateRequest.getCompTypeId());
        complaintEntity.setCompStatus(complaintUpdateRequest.getCompStatus());
        complaintEntity.setRemark(complaintUpdateRequest.getRemark());
        complaintEntity.setStatusCd(complaintUpdateRequest.getStatusCd());
        complaintEntity.setRemark(complaintUpdateRequest.getRemark());
        complaintEntity.setCreatedUserId(complaintUpdateRequest.getEmployeeId());
        return complaintEntity;
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

        complaintEntity.setCompTypeDeptId(complaintCreateRequest.getCompTypeDeptId());
        complaintEntity.setCompTypeId(complaintCreateRequest.getCompTypeId());
        complaintEntity.setCompStatus("Pending");
        complaintEntity.setRemark(complaintCreateRequest.getRemark());
        complaintEntity.setStatusCd(complaintCreateRequest.getStatusCd());
        complaintEntity.setCreatedUserId(complaintCreateRequest.getEmployeeId());
        return complaintEntity;
    }


    @Override
    public EmployeeComplaintResponse findAllEmployeeCompById(Integer empCompId) {
        try {
            List<Object[]> comlaintData = complaintRepo.getEmployeeComplaintByIdDetail(empCompId);
            List<EmployeeComplaintResponse> complaintReponses = comlaintData.stream().map(EmployeeComplaintResponse::new).collect(Collectors.toList());
            if (complaintReponses.size() > 0) {
                return complaintReponses.get(0);
            }
            return null;
        } catch (Exception ex) {
            log.error("ComplaintServiceImpl >>findAllEmployeeCompById :{}", ex);
            throw new KPIException("DepartmentServiceImpl", false, ex.getMessage());
        }
    }

    @Override
    public KPIResponse findComplaintDetails(Integer empId, String compId, Integer roleId, Integer deptId, String compDesc, String compStatus, Integer compTypeDeptId, String statusCd, Pageable requestPageable) {
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

        Integer totalCount = complaintRepo.getEmployeeComplaintCount(empId, compId, roleId, deptId, compDesc, compStatus, compTypeDeptId, statusCd);
        List<Object[]> complaintData = complaintRepo.getEmployeeComplaintDetail(empId, compId, roleId, deptId, compDesc, compStatus,  compTypeDeptId, statusCd, sortName, pageSize, pageOffset);

        List<EmployeeComplaintResponse> complaintResponses = complaintData.stream().map(EmployeeComplaintResponse::new).collect(Collectors.toList());

        complaintResponses = complaintResponses.stream()
                .sorted(Comparator.comparing(EmployeeComplaintResponse::getCompId))
                .collect(Collectors.toList());

        return KPIResponse.builder()
                .isSuccess(true)
                .responseData(new PageImpl<>(complaintResponses, requestPageable, totalCount))
                .responseMessage(KPIConstants.RECORD_FETCH)
                .build();
    }


    @Transactional
    @Override
    public KPIResponse deleteEmployeeComplaint(Integer empCompId) {
        KPIResponse kpiResponse = new KPIResponse();
        try {
            complaintRepo.deleteEmployeeComplaint(empCompId);
            kpiResponse.setSuccess(true);
            kpiResponse.setResponseMessage("Employee Complaint Deleted Successfully");
            return kpiResponse;
        } catch (Exception ex) {
            log.error("ComplaintServiceImpl >>findAllEmployeeCompById :{}", ex);
            throw new KPIException("DepartmentServiceImpl", false, ex.getMessage());
        }
    }





    private Integer getRandomNumber() {
        return (int) (Math.random() * 10000);
    }
}
