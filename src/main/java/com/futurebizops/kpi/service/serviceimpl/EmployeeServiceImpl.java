package com.futurebizops.kpi.service.serviceimpl;

import com.futurebizops.kpi.constants.KPIConstants;
import com.futurebizops.kpi.entity.AuditEnabledEntity;
import com.futurebizops.kpi.entity.EmployeeAudit;
import com.futurebizops.kpi.entity.EmployeeEntity;
import com.futurebizops.kpi.entity.EmployeeKppDetailsAudit;
import com.futurebizops.kpi.entity.EmployeeKppDetailsEntity;
import com.futurebizops.kpi.entity.EmployeeKppMasterAudit;
import com.futurebizops.kpi.entity.EmployeeKppMasterEntity;
import com.futurebizops.kpi.entity.EmployeeLoginAudit;
import com.futurebizops.kpi.entity.EmployeeLoginEntity;
import com.futurebizops.kpi.entity.KeyPerfParamEntity;
import com.futurebizops.kpi.exception.KPIException;
import com.futurebizops.kpi.repository.EmployeeAuditRepo;
import com.futurebizops.kpi.repository.EmployeeKppDetailsAuditRepo;
import com.futurebizops.kpi.repository.EmployeeKppMasterAuditRepo;
import com.futurebizops.kpi.repository.EmployeeKppMasterRepo;
import com.futurebizops.kpi.repository.EmployeeKppDetailsRepo;
import com.futurebizops.kpi.repository.EmployeeLoginAuditRepo;
import com.futurebizops.kpi.repository.EmployeeLoginRepo;
import com.futurebizops.kpi.repository.EmployeeRepo;
import com.futurebizops.kpi.repository.KeyPerfParameterRepo;
import com.futurebizops.kpi.request.EmployeeCreateRequest;
import com.futurebizops.kpi.request.EmployeeUpdateRequest;
import com.futurebizops.kpi.response.CummalitiveEmployeeResponse;
import com.futurebizops.kpi.response.EmployeeResponse;
import com.futurebizops.kpi.response.EmployeeSearchResponse;
import com.futurebizops.kpi.response.EmployeeKppStatusResponse;
import com.futurebizops.kpi.response.KPIResponse;
import com.futurebizops.kpi.service.EmployeeService;
import com.futurebizops.kpi.utils.DateTimeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepo employeeRepo;

    @Autowired
    private EmployeeAuditRepo employeeAuditRepo;

    @Autowired
    private EmployeeLoginRepo employeeLoginRepo;

    @Autowired
    private EmployeeLoginAuditRepo employeeLoginAuditRepo;

    @Autowired
    private KeyPerfParameterRepo keyPerfParameterRepo;

    @Autowired
    private EmployeeKppDetailsRepo employeeKeyPerfParamRepo;

    @Autowired
    private EmployeeKppDetailsAuditRepo employeeKeyPerfParamAuditRepo;

    @Autowired
    private EmployeeKppMasterRepo employeeKeyPerfParamMasterRepo;

    @Autowired
    private EmployeeKppMasterAuditRepo employeeKeyPerfParamMasterAuditRepo;

    @Transactional
    @Override
    public KPIResponse saveEmployee(EmployeeCreateRequest employeeCreateRequest) {

        //When hod is inserted then gm set to reporing employee id only
        Integer gmEmpId = null;
        //2 is for HOD role
        if (2 == employeeCreateRequest.getRoleId()) {
            gmEmpId = employeeCreateRequest.getReportingEmpId();
        } else {
            gmEmpId = getGmEmpId(employeeCreateRequest.getReportingEmpId());
        }


        if (null == employeeCreateRequest.getReportingEmpId()) {
            log.error("Inside EmployeeServiceImpl >> saveEmployee()");
            throw new KPIException("EmployeeServiceImpl Class", false, "Reporting Employee must be present");
        }
        Optional<EmployeeEntity> optionalEmployeeEntity = employeeRepo.findByEmpEIdEqualsIgnoreCase(employeeCreateRequest.getEmpEId());
        if (optionalEmployeeEntity.isPresent()) {
            log.error("Inside EmployeeServiceImpl >> saveEmployee()");
            throw new KPIException("EmployeeServiceImpl Class", false, "Employee Id is already exist");
        }
        EmployeeEntity employeeEntity = convertEmployeeCreateRequestToEntity(employeeCreateRequest);
        employeeEntity.setGmEmpId(gmEmpId);
        try {

            List<KeyPerfParamEntity> empKpp = keyPerfParameterRepo.findByRoleIdAndDeptIdAndDesigId(employeeEntity.getRoleId(), employeeEntity.getDeptId(), employeeEntity.getDesigId());
            if (CollectionUtils.isEmpty(empKpp)) {
                log.error("Inside EmployeeServiceImpl >> saveEmployee()");
                throw new KPIException("EmployeeServiceImpl Class", false, "Please set the KPP for Designation first");
            }

            employeeRepo.save(employeeEntity);
            EmployeeAudit employeeAudit = new EmployeeAudit(employeeEntity);
            employeeAuditRepo.save(employeeAudit);

            EmployeeLoginEntity employeeLoginEntity = convertRequestToEmployeeLogin(employeeCreateRequest);
            employeeLoginRepo.save(employeeLoginEntity);
            EmployeeLoginAudit employeeLoginAudit = new EmployeeLoginAudit(employeeLoginEntity);
            employeeLoginAuditRepo.save(employeeLoginAudit);
            return KPIResponse.builder()
                    .isSuccess(true)
                    .responseMessage(KPIConstants.RECORD_SUCCESS)
                    .build();
        } catch (Exception ex) {
            log.error("Inside EmployeeServiceImpl >> saveEmployee()");
            throw new KPIException("EmployeeServiceImpl", false, ex.getMessage());
        }
    }

    private Integer getGmEmpId(Integer reportinEmpId) {
        Optional<EmployeeEntity> employeeEntity = employeeRepo.findById(reportinEmpId);
        if (employeeEntity.isPresent()) {
            return employeeEntity.get().getReportingEmpId();
        }
        return null;
    }

    @Override
    public KPIResponse updateEmployee(EmployeeUpdateRequest employeeUpdateRequest) {
        EmployeeEntity employeeEntity = convertEmployeeUpdateRequestToEntity(employeeUpdateRequest);
        try {
            employeeRepo.save(employeeEntity);
            EmployeeAudit employeeAudit = new EmployeeAudit(employeeEntity);
            employeeAuditRepo.save(employeeAudit);
            return KPIResponse.builder()
                    .isSuccess(true)
                    .responseMessage(KPIConstants.RECORD_UPDATE)
                    .build();
        } catch (Exception ex) {
            log.error("Inside EmployeeServiceImpl >> updateEmployee()");
            throw new KPIException("EmployeeServiceImpl", false, ex.getMessage());
        }
    }


    @Override
    public KPIResponse getAllEmployeeDetails(Integer empId, Integer roleId, Integer deptId, Integer desigId, String empFirstName, String empMiddleName, String empLastName, String empMobileNo, String emailId, String statusCd, Pageable pageable) {
        String sortName = null;
        //  String sortDirection = null;
        Integer pageSize = pageable.getPageSize();
        Integer pageOffset = (int) pageable.getOffset();
        // pageable = KPIUtils.sort(requestPageable, sortParam, pageDirection);
        Optional<Sort.Order> order = pageable.getSort().get().findFirst();
        if (order.isPresent()) {
            sortName = order.get().getProperty();  //order by this field
            //  sortDirection = order.get().getDirection().toString(); // Sort ASC or DESC
        }

        Integer totalCount = employeeRepo.getEmployeeCount(empId, roleId, deptId, desigId, empFirstName, empMiddleName, empLastName, empMobileNo, emailId, statusCd);
        List<Object[]> employeeDetail = employeeRepo.getEmployeeDetail(empId, roleId, deptId, desigId, empFirstName, empMiddleName, empLastName, empMobileNo, emailId, statusCd, sortName, pageSize, pageOffset);

        List<EmployeeResponse> employeeResponses = employeeDetail.stream().map(EmployeeResponse::new).collect(Collectors.toList());
        employeeResponses = employeeResponses.stream()
                .sorted(Comparator.comparing(EmployeeResponse::getDeptName))
                .collect(Collectors.toList());

        return KPIResponse.builder()
                .isSuccess(true)
                .responseData(new PageImpl<>(employeeResponses, pageable, totalCount))
                .responseMessage(KPIConstants.RECORD_FETCH)
                .build();
    }

    @Override
    public EmployeeResponse getAllEmployeeById(Integer empId) {
        List<Object[]> employeeDetail = employeeRepo.getEmployeeById(empId);
        List<EmployeeResponse> employeeResponses = employeeDetail.stream().map(EmployeeResponse::new).collect(Collectors.toList());
        return employeeResponses.get(0);
    }

    @Override
    public EmployeeSearchResponse getEmployeeSearchById(Integer empId) {
        List<Object[]> employeeDetail = employeeRepo.getEmployeeSearchById(empId);
        List<EmployeeSearchResponse> employeeResponses = employeeDetail.stream().map(EmployeeSearchResponse::new).collect(Collectors.toList());
        return employeeResponses.get(0);
    }

    @Override
    public List<EmployeeSearchResponse> getEmployeeSuggestByName(Integer roleId, Integer deptId, Integer desigId) {
        //  List<EmployeeEntity> employeeEntities = employeeRepo.findByRoleIdAndDeptIdAndDesigId(roleId,deptId,desigId);
        List<EmployeeEntity> employeeEntities = employeeRepo.findByRoleIdOrDeptIdOrDesigId(roleId, deptId, desigId);
        return employeeEntities.stream()
                .map(empDetails -> EmployeeSearchResponse.builder()
                        .empId(empDetails.getEmpId())
                        .empEId(empDetails.getEmpEId())
                        .roleId(empDetails.getRoleId())
                        .deptId(empDetails.getDeptId())
                        .desigId(empDetails.getDesigId())
                        .empFirstName(empDetails.getEmpFirstName())
                        .empMiddleName(empDetails.getEmpMiddleName())
                        .empLastName(empDetails.getEmpLastName())
                        .build())
                .collect(Collectors.toList());

    }

    //get details for employee, HOD and GM to approve or reject kpp details
    @Override
    public KPIResponse getAllEmployeeKPPStatus(Integer reportingEmployee, Integer gmEmpId, Integer empId, String empEId, Integer roleId, Integer deptId, Integer desigId, String empFirstName, String empMiddleName, String empLastName, String empMobileNo, String emailId, String statusCd, String empKppStatus, String hodKppStatus, String gmKppStatus, Pageable pageable) {
        String sortName = null;

        //for all records
        if ("All".equalsIgnoreCase(empKppStatus)) {
            empKppStatus = null;
        }
        // String sortDirection = null;
        Integer pageSize = pageable.getPageSize();
        Integer pageOffset = (int) pageable.getOffset();
        // pageable = KPIUtils.sort(requestPageable, sortParam, pageDirection);
        Optional<Sort.Order> order = pageable.getSort().get().findFirst();
        if (order.isPresent()) {
            sortName = order.get().getProperty();  //order by this field
            //sortDirection = order.get().getDirection().toString(); // Sort ASC or DESC
        }
        try {
            Integer totalCount = keyPerfParameterRepo.getEmployeeKppStatusDetailCount(reportingEmployee, gmEmpId, empId, empEId, roleId, deptId, desigId, empFirstName, empMiddleName, empLastName, empMobileNo, emailId, statusCd, empKppStatus, hodKppStatus, gmKppStatus);
            List<Object[]> employeeDetail = keyPerfParameterRepo.getEmployeeKppStatusDetail(reportingEmployee, gmEmpId, empId, empEId, roleId, deptId, desigId, empFirstName, empMiddleName, empLastName, empMobileNo, emailId, statusCd, empKppStatus, hodKppStatus, gmKppStatus, sortName, pageSize, pageOffset);

            List<EmployeeKppStatusResponse> employeeKppStatusResponses = employeeDetail.stream().map(EmployeeKppStatusResponse::new).collect(Collectors.toList());
            employeeKppStatusResponses = employeeKppStatusResponses.stream()
                    .sorted(Comparator.comparing(EmployeeKppStatusResponse::getDesigName))
                    .collect(Collectors.toList());

            return KPIResponse.builder()
                    .isSuccess(true)
                    .responseData(new PageImpl<>(employeeKppStatusResponses, pageable, totalCount))
                    .responseMessage(KPIConstants.RECORD_FETCH)
                    .build();
        } catch (Exception ex) {
            log.error("Inside EmployeeKeyPerfParamServiceImpl >> getAllEmployeeDetailsForHod()");
            throw new KPIException("EmployeeKeyPerfParamServiceImpl", false, ex.getMessage());
        }
    }

    //get details for employee, HOD and GM to approve or reject kpp details
    @Override
    public KPIResponse getAllEmployeeKPPStatusReport(String fromDate, String toDate, Integer reportingEmployee, Integer gmEmpId, Integer empId, String empEId, Integer roleId, Integer deptId, Integer desigId, String empFirstName, String empMiddleName, String empLastName, String empMobileNo, String emailId, String statusCd, String empKppStatus, String hodKppStatus, String gmKppStatus, Pageable pageable) {
        String sortName = null;
        String startDate = StringUtils.isNotEmpty(fromDate) ? DateTimeUtils.convertStringToInstant(fromDate).toString() : DateTimeUtils.getFirstDateOfYear();
        String endDate = StringUtils.isNotEmpty(toDate) ? DateTimeUtils.convertStringToInstant(toDate).toString() : Instant.now().toString();

        //for all records
        if ("All".equalsIgnoreCase(empKppStatus)) {
            empKppStatus = null;
        }
        // String sortDirection = null;
        Integer pageSize = pageable.getPageSize();
        Integer pageOffset = (int) pageable.getOffset();
        // pageable = KPIUtils.sort(requestPageable, sortParam, pageDirection);
        Optional<Sort.Order> order = pageable.getSort().get().findFirst();
        if (order.isPresent()) {
            sortName = order.get().getProperty();  //order by this field
            //sortDirection = order.get().getDirection().toString(); // Sort ASC or DESC
        }
        try {
            Integer totalCount = keyPerfParameterRepo.getEmployeeKppStatusDetailCount(reportingEmployee, gmEmpId, empId, empEId, roleId, deptId, desigId, empFirstName, empMiddleName, empLastName, empMobileNo, emailId, statusCd, empKppStatus, hodKppStatus, gmKppStatus);
            List<Object[]> employeeDetail = keyPerfParameterRepo.getEmployeeKppStatusReportDetail(startDate, endDate, reportingEmployee, gmEmpId, empId, empEId, roleId, deptId, desigId, empFirstName, empMiddleName, empLastName, empMobileNo, emailId, statusCd, empKppStatus, hodKppStatus, gmKppStatus, sortName, pageSize, pageOffset);

            List<EmployeeKppStatusResponse> employeeKppStatusResponses = employeeDetail.stream().map(EmployeeKppStatusResponse::new).collect(Collectors.toList());

            Integer sumOfEmployeeRatings = 0;
            Integer sumOfHodRatings = 0;
            Integer sumOfGMRatings = 0;

            Integer cummulativeRatings = 0;
            Float avgCummulativeRatings = 0.0f;
            for(EmployeeKppStatusResponse statusResponse : employeeKppStatusResponses){
                Integer sumOfRatings=0;
                sumOfRatings = Integer.parseInt(statusResponse.getEmpOverallAchive()) +Integer.parseInt(statusResponse.getHodOverallAchieve())+Integer.parseInt(statusResponse.getGmOverallAchieve());
                statusResponse.setSumOfRatings(sumOfRatings);

                sumOfEmployeeRatings += Integer.parseInt(statusResponse.getEmpOverallAchive());
                sumOfHodRatings += Integer.parseInt(statusResponse.getHodOverallAchieve());
                sumOfGMRatings += Integer.parseInt(statusResponse.getGmOverallAchieve());

                statusResponse.setEmpOverallAchive(String.valueOf(sumOfEmployeeRatings));
                cummulativeRatings += sumOfRatings;

            }
            avgCummulativeRatings = Float.valueOf(cummulativeRatings / employeeKppStatusResponses.size());
            employeeKppStatusResponses = employeeKppStatusResponses.stream()
                        .sorted(Comparator.comparing(EmployeeKppStatusResponse::getEkppMonth))
                    .collect(Collectors.toList());

            CummalitiveEmployeeResponse cummalitiveEmployeeResponse = new CummalitiveEmployeeResponse();
            cummalitiveEmployeeResponse.setEmployeeKppStatusResponses(new PageImpl<>(employeeKppStatusResponses, pageable, totalCount));
            cummalitiveEmployeeResponse.setSumOfEmployeeRatings(sumOfEmployeeRatings);
            cummalitiveEmployeeResponse.setSumOfHodRatings(sumOfHodRatings);
            cummalitiveEmployeeResponse.setSumOfGMRatings(sumOfGMRatings);

            cummalitiveEmployeeResponse.setCummulativeRatings(cummulativeRatings);
            cummalitiveEmployeeResponse.setAvgCummulativeRatings(avgCummulativeRatings);

            return KPIResponse.builder()
                    .isSuccess(true)
                    .responseData(cummalitiveEmployeeResponse)
                    .responseMessage(KPIConstants.RECORD_FETCH)
                    .build();
        } catch (Exception ex) {
            log.error("Inside EmployeeKeyPerfParamServiceImpl >> getAllEmployeeDetailsForHod()");
            throw new KPIException("EmployeeKeyPerfParamServiceImpl", false, ex.getMessage());
        }
    }

    private EmployeeLoginEntity convertRequestToEmployeeLogin(EmployeeCreateRequest employeeCreateRequest) {
        EmployeeLoginEntity employeeLogin = new EmployeeLoginEntity();
        employeeLogin.setEmpEId(employeeCreateRequest.getEmpEId());
        employeeLogin.setRoleId(employeeCreateRequest.getRoleId());
        employeeLogin.setDeptId(employeeCreateRequest.getDeptId());
        employeeLogin.setDesigId(employeeCreateRequest.getDesigId());
        employeeLogin.setEmpMobileNo(employeeCreateRequest.getEmpMobileNo());
        employeeLogin.setEmailId(employeeCreateRequest.getEmailId());
        employeeLogin.setEmpPassword(employeeCreateRequest.getEmpMobileNo());
        employeeLogin.setRemark(employeeCreateRequest.getRemark());
        employeeLogin.setStatusCd(employeeCreateRequest.getStatusCd());
        employeeLogin.setCreatedUserId(employeeCreateRequest.getEmployeeId());
        return employeeLogin;
    }

    private EmployeeEntity convertEmployeeCreateRequestToEntity(EmployeeCreateRequest employeeCreateRequest) {
        EmployeeEntity employeeEntity = new EmployeeEntity();
        employeeEntity.setEmpEId(employeeCreateRequest.getEmpEId());
        employeeEntity.setRoleId(employeeCreateRequest.getRoleId());
        employeeEntity.setDeptId(employeeCreateRequest.getDeptId());
        employeeEntity.setDesigId(employeeCreateRequest.getDesigId());
        employeeEntity.setReportingEmpId(employeeCreateRequest.getReportingEmpId());
        employeeEntity.setRegionId(employeeCreateRequest.getRegionId());
        employeeEntity.setSiteId(employeeCreateRequest.getSiteId());
        employeeEntity.setEmpFirstName(employeeCreateRequest.getEmpFirstName());
        employeeEntity.setEmpMiddleName(employeeCreateRequest.getEmpMiddleName());
        employeeEntity.setEmpLastName(employeeCreateRequest.getEmpLastName());
        employeeEntity.setEmpDob(employeeCreateRequest.getEmpDob());
        employeeEntity.setEmpMobileNo(employeeCreateRequest.getEmpMobileNo());
        employeeEntity.setEmpEmerMobileNo(employeeCreateRequest.getEmpEmerMobileNo());
        employeeEntity.setEmpPhoto(employeeCreateRequest.getEmpPhoto());
        employeeEntity.setEmailId(employeeCreateRequest.getEmailId());
        employeeEntity.setTempAddress(employeeCreateRequest.getTempAddress());
        employeeEntity.setPermAddress(employeeCreateRequest.getPermAddress());
        employeeEntity.setEmpGender(employeeCreateRequest.getEmpGender());
        employeeEntity.setEmpBloodgroup(employeeCreateRequest.getEmpBloodgroup());

        employeeEntity.setRemark(employeeCreateRequest.getRemark());
        employeeEntity.setStatusCd(employeeCreateRequest.getStatusCd());
        employeeEntity.setCreatedUserId(employeeCreateRequest.getEmployeeId());
        return employeeEntity;
    }

    private EmployeeEntity convertEmployeeUpdateRequestToEntity(EmployeeUpdateRequest employeeUpdateRequest) {
        EmployeeEntity employeeEntity = new EmployeeEntity();
        employeeEntity.setEmpId(employeeUpdateRequest.getEmpId());
        employeeEntity.setEmpEId(employeeUpdateRequest.getEmpEId());
        employeeEntity.setRoleId(employeeUpdateRequest.getRoleId());
        employeeEntity.setDeptId(employeeUpdateRequest.getDeptId());
        employeeEntity.setDesigId(employeeUpdateRequest.getDesigId());
        employeeEntity.setReportingEmpId(employeeUpdateRequest.getReportingEmpId());
        employeeEntity.setRegionId(employeeUpdateRequest.getRegionId());
        employeeEntity.setSiteId(employeeUpdateRequest.getSiteId());
        employeeEntity.setEmpFirstName(employeeUpdateRequest.getEmpFirstName());
        employeeEntity.setEmpMiddleName(employeeUpdateRequest.getEmpMiddleName());
        employeeEntity.setEmpLastName(employeeUpdateRequest.getEmpLastName());
        employeeEntity.setEmpDob(employeeUpdateRequest.getEmpDob());
        employeeEntity.setEmpMobileNo(employeeUpdateRequest.getEmpMobileNo());
        employeeEntity.setEmpEmerMobileNo(employeeUpdateRequest.getEmpEmerMobileNo());
        employeeEntity.setEmpPhoto(employeeUpdateRequest.getEmpPhoto());
        employeeEntity.setEmailId(employeeUpdateRequest.getEmailId());
        employeeEntity.setTempAddress(employeeUpdateRequest.getTempAddress());
        employeeEntity.setPermAddress(employeeUpdateRequest.getPermAddress());
        employeeEntity.setEmpGender(employeeUpdateRequest.getEmpGender());
        employeeEntity.setEmpBloodgroup(employeeUpdateRequest.getEmpBloodgroup());
        employeeEntity.setRemark(employeeUpdateRequest.getRemark());
        employeeEntity.setStatusCd(employeeUpdateRequest.getStatusCd());
        employeeEntity.setUpdatedUserId(employeeUpdateRequest.getEmployeeId());
        return employeeEntity;
    }

    private String getCreatedEmployeeName(Integer empId) {
        Optional<EmployeeEntity> employeeEntity = employeeRepo.findById(empId);
        return employeeEntity.map(AuditEnabledEntity::getCreatedUserId).orElse(null);
    }

    private Instant getCreatedDateTime(Integer empId) {
        Optional<EmployeeEntity> employeeEntity = employeeRepo.findById(empId);
        return employeeEntity.map(AuditEnabledEntity::getCreatedDate).orElse(null);
    }

}
