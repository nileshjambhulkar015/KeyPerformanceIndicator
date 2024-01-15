package com.futurebizops.kpi.service.serviceimpl;

import com.futurebizops.kpi.constants.KPIConstants;
import com.futurebizops.kpi.entity.DepartmentEntity;
import com.futurebizops.kpi.entity.DesignationEntity;
import com.futurebizops.kpi.entity.KeyPerfParamAudit;
import com.futurebizops.kpi.entity.KeyPerfParamEntity;
import com.futurebizops.kpi.entity.RoleEntity;
import com.futurebizops.kpi.exception.KPIException;
import com.futurebizops.kpi.repository.DepartmentRepo;
import com.futurebizops.kpi.repository.DesignationRepo;
import com.futurebizops.kpi.repository.KeyPerfParameterAuditRepo;
import com.futurebizops.kpi.repository.KeyPerfParameterRepo;
import com.futurebizops.kpi.repository.RoleRepo;
import com.futurebizops.kpi.request.KeyPerfParamCreateRequest;
import com.futurebizops.kpi.request.KeyPerfParamUpdateRequest;
import com.futurebizops.kpi.request.uploadexcel.KeyPerfParamExcelReadData;
import com.futurebizops.kpi.response.KPIResponse;
import com.futurebizops.kpi.response.KPPResponse;
import com.futurebizops.kpi.service.KeyPerfParameterService;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
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
public class KeyPerfParameterServiceImpl implements KeyPerfParameterService {

    @Autowired
    private KeyPerfParameterRepo keyPerfParameterRepo;

    @Autowired
    private KeyPerfParameterAuditRepo keyPerfParameterAuditRepo;

    @Autowired
    DesignationRepo designationRepo;

    @Autowired
    DepartmentRepo departmentRepo;

    @Autowired
    RoleRepo roleRepo;

    @Override
    public KPIResponse saveKeyPerfomanceParameter(KeyPerfParamCreateRequest keyPerfParamCreateRequest) {

        KeyPerfParamEntity keyPerfParamEntity = convertKeyPerfParamCreateRequestToEntity(keyPerfParamCreateRequest);
        try {
            keyPerfParameterRepo.save(keyPerfParamEntity);
            KeyPerfParamAudit keyPerfParamAudit = new KeyPerfParamAudit(keyPerfParamEntity);
            keyPerfParameterAuditRepo.save(keyPerfParamAudit);
            return KPIResponse.builder()
                    .isSuccess(true)
                    .responseMessage(KPIConstants.RECORD_SUCCESS)
                    .build();
        } catch (Exception ex) {
            log.error("Inside KeyPerfParameterServiceImpl >> saveKeyPerfomanceParameter()");
            throw new KPIException("KeyPerfParameterServiceImpl", false, ex.getMessage());
        }
    }

    @Override
    public KPIResponse updateKeyPerfomanceParameter(KeyPerfParamUpdateRequest ratingRatioUpdateRequest) {
        KeyPerfParamEntity keyPerfParamEntity = convertKeyPerfParamUpdateRequestToEntity(ratingRatioUpdateRequest);
        try {
            keyPerfParameterRepo.save(keyPerfParamEntity);
            KeyPerfParamAudit departmentAudit = new KeyPerfParamAudit(keyPerfParamEntity);
            keyPerfParameterAuditRepo.save(departmentAudit);
            return KPIResponse.builder()
                    .isSuccess(true)
                    .responseMessage(KPIConstants.RECORD_UPDATE)
                    .build();
        } catch (Exception ex) {
            log.error("Inside KeyPerfParameterServiceImpl >> updateKeyPerfomanceParameter()");
            throw new KPIException("KeyPerfParameterServiceImpl", false, ex.getMessage());
        }
    }

