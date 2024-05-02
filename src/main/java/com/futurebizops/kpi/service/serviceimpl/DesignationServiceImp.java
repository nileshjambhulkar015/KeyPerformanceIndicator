package com.futurebizops.kpi.service.serviceimpl;

import com.futurebizops.kpi.constants.KPIConstants;
import com.futurebizops.kpi.entity.DepartmentEntity;
import com.futurebizops.kpi.entity.DesignationAudit;
import com.futurebizops.kpi.entity.DesignationEntity;
import com.futurebizops.kpi.entity.RoleEntity;
import com.futurebizops.kpi.exception.KPIException;
import com.futurebizops.kpi.repository.DepartmentRepo;
import com.futurebizops.kpi.repository.DesignationAuditRepo;
import com.futurebizops.kpi.repository.DesignationRepo;
import com.futurebizops.kpi.repository.RoleRepo;
import com.futurebizops.kpi.request.DesignationCreateRequest;
import com.futurebizops.kpi.request.uploadexcel.DesignationExcelReadData;
import com.futurebizops.kpi.request.DesignationUpdateRequest;
import com.futurebizops.kpi.response.DepartmentReponse;
import com.futurebizops.kpi.response.DesignationReponse;
import com.futurebizops.kpi.response.KPIResponse;
import com.futurebizops.kpi.service.DepartmentService;
import com.futurebizops.kpi.service.DesignationService;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
    public KPIResponse saveDesignation(DesignationCreateRequest designationCreateRequest) {
        Optional<DesignationEntity> designationEntities = designationRepo.findByDeptIdAndDesigNameEqualsIgnoreCase(designationCreateRequest.getDeptId(), designationCreateRequest.getDesigName());
        if (designationEntities.isPresent()) {
            log.error("Inside DesignationServiceImp >> saveDesignation()");
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
            log.error("Inside DesignationServiceImp >> saveDesignation()");
            throw new KPIException("DesignationServiceImp", false, ex.getMessage());
        }
    }

    @Override
    public KPIResponse updateDesignation(DesignationUpdateRequest departmentUpdateRequest) {
        DesignationEntity designationEntity = convertDesignationUpdateRequestToEntity(departmentUpdateRequest);
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
            throw new KPIException("DesignationServiceImp", false, ex.getMessage());
        }
    }

    @Override
    public KPIResponse findDesignationDetails(Integer deptId, String desigName, String statusCd, Pageable requestPageable) {

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

        Integer totalCount = designationRepo.getDesignationCount(deptId, desigName, statusCd);
        List<Object[]> designationData = designationRepo.getDesignationDetail(deptId, desigName, statusCd, sortName, pageSize, pageOffset);

        List<DesignationReponse> designationReponses = designationData.stream().map(DesignationReponse::new).collect(Collectors.toList());

        designationReponses = designationReponses.stream()
                .sorted(Comparator.comparing(DesignationReponse::getDeptName))
                .collect(Collectors.toList());

        return KPIResponse.builder()
                .isSuccess(true)
                .responseData(new PageImpl<>(designationReponses, requestPageable, totalCount))
                .responseMessage(KPIConstants.RECORD_FETCH)
                .build();
    }

    @Override
    public DesignationReponse findDesignationById(Integer desigId) {
        List<Object[]> designationData = designationRepo.getDesignationByDesigId(desigId);
        List<DesignationReponse> designationReponses = designationData.stream().map(DesignationReponse::new).collect(Collectors.toList());
        return designationReponses.get(0);
    }

    @Override
    public List<DesignationReponse> findAllDesignationByDeptId(Integer deptId) {

        List<Object[]> designationData = designationRepo.getAllDesigByDeptId(deptId);
        return designationData.stream().map(DesignationReponse::new).collect(Collectors.toList());
    }

    @Override
    public List<DepartmentReponse> getAllDepartmentFromDesig(Integer deptId) {
        List<Object[]> designationData = designationRepo.getDeptInDesigById(deptId);
        return designationData.stream().map(DepartmentReponse::new).collect(Collectors.toList());
    }

    @Override
    public  void uploadDesigExcelFile(MultipartFile file) throws IOException {

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
            Integer roleId = null;
            for (int rowIndex = startRow; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                Row row = sheet.getRow(rowIndex);
                if (row != null) {
                    currentRow = rowIndex;
                    DesignationExcelReadData model = new DesignationExcelReadData();
                    model.setDeptId(getDeptId(roleId, row.getCell(0).getStringCellValue().trim()));
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
            throw new KPIException("DesignationServiceImpl", false, "Issue in row no: " + currentRow);
        }

        Integer currentExcelRow = 0;
        for (DesignationExcelReadData request : designationData) {
            try {
                currentExcelRow++;
                DesignationCreateRequest designationCreateRequest = new DesignationCreateRequest();


                designationCreateRequest.setDeptId(request.getDeptId());
                designationCreateRequest.setDesigName(request.getDesigName());
                designationCreateRequest.setRemark(request.getRemark());
                designationCreateRequest.setStatusCd(request.getStatusCd());
                designationCreateRequest.setEmployeeId(request.getEmployeeId());


                designationCreateRequests.add(designationCreateRequest);//final request

            } catch (Exception ex) {
                throw new KPIException("DesignationServiceImpl", false, "Issue in row no: " + currentExcelRow);

            } finally {
                System.out.println("employeeCreateRequests::" + designationCreateRequests);
            }
        }
        for (DesignationCreateRequest request : designationCreateRequests) {
            try {
                saveDesignation(request);

                log.info("DesignationCreateRequest::" + request);
            } catch (Exception ex) {
                designationNotSavedRecords.add(request);
                log.info("DesignationNotSavedRecords" + request);
            }
        }
    }

    private Integer getRoleId(String roleName) {
        Optional<RoleEntity> optionalRoleEntity = roleRepo.findByRoleNameEqualsIgnoreCase(roleName);
        if (optionalRoleEntity.isPresent()) {
            return optionalRoleEntity.get().getRoleId();
        }
        log.error("Inside EmployeeServiceImpl >> getRoleId");
        throw new KPIException("EmployeeServiceImpl", false, "Role Name is not exist");
    }
    private Integer getDeptId(Integer roleId,String deptName) {
        Optional<DepartmentEntity> optionalDepartmentEntity = departmentRepo.findByDeptNameEqualsIgnoreCase(deptName);
        if (optionalDepartmentEntity.isPresent()) {
            return optionalDepartmentEntity.get().getDeptId();
        }
        log.error("Inside EmployeeServiceImpl >> getRoleId");
        throw new KPIException("EmployeeServiceImpl", false, "Department Name is not exist");
    }

    private DesignationEntity convertDesignationCreateRequestToEntity(DesignationCreateRequest designationCreateRequest) {
        DesignationEntity designationEntity = new DesignationEntity();

        designationEntity.setDeptId(designationCreateRequest.getDeptId());
        designationEntity.setDesigName(designationCreateRequest.getDesigName());
        designationEntity.setRemark(designationCreateRequest.getRemark());
        designationEntity.setStatusCd(designationCreateRequest.getStatusCd());
        designationEntity.setCreatedUserId(designationCreateRequest.getEmployeeId());
        return designationEntity;
    }

    private DesignationEntity convertDesignationUpdateRequestToEntity(DesignationUpdateRequest designationUpdateRequest) {
        DesignationEntity designationEntity = new DesignationEntity();
        designationEntity.setDesigId(designationUpdateRequest.getDesigId());

        designationEntity.setDeptId(designationUpdateRequest.getDeptId());
        designationEntity.setDesigName(designationUpdateRequest.getDesigName());
        designationEntity.setRemark(designationUpdateRequest.getRemark());
        designationEntity.setStatusCd(designationUpdateRequest.getStatusCd());
        designationEntity.setUpdatedUserId(designationUpdateRequest.getEmployeeId());
        return designationEntity;
    }
}
