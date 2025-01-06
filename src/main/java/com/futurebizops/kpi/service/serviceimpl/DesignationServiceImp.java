package com.futurebizops.kpi.service.serviceimpl;

import com.futurebizops.kpi.constants.KPIConstants;
import com.futurebizops.kpi.entity.DepartmentEntity;
import com.futurebizops.kpi.entity.DesignationAudit;
import com.futurebizops.kpi.entity.DesignationEntity;
import com.futurebizops.kpi.exception.KPIException;
import com.futurebizops.kpi.repository.DepartmentRepo;
import com.futurebizops.kpi.repository.DesignationAuditRepo;
import com.futurebizops.kpi.repository.DesignationRepo;
import com.futurebizops.kpi.repository.RoleRepo;
import com.futurebizops.kpi.request.DesignationCreateRequest;
import com.futurebizops.kpi.request.DesignationUpdateRequest;
import com.futurebizops.kpi.request.uploadexcel.DesignationExcelReadData;
import com.futurebizops.kpi.response.DepartmentReponse;
import com.futurebizops.kpi.response.DesignationReponse;
import com.futurebizops.kpi.response.KPIResponse;
import com.futurebizops.kpi.service.DepartmentService;
import com.futurebizops.kpi.service.DesignationService;
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
public class DesignationServiceImp implements DesignationService {

    @Autowired
    private DesignationRepo designationRepo;

    @Autowired
    private DesignationAuditRepo designationAuditRepo;

    @Autowired
    private DepartmentRepo departmentRepo;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    RoleRepo roleRepo;

    @Override
    @Retryable(include = {KPIException.class}, maxAttemptsExpression = "${retry-max-attempts}")
    public KPIResponse saveDesignation(DesignationCreateRequest designationCreateRequest) {
        log.debug("Inside DesignationServiceImp >> saveDesignation() designationCreateRequest: {}", designationCreateRequest);
        Optional<DesignationEntity> designationEntities = designationRepo.findByDeptIdAndDesigNameEqualsIgnoreCase(designationCreateRequest.getDeptId(), designationCreateRequest.getDesigName());
        if (designationEntities.isPresent()) {
            log.error("Inside DesignationServiceImp >> saveDesignation() With Department name Designation name already exist");
            throw new KPIException("DesignationServiceImp class", false, "With Department name Designation name already exist");
        }

        DesignationEntity designationEntity = convertDesignationCreateRequestToEntity(designationCreateRequest);
        try {
            designationRepo.save(designationEntity);
            DesignationAudit designationAudit = new DesignationAudit(designationEntity);
            designationAuditRepo.save(designationAudit);
            return KPIResponse.builder()
                    .isSuccess(true)
                    .responseMessage(KPIConstants.RECORD_SUCCESS)
                    .build();
        } catch (Exception ex) {
            log.error("Inside DesignationServiceImp >> saveDesignation() : {}", ex);
            throw new KPIException("DesignationServiceImp >> saveDesignation()", false, ex.getMessage());
        }
    }

    @Transactional
    @Override
    @Retryable(include = {KPIException.class}, maxAttemptsExpression = "${retry-max-attempts}")
    public KPIResponse deleteDesignationDetails(Integer desigId) {
        log.debug("Inside DesignationServiceImp >> deleteDesignationDetails() desigId: {}", desigId);
        KPIResponse busPassResponse = new KPIResponse();
        try {
            designationRepo.deleteDesignationDetails(desigId);
            busPassResponse.setSuccess(true);
            busPassResponse.setResponseMessage("Designation details deleted Successfully");
            return busPassResponse;
        } catch (Exception ex) {
            log.error("Inside DesignationServiceImp >> deleteDesignationDetails() : {}",ex);
            throw new KPIException("DesignationServiceImp >> deleteDesignationDetails()", false, ex.getMessage());
        }
    }

