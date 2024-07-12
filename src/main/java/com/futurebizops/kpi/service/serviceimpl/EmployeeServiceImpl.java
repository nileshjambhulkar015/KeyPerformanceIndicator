package com.futurebizops.kpi.service.serviceimpl;

import com.futurebizops.kpi.constants.KPIConstants;
import com.futurebizops.kpi.entity.AuditEnabledEntity;
import com.futurebizops.kpi.entity.CompanyMasterEntity;
import com.futurebizops.kpi.entity.DepartmentEntity;
import com.futurebizops.kpi.entity.DesignationEntity;
import com.futurebizops.kpi.entity.EmployeeAudit;
import com.futurebizops.kpi.entity.EmployeeEntity;
import com.futurebizops.kpi.entity.EmployeeLoginAudit;
import com.futurebizops.kpi.entity.EmployeeLoginEntity;
import com.futurebizops.kpi.entity.EmployeeTypeEntity;
import com.futurebizops.kpi.entity.KeyPerfParamEntity;
import com.futurebizops.kpi.entity.RegionEntity;
import com.futurebizops.kpi.entity.RoleEntity;
import com.futurebizops.kpi.entity.SiteEntity;
import com.futurebizops.kpi.exception.KPIException;
import com.futurebizops.kpi.repository.CompanyMasterRepo;
import com.futurebizops.kpi.repository.DepartmentRepo;
import com.futurebizops.kpi.repository.DesignationRepo;
import com.futurebizops.kpi.repository.EmployeeAuditRepo;
import com.futurebizops.kpi.repository.EmployeeKppDetailsAuditRepo;
import com.futurebizops.kpi.repository.EmployeeKppDetailsRepo;
import com.futurebizops.kpi.repository.EmployeeKppMasterAuditRepo;
import com.futurebizops.kpi.repository.EmployeeKppMasterRepo;
import com.futurebizops.kpi.repository.EmployeeLoginAuditRepo;
import com.futurebizops.kpi.repository.EmployeeLoginRepo;
import com.futurebizops.kpi.repository.EmployeeRepo;
import com.futurebizops.kpi.repository.EmployeeTypeRepo;
import com.futurebizops.kpi.repository.KeyPerfParameterRepo;
import com.futurebizops.kpi.repository.RegionRepo;
import com.futurebizops.kpi.repository.RoleRepo;
import com.futurebizops.kpi.repository.SiteRepo;
import com.futurebizops.kpi.request.EmployeeCreateRequest;
import com.futurebizops.kpi.request.EmployeeExcelReadData;
import com.futurebizops.kpi.request.EmployeeUpdateRequest;
import com.futurebizops.kpi.response.EmployeeKppStatusResponse;
import com.futurebizops.kpi.response.EmployeeResponse;
import com.futurebizops.kpi.response.EmployeeSearchResponse;
import com.futurebizops.kpi.response.KPIResponse;
import com.futurebizops.kpi.response.dropdown.DepartmentDDResponse;
import com.futurebizops.kpi.response.dropdown.DesignationDDResponse;
import com.futurebizops.kpi.response.dropdown.RoleDDResponse;
import com.futurebizops.kpi.service.EmployeeService;
import com.futurebizops.kpi.utils.DateTimeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
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

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private DepartmentRepo departmentRepo;

    @Autowired
    private DesignationRepo designationRepo;


    @Autowired
    EmployeeTypeRepo employeeTypeRepo;

    @Autowired
    RegionRepo regionRepo;

    @Autowired
    SiteRepo siteRepo;

    @Autowired
    CompanyMasterRepo companyMasterRepo;

    @Transactional
    @Override
    public KPIResponse saveEmployee(EmployeeCreateRequest employeeCreateRequest) {

        KPIResponse kpiResponse = new KPIResponse();
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
            kpiResponse.setSuccess(false);
            kpiResponse.setResponseMessage("Reporting Employee must be present");
            return kpiResponse;
        }
        Optional<EmployeeEntity> optionalEmployeeEntity = employeeRepo.findByEmpEIdEqualsIgnoreCase(employeeCreateRequest.getEmpEId());
        if (optionalEmployeeEntity.isPresent()) {
            log.error("Inside EmployeeServiceImpl >> saveEmployee()");
            kpiResponse.setSuccess(false);
            kpiResponse.setResponseMessage("Employee Id is already available");
            return kpiResponse;
        }
        EmployeeEntity employeeEntity = convertEmployeeCreateRequestToEntity(employeeCreateRequest);
        employeeEntity.setGmEmpId(gmEmpId);

        try {
//need to be check in flow..after remove method
            List<KeyPerfParamEntity> empKpp = keyPerfParameterRepo.findAll();
            if (CollectionUtils.isEmpty(empKpp)) {
                log.error("Inside EmployeeServiceImpl >> saveEmployee()");
                kpiResponse.setSuccess(false);
                kpiResponse.setResponseMessage("Please set the KPP for Designation first");
                return kpiResponse;
            }

            employeeRepo.save(employeeEntity);
            EmployeeAudit employeeAudit = new EmployeeAudit(employeeEntity);
            employeeAuditRepo.save(employeeAudit);

            EmployeeLoginEntity employeeLoginEntity = convertRequestToEmployeeLogin(employeeCreateRequest);
            employeeLoginRepo.save(employeeLoginEntity);
            EmployeeLoginAudit employeeLoginAudit = new EmployeeLoginAudit(employeeLoginEntity);
            employeeLoginAuditRepo.save(employeeLoginAudit);
            kpiResponse.setSuccess(true);
            kpiResponse.setResponseMessage(KPIConstants.RECORD_SUCCESS);
            return kpiResponse;
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
        if (employeeUpdateRequest.getRoleId() == 2 && employeeUpdateRequest.getStatusCd().equalsIgnoreCase("I")) {
            List<EmployeeEntity> employeeEntity = employeeRepo.findByReportingEmpId(employeeUpdateRequest.getEmpId());
            if (employeeEntity.size() > 0) {
                return KPIResponse.builder()
                        .isSuccess(false)
                        .responseMessage("First assign employee to Other HOD")
                        .build();
            }
        }
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
    public KPIResponse getAllEmployeeDetails(Integer empId, String empEId, Integer roleId, Integer deptId, Integer desigId, String empFirstName, String empMiddleName, String empLastName, String empMobileNo, String emailId, String statusCd, Integer empTypeId, Integer companyId, Integer reportingEmpId, Pageable pageable) {
        KPIResponse kpiResponse = new KPIResponse();
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

        Integer totalCount = employeeRepo.getEmployeeCount(empId, empEId, roleId, deptId, desigId, empFirstName, empMiddleName, empLastName, empMobileNo, emailId, statusCd, empTypeId, companyId, reportingEmpId);
        List<Object[]> employeeDetail = employeeRepo.getEmployeeDetail(empId, empEId, roleId, deptId, desigId, empFirstName, empMiddleName, empLastName, empMobileNo, emailId, statusCd, empTypeId, companyId, reportingEmpId, sortName, pageSize, pageOffset);
        if (employeeDetail.size() > 0) {
            List<EmployeeResponse> employeeResponses = employeeDetail.stream().map(EmployeeResponse::new).collect(Collectors.toList());
            for (EmployeeResponse response : employeeResponses) {
                Optional<EmployeeEntity> employeeEntity = employeeRepo.findById(response.getReportingEmpId());
                if (employeeEntity.isPresent()) {
                    EmployeeEntity entity = employeeEntity.get();
                    response.setReportingHODName(entity.getEmpFirstName() + " " + entity.getEmpMiddleName() + " " + entity.getEmpLastName());
                    response.setReportingHODEId(entity.getEmpEId());
                }
            }

            employeeResponses = employeeResponses.stream()
                    .sorted(Comparator.comparing(EmployeeResponse::getDeptName))
                    .collect(Collectors.toList());
            kpiResponse.setSuccess(true);
            kpiResponse.setResponseData(new PageImpl<>(employeeResponses, pageable, totalCount));
            kpiResponse.setResponseMessage(KPIConstants.RECORD_FETCH);

        } else {
            kpiResponse.setSuccess(false);
            kpiResponse.setResponseMessage(KPIConstants.RECORD_NOT_FOUND);
        }
        return kpiResponse;
    }

    @Override
    public KPIResponse getAllEmployeeAdvanceSearch(Integer roleId, Integer deptId, Integer desigId, Integer regionId, Integer siteId, Integer companyId, Integer empTypeId, Pageable pageable) {
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

        Integer totalCount = employeeRepo.getEmployeeAdvanceSearchCount(roleId, deptId, desigId, regionId, siteId, companyId, empTypeId);
        List<Object[]> employeeDetail = employeeRepo.getEmployeeAdvanceSearchDetails(roleId, deptId, desigId, regionId, siteId, companyId, empTypeId, sortName, pageSize, pageOffset);

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
        employeeEntity.setEmpTypeId(employeeCreateRequest.getEmpTypeId());
        employeeEntity.setReportingEmpId(employeeCreateRequest.getReportingEmpId());
        employeeEntity.setRegionId(employeeCreateRequest.getRegionId());
        employeeEntity.setSiteId(employeeCreateRequest.getSiteId());
        employeeEntity.setCompanyId(employeeCreateRequest.getCompanyId());
        employeeEntity.setEmpFirstName(employeeCreateRequest.getEmpFirstName());
        employeeEntity.setEmpMiddleName(employeeCreateRequest.getEmpMiddleName());
        employeeEntity.setEmpLastName(employeeCreateRequest.getEmpLastName());
        employeeEntity.setEmpDob(StringUtils.isNotEmpty(employeeCreateRequest.getEmpDob())?DateTimeUtils.convertStringToInstant(employeeCreateRequest.getEmpDob()):null);
        employeeEntity.setEmpMobileNo(employeeCreateRequest.getEmpMobileNo());
        employeeEntity.setEmpEmerMobileNo(employeeCreateRequest.getEmpEmerMobileNo());
        employeeEntity.setEmpPhoto(employeeCreateRequest.getEmpPhoto());
        employeeEntity.setEmailId(employeeCreateRequest.getEmailId());
        employeeEntity.setTempAddress(employeeCreateRequest.getTempAddress());
        employeeEntity.setPermAddress(employeeCreateRequest.getPermAddress());
        employeeEntity.setEmpGender(employeeCreateRequest.getEmpGender());
        employeeEntity.setEmpBloodgroup(employeeCreateRequest.getEmpBloodgroup());
        employeeEntity.setGmEmpId(employeeCreateRequest.getGmEmpId());
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
        employeeEntity.setEmpTypeId(employeeUpdateRequest.getEmpTypeId());
        employeeEntity.setReportingEmpId(employeeUpdateRequest.getReportingEmpId());
        employeeEntity.setRegionId(employeeUpdateRequest.getRegionId());
        employeeEntity.setSiteId(employeeUpdateRequest.getSiteId());
        employeeEntity.setCompanyId(employeeUpdateRequest.getCompanyId());
        employeeEntity.setEmpFirstName(employeeUpdateRequest.getEmpFirstName());
        employeeEntity.setEmpMiddleName(employeeUpdateRequest.getEmpMiddleName());
        employeeEntity.setEmpLastName(employeeUpdateRequest.getEmpLastName());
        employeeEntity.setEmpDob(StringUtils.isNotEmpty(employeeUpdateRequest.getEmpDob())? DateTimeUtils.convertStringToInstant(employeeUpdateRequest.getEmpDob()):null);
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


    public KPIResponse processExcelFile(MultipartFile file) {
        {
            Integer currentRow = 0;
            List<EmployeeCreateRequest> employeeCreateRequests = new ArrayList<>();
            List<EmployeeCreateRequest> employeeNotSavedRecords = new ArrayList<>();
            List<EmployeeExcelReadData> employeeData = new ArrayList<>();
            byte[] excelBytes = null;
            if (file.isEmpty()) {
                throw new KPIException("EmployeeServiceImpl", false, "File not uploaded");
            }

            try {
                excelBytes = file.getBytes();

            } catch (Exception ex) {
                log.error("Inside EmployeeServiceImpl >> updateEmployee()");
                throw new KPIException("EmployeeServiceImpl", false, ex.getMessage());
            }
            try (InputStream inputStream = new ByteArrayInputStream(excelBytes)) {
                Workbook workbook = WorkbookFactory.create(inputStream);
                Sheet sheet = workbook.getSheetAt(0);
                int startRow = 1;

                for (int rowIndex = startRow; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                    Row row = sheet.getRow(rowIndex);

                    if (row != null) {
                        currentRow = rowIndex;
                        EmployeeExcelReadData model = new EmployeeExcelReadData();
                        model.setEmpTypeName(row.getCell(0).getStringCellValue().trim());
                        model.setRegionName(row.getCell(1).getStringCellValue().trim());
                        model.setSiteName(row.getCell(2).getStringCellValue().trim());
                        model.setCompanyName(row.getCell(3).getStringCellValue().trim());
                        model.setRoleName(row.getCell(4).getStringCellValue().trim());
                        model.setDeptName(row.getCell(5).getStringCellValue().trim());
                        model.setDesigName(row.getCell(6).getStringCellValue().trim());
                        model.setReportingEmpEid(row.getCell(7).getStringCellValue().trim());
                        model.setGmEmpId(row.getCell(8).getStringCellValue().trim());

                        model.setEmpFirstName(row.getCell(9).getStringCellValue().trim());
                        model.setEmpMiddleName(row.getCell(10).getStringCellValue().trim());
                        model.setEmpLastName(row.getCell(11).getStringCellValue().trim());
                        model.setEmpEid(row.getCell(12).getStringCellValue().trim());
                        Long mobile1 = (long) row.getCell(13).getNumericCellValue();
                        Long mobile2 = (long) row.getCell(14).getNumericCellValue();
                        model.setEmpMobileNo(String.valueOf(mobile1));
                        model.setEmpEmerMobileNo(String.valueOf(mobile2));
                        model.setTempAddress(row.getCell(15).getStringCellValue().trim());
                        model.setPermAddress(row.getCell(16).getStringCellValue().trim());
                        model.setEmailId(row.getCell(17).getStringCellValue().trim());
                        model.setEmpGender(row.getCell(18).getStringCellValue().trim());
                        model.setEmpBloodgroup(row.getCell(19).getStringCellValue().trim());
                        model.setCreatedByEmployeeId(row.getCell(20).getStringCellValue().trim());
                        model.setEmpDOB(row.getCell(21).getLocalDateTimeCellValue().toString());
                        model.setStatusCd("A");

                        employeeData.add(model);
                    }
                }
                workbook.close();
            } catch (Exception ex) {
                log.error("Inside EmployeeServiceImpl >> processExcelFile() :{} ",ex);
                throw new KPIException("EmployeeServiceImpl", false, "Issue in row no: " + currentRow);
            }

            Integer currentExcelRow = 0;
            for (EmployeeExcelReadData request : employeeData) {
                try {
                    if (StringUtils.isNotEmpty(request.getEmpEid())) {
                        currentExcelRow++;
                        EmployeeCreateRequest employeeCreateRequest = new EmployeeCreateRequest();

                        Integer empTypeId = getEmployeeTypeId(request.getEmpTypeName().trim());
                        employeeCreateRequest.setEmpTypeId(empTypeId);

                        Integer regionId = getRegionId(request.getRegionName().trim());
                        employeeCreateRequest.setRegionId(regionId);

                        Integer siteId = getSiteId(regionId, request.getSiteName().trim());
                        employeeCreateRequest.setSiteId(siteId);

                        Integer companyId = getCompanyId(request.getCompanyName().trim(), regionId, siteId);
                        employeeCreateRequest.setCompanyId(companyId);

                        Integer roleId = getRoleId(request.getRoleName().trim());
                        employeeCreateRequest.setRoleId(roleId);

                        Integer deptId = getDeptId(request.getDeptName().trim());
                        employeeCreateRequest.setDeptId(deptId);

                        Integer desigId = getDesigId(request.getDesigName().trim(), deptId, roleId);
                        employeeCreateRequest.setDesigId(desigId);

                        employeeCreateRequest.setEmpFirstName(request.getEmpFirstName());
                        employeeCreateRequest.setEmpMiddleName(request.getEmpMiddleName());
                        employeeCreateRequest.setEmpLastName(request.getEmpLastName());
                        employeeCreateRequest.setEmpEId(request.getEmpEid());

                        employeeCreateRequest.setEmpMobileNo(request.getEmpMobileNo());
                        employeeCreateRequest.setEmpEmerMobileNo(request.getEmpEmerMobileNo());

                        employeeCreateRequest.setPermAddress(request.getPermAddress());
                        employeeCreateRequest.setTempAddress(request.getTempAddress());

                        employeeCreateRequest.setEmailId(request.getEmailId());
                        employeeCreateRequest.setEmpGender(request.getEmpGender());
                        employeeCreateRequest.setEmpBloodgroup(request.getEmpBloodgroup());

                        Integer reportingEmpId = getReportingEmpId(request.getReportingEmpEid());
                        employeeCreateRequest.setReportingEmpId(reportingEmpId);

                        Integer gmId = getReportingEmpId(request.getGmEmpId());
                        employeeCreateRequest.setGmEmpId(gmId);

                        employeeCreateRequest.setEmpPhoto("");

                        employeeCreateRequest.setEmployeeId(request.getCreatedByEmployeeId());

                        employeeCreateRequest.setEmpDob(request.getEmpDOB());
                        employeeCreateRequest.setStatusCd("A");

                        employeeCreateRequests.add(employeeCreateRequest);//final request
                    } else {
                        // if record is not present
                    }

                } catch (Exception ex) {
                    throw new KPIException("EmployeeServiceImpl", false, "Issue in row no: " + currentExcelRow);

                } finally {
                  //  System.out.println("employeeCreateRequests::" + employeeCreateRequests);
                }
            }
            for (EmployeeCreateRequest request : employeeCreateRequests) {
                try {
                    if (request.getRoleId() != null && request.getDeptId() != null && request.getDesigId() != null && request.getReportingEmpId() != null &&
                            request.getGmEmpId() != null && null != request.getEmpTypeId() && null != request.getRegionId() && null != request.getSiteId() && null != request.getCompanyId()
                    ) {
                        saveEmployeeFromExcel(request);
                    } else {
                        employeeNotSavedRecords.add(request);
                    }
                } catch (Exception ex) {
                    employeeNotSavedRecords.add(request);
                }
            }
            ///export data in excel logic need to return
            return KPIResponse.builder().responseData(employeeNotSavedRecords).build();
        }
    }

    @Override
    public List<RoleDDResponse> getRolesExceptEmployee(Integer roleId, String roleName) {
        List<Object[]> roleData = employeeRepo.getRolesExceptEmployee(roleId, roleName);
        return roleData.stream().map(RoleDDResponse::new).collect(Collectors.toList());
    }

    @Override
    public List<DepartmentDDResponse> getDepartmentFromEmployee(Integer roleId, Integer deptId) {
        List<Object[]> roleData = employeeRepo.getDepartmentFromEmployee(roleId, deptId);
        return roleData.stream().map(DepartmentDDResponse::new).collect(Collectors.toList());
    }

    @Override
    public List<DesignationDDResponse> getDesignationFromEmployee(Integer roleId, Integer deptId, Integer desigId) {
        List<Object[]> roleData = employeeRepo.getDesignationFromEmployee(roleId, deptId, desigId);
        return roleData.stream().map(DesignationDDResponse::new).collect(Collectors.toList());
    }

//Save employee from excel

    @Transactional
    public KPIResponse saveEmployeeFromExcel(EmployeeCreateRequest employeeCreateRequest) {

        KPIResponse kpiResponse = new KPIResponse();

        if (null == employeeCreateRequest.getReportingEmpId()) {
            log.error("Inside EmployeeServiceImpl >> saveEmployee()");
            kpiResponse.setSuccess(false);
            kpiResponse.setResponseMessage("Reporting Employee must be present");
            return kpiResponse;
        }
        Optional<EmployeeEntity> optionalEmployeeEntity = employeeRepo.findByEmpEIdEqualsIgnoreCase(employeeCreateRequest.getEmpEId());
        if (optionalEmployeeEntity.isPresent()) {
            log.error("Inside EmployeeServiceImpl >> saveEmployee()");
            kpiResponse.setSuccess(false);
            kpiResponse.setResponseMessage("Employee Id is already available");
            return kpiResponse;
        }
        EmployeeEntity employeeEntity = convertEmployeeCreateRequestToEntity(employeeCreateRequest);

        try {
//need to be check in flow..after remove method
            List<KeyPerfParamEntity> empKpp = keyPerfParameterRepo.findAll();
            if (CollectionUtils.isEmpty(empKpp)) {
                log.error("Inside EmployeeServiceImpl >> saveEmployee()");
                kpiResponse.setSuccess(false);
                kpiResponse.setResponseMessage("Please set the KPP for Designation first");
                return kpiResponse;
            }

            employeeRepo.save(employeeEntity);
            EmployeeAudit employeeAudit = new EmployeeAudit(employeeEntity);
            employeeAuditRepo.save(employeeAudit);

            EmployeeLoginEntity employeeLoginEntity = convertRequestToEmployeeLogin(employeeCreateRequest);
            employeeLoginRepo.save(employeeLoginEntity);
            EmployeeLoginAudit employeeLoginAudit = new EmployeeLoginAudit(employeeLoginEntity);
            employeeLoginAuditRepo.save(employeeLoginAudit);
            kpiResponse.setSuccess(true);
            kpiResponse.setResponseMessage(KPIConstants.RECORD_SUCCESS);
            return kpiResponse;
        } catch (Exception ex) {
            log.error("Inside EmployeeServiceImpl >> saveEmployee()");
            throw new KPIException("EmployeeServiceImpl", false, ex.getMessage());
        }
    }

    private Integer getRoleId(String roleName) {
        Optional<RoleEntity> optionalRoleEntity = roleRepo.findByRoleNameEqualsIgnoreCase(roleName);
        if (optionalRoleEntity.isPresent()) {
            return optionalRoleEntity.get().getRoleId();
        }
        return null;
    }

    private Integer getDeptId(String deptName) {
        Optional<DepartmentEntity> optionalDepartmentEntity = departmentRepo.findByDeptNameEqualsIgnoreCase(deptName);
        if (optionalDepartmentEntity.isPresent()) {
            return optionalDepartmentEntity.get().getDeptId();
        }
        return null;
    }

    private Integer getDesigId(String desigName, Integer deptId, Integer roleId) {
        Optional<DesignationEntity> optionalDesignationEntity = designationRepo.findByDesigNameEqualsIgnoreCaseAndDeptId(desigName, deptId);

        if (optionalDesignationEntity.isPresent()) {
            return optionalDesignationEntity.get().getDesigId();
        }
        return null;
    }

    private Integer getReportingEmpId(String empEid) {
        Optional<EmployeeEntity> optionalEmployeeEntity = employeeRepo.findByEmpEIdEqualsIgnoreCase(empEid);

        if (optionalEmployeeEntity.isPresent()) {
            return optionalEmployeeEntity.get().getEmpId();
        }
        return null;
    }


    private Integer getEmployeeTypeId(String empEmpType) {
        Optional<EmployeeTypeEntity> employeeType = employeeTypeRepo.findByEmpTypeNameEqualsIgnoreCase(empEmpType);

        if (employeeType.isPresent()) {
            return employeeType.get().getEmpTypeId();
        }
        return null;
    }

    private Integer getRegionId(String regionName) {
        Optional<RegionEntity> optionalRegionEntity = regionRepo.findByRegionNameEqualsIgnoreCase(regionName);

        if (optionalRegionEntity.isPresent()) {
            return optionalRegionEntity.get().getRegionId();
        }
        return null;
    }

    private Integer getSiteId(Integer regionId, String siteName) {
        Optional<SiteEntity> optionalSiteEntity = siteRepo.findByRegionIdAndSiteNameEqualsIgnoreCase(regionId, siteName);

        if (optionalSiteEntity.isPresent()) {
            return optionalSiteEntity.get().getSiteId();
        }
        return null;
    }

    private Integer getCompanyId(String companyName, Integer regionId, Integer siteId) {
        Optional<CompanyMasterEntity> companyMasterEntity = companyMasterRepo.findByCompanyNameEqualsIgnoreCaseAndRegionIdAndSiteId(companyName, regionId, siteId);

        if (companyMasterEntity.isPresent()) {
            return companyMasterEntity.get().getCompanyId();
        }
        return null;
    }

}
