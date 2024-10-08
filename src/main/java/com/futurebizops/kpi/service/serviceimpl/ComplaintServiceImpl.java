package com.futurebizops.kpi.service.serviceimpl;

import com.futurebizops.kpi.constants.KPIConstants;
import com.futurebizops.kpi.entity.ComplaintAudit;
import com.futurebizops.kpi.entity.ComplaintEntity;
import com.futurebizops.kpi.entity.ComplaintTypeEntity;
import com.futurebizops.kpi.entity.DepartmentEntity;
import com.futurebizops.kpi.excel.ComplaintExcel;
import com.futurebizops.kpi.exception.KPIException;
import com.futurebizops.kpi.repository.ComplaintAuditRepo;
import com.futurebizops.kpi.repository.ComplaintRepo;
import com.futurebizops.kpi.repository.ComplaintTypeRepo;
import com.futurebizops.kpi.repository.DepartmentRepo;
import com.futurebizops.kpi.request.ComplaintCreateRequest;
import com.futurebizops.kpi.request.EmployeeComplaintUpdateRequest;
import com.futurebizops.kpi.request.advsearch.ComplaintAdvSearch;
import com.futurebizops.kpi.response.ComplaintTypeReponse;
import com.futurebizops.kpi.response.DepartmentReponse;
import com.futurebizops.kpi.response.EmpKppStatusResponse;
import com.futurebizops.kpi.response.EmployeeComplaintResponse;
import com.futurebizops.kpi.response.KPIResponse;
import com.futurebizops.kpi.service.ComplaintService;
import com.futurebizops.kpi.service.DepartmentService;
import com.futurebizops.kpi.utils.DateTimeUtils;
import com.futurebizops.kpi.utils.EmailUtils;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
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

    @Autowired
    DepartmentRepo departmentRepo;


    @Value("${complaint-max-no}")
    private Integer compMaxNumber;

    @Autowired
    EmailUtils emailUtils;

    @Autowired
    ComplaintExcel complaintExcel;

    @Override
    public KPIResponse saveComplaint(ComplaintCreateRequest complaintCreateRequest) {
        System.out.println("complaintCreateRequest :"+complaintCreateRequest.getDeptId());
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

            Optional<ComplaintTypeEntity>  optionalComplaintType =complaintTypeRepo.findByDeptId(complaintEntity.getCompTypeDeptId());


            Optional<DepartmentEntity>  optionalDepartmentEntity =departmentRepo.findById(complaintEntity.getDeptId());

            String messageBody = "<html><body>  Complaint is register with complaint id :"+complaintId+" <br><br>" +
                    "Complaint details : "+complaintEntity.getCompDesc()+ "</body></html>";

            //Send mail
            emailUtils.sendEmail(complaintCreateRequest.getEmpEmailId(), "Complaint : "+optionalComplaintType.get().getCompTypeName(), messageBody);

            //send request complaint raised department also
            emailUtils.sendEmail(optionalDepartmentEntity.get().getDeptMailId(),
                    "Complaint : "+optionalComplaintType.get().getCompTypeName(), messageBody);

            return KPIResponse.builder()
                    .isSuccess(true)
                    .responseMessage(KPIConstants.RECORD_SUCCESS + " With complaint id : " + complaintId)
                    .build();

        } catch (Exception ex) {
            log.error("Inside ComplaintServiceImpl >> saveComplaint() :{}", ex);
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
            Instant complaintResolveDate = null != complaintUpdateRequest.getCompResolveDateTime()? DateTimeUtils.convertResolveDateStringToInstant(complaintUpdateRequest.getCompResolveDateTime()):null;

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

    @Transactional
    @Override
    public KPIResponse updateEmpAssignComplaintHimself(EmployeeComplaintUpdateRequest complaintUpdateRequest) {
        try {
            complaintRepo.updateEmpAssignComplaintHimself(complaintUpdateRequest.getEmpCompId(), complaintUpdateRequest.getCompStatus(), complaintUpdateRequest.getCompResolveEmpId(),complaintUpdateRequest.getCompResolveEmpName(), complaintUpdateRequest.getCompResolveEmpEId() );
            return KPIResponse.builder()
                    .isSuccess(true)
                    .responseMessage(KPIConstants.RECORD_UPDATE)
                    .build();
        } catch (Exception ex) {
            log.error("Inside ComplaintServiceImpl >> updateEmpAssignComplaintHimself() :{}", ex);
            throw new KPIException("ComplaintServiceImpl", false, ex.getMessage());
        }
    }
    @Override
    public KPIResponse advSearchEmployeeComplaintDetails(ComplaintAdvSearch complaintAdvSearch, Pageable requestPageable) {

        String compFromDate = StringUtils.isNotEmpty(complaintAdvSearch.getCompFromDate()) ? complaintAdvSearch.getCompFromDate() : null;

        String compToDate = StringUtils.isNotEmpty(complaintAdvSearch.getCompToDate()) ? complaintAdvSearch.getCompToDate() : null;

        Integer empId = null != complaintAdvSearch.getEmpId() ? complaintAdvSearch.getEmpId() : null;

        Integer deptId = null != complaintAdvSearch.getEmpCompDeptId() ? complaintAdvSearch.getEmpCompDeptId() : null;

        Integer asDeptId = null != complaintAdvSearch.getAsCompTypeDeptId() ? complaintAdvSearch.getAsCompTypeDeptId() : null;

        String asCompId = StringUtils.isNotEmpty(complaintAdvSearch.getAsCompId()) ? complaintAdvSearch.getAsCompId() : null;


        String asCompStatus = StringUtils.isNotEmpty(complaintAdvSearch.getAsCompStatus()) ? complaintAdvSearch.getAsCompStatus() : null;

        Integer asCompResolveEmpId = null != complaintAdvSearch.getAsCompResolveEmpId() ? complaintAdvSearch.getAsCompResolveEmpId() : null;

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

        Integer totalCount = complaintRepo.getAdvSearchEmployeeComplaintCount(empId,deptId, asCompId,asDeptId, asCompStatus, compFromDate,compToDate,asCompResolveEmpId);
        List<Object[]> complaintData = complaintRepo.getAdvSearchEmployeeComplaintDetail(empId,deptId, asCompId,asDeptId, asCompStatus, compFromDate,compToDate,asCompResolveEmpId, sortName, pageSize, pageOffset);

        List<EmployeeComplaintResponse> complaintResponses = complaintData.stream().map(EmployeeComplaintResponse::new).collect(Collectors.toList());

        for(EmployeeComplaintResponse employeeComplaintResponse : complaintResponses){
            employeeComplaintResponse.setCompTypeDeptName(findDepartmentNameById(employeeComplaintResponse.getCompTypeDeptId()));
        }

        complaintResponses = complaintResponses.stream()
               // .sorted(Comparator.comparing(EmployeeComplaintResponse::getCompId))
                .sorted((o1, o2)->o2.getCompDate().
                        compareTo(o1.getCompDate()))
                .collect(Collectors.toList());
        if(complaintResponses.size()>0) {
            return KPIResponse.builder()
                    .isSuccess(true)
                    .responseData(new PageImpl<>(complaintResponses, requestPageable, totalCount))
                    .responseMessage(KPIConstants.RECORD_FETCH)
                    .build();
        }
        return KPIResponse.builder()
                .isSuccess(false)
                .responseData(null)
                .responseMessage(KPIConstants.RECORD_NOT_FOUND)
                .build();
    }

    @Override
    public KPIResponse downloadEmployeeComplaints(HttpServletResponse httpServletResponse, String compFromDate,String compToDate,Integer empId,String empCompDeptId,Integer asCompTypeDeptId,String empCompId,String asCompStatus,Integer asCompResolveEmpId) {
        compFromDate = StringUtils.isNotEmpty(compFromDate) ? compFromDate : null;

        compToDate = StringUtils.isNotEmpty(compToDate) ? compToDate : null;

        empId = null != empId ? empId : null;


        Integer deptId = StringUtils.isNotEmpty(empCompDeptId) ?Integer.parseInt(empCompDeptId) : null;

        Integer asDeptId = null != asCompTypeDeptId ? asCompTypeDeptId : null;

        String asCompId = StringUtils.isNotEmpty(empCompId) ? empCompId : null;

        asCompStatus = StringUtils.isNotEmpty(asCompStatus) ? asCompStatus : null;

        asCompResolveEmpId = null != asCompResolveEmpId ? asCompResolveEmpId : null;


        List<Object[]> complaintData = complaintRepo.downloadEmployeeComplaintDetail(empId, asCompId,deptId,asDeptId, asCompStatus, compFromDate,compToDate,asCompResolveEmpId);

        List<EmployeeComplaintResponse> complaintResponses = complaintData.stream().map(EmployeeComplaintResponse::new).collect(Collectors.toList());

        for(EmployeeComplaintResponse employeeComplaintResponse : complaintResponses){
            employeeComplaintResponse.setCompTypeDeptName(findDepartmentNameById(employeeComplaintResponse.getCompTypeDeptId()));
        }

        complaintResponses = complaintResponses.stream()
                // .sorted(Comparator.comparing(EmployeeComplaintResponse::getCompId))
                .sorted((o1, o2)->o2.getCompDate().
                        compareTo(o1.getCompDate()))
                .collect(Collectors.toList());

        if(complaintResponses.size()>0) {
            complaintExcel.getEmployeeComplaintExcel(complaintResponses, httpServletResponse);

            return KPIResponse.builder()
                    .isSuccess(true)
                    .responseData(complaintResponses)
                    .responseMessage(KPIConstants.RECORD_FETCH)
                    .build();
        }
        return KPIResponse.builder()
                .isSuccess(false)
                .responseData(null)
                .responseMessage(KPIConstants.RECORD_NOT_FOUND)
                .build();
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
    public KPIResponse findComplaintDetails(Integer empId, String compId, Integer roleId, Integer deptId, String compDesc, String compStatus, Integer compTypeDeptId,Integer resolveEmpId, String statusCd, Pageable requestPageable) {
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

        Integer totalCount = complaintRepo.getEmployeeComplaintCount(empId, compId, roleId, deptId, compDesc, compStatus, compTypeDeptId,resolveEmpId, statusCd);
        List<Object[]> complaintData = complaintRepo.getEmployeeComplaintDetail(empId, compId, roleId, deptId, compDesc, compStatus, compTypeDeptId,resolveEmpId, statusCd, sortName, pageSize, pageOffset);

        List<EmployeeComplaintResponse> complaintResponses = complaintData.stream().map(EmployeeComplaintResponse::new).collect(Collectors.toList());

        for(EmployeeComplaintResponse employeeComplaintResponse : complaintResponses){
            employeeComplaintResponse.setCompTypeDeptName(findDepartmentNameById(employeeComplaintResponse.getCompTypeDeptId()));
        }

        complaintResponses = complaintResponses.stream()
                //.sorted(Comparator.comparing(EmployeeComplaintResponse::getCompDate))
                .sorted((o1, o2)->o2.getCompDate().
                        compareTo(o1.getCompDate()))
                .collect(Collectors.toList());

        return KPIResponse.builder()
                .isSuccess(true)
                .responseData(new PageImpl<>(complaintResponses, requestPageable, totalCount))
                .responseMessage(KPIConstants.RECORD_FETCH)
                .build();
    }


    public String findDepartmentNameById(Integer deptId){
        Optional<DepartmentEntity> departmentEntity =   departmentRepo.findById(deptId);
        if(departmentEntity.isPresent()){
            return departmentEntity.get().getDeptName();
        }
        return null;
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
        return (int) (Math.random() * compMaxNumber);
    }
}
