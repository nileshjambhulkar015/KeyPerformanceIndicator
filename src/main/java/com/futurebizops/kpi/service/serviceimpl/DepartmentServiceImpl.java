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
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentRepo departmentRepo;

    @Autowired
    private DepartmentAuditRepo departmentAuditRepo;

    @Autowired
    RoleRepo roleRepo;

    @Override
    public KPIResponse saveDepartment(DepartmentCreateRequest departmentCreateRequest) {

        Optional<DepartmentEntity> optionalDepartmentEntity = departmentRepo.findByDeptNameEqualsIgnoreCase(departmentCreateRequest.getDeptName() );
        if(optionalDepartmentEntity.isPresent()){
            log.error("Inside DepartmentServiceImpl >> saveDepartment()");
            throw new KPIException("DepartmentServiceImpl Class", false, "Department name already exist");
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
            log.error("Inside DepartmentServiceImpl >> saveDepartment()");
            throw new KPIException("DepartmentServiceImpl", false, ex.getMessage());
        }
    }

    @Override
    public KPIResponse updateDepartment(DepartmentUpdateRequest departmentUpdateRequest) {
        DepartmentEntity departmentEntity = convertDepartmentUpdateRequestToEntity(departmentUpdateRequest);
        try {
            departmentRepo.save(departmentEntity);
            DepartmentAudit departmentAudit = new DepartmentAudit(departmentEntity);
            departmentAuditRepo.save(departmentAudit);
            return KPIResponse.builder()
                    .isSuccess(true)
                    .responseMessage(KPIConstants.RECORD_UPDATE)
                    .build();
        } catch (Exception ex) {
            log.error("Inside DepartmentServiceImpl >> updateDepartment()");
            throw new KPIException("DepartmentServiceImpl", false, ex.getMessage());
        }
    }

    @Override
    public KPIResponse findDepartmentDetails(Integer deptId, String deptName, String statusCd, Pageable requestPageable) {
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

        Integer totalCount = departmentRepo.getDepartmentCount(deptId, deptName, statusCd);
        List<Object[]> departmentData = departmentRepo.getDepartmentDetail(deptId, deptName, statusCd, sortName, pageSize, pageOffset);

        List<DepartmentReponse> departmentReponses = departmentData.stream().map(DepartmentReponse::new).collect(Collectors.toList());

        departmentReponses= departmentReponses.stream()
                .sorted(Comparator.comparing(DepartmentReponse::getDeptName))
                .collect(Collectors.toList());

        return KPIResponse.builder()
                .isSuccess(true)
                .responseData(new PageImpl<>(departmentReponses, requestPageable, totalCount))
                .responseMessage(KPIConstants.RECORD_FETCH)
                .build();
    }

    @Override
    public List<DepartmentDDResponse> findAllDepartmentExceptGM() {
        List<DepartmentEntity> departmentEntities = departmentRepo.findAllDepartmentDetailsForEmployee();
        DepartmentDDResponse departmentDDResponse = null;
        List<DepartmentDDResponse> departmentDDResponses =new ArrayList<>();

        for(DepartmentEntity departmentEntity : departmentEntities){
            if(departmentEntity.getDeptId()!=1){
                departmentDDResponse = new DepartmentDDResponse();
                departmentDDResponse.setDeptId(departmentEntity.getDeptId());
                departmentDDResponse.setDeptName(departmentEntity.getDeptName());
                departmentDDResponse.setStatusCd(departmentEntity.getStatusCd());
                departmentDDResponses.add(departmentDDResponse);
            }

        }
        return departmentDDResponses;
    }


    @Override
    public List<DepartmentReponse> findAllDepartmentDetails() {
        List<DepartmentEntity> departmentEntities =  departmentRepo.findAllDepartmentDetailsForEmployee();
        List<DepartmentReponse> departmentReponses = new ArrayList<>();
        DepartmentReponse departmentReponse = null;
        for(DepartmentEntity departmentEntity : departmentEntities){
            departmentReponse = new DepartmentReponse();
            departmentReponse.setDeptId(departmentEntity.getDeptId());
            departmentReponse.setDeptName(departmentEntity.getDeptName());
            departmentReponse.setRemark(departmentEntity.getRemark());
            departmentReponse.setStatusCd(departmentEntity.getStatusCd());
            departmentReponses.add(departmentReponse);
        }
        return departmentReponses;
    }

    @Override
    public DepartmentReponse findAllDepartmentById(Integer deptId) {
        List<Object[]> designationData = departmentRepo.getDepartmentByIdDetail(deptId);
        List<DepartmentReponse> designationReponses = designationData.stream().map(DepartmentReponse::new).collect(Collectors.toList());
        return  designationReponses.get(0);
    }

    @Override
    public void uploadDeptExcelFile(MultipartFile file) throws IOException {        {

            Integer currentRow = 0;
            List<DepartmentCreateRequest> createRequests = new ArrayList<>();
            List<DepartmentCreateRequest> departmentNotSavedRecords = new ArrayList<>();
            List<DepartmentExcelReadData> departmentData = new ArrayList<>();

            byte[] excelBytes = null;
            if (file.isEmpty()) {
                throw new KPIException("DepartmentServiceImpl", false, "File not uploaded");
            }

            try {
                excelBytes = file.getBytes();

            } catch (Exception ex) {
                log.error("DepartmentServiceImpl >>departmentProcessExcel ");
                throw new KPIException("DepartmentServiceImpl", false, ex.getMessage());
            }
            try (InputStream inputStream = new ByteArrayInputStream(excelBytes)) {
                Workbook workbook = WorkbookFactory.create(inputStream);
                Sheet sheet = workbook.getSheetAt(0);
                int startRow = 1;
                for (int rowIndex = startRow; rowIndex <=sheet.getLastRowNum(); rowIndex++) {
                    Row row = sheet.getRow(rowIndex);
                    if (row != null) {
                        currentRow = rowIndex;
                        DepartmentExcelReadData model = new DepartmentExcelReadData();
                        model.setDeptName(row.getCell(0).getStringCellValue());
                        model.setRemark(row.getCell(1).getStringCellValue());
                        model.setEmployeeId(row.getCell(2).getStringCellValue().trim());

                        model.setStatusCd("A");
                        departmentData.add(model);
                    }
                }
                workbook.close();
            } catch (Exception ex) {
                log.error("Inside DepartmentServiceImpl >> DepartmentprocessExcelFile()");
                throw new KPIException("DepartmentServiceImpl", false, "Issue in row no: " + currentRow);
            }

            Integer currentExcelRow = 0;
            for (DepartmentExcelReadData request : departmentData) {
                try {
                    currentExcelRow++;
                    DepartmentCreateRequest departmentCreateRequest = new DepartmentCreateRequest();
                    departmentCreateRequest.setDeptName(request.getDeptName());
                    departmentCreateRequest.setRemark(request.getRemark());
                    departmentCreateRequest.setStatusCd(request.getStatusCd());
                    departmentCreateRequest.setEmployeeId(request.getEmployeeId());
                    createRequests.add(departmentCreateRequest);//final request
                } catch (Exception ex) {
                    throw new KPIException("EmployeeServiceImpl", false, "Issue in row no: " + currentExcelRow);

                } finally {
                    System.out.println("employeeCreateRequests::" + createRequests);
                }
            }
            for (DepartmentCreateRequest request : createRequests) {
                try {
                    saveDepartment(request);

                    log.info("DepartmentCreateRequest::" + request);
                } catch (Exception ex) {
                    departmentNotSavedRecords.add(request);
                    log.info("departmentNotSavedRecords"+request);
                }
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

    private DepartmentEntity convertDepartmentCreateRequestToEntity(DepartmentCreateRequest departmentCreateRequest) {
        DepartmentEntity departmentEntity = new DepartmentEntity();

        departmentEntity.setDeptName(departmentCreateRequest.getDeptName());
        departmentEntity.setRemark(departmentCreateRequest.getRemark());
        departmentEntity.setStatusCd(departmentCreateRequest.getStatusCd());
        departmentEntity.setCreatedUserId(departmentCreateRequest.getEmployeeId());
        return  departmentEntity;
    }

    private DepartmentEntity convertDepartmentUpdateRequestToEntity(DepartmentUpdateRequest departmentUpdateRequest) {
        DepartmentEntity departmentEntity = new DepartmentEntity();
        departmentEntity.setDeptId(departmentUpdateRequest.getDeptId());
        departmentEntity.setDeptName(departmentUpdateRequest.getDeptName());
        departmentEntity.setRemark(departmentUpdateRequest.getRemark());
        departmentEntity.setStatusCd(departmentUpdateRequest.getStatusCd());
        departmentEntity.setCreatedUserId(departmentUpdateRequest.getEmployeeId());
        return  departmentEntity;
    }
}