    @Override
    public KPIResponse findKeyPerfomanceParameterDetails(Integer kppId, Integer roleId, Integer deptId, Integer desigId, String kppObjective, String statusCd, Pageable pageable) {
        String sortName = null;
        //String sortDirection = null;
        Integer pageSize = pageable.getPageSize();
        Integer pageOffset = (int) pageable.getOffset();
        // pageable = KPIUtils.sort(requestPageable, sortParam, pageDirection);
        Optional<Sort.Order> order = pageable.getSort().get().findFirst();
        if (order.isPresent()) {
            sortName = order.get().getProperty();  //order by this field
          //  sortDirection = order.get().getDirection().toString(); // Sort ASC or DESC
        }

        Integer totalCount = keyPerfParameterRepo.getKeyPerfParameterCount(kppId, roleId, deptId, desigId, kppObjective, statusCd);
        List<Object[]> designationData = keyPerfParameterRepo.getKeyPerfParameterDetail(kppId, roleId, deptId, desigId, kppObjective, statusCd, sortName, pageSize, pageOffset);

        List<KPPResponse> kppResponses = designationData.stream().map(KPPResponse::new).collect(Collectors.toList());
        kppResponses = kppResponses.stream()
                .sorted(Comparator.comparing(KPPResponse::getDeptName))
                .collect(Collectors.toList());

        return KPIResponse.builder()
                .isSuccess(true)
                .responseData(new PageImpl<>(kppResponses, pageable, totalCount))
                .responseMessage(KPIConstants.RECORD_FETCH)
                .build();
    }

    @Override
    public KPPResponse findKeyPerfomanceParameterDetailById(Integer kppId) {
        try {
            List<Object[]> designationData = keyPerfParameterRepo.getKeyPerfParameterDetailById(kppId);

            List<KPPResponse> kppResponses = designationData.stream().map(KPPResponse::new).collect(Collectors.toList());

            return kppResponses.get(0);
        } catch (Exception ex) {
            log.error("Inside KeyPerfParameterServiceImpl >> findKeyPerfomanceParameterDetailById()");
            throw new KPIException("KeyPerfParameterServiceImpl class", false, ex.getMessage());
        }
    }

