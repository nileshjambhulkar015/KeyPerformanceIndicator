package com.futurebizops.kpi.service.serviceimpl;

import com.futurebizops.kpi.constants.KPIConstants;
import com.futurebizops.kpi.entity.AuditEnabledEntity;
import com.futurebizops.kpi.entity.EmployeeAudit;
import com.futurebizops.kpi.entity.EmployeeEntity;
import com.futurebizops.kpi.entity.EmployeeKeyPerfParamDetailsAudit;
import com.futurebizops.kpi.entity.EmployeeKeyPerfParamDetailsEntity;
import com.futurebizops.kpi.entity.EmployeeKeyPerfParamMasterAudit;
import com.futurebizops.kpi.entity.EmployeeKeyPerfParamMasterEntity;
import com.futurebizops.kpi.entity.EmployeeLoginAudit;
import com.futurebizops.kpi.entity.EmployeeLoginEntity;
import com.futurebizops.kpi.entity.KeyPerfParamEntity;
import com.futurebizops.kpi.exception.KPIException;
import com.futurebizops.kpi.repository.EmployeeAuditRepo;
import com.futurebizops.kpi.repository.EmployeeKeyPerfParamDetailsAuditRepo;
import com.futurebizops.kpi.repository.EmployeeKeyPerfParamMasterAuditRepo;
import com.futurebizops.kpi.repository.EmployeeKeyPerfParamMasterRepo;
import com.futurebizops.kpi.repository.EmployeeKeyPerfParamDetailsRepo;
import com.futurebizops.kpi.repository.EmployeeLoginAuditRepo;
import com.futurebizops.kpi.repository.EmployeeLoginRepo;
import com.futurebizops.kpi.repository.EmployeeRepo;
import com.futurebizops.kpi.repository.KeyPerfParameterRepo;
import com.futurebizops.kpi.request.EmployeeCreateRequest;
import com.futurebizops.kpi.request.EmployeeUpdateRequest;
import com.futurebizops.kpi.response.EmployeeResponse;
import com.futurebizops.kpi.response.EmployeeSearchResponse;
import com.futurebizops.kpi.response.KPIResponse;
import com.futurebizops.kpi.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.hql.internal.CollectionSubqueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
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
    private EmployeeKeyPerfParamDetailsRepo employeeKeyPerfParamRepo;

    @Autowired
    private EmployeeKeyPerfParamDetailsAuditRepo employeeKeyPerfParamAuditRepo;

    @Autowired
    private EmployeeKeyPerfParamMasterRepo employeeKeyPerfParamMasterRepo;

    @Autowired
    private EmployeeKeyPerfParamMasterAuditRepo employeeKeyPerfParamMasterAuditRepo;

    Random randEid = null;

    @Transactional
    @Override
    public KPIResponse saveEmployee(EmployeeCreateRequest employeeCreateRequest) {
        //Generate random number for employee till 1000 only.. increase size if you want to create more
        randEid = new Random();
        int empEId = randEid.nextInt(1000);

        Optional<EmployeeEntity> optionalEmployeeEntity = employeeRepo.findByEmpMobileNoOrEmailIdEqualsIgnoreCase(employeeCreateRequest.getEmpMobileNo(), employeeCreateRequest.getEmailId());
        if (optionalEmployeeEntity.isPresent()) {
            log.error("Inside EmployeeServiceImpl >> saveEmployee()");
            throw new KPIException("EmployeeServiceImpl Class", false, "Employee Mobile number or email id already exist");
        }
        EmployeeEntity employeeEntity = convertEmployeeCreateRequestToEntity(employeeCreateRequest);
        employeeEntity.setEmpEId("e" + empEId);
        try {

            List<KeyPerfParamEntity> empKpp = keyPerfParameterRepo.findByRoleIdAndDeptIdAndDesigId(employeeEntity.getRoleId(), employeeEntity.getDeptId(), employeeEntity.getDesigId());
            if(CollectionUtils.isEmpty(empKpp)){
                log.error("Inside EmployeeServiceImpl >> saveEmployee()");
                throw new KPIException("EmployeeServiceImpl Class", false, "Please set the KPP for Designation first");
            }
            List<EmployeeKeyPerfParamDetailsEntity> paramEntities = new ArrayList<>();
            for (KeyPerfParamEntity keyPerfParam : empKpp) {
                EmployeeKeyPerfParamDetailsEntity keyPerfParamEntity = new EmployeeKeyPerfParamDetailsEntity();
                keyPerfParamEntity.setEmpEId("e" + empEId);
                keyPerfParamEntity.setRoleId(employeeEntity.getRoleId());
                keyPerfParamEntity.setDeptId(employeeEntity.getDeptId());
                keyPerfParamEntity.setDesigId(employeeEntity.getDesigId());
                keyPerfParamEntity.setKppId(keyPerfParam.getKppId());
                keyPerfParamEntity.setHodEmpId(employeeCreateRequest.getReportingEmpId());
                keyPerfParamEntity.setGmEmpId(getGmEmpId(employeeCreateRequest.getReportingEmpId()));
                keyPerfParamEntity.setStatusCd("A");
                keyPerfParamEntity.setCreatedUserId(employeeCreateRequest.getEmployeeId());
                paramEntities.add(keyPerfParamEntity);

                EmployeeKeyPerfParamDetailsAudit keyPerfParamAudit = new EmployeeKeyPerfParamDetailsAudit(keyPerfParamEntity);
                employeeKeyPerfParamAuditRepo.save(keyPerfParamAudit);
            }
            employeeKeyPerfParamRepo.saveAll(paramEntities);

            EmployeeKeyPerfParamMasterEntity employeeKeyPerfParamMasterEntity = new EmployeeKeyPerfParamMasterEntity();
            employeeKeyPerfParamMasterEntity.setEmpEId("e" + empEId);
            employeeKeyPerfParamMasterEntity.setRoleId(employeeEntity.getRoleId());
            employeeKeyPerfParamMasterEntity.setDeptId(employeeEntity.getDeptId());
            employeeKeyPerfParamMasterEntity.setDesigId(employeeEntity.getDesigId());
            employeeKeyPerfParamMasterEntity.setTotalOverallAchieve("0");
            employeeKeyPerfParamMasterEntity.setEmpKppStatus("Pending");

            employeeKeyPerfParamMasterEntity.setHodEmpId(employeeCreateRequest.getReportingEmpId());
            employeeKeyPerfParamMasterEntity.setHodKppStatus("Pending");
            employeeKeyPerfParamMasterEntity.setGmEmpId(getGmEmpId(employeeCreateRequest.getReportingEmpId()));
            employeeKeyPerfParamMasterEntity.setGmKppStatus("Pending");
            employeeKeyPerfParamMasterEntity.setStatusCd("A");
            employeeKeyPerfParamMasterEntity.setCreatedUserId(employeeCreateRequest.getEmployeeId());
            employeeKeyPerfParamMasterRepo.save(employeeKeyPerfParamMasterEntity);
            EmployeeKeyPerfParamMasterAudit employeeKeyPerfParamMasterAudit = new EmployeeKeyPerfParamMasterAudit();
            employeeKeyPerfParamMasterAuditRepo.save(employeeKeyPerfParamMasterAudit);

            employeeRepo.save(employeeEntity);
            EmployeeAudit employeeAudit = new EmployeeAudit(employeeEntity);
            employeeAuditRepo.save(employeeAudit);

            EmployeeLoginEntity employeeLoginEntity = convertRequestToEmployeeLogin(employeeCreateRequest);
            employeeLoginEntity.setEmpEId("e" + empEId);
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

    private Integer getGmEmpId(Integer reportinEmpId){
        Optional<EmployeeEntity> employeeEntity = employeeRepo.findById(reportinEmpId);
        if(employeeEntity.isPresent()){
            return  employeeEntity.get().getReportingEmpId();
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
        List<EmployeeEntity> employeeEntities = employeeRepo.findByRoleIdOrDeptIdOrDesigId(roleId,deptId,desigId);
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


    private EmployeeLoginEntity convertRequestToEmployeeLogin(EmployeeCreateRequest employeeCreateRequest) {
        return EmployeeLoginEntity.employeeLoginEntityBuilder()
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
