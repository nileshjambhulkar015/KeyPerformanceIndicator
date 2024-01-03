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
import com.futurebizops.kpi.response.EmployeeResponse;
import com.futurebizops.kpi.response.EmployeeSearchResponse;
import com.futurebizops.kpi.response.EmployeeKppStatusResponse;
import com.futurebizops.kpi.response.KPIResponse;
import com.futurebizops.kpi.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.persistence.Column;
import javax.transaction.Transactional;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Random;
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
            List<EmployeeKppDetailsEntity> paramEntities = new ArrayList<>();
            for (KeyPerfParamEntity keyPerfParam : empKpp) {
                EmployeeKppDetailsEntity keyPerfParamEntity = new EmployeeKppDetailsEntity();
                keyPerfParamEntity.setEmpEId(employeeEntity.getEmpEId());
                keyPerfParamEntity.setRoleId(employeeEntity.getRoleId());
                keyPerfParamEntity.setDeptId(employeeEntity.getDeptId());
                keyPerfParamEntity.setDesigId(employeeEntity.getDesigId());
                keyPerfParamEntity.setKppId(keyPerfParam.getKppId());
                keyPerfParamEntity.setEmpAchivedWeight("0");
                keyPerfParamEntity.setEmpOverallAchieve("0");
                keyPerfParamEntity.setEmpOverallTaskComp("0");
                keyPerfParamEntity.setHodEmpId(employeeCreateRequest.getReportingEmpId());
                keyPerfParamEntity.setHodAchivedWeight("0");
                keyPerfParamEntity.setHodOverallAchieve("0");
                keyPerfParamEntity.setHodOverallTaskComp("0");
                keyPerfParamEntity.setGmEmpId(gmEmpId);
                keyPerfParamEntity.setGmAchivedWeight("0");
                keyPerfParamEntity.setGmOverallAchieve("0");
                keyPerfParamEntity.setGmOverallTaskComp("0");
                keyPerfParamEntity.setStatusCd("A");
                keyPerfParamEntity.setCreatedUserId(employeeCreateRequest.getEmployeeId());
                paramEntities.add(keyPerfParamEntity);

                EmployeeKppDetailsAudit keyPerfParamAudit = new EmployeeKppDetailsAudit(keyPerfParamEntity);
                employeeKeyPerfParamAuditRepo.save(keyPerfParamAudit);
            }
            employeeKeyPerfParamRepo.saveAll(paramEntities);

            EmployeeKppMasterEntity kppMasterEntity = new EmployeeKppMasterEntity();
            kppMasterEntity.setEmpEId(employeeEntity.getEmpEId());
            kppMasterEntity.setRoleId(employeeEntity.getRoleId());
            kppMasterEntity.setDeptId(employeeEntity.getDeptId());
            kppMasterEntity.setDesigId(employeeEntity.getDesigId());
            kppMasterEntity.setTotalAchivedWeight("0");
            kppMasterEntity.setTotalOverallAchieve("0");
            kppMasterEntity.setTotalOverallTaskComp("0");
            kppMasterEntity.setEmpKppStatus("Pending");
            kppMasterEntity.setEmpKppAppliedDate(null);
            kppMasterEntity.setEmpRemark(null);
            kppMasterEntity.setEmpEvidence(null);
            kppMasterEntity.setHodEmpId(employeeCreateRequest.getReportingEmpId());
            kppMasterEntity.setHodAchivedWeight("0");
            kppMasterEntity.setHodOverallAchieve("0");
            kppMasterEntity.setHodOverallTaskComp("0");
            kppMasterEntity.setHodKppAppliedDate(null);
            kppMasterEntity.setHodKppStatus("Pending");
            kppMasterEntity.setHodRemark(null);
            kppMasterEntity.setGmEmpId(gmEmpId);
            kppMasterEntity.setGmKppStatus("Pending");
            kppMasterEntity.setGmAchivedWeight("0");
            kppMasterEntity.setGmOverallAchieve("0");
            kppMasterEntity.setGmOverallTaskComp("0");
            kppMasterEntity.setGmKppAppliedDate(null);
            kppMasterEntity.setGmRemark(null);
            kppMasterEntity.setRemark(null);
            kppMasterEntity.setStatusCd("A");
            kppMasterEntity.setCreatedUserId(employeeCreateRequest.getEmployeeId());

            employeeKeyPerfParamMasterRepo.save(kppMasterEntity);
            EmployeeKppMasterAudit employeeKeyPerfParamMasterAudit = new EmployeeKppMasterAudit();
            employeeKeyPerfParamMasterAuditRepo.save(employeeKeyPerfParamMasterAudit);

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

    private EmployeeLoginEntity convertRequestToEmployeeLogin(EmployeeCreateRequest employeeCreateRequest) {
        return EmployeeLoginEntity.employeeLoginEntityBuilder()
                .empEId(employeeCreateRequest.getEmpEId())
                .roleId(employeeCreateRequest.getRoleId())
                .deptId(employeeCreateRequest.getDeptId())
                .desigId(employeeCreateRequest.getDesigId())
                .empMobileNo(employeeCreateRequest.getEmpMobileNo())
                .emailId(employeeCreateRequest.getEmailId())
                .empPassword(employeeCreateRequest.getEmpMobileNo())
                .remark(employeeCreateRequest.getRemark())
                .statusCd(employeeCreateRequest.getStatusCd())
                .createdUserId(employeeCreateRequest.getEmployeeId())
                .build();
    }

    private EmployeeEntity convertEmployeeCreateRequestToEntity(EmployeeCreateRequest employeeCreateRequest) {
        return EmployeeEntity.employeeEntityBuilder()
                .empEId(employeeCreateRequest.getEmpEId())
                .roleId(employeeCreateRequest.getRoleId())
                .depId(employeeCreateRequest.getDeptId())
                .desigId(employeeCreateRequest.getDesigId())
                .reportingEmpId(employeeCreateRequest.getReportingEmpId())
                .regionId(employeeCreateRequest.getRegionId())
                .siteId(employeeCreateRequest.getSiteId())
                .empFirstName(employeeCreateRequest.getEmpFirstName())
                .empMiddleName(employeeCreateRequest.getEmpMiddleName())
                .empLastName(employeeCreateRequest.getEmpLastName())
                .empDob(employeeCreateRequest.getEmpDob())
                .empMobileNo(employeeCreateRequest.getEmpMobileNo())
                .empEmerMobileNo(employeeCreateRequest.getEmpEmerMobileNo())
                .empPhoto(employeeCreateRequest.getEmpPhoto())
                .emailId(employeeCreateRequest.getEmailId())
                .tempAddress(employeeCreateRequest.getTempAddress())
                .permAddress(employeeCreateRequest.getPermAddress())
                .empGender(employeeCreateRequest.getEmpGender())
                .empBloodgroup(employeeCreateRequest.getEmpBloodgroup())
                .remark(employeeCreateRequest.getRemark())
                .statusCd(employeeCreateRequest.getStatusCd())
                .createdUserId(employeeCreateRequest.getEmployeeId())
                .build();
    }

    private EmployeeEntity convertEmployeeUpdateRequestToEntity(EmployeeUpdateRequest employeeUpdateRequest) {
        return EmployeeEntity.employeeEntityBuilder()
                .empId(employeeUpdateRequest.getEmpId())
                .empEId(employeeUpdateRequest.getEmpEId())
                .roleId(employeeUpdateRequest.getRoleId())
                .depId(employeeUpdateRequest.getDeptId())
                .desigId(employeeUpdateRequest.getDesigId())
                .reportingEmpId(employeeUpdateRequest.getReportingEmpId())
                .regionId(employeeUpdateRequest.getRegionId())
                .siteId(employeeUpdateRequest.getSiteId())
                .empFirstName(employeeUpdateRequest.getEmpFirstName())
                .empMiddleName(employeeUpdateRequest.getEmpMiddleName())
                .empLastName(employeeUpdateRequest.getEmpLastName())
                .empDob(employeeUpdateRequest.getEmpDob())
                .empMobileNo(employeeUpdateRequest.getEmpMobileNo())
                .empEmerMobileNo(employeeUpdateRequest.getEmpEmerMobileNo())
                .empPhoto(employeeUpdateRequest.getEmpPhoto())
                .emailId(employeeUpdateRequest.getEmailId())
                .tempAddress(employeeUpdateRequest.getTempAddress())
                .permAddress(employeeUpdateRequest.getPermAddress())
                .empGender(employeeUpdateRequest.getEmpGender())
                .empBloodgroup(employeeUpdateRequest.getEmpBloodgroup())
                .remark(employeeUpdateRequest.getRemark())
                .statusCd(employeeUpdateRequest.getStatusCd())
                .createdUserId(getCreatedEmployeeName(employeeUpdateRequest.getEmpId()))
                .createdDate(getCreatedDateTime(employeeUpdateRequest.getEmpId()))
                .updatedUserId(employeeUpdateRequest.getEmployeeId())
                .build();
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
