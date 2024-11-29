package com.futurebizops.kpi.service.serviceimpl;

import com.futurebizops.kpi.constants.KPIConstants;
import com.futurebizops.kpi.entity.EmployeeMeetingAudit;
import com.futurebizops.kpi.entity.EmployeeMeetingEntity;
import com.futurebizops.kpi.exception.KPIException;
import com.futurebizops.kpi.repository.EmployeeMeetingAuditRepo;
import com.futurebizops.kpi.repository.EmployeeMeetingRepo;
import com.futurebizops.kpi.request.EmployeeMeetingCreateRequest;
import com.futurebizops.kpi.request.EmployeeMeetingUpdateRequest;
import com.futurebizops.kpi.response.EmployeeMeetingReponse;
import com.futurebizops.kpi.response.KPIResponse;
import com.futurebizops.kpi.service.EmployeeMeetingService;
import com.futurebizops.kpi.utils.DateTimeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class EmployeeMeetingServiceImpl implements EmployeeMeetingService {

    @Autowired
    EmployeeMeetingRepo employeeMeetingRepo;

    @Autowired
    EmployeeMeetingAuditRepo employeeMeetingAuditRepo;

    @Override
    public KPIResponse saveEmployeeMeeting(EmployeeMeetingCreateRequest employeeMeetingCreateRequest) {
        System.out.println(employeeMeetingCreateRequest);
        EmployeeMeetingEntity employeeMeetingEntity = convertMeetingCreateRequestToEntity(employeeMeetingCreateRequest);
        try {
            employeeMeetingRepo.save(employeeMeetingEntity);
            EmployeeMeetingAudit partAudit = new EmployeeMeetingAudit(employeeMeetingEntity);
            employeeMeetingAuditRepo.save(partAudit);
            return KPIResponse.builder()
                    .isSuccess(true)
                    .responseMessage(KPIConstants.RECORD_SUCCESS)
                    .build();
        } catch (Exception ex) {
            log.error("Inside EmployeeMeetingServiceImpl >> saveDepartment()");
            throw new KPIException("EmployeeMeetingServiceImpl", false, ex.getMessage());
        }
    }

    @Transactional
    @Override
    public KPIResponse cancelEmployeeMeeting(EmployeeMeetingUpdateRequest employeeMeetingUpdateRequest) {
        try {
            employeeMeetingRepo.cancelMeeting(employeeMeetingUpdateRequest.getMeetId(),employeeMeetingUpdateRequest.getMeetStatus());
            return KPIResponse.builder()
                    .isSuccess(true)
                    .responseMessage(KPIConstants.RECORD_UPDATE)
                    .build();
        } catch (Exception ex) {
            log.error("Inside EmployeeMeetingServiceImpl >> cancelEmployeeMeeting() :{}", ex);
            throw new KPIException("EmployeeMeetingServiceImpl", false, ex.getMessage());
        }
    }

    @Override
    public KPIResponse findAllMeetings(Pageable requestPageable) {
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

        Integer totalCount = employeeMeetingRepo.getEmployeeMeetingCount();
        List<Object[]> departmentData = employeeMeetingRepo.getEmployeeMeetingDetail(pageSize, pageOffset);

        List<EmployeeMeetingReponse> employeeMeetingReponses = departmentData.stream().map(EmployeeMeetingReponse::new).collect(Collectors.toList());


        return KPIResponse.builder()
                .isSuccess(true)
                .responseData(new PageImpl<>(employeeMeetingReponses, requestPageable, totalCount))
                .responseMessage(KPIConstants.RECORD_FETCH)
                .build();
    }

    @Override
    public EmployeeMeetingReponse findMeetingById(Integer meetingId, String statusCd) {
        List<Object[]> meetingData = employeeMeetingRepo.getMeetingByMeetingId(meetingId, statusCd);
        if (meetingData.size() > 0) {
            List<EmployeeMeetingReponse> employeeMeetingReponses = meetingData.stream().map(EmployeeMeetingReponse::new).collect(Collectors.toList());
            return employeeMeetingReponses.get(0);
        }
        return null;
    }

    @Override
    public List<EmployeeMeetingReponse> findAllMeeting(Integer meetingId, String statusCd) {
        List<Object[]> meetingData = employeeMeetingRepo.getMeetingByMeetingId(meetingId, statusCd);
        if (meetingData.size() > 0) {
            List<EmployeeMeetingReponse> employeeMeetingReponses = meetingData.stream().map(EmployeeMeetingReponse::new).collect(Collectors.toList());
            return employeeMeetingReponses;
        }
        return null;
    }


    private EmployeeMeetingEntity convertMeetingCreateRequestToEntity(EmployeeMeetingCreateRequest employeeMeetingCreateRequest) {
        Instant meetinStartDateTime = StringUtils.isNotEmpty(employeeMeetingCreateRequest.getMeetStartDate())? DateTimeUtils.convertResolveDateStringToInstant(employeeMeetingCreateRequest.getMeetStartDate()):null;
        Instant meetingEndDateTime = StringUtils.isNotEmpty(employeeMeetingCreateRequest.getMeetEndDate())? DateTimeUtils.convertResolveDateStringToInstant(employeeMeetingCreateRequest.getMeetEndDate()):null;


        EmployeeMeetingEntity employeeMeetingEntity = new EmployeeMeetingEntity();

        employeeMeetingEntity.setMeetStartDate(meetinStartDateTime);
        employeeMeetingEntity.setMeetEndDate(meetingEndDateTime);
        employeeMeetingEntity.setMeetCreatedByEmpId(employeeMeetingCreateRequest.getMeetCreatedByEmpId());
        employeeMeetingEntity.setMeetCreatedByEmpEId(employeeMeetingCreateRequest.getMeetCreatedByEmpEId());
        employeeMeetingEntity.setMeetCreatedByEmpName(employeeMeetingCreateRequest.getMeetCreatedByEmpName());
        employeeMeetingEntity.setMeetCreatedByRoleId(employeeMeetingCreateRequest.getMeetCreatedByRoleId());
        employeeMeetingEntity.setMeetCreatedByRoleName(employeeMeetingCreateRequest.getMeetCreatedByRoleName());
        employeeMeetingEntity.setMeetCreatedByDeptId(employeeMeetingCreateRequest.getMeetCreatedByDeptId());
        employeeMeetingEntity.setMeetCreatedByDeptName(employeeMeetingCreateRequest.getMeetCreatedByDeptName());
        employeeMeetingEntity.setMeetCreatedByDesigId(employeeMeetingCreateRequest.getMeetCreatedByDesigId());
        employeeMeetingEntity.setMeetCreatedByDesigName(employeeMeetingCreateRequest.getMeetCreatedByDesigName());
        employeeMeetingEntity.setMeetVenue(employeeMeetingCreateRequest.getMeetVenue());
        employeeMeetingEntity.setMeetTitle(employeeMeetingCreateRequest.getMeetTitle());
        employeeMeetingEntity.setMeetDescription(employeeMeetingCreateRequest.getMeetDescription());
        employeeMeetingEntity.setMeetStatus(employeeMeetingCreateRequest.getMeetStatus());
        employeeMeetingEntity.setRemark(employeeMeetingCreateRequest.getRemark());
        employeeMeetingEntity.setStatusCd(employeeMeetingCreateRequest.getStatusCd());
        employeeMeetingEntity.setCreatedUserId(employeeMeetingCreateRequest.getEmployeeId());
        return employeeMeetingEntity;
    }

}