    @Override
    @Retryable(include = {KPIException.class}, maxAttemptsExpression = "${retry-max-attempts}")
    public KPIResponse updateDesignation(DesignationUpdateRequest designationUpdateRequest) {
        log.debug("Inside DesignationServiceImp >> updateDesignation() designationUpdateRequest: {}", designationUpdateRequest);
        DesignationEntity designationEntity = convertDesignationUpdateRequestToEntity(designationUpdateRequest);
        try {
            designationRepo.save(designationEntity);
            DesignationAudit designationAudit = new DesignationAudit(designationEntity);
            designationAuditRepo.save(designationAudit);
            return KPIResponse.builder()
                    .isSuccess(true)
                    .responseMessage(KPIConstants.RECORD_UPDATE)
                    .build();
        } catch (Exception ex) {
            log.error("Inside DesignationServiceImp >> updateDesignation() : {}", ex);
            throw new KPIException("DesignationServiceImp >> updateDesignation()", false, ex.getMessage());
        }
    }

    @Override
    @Retryable(include = {KPIException.class}, maxAttemptsExpression = "${retry-max-attempts}")
    public KPIResponse findDesignationDetails(Integer deptId, String desigName, String statusCd, Pageable requestPageable) {
        log.debug("Inside DesignationServiceImp >> findDesignationDetails() deptId: {}, desigName : {}", deptId, desigName);
        String sortName = null;
        //String sortDirection = null;
        Integer pageSize = requestPageable.getPageSize();
        Integer pageOffset = (int) requestPageable.getOffset();
        // pageable = KPIUtils.sort(requestPageable, sortParam, pageDirection);
        Optional<Sort.Order> order = requestPageable.getSort().get().findFirst();
        if (order.isPresent()) {
            sortName = order.get().getProperty();  //order by this field
            // sortDirection = order.get().getDirection().toString(); // Sort ASC or DESC
        }

        try{
        Integer totalCount = designationRepo.getDesignationCount(deptId, desigName, statusCd);
        List<Object[]> designationData = designationRepo.getDesignationDetail(deptId, desigName, statusCd, sortName, pageSize, pageOffset);

        List<DesignationReponse> designationReponses = designationData.stream().map(DesignationReponse::new).collect(Collectors.toList());

        designationReponses = designationReponses.stream()
                .sorted(Comparator.comparing(DesignationReponse::getDeptName))
                .collect(Collectors.toList());
        if (designationReponses.size() > 0) {
            return KPIResponse.builder()
                    .isSuccess(true)
                    .responseData(new PageImpl<>(designationReponses, requestPageable, totalCount))
                    .responseMessage(KPIConstants.RECORD_FETCH)
                    .build();
        }
        } catch (Exception ex) {
            log.error("Inside DesignationServiceImp >> findDesignationDetails() : {}", ex);
            throw new KPIException("DesignationServiceImp >> findDesignationDetails()", false, ex.getMessage());
        }
        return KPIResponse.builder()
                .responseMessage("Designation name is not available")
                .isSuccess(false)
                .build();
    }

    @Override
    @Retryable(include = {KPIException.class}, maxAttemptsExpression = "${retry-max-attempts}")
    public DesignationReponse findDesignationById(Integer desigId) {
        log.debug("Inside DesignationServiceImp >> findDesignationById() desigId: {}", desigId);
        try{
        List<Object[]> designationData = designationRepo.getDesignationByDesigId(desigId);
        List<DesignationReponse> designationReponses = designationData.stream().map(DesignationReponse::new).collect(Collectors.toList());
        return designationReponses.get(0);
        } catch (Exception ex) {
            log.error("Inside DesignationServiceImp >> findDesignationById() : {}", ex);
            throw new KPIException("DesignationServiceImp >> findDesignationById()", false, ex.getMessage());
        }
    }