    @Override
    public void uploadKppExcelFile(MultipartFile file) throws IOException {

        Integer currentRow = 0;
        List<KeyPerfParamCreateRequest> KeyPerfParamCreateRequests = new ArrayList<>();
        List<KeyPerfParamCreateRequest> KeyPerfParamNotSavedRecords = new ArrayList<>();
        List<KeyPerfParamExcelReadData> KeyPerfParamData = new ArrayList<>();

        byte[] excelBytes = null;
        if (file.isEmpty()) {
            throw new KPIException("DesignationServiceImpl", false, "File not uploaded");
        }

        try {
            excelBytes = file.getBytes();

        } catch (Exception ex) {
            log.error("KeyPerfParamServiceImpl >>KeyPerfParamProcessExcel ");
            throw new KPIException("KeyPerfParamServiceImpl", false, ex.getMessage());
        }
        try (InputStream inputStream = new ByteArrayInputStream(excelBytes)) {
            Workbook workbook = WorkbookFactory.create(inputStream);
            Sheet sheet = workbook.getSheetAt(0);
            int startRow = 1;

            for (int rowIndex = startRow; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                Row row = sheet.getRow(rowIndex);
                if (row != null) {
                    currentRow = rowIndex;
                    KeyPerfParamExcelReadData model = new KeyPerfParamExcelReadData();
                    model.setEmployeeId("1");
                    model.setRoleId(getRoleId(row.getCell(0).getStringCellValue().trim()));
                    model.setDeptId(getDeptId(row.getCell(1).getStringCellValue().trim()));
                    model.setDesigId(getDesigId(row.getCell(2).getStringCellValue().trim()));
                    model.setKppObjective(row.getCell(3).getStringCellValue().trim());
                    model.setKppPerformanceIndi(row.getCell(4).getStringCellValue().trim());
                    model.setKppTargetPeriod(row.getCell(5).getStringCellValue().trim());
                    model.setKppUoM(row.getCell(6).getStringCellValue().trim());
                    model.setRrDescription(row.getCell(7).getStringCellValue().trim());
                    model.setKppOverallTarget(row.getCell(8).getStringCellValue().trim());
                    model.setKppOverallWeightage(row.getCell(9).getStringCellValue().trim());
                    model.setKppRating1(row.getCell(10).getStringCellValue().trim());
                    model.setKppRating2(row.getCell(11).getStringCellValue().trim());
                    model.setKppRating3(row.getCell(12).getStringCellValue().trim());
                    model.setKppRating4(row.getCell(13).getStringCellValue().trim());
                    model.setKppRating5(row.getCell(14).getStringCellValue().trim());
                    model.setStatusCd("A");
                    model.setRemark(row.getCell(15).getStringCellValue().trim());
                    KeyPerfParamData.add(model);
                }
            }
            workbook.close();
        } catch (Exception ex) {
            log.error("Inside KeyPerfParamServiceImpl >> KeyPerfParamprocessExcelFile()");
            throw new KPIException("KeyPerfParamServiceImpl", false, "Issue in row no: " + currentRow);
        }

        Integer currentExcelRow = 0;
        for (KeyPerfParamExcelReadData request : KeyPerfParamData) {
            try {
                currentExcelRow++;
                KeyPerfParamCreateRequest keyPerfParamCreateRequest = new KeyPerfParamCreateRequest();
                keyPerfParamCreateRequest.setEmployeeId("1");
                keyPerfParamCreateRequest.setRoleId(request.getRoleId());
                keyPerfParamCreateRequest.setDeptId(request.getDeptId());
                keyPerfParamCreateRequest.setKppObjective(request.getKppObjective());
                keyPerfParamCreateRequest.setKppTargetPeriod(request.getKppTargetPeriod());
                keyPerfParamCreateRequest.setKppPerformanceIndi(request.getKppPerformanceIndi());
                keyPerfParamCreateRequest.setKppOverallTarget(request.getKppOverallTarget());
                keyPerfParamCreateRequest.setKppUoM(request.getKppUoM());
                keyPerfParamCreateRequest.setKppOverallWeightage(request.getKppOverallWeightage());
                keyPerfParamCreateRequest.setKppRating1(request.getKppRating1());
                keyPerfParamCreateRequest.setKppRating2(request.getKppRating2());
                keyPerfParamCreateRequest.setKppRating3(request.getKppRating3());
                keyPerfParamCreateRequest.setKppRating4(request.getKppRating4());
                keyPerfParamCreateRequest.setKppRating5(request.getKppRating5());
                keyPerfParamCreateRequest.setStatusCd(request.getStatusCd());
                keyPerfParamCreateRequest.setRemark(request.getRemark());
                keyPerfParamCreateRequest.setRrDescription(request.getRrDescription());

                KeyPerfParamCreateRequests.add(keyPerfParamCreateRequest);//final request

            } catch (Exception ex) {
                throw new KPIException("DesignationServiceImpl", false, "Issue in row no: " + currentExcelRow);

            } finally {
                System.out.println("employeeCreateRequests::" + KeyPerfParamCreateRequests);
            }
        }
        for (KeyPerfParamCreateRequest request : KeyPerfParamCreateRequests) {
            try {
                saveKeyPerfomanceParameter(request);

                log.info("DesignationCreateRequest::" + request);
            } catch (Exception ex) {
                KeyPerfParamNotSavedRecords.add(request);
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
    private Integer getDeptId(String deptName) {
        Optional<DepartmentEntity> optionalDepartmentEntity = departmentRepo.findByDeptNameEqualsIgnoreCase(deptName);
        if (optionalDepartmentEntity.isPresent()) {
            return optionalDepartmentEntity.get().getDeptId();
        }
        log.error("Inside EmployeeServiceImpl >> getRoleId");
        throw new KPIException("EmployeeServiceImpl", false, "Department Name is not exist");
    }
    private Integer getDesigId(String desigName) {
        Optional<DesignationEntity> optionalDesignationEntity = designationRepo.findByDesigNameEqualsIgnoreCase(desigName);
        if (optionalDesignationEntity.isPresent()) {
            return optionalDesignationEntity.get().getDesigId();
        }
        log.error("Inside EmployeeServiceImpl >> getRoleId");
        throw new KPIException("EmployeeServiceImpl", false, "Designation Name is not exist");
    }

    private KeyPerfParamEntity convertKeyPerfParamCreateRequestToEntity(KeyPerfParamCreateRequest keyPerfParamCreateRequest) {
        KeyPerfParamEntity keyPerfParamEntity = new KeyPerfParamEntity();
        keyPerfParamEntity.setRoleId(keyPerfParamCreateRequest.getRoleId());
        keyPerfParamEntity.setDeptId(keyPerfParamCreateRequest.getDeptId());
        keyPerfParamEntity.setDesigId(keyPerfParamCreateRequest.getDesigId());
        keyPerfParamEntity.setKppObjective(keyPerfParamCreateRequest.getKppObjective());
        keyPerfParamEntity.setKppPerformanceIndi(keyPerfParamCreateRequest.getKppPerformanceIndi());
        keyPerfParamEntity.setKppOverallTarget(keyPerfParamCreateRequest.getKppOverallTarget());
        keyPerfParamEntity.setKppTargetPeriod(keyPerfParamCreateRequest.getKppTargetPeriod());
        keyPerfParamEntity.setKppUoM(keyPerfParamCreateRequest.getKppUoM());
        keyPerfParamEntity.setKppOverallWeightage(keyPerfParamCreateRequest.getKppOverallWeightage());
        keyPerfParamEntity.setKppRating1(keyPerfParamCreateRequest.getKppRating1());
        keyPerfParamEntity.setKppRating2(keyPerfParamCreateRequest.getKppRating2());
        keyPerfParamEntity.setKppRating3(keyPerfParamCreateRequest.getKppRating3());
        keyPerfParamEntity.setKppRating4(keyPerfParamCreateRequest.getKppRating4());
        keyPerfParamEntity.setKppRating5(keyPerfParamCreateRequest.getKppRating5());

        keyPerfParamEntity.setRemark(keyPerfParamCreateRequest.getRemark());
        keyPerfParamEntity.setStatusCd(keyPerfParamCreateRequest.getStatusCd());
        keyPerfParamEntity.setCreatedUserId(keyPerfParamCreateRequest.getEmployeeId());
        return keyPerfParamEntity;
    }

    private KeyPerfParamEntity convertKeyPerfParamUpdateRequestToEntity(KeyPerfParamUpdateRequest keyPerfParamUpdateRequest) {
        KeyPerfParamEntity keyPerfParamEntity = new KeyPerfParamEntity();
        keyPerfParamEntity.setKppId(keyPerfParamUpdateRequest.getKppId());
        keyPerfParamEntity.setRoleId(keyPerfParamUpdateRequest.getRoleId());
        keyPerfParamEntity.setDeptId(keyPerfParamUpdateRequest.getDeptId());
        keyPerfParamEntity.setDesigId(keyPerfParamUpdateRequest.getDesigId());
        keyPerfParamEntity.setKppObjective(keyPerfParamUpdateRequest.getKppObjective());
        keyPerfParamEntity.setKppPerformanceIndi(keyPerfParamUpdateRequest.getKppPerformanceIndi());
        keyPerfParamEntity.setKppOverallTarget(keyPerfParamUpdateRequest.getKppOverallTarget());
        keyPerfParamEntity.setKppTargetPeriod(keyPerfParamUpdateRequest.getKppTargetPeriod());
        keyPerfParamEntity.setKppUoM(keyPerfParamUpdateRequest.getKppUoM());
        keyPerfParamEntity.setKppOverallWeightage(keyPerfParamUpdateRequest.getKppOverallWeightage());
        keyPerfParamEntity.setKppRating1(keyPerfParamUpdateRequest.getKppRating1());
        keyPerfParamEntity.setKppRating2(keyPerfParamUpdateRequest.getKppRating2());
        keyPerfParamEntity.setKppRating3(keyPerfParamUpdateRequest.getKppRating3());
        keyPerfParamEntity.setKppRating4(keyPerfParamUpdateRequest.getKppRating4());
        keyPerfParamEntity.setKppRating5(keyPerfParamUpdateRequest.getKppRating5());

        keyPerfParamEntity.setRemark(keyPerfParamUpdateRequest.getRemark());
        keyPerfParamEntity.setStatusCd(keyPerfParamUpdateRequest.getStatusCd());
        keyPerfParamEntity.setUpdatedUserId(keyPerfParamUpdateRequest.getEmployeeId());
        return keyPerfParamEntity;
    }
}