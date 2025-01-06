package com.futurebizops.kpi.service.serviceimpl;

import com.futurebizops.kpi.constants.KPIConstants;
import com.futurebizops.kpi.entity.DepartmentAudit;
import com.futurebizops.kpi.entity.DepartmentEntity;
import com.futurebizops.kpi.entity.RoleEntity;
import com.futurebizops.kpi.exception.KPIException;
import com.futurebizops.kpi.repository.DepartmentAuditRepo;
import com.futurebizops.kpi.repository.DepartmentRepo;
import com.futurebizops.kpi.repository.RoleRepo;
import com.futurebizops.kpi.request.DepartmentCreateRequest;
import com.futurebizops.kpi.request.uploadexcel.DepartmentExcelReadData;
import com.futurebizops.kpi.request.DepartmentUpdateRequest;
import com.futurebizops.kpi.response.DepartmentReponse;
import com.futurebizops.kpi.response.KPIResponse;
import com.futurebizops.kpi.response.dropdown.DepartmentDDResponse;
import com.futurebizops.kpi.service.DepartmentService;
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
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentRepo departmentRepo;

    @Autowired
    private DepartmentAuditRepo departmentAuditRepo;

    @Autowired
    RoleRepo roleRepo;

    @Override
    @Retryable(include = {KPIException.class}, maxAttemptsExpression = "${retry-max-attempts}")
    public KPIResponse saveDepartment(DepartmentCreateRequest departmentCreateRequest) {
        log.debug("Inside DepartmentServiceImpl >> saveDepartment() departmentCreateRequest : {}", departmentCreateRequest);
        Optional<DepartmentEntity> optionalDepartmentEntity = departmentRepo.findByDeptNameEqualsIgnoreCase(departmentCreateRequest.getDeptName());
        if (optionalDepartmentEntity.isPresent()) {
            log.error("Inside DepartmentServiceImpl >> saveDepartment() Department name already exist");
            return KPIResponse.builder()
                    .isSuccess(false)
                    .responseMessage("Department name already exist")
                    .build();
        }

        DepartmentEntity departmentEntity = convertDepartmentCreateRequestToEntity(departmentCreateRequest);
        try {
            departmentRepo.save(departmentEntity);
            DepartmentAudit partAudit = new DepartmentAudit(departmentEntity);
            departmentAuditRepo.save(partAudit);
            return KPIResponse.builder()
                    .isSuccess(true)
                    .responseMessage(KPIConstants.RECORD_SUCCESS)
                    .build();
        } catch (Exception ex) {
            log.error("Inside DepartmentServiceImpl >> saveDepartment() : {}", ex);
            throw new KPIException("DepartmentServiceImpl >> saveDepartment()", false, ex.getMessage());
        }
    }

    @Transactional
    @Override
    @Retryable(include = {KPIException.class}, maxAttemptsExpression = "${retry-max-attempts}")
    public KPIResponse deleteDepartmentDetails(Integer deptId) {
        log.debug("Inside DepartmentServiceImpl >> deleteDepartmentDetails() deptId : {}", deptId);
        KPIResponse kpiResponse = new KPIResponse();
        try {
            departmentRepo.deleteDepartmentDetails(deptId);
            kpiResponse.setSuccess(true);
            kpiResponse.setResponseMessage("Department details deleted Successfully");
            return kpiResponse;
        } catch (Exception ex) {
            log.error("Inside DepartmentServiceImpl >> deleteDepartmentDetails() : {}", ex);
            throw new KPIException("DepartmentServiceImpl >> deleteDepartmentDetails()", false, ex.getMessage());
        }
    }

    @Override
    @Retryable(include = {KPIException.class}, maxAttemptsExpression = "${retry-max-attempts}")
    public KPIResponse updateDepartment(DepartmentUpdateRequest departmentUpdateRequest) {
        log.debug("Inside DepartmentServiceImpl >> updateDepartment() departmentUpdateRequest : {}", departmentUpdateRequest);
        try {
            Optional<DepartmentEntity> optionalDepartmentEntity = departmentRepo.findById(departmentUpdateRequest.getDeptId());
            DepartmentEntity departmentEntity = null;
            if (optionalDepartmentEntity.isPresent()) {
                departmentEntity = optionalDepartmentEntity.get();
                departmentEntity.setDeptName(departmentUpdateRequest.getDeptName());
                departmentEntity.setRemark(departmentUpdateRequest.getRemark());
                departmentEntity.setUpdatedUserId(departmentUpdateRequest.getEmployeeId());
                departmentRepo.save(departmentEntity);
                DepartmentAudit departmentAudit = new DepartmentAudit(departmentEntity);
                departmentAuditRepo.save(departmentAudit);
                return KPIResponse.builder()
                        .isSuccess(true)
                        .responseMessage(KPIConstants.RECORD_UPDATE)
                        .build();
            } else {
                return KPIResponse.builder()
                        .isSuccess(false)
                        .responseMessage("Department is not available")
                        .build();
            }
        } catch (Exception ex) {
            log.error("Inside DepartmentServiceImpl >> updateDepartment() : {}", ex);
            throw new KPIException("DepartmentServiceImpl >> updateDepartment()", false, ex.getMessage());
        }
    }

    @Override
    @Retryable(include = {KPIException.class}, maxAttemptsExpression = "${retry-max-attempts}")
    public KPIResponse findDepartmentDetails(Integer deptId, String deptName, String statusCd, Pageable requestPageable) {
        log.debug("Inside DepartmentServiceImpl >> findDepartmentDetails() deptId : {}, deptName : {}", deptId, deptName);
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

        try{
        Integer totalCount = departmentRepo.getDepartmentCount(deptId, deptName, statusCd);
        List<Object[]> departmentData = departmentRepo.getDepartmentDetail(deptId, deptName, statusCd, sortName, pageSize, pageOffset);

        List<DepartmentReponse> departmentReponses = departmentData.stream().map(DepartmentReponse::new).collect(Collectors.toList());

        departmentReponses = departmentReponses.stream()
                .sorted(Comparator.comparing(DepartmentReponse::getDeptName))
                .collect(Collectors.toList());

        if (departmentReponses.size() > 0) {
            return KPIResponse.builder()
                    .isSuccess(true)
                    .responseData(new PageImpl<>(departmentReponses, requestPageable, totalCount))
                    .responseMessage(KPIConstants.RECORD_FETCH)
                    .build();
        }
        } catch (Exception ex) {
            log.error("Inside DepartmentServiceImpl >> findDepartmentDetails() : {}", ex);
            throw new KPIException("DepartmentServiceImpl >> findDepartmentDetails()", false, ex.getMessage());
        }
        return KPIResponse.builder()
                .isSuccess(false)
                .build();
    }

    @Override
    @Retryable(include = {KPIException.class}, maxAttemptsExpression = "${retry-max-attempts}")
    public List<DepartmentDDResponse> findAllDepartmentExceptGM() {
        log.debug("Inside DepartmentServiceImpl >> findAllDepartmentExceptGM()");

        try{
        List<DepartmentEntity> departmentEntities = departmentRepo.findAllDepartmentDetailsForEmployee();
        DepartmentDDResponse departmentDDResponse = null;
        List<DepartmentDDResponse> departmentDDResponses = new ArrayList<>();
        for (DepartmentEntity departmentEntity : departmentEntities) {
            if (departmentEntity.getDeptId() != 1) {
                departmentDDResponse = new DepartmentDDResponse();
                departmentDDResponse.setDeptId(departmentEntity.getDeptId());
                departmentDDResponse.setDeptName(departmentEntity.getDeptName());
                departmentDDResponses.add(departmentDDResponse);
            }
        }
        return departmentDDResponses;
        } catch (Exception ex) {
            log.error("Inside DepartmentServiceImpl >> findAllDepartmentExceptGM() : {}", ex);
            throw new KPIException("DepartmentServiceImpl >> findAllDepartmentExceptGM()", false, ex.getMessage());
        }
    }

    @Override
    @Retryable(include = {KPIException.class}, maxAttemptsExpression = "${retry-max-attempts}")
    public List<DepartmentDDResponse> ddAllDepartment() {
        log.debug("Inside DepartmentServiceImpl >> ddAllDepartment()");
        try{
        List<DepartmentEntity> departmentEntities = departmentRepo.findAllDepartmentDetailsForEmployee();
        DepartmentDDResponse departmentDDResponse = null;
        List<DepartmentDDResponse> departmentDDResponses = new ArrayList<>();

        for (DepartmentEntity departmentEntity : departmentEntities) {
            departmentDDResponse = new DepartmentDDResponse();
            departmentDDResponse.setDeptId(departmentEntity.getDeptId());
            departmentDDResponse.setDeptName(departmentEntity.getDeptName());
            departmentDDResponses.add(departmentDDResponse);
        }
        return departmentDDResponses;
        } catch (Exception ex) {
            log.error("Inside DepartmentServiceImpl >> ddAllDepartment() : {}", ex);
            throw new KPIException("DepartmentServiceImpl >> ddAllDepartment()", false, ex.getMessage());
        }
    }

    @Override
    @Retryable(include = {KPIException.class}, maxAttemptsExpression = "${retry-max-attempts}")
    public List<DepartmentReponse> findAllDepartmentDetails() {
        log.debug("Inside DepartmentServiceImpl >> findAllDepartmentDetails()");

        try{
        List<DepartmentEntity> departmentEntities = departmentRepo.findAllDepartmentDetailsForEmployee();
        List<DepartmentReponse> departmentReponses = new ArrayList<>();
        DepartmentReponse departmentReponse = null;
        for (DepartmentEntity departmentEntity : departmentEntities) {
            departmentReponse = new DepartmentReponse();
            departmentReponse.setDeptId(departmentEntity.getDeptId());
            departmentReponse.setDeptName(departmentEntity.getDeptName());
            departmentReponse.setDeptMailId(departmentEntity.getDeptMailId());
            departmentReponse.setRemark(departmentEntity.getRemark());
            departmentReponse.setStatusCd(departmentEntity.getStatusCd());
            departmentReponses.add(departmentReponse);
        }
        return departmentReponses;
        } catch (Exception ex) {
            log.error("Inside DepartmentServiceImpl >> findAllDepartmentDetails() : {}", ex);
            throw new KPIException("DepartmentServiceImpl >> findAllDepartmentDetails()", false, ex.getMessage());
        }
    }

    @Override
    @Retryable(include = {KPIException.class}, maxAttemptsExpression = "${retry-max-attempts}")
    public DepartmentReponse findAllDepartmentById(Integer deptId) {
        log.debug("Inside DepartmentServiceImpl >> findAllDepartmentById() deptId : {}", deptId);
        try {
            List<Object[]> designationData = departmentRepo.getDepartmentByIdDetail(deptId);
            List<DepartmentReponse> departmentReponses = designationData.stream().map(DepartmentReponse::new).collect(Collectors.toList());
            if (departmentReponses.size() > 0) {
                return departmentReponses.get(0);
            }
        } catch (Exception ex) {
            log.error("DepartmentServiceImpl >>findAllDepartmentById :{}", ex);
            throw new KPIException("DepartmentServiceImpl >> findAllDepartmentById()", false, ex.getMessage());
        }
        return null;
    }

    @Override
    @Retryable(include = {KPIException.class}, maxAttemptsExpression = "${retry-max-attempts}")
    public void uploadDeptExcelFile(MultipartFile file) throws IOException {
        log.debug("Inside DepartmentServiceImpl >> uploadDeptExcelFile()");
        Integer currentRow = 0;
        List<DepartmentCreateRequest> createRequests = new ArrayList<>();
        List<DepartmentCreateRequest> departmentNotSavedRecords = new ArrayList<>();
        List<DepartmentExcelReadData> departmentData = new ArrayList<>();

        byte[] excelBytes = null;
        if (file.isEmpty()) {
            throw new KPIException("DepartmentServiceImpl >> uploadDeptExcelFile()", false, "File not uploaded");
        }

        try {
            excelBytes = file.getBytes();

        } catch (Exception ex) {
            log.error("DepartmentServiceImpl >> uploadDeptExcelFile :{}", ex);
            //   throw new KPIException("DepartmentServiceImpl", false, ex.getMessage());
        }
        try (InputStream inputStream = new ByteArrayInputStream(excelBytes)) {
            Workbook workbook = WorkbookFactory.create(inputStream);
            Sheet sheet = workbook.getSheetAt(0);
            int startRow = 1;
            for (int rowIndex = startRow; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                Row row = sheet.getRow(rowIndex);
                if (row != null) {
                    currentRow = rowIndex;
                    DepartmentExcelReadData model = new DepartmentExcelReadData();
                    model.setDeptName(row.getCell(0).getStringCellValue());
                    model.setDeptMailId(row.getCell(1).getStringCellValue());
                    model.setRemark(row.getCell(2).getStringCellValue());
                    model.setEmployeeId(row.getCell(3).getStringCellValue().trim());

                    model.setStatusCd("A");
                    departmentData.add(model);
                }
            }
            workbook.close();
        } catch (Exception ex) {
            log.error("Inside DepartmentServiceImpl >> uploadDeptExcelFile() :", ex);
            // throw new KPIException("DepartmentServiceImpl", false, "Issue in row no: " + currentRow);
        }

        Integer currentExcelRow = 0;
        for (DepartmentExcelReadData request : departmentData) {
            try {
                if (StringUtils.isNotEmpty(request.getDeptName())) {
                    currentExcelRow++;
                    DepartmentCreateRequest departmentCreateRequest = new DepartmentCreateRequest();
                    departmentCreateRequest.setDeptName(request.getDeptName());
                    departmentCreateRequest.setDeptMailId(request.getDeptMailId());
                    departmentCreateRequest.setRemark(request.getRemark());
                    departmentCreateRequest.setStatusCd(request.getStatusCd());
                    departmentCreateRequest.setEmployeeId(request.getEmployeeId());
                    createRequests.add(departmentCreateRequest);//final request
                }
            } catch (Exception ex) {
                //   throw new KPIException("EmployeeServiceImpl", false, "Issue in row no: " + currentExcelRow);
                log.error("Inside DepartmentServiceImpl >> uploadDeptExcelFile() :{}", ex);
            }
        }
        for (DepartmentCreateRequest request : createRequests) {
            try {
                if (request.getDeptName() != null) {
                    saveDepartment(request);
                }
            } catch (Exception ex) {
                departmentNotSavedRecords.add(request);
                log.error("Inside DepartmentServiceImpl >> uploadDeptExcelFile() :{}", ex);
            }
        }
    }


    private DepartmentEntity convertDepartmentCreateRequestToEntity(DepartmentCreateRequest departmentCreateRequest) {
        log.debug("Inside DepartmentServiceImpl >> convertDepartmentCreateRequestToEntity() : {}", departmentCreateRequest);
        try {
            DepartmentEntity departmentEntity = new DepartmentEntity();
            departmentEntity.setDeptName(departmentCreateRequest.getDeptName());
            departmentEntity.setDeptMailId(departmentCreateRequest.getDeptMailId());
            departmentEntity.setRemark(departmentCreateRequest.getRemark());
            departmentEntity.setStatusCd(departmentCreateRequest.getStatusCd());
            departmentEntity.setCreatedUserId(departmentCreateRequest.getEmployeeId());
            return departmentEntity;
        } catch (Exception ex) {
            log.error("DepartmentServiceImpl >>convertDepartmentCreateRequestToEntity :{}", ex);
            throw new KPIException("DepartmentServiceImpl >> convertDepartmentCreateRequestToEntity()", false, ex.getMessage());
        }
    }
}