    @Override
    @Retryable(include = {KPIException.class}, maxAttemptsExpression = "${retry-max-attempts}")
    public List<DesignationReponse> findAllDesignationByDeptId(Integer deptId) {
        log.debug("Inside DesignationServiceImp >> findAllDesignationByDeptId() deptId: {}", deptId);
        try{
        List<Object[]> designationData = designationRepo.getAllDesigByDeptId(deptId);
        return designationData.stream().map(DesignationReponse::new).collect(Collectors.toList());
        } catch (Exception ex) {
            log.error("Inside DesignationServiceImp >> findAllDesignationByDeptId() : {}", ex);
            throw new KPIException("DesignationServiceImp >> findAllDesignationByDeptId()", false, ex.getMessage());
        }
    }

    @Override
    @Retryable(include = {KPIException.class}, maxAttemptsExpression = "${retry-max-attempts}")
    public List<DepartmentReponse> getAllDepartmentFromDesig(Integer deptId) {
        log.debug("Inside DesignationServiceImp >> getAllDepartmentFromDesig() deptId: {}", deptId);
        try{
        List<Object[]> designationData = designationRepo.getDeptInDesigById(deptId);
        return designationData.stream().map(DepartmentReponse::new).collect(Collectors.toList());
    } catch (Exception ex) {
        log.error("Inside DesignationServiceImp >> getAllDepartmentFromDesig() : {}", ex);
        throw new KPIException("DesignationServiceImp >> getAllDepartmentFromDesig()", false, ex.getMessage());
    }
    }

    @Override
    @Retryable(include = {KPIException.class}, maxAttemptsExpression = "${retry-max-attempts}")
    public  void uploadDesigExcelFile(MultipartFile file) throws IOException {
        log.debug("Inside DesignationServiceImp >> uploadDesigExcelFile()");
        Integer currentRow = 0;
        List<DesignationCreateRequest> designationCreateRequests = new ArrayList<>();
        List<DesignationCreateRequest> designationNotSavedRecords = new ArrayList<>();
        List<DesignationExcelReadData> designationData = new ArrayList<>();

        byte[] excelBytes = null;
        if (file.isEmpty()) {
            throw new KPIException("DesignationServiceImpl", false, "File not uploaded");
        }

        try {
            excelBytes = file.getBytes();

        } catch (Exception ex) {
            log.error("DesignationServiceImpl >>designationProcessExcel ");
            throw new KPIException("DesignationServiceImpl", false, ex.getMessage());
        }
        try (InputStream inputStream = new ByteArrayInputStream(excelBytes)) {
            Workbook workbook = WorkbookFactory.create(inputStream);
            Sheet sheet = workbook.getSheetAt(0);
            int startRow = 1;

            for (int rowIndex = startRow; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                Row row = sheet.getRow(rowIndex);
                if (row != null) {
                    currentRow = rowIndex;
                    DesignationExcelReadData model = new DesignationExcelReadData();
                    model.setDeptId(getDeptIdByDeptName(row.getCell(0).getStringCellValue().trim()));
                    model.setDesigName(row.getCell(1).getStringCellValue().trim());
                    model.setRemark(row.getCell(2).getStringCellValue().trim());
                    model.setEmployeeId(row.getCell(3).getStringCellValue().trim());
                    model.setStatusCd("A");

                    designationData.add(model);
                }
            }
            workbook.close();
        } catch (Exception ex) {
            log.error("Inside DesignationServiceImpl >> designationServiceImpl()");
            throw new KPIException("DesignationServiceImpl designationServiceImpl : designationServiceImpl()", false, "Issue in row no: " + currentRow);
        }

        Integer currentExcelRow = 0;
        for (DesignationExcelReadData request : designationData) {
            try {
                if(StringUtils.isNotEmpty(request.getDesigName())) {
                    currentExcelRow++;
                    DesignationCreateRequest designationCreateRequest = new DesignationCreateRequest();
                    designationCreateRequest.setDeptId(request.getDeptId());
                    designationCreateRequest.setDesigName(request.getDesigName());
                    designationCreateRequest.setRemark(request.getRemark());
                    designationCreateRequest.setStatusCd(request.getStatusCd());
                    designationCreateRequest.setEmployeeId(request.getEmployeeId());
                    designationCreateRequests.add(designationCreateRequest);//final request
                }
            } catch (Exception ex) {
                log.error("DesignationCreateRequest >> designationServiceImpl() Issue in row no: {}", currentExcelRow);
                throw new KPIException("DesignationServiceImpl >> designationServiceImpl()", false, "Issue in row no: " + currentExcelRow);

            }
        }
        for (DesignationCreateRequest request : designationCreateRequests) {
            try {
                if(null!=request.getDeptId() || null != request.getDesigName()){
                saveDesignation(request);
                }

                log.info("DesignationCreateRequest::" + request);
            } catch (Exception ex) {
                designationNotSavedRecords.add(request);
                log.info("DesignationNotSavedRecords" + request);
            }
        }
    }

     private DesignationEntity convertDesignationCreateRequestToEntity(DesignationCreateRequest designationCreateRequest) {
         log.debug("Inside DesignationServiceImp >> convertDesignationCreateRequestToEntity() designationCreateRequest: {}", designationCreateRequest);
      try{
          DesignationEntity designationEntity = new DesignationEntity();

        designationEntity.setDeptId(designationCreateRequest.getDeptId());
        designationEntity.setDesigName(designationCreateRequest.getDesigName());
        designationEntity.setRemark(designationCreateRequest.getRemark());
        designationEntity.setStatusCd(designationCreateRequest.getStatusCd());
        designationEntity.setCreatedUserId(designationCreateRequest.getEmployeeId());
        return designationEntity;
     } catch (Exception ex) {
        log.error("Inside DesignationServiceImp >> convertDesignationCreateRequestToEntity() : {}", ex);
        throw new KPIException("DesignationServiceImp >> convertDesignationCreateRequestToEntity()", false, ex.getMessage());
    }
    }

    private DesignationEntity convertDesignationUpdateRequestToEntity(DesignationUpdateRequest designationUpdateRequest) {
        log.debug("Inside DesignationServiceImp >> convertDesignationUpdateRequestToEntity() designationUpdateRequest: {}", designationUpdateRequest);
       try{
           DesignationEntity designationEntity = new DesignationEntity();
        designationEntity.setDesigId(designationUpdateRequest.getDesigId());

        designationEntity.setDeptId(designationUpdateRequest.getDeptId());
        designationEntity.setDesigName(designationUpdateRequest.getDesigName());
        designationEntity.setRemark(designationUpdateRequest.getRemark());
        designationEntity.setStatusCd(designationUpdateRequest.getStatusCd());
        designationEntity.setUpdatedUserId(designationUpdateRequest.getEmployeeId());
        return designationEntity;
    } catch (Exception ex) {
        log.error("Inside DesignationServiceImp >> convertDesignationUpdateRequestToEntity() : {}", ex);
        throw new KPIException("DesignationServiceImp >> convertDesignationUpdateRequestToEntity()", false, ex.getMessage());
    }
    }

    private Integer getDeptIdByDeptName(String deptName) {
        log.debug("Inside DesignationServiceImp >> getDeptIdByDeptName() deptName: {}", deptName);

        try{
        Optional<DepartmentEntity> optionalDepartmentEntity = departmentRepo.findByDeptNameEqualsIgnoreCase(deptName);
        if (optionalDepartmentEntity.isPresent()) {
            return optionalDepartmentEntity.get().getDeptId();
        }
      return null;
        } catch (Exception ex) {
            log.error("Inside DesignationServiceImp >> getDeptIdByDeptName() : {}", ex);
            throw new KPIException("DesignationServiceImp >> getDeptIdByDeptName()", false, ex.getMessage());
        }
    }
}
