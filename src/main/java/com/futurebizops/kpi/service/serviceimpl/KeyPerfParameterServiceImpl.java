package com.futurebizops.kpi.service.serviceimpl;

import com.futurebizops.kpi.constants.KPIConstants;
import com.futurebizops.kpi.entity.DepartmentEntity;
import com.futurebizops.kpi.entity.DesignationEntity;
import com.futurebizops.kpi.entity.KeyPerfParamAudit;
import com.futurebizops.kpi.entity.KeyPerfParamEntity;
import com.futurebizops.kpi.entity.RoleEntity;
import com.futurebizops.kpi.entity.UoMEntity;
import com.futurebizops.kpi.exception.KPIException;
import com.futurebizops.kpi.repository.DepartmentRepo;
import com.futurebizops.kpi.repository.DesignationRepo;
import com.futurebizops.kpi.repository.KeyPerfParameterAuditRepo;
import com.futurebizops.kpi.repository.KeyPerfParameterRepo;
import com.futurebizops.kpi.repository.RoleRepo;
import com.futurebizops.kpi.repository.UoMRepo;
import com.futurebizops.kpi.request.KeyPerfParamCreateRequest;
import com.futurebizops.kpi.request.KeyPerfParamUpdateRequest;
import com.futurebizops.kpi.request.uploadexcel.KeyPerfParamExcelReadData;
import com.futurebizops.kpi.response.AssignKPPResponse;
import com.futurebizops.kpi.response.DepartmentReponse;
import com.futurebizops.kpi.response.KPIResponse;
import com.futurebizops.kpi.response.KPPResponse;
import com.futurebizops.kpi.response.dropdown.DepartmentDDResponse;
import com.futurebizops.kpi.service.KeyPerfParameterService;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.CellType;
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

    @Autowired
    UoMRepo uoMRepo;

    @Override
    public KPIResponse saveKeyPerfomanceParameter(KeyPerfParamCreateRequest keyPerfParamCreateRequest) {
        KPIResponse response = new KPIResponse();
       Optional<KeyPerfParamEntity> optionalKeyPerfParamEntity = keyPerfParameterRepo.findByKppObjectiveNoEqualsIgnoreCaseAndStatusCd(keyPerfParamCreateRequest.getKppObjectiveNo(), "A");
       if(optionalKeyPerfParamEntity.isPresent()){
           response.setResponseMessage(KPIConstants.RECORD_ALREDY_EXIST);
           response.setSuccess(false);
           return response;
       }
        KeyPerfParamEntity keyPerfParamEntity = convertKeyPerfParamCreateRequestToEntity(keyPerfParamCreateRequest);
        try {
            keyPerfParameterRepo.save(keyPerfParamEntity);
            KeyPerfParamAudit keyPerfParamAudit = new KeyPerfParamAudit(keyPerfParamEntity);
            keyPerfParameterAuditRepo.save(keyPerfParamAudit);
            response.setResponseMessage(KPIConstants.RECORD_SUCCESS);
            response.setSuccess(true);
            return  response;
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
            KeyPerfParamAudit keyPerfParamAudit = new KeyPerfParamAudit(keyPerfParamEntity);
            keyPerfParameterAuditRepo.save(keyPerfParamAudit);
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
    public KPIResponse findKeyPerfomanceParameterDetails(Integer kppId, String kppObjectiveNo, String kppObjective, String statusCd, Pageable pageable) {
        String sortName = null;
        KPIResponse kpiResponse = new KPIResponse();
        //String sortDirection = null;
        Integer pageSize = pageable.getPageSize();
        Integer pageOffset = (int) pageable.getOffset();
        // pageable = KPIUtils.sort(requestPageable, sortParam, pageDirection);
        Optional<Sort.Order> order = pageable.getSort().get().findFirst();
        if (order.isPresent()) {
            sortName = order.get().getProperty();  //order by this field
          //  sortDirection = order.get().getDirection().toString(); // Sort ASC or DESC
        }

        try{
        Integer totalCount = keyPerfParameterRepo.getKeyPerfParameterCount(kppId, kppObjectiveNo, kppObjective, statusCd);
        List<Object[]> kppData = keyPerfParameterRepo.getKeyPerfParameterDetail(kppId, kppObjectiveNo,kppObjective, statusCd, sortName, pageSize, pageOffset);

        if(kppData.size()>0) {
            List<KPPResponse> kppResponses = kppData.stream().map(KPPResponse::new).collect(Collectors.toList());
            kppResponses = kppResponses.stream()
                    .sorted(Comparator.comparing(KPPResponse::getKppObjectiveNo))
                    .collect(Collectors.toList());
            kpiResponse.setSuccess(true);
            kpiResponse.setResponseData(new PageImpl<>(kppResponses, pageable, totalCount));
            kpiResponse.setResponseMessage(KPIConstants.RECORD_FETCH);
        } else {
            kpiResponse.setSuccess(false);
            kpiResponse.setResponseMessage(KPIConstants.RECORD_NOT_FOUND);
        }}
        catch (Exception ex){
            log.error("Inside KeyPerfParameterServiceImpl >> findKeyPerfomanceParameterDetails()");
            throw new KPIException("KeyPerfParameterServiceImpl class", false, ex.getMessage());
        }

       return kpiResponse;
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
        String rating1 = null;
        String rating2 = null;
        String rating3 = null;
        String rating4 = null;
        String rating5 = null;

        List<KeyPerfParamCreateRequest> keyPerfParamCreateRequests = new ArrayList<>();
        List<KeyPerfParamCreateRequest> keyPerfParamNotSavedRecords = new ArrayList<>();
        List<KeyPerfParamExcelReadData> KeyPerfParamData = new ArrayList<>();

        byte[] excelBytes = null;
        if (file.isEmpty()) {
            throw new KPIException("Inside KeyPerfParameterServiceImpl class", false, "File not uploaded");
        }

        try {
            excelBytes = file.getBytes();

        } catch (Exception ex) {
            log.error("KeyPerfParamServiceImpl >> uploadKppExcelFile : {}",ex);
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
                    model.setKppObjectiveNo(row.getCell(1).getStringCellValue().trim());

                    model.setKppObjective(row.getCell(2).getStringCellValue().trim());
                    model.setKppPerformanceIndi(row.getCell(3).getStringCellValue().trim());

                    model.setKppTargetPeriod(row.getCell(4).getStringCellValue().trim());
                    model.setKppUoM(row.getCell(5).getStringCellValue().trim());


                    if (row.getCell(6).getCellType().equals(CellType.NUMERIC)) {
                        rating1=  String.valueOf(row.getCell(6).getNumericCellValue());
                    } else{
                        rating1 = row.getCell(6).getStringCellValue().trim();
                    }

                    model.setKppRating1(rating1);

                    if (row.getCell(7).getCellType().equals(CellType.NUMERIC)) {
                        rating2=  String.valueOf(row.getCell(7).getNumericCellValue());
                    } else{
                        rating2 = row.getCell(7).getStringCellValue().trim();
                    }

                    model.setKppRating2(rating2);

                    if (row.getCell(8).getCellType().equals(CellType.NUMERIC)) {
                        rating3=  String.valueOf(row.getCell(8).getNumericCellValue());
                    } else{
                        rating3 = row.getCell(8).getStringCellValue().trim();
                    }

                    model.setKppRating3(rating3);

                    if (row.getCell(9).getCellType().equals(CellType.NUMERIC)) {
                        rating4=  String.valueOf(row.getCell(9).getNumericCellValue());
                    } else{
                        rating4 = row.getCell(9).getStringCellValue().trim();
                    }

                    model.setKppRating4(rating4);

                    if (row.getCell(10).getCellType().equals(CellType.NUMERIC)) {
                        rating5=  String.valueOf(row.getCell(10).getNumericCellValue());
                    } else{
                        rating5 = row.getCell(10).getStringCellValue().trim();
                    }
                    model.setKppRating5(rating5);
                    model.setStatusCd("A");
                    model.setRemark(row.getCell(11).getStringCellValue().trim());
                    KeyPerfParamData.add(model);
                }
            }
            workbook.close();
        } catch (Exception ex) {
            log.error("Inside KeyPerfParamServiceImpl >> uploadKppExcelFile() : {}",ex);
            log.error("Inside KeyPerfParamServiceImpl >> uploadKppExcelFile() Issue in row no: {}", currentRow);
        }

        Integer currentExcelRow = 0;
        for (KeyPerfParamExcelReadData request : KeyPerfParamData) {
            try {
                currentExcelRow++;
                Integer uomId = getUoMId(request.getKppUoM());
                log.info("Inside KeyPerfParamServiceImpl >> uploadKppExcelFile() UomId = {}", uomId);
                if(null != uomId) {
                    KeyPerfParamCreateRequest keyPerfParamCreateRequest = new KeyPerfParamCreateRequest();
                    keyPerfParamCreateRequest.setEmployeeId("1");
                    keyPerfParamCreateRequest.setKppObjectiveNo(request.getKppObjectiveNo());
                    keyPerfParamCreateRequest.setKppObjective(request.getKppObjective());
                    keyPerfParamCreateRequest.setKppTargetPeriod(request.getKppTargetPeriod());
                    keyPerfParamCreateRequest.setKppPerformanceIndi(request.getKppPerformanceIndi());
                    keyPerfParamCreateRequest.setUomId(uomId);
                    keyPerfParamCreateRequest.setKppRating1(request.getKppRating1());
                    keyPerfParamCreateRequest.setKppRating2(request.getKppRating2());
                    keyPerfParamCreateRequest.setKppRating3(request.getKppRating3());
                    keyPerfParamCreateRequest.setKppRating4(request.getKppRating4());
                    keyPerfParamCreateRequest.setKppRating5(request.getKppRating5());
                    keyPerfParamCreateRequest.setStatusCd(request.getStatusCd());
                    keyPerfParamCreateRequest.setRemark(request.getRemark());

                    keyPerfParamCreateRequests.add(keyPerfParamCreateRequest);//final request
                } else{
                    log.info("Inside KeyPerfParameterServiceImpl >> uploadKppExcelFile() Row data is not added for: {}", request.getKppObjectiveNo());
                }
                log.info("Total record added : {}", keyPerfParamCreateRequests.size());
            } catch (Exception ex) {
                log.error("Inside KeyPerfParameterServiceImpl >> uploadKppExcelFile() : {} ",ex);
               // throw new KPIException("KeyPerfParameterServiceImpl", false, "Issue in row no: " + currentExcelRow);
            }
        }
        for (KeyPerfParamCreateRequest request : keyPerfParamCreateRequests) {
            try {
                if(null != request.getUomId()) {
                    saveKeyPerfomanceParameter(request);
                }else{
                    keyPerfParamNotSavedRecords.add(request);
                }

            } catch (Exception ex) {
                log.error("Exception in KeyPerfParameterServiceImpl >> line 301 : {}", ex);
                keyPerfParamNotSavedRecords.add(request);
            }
        }
        log.info("KeyPerfParamNotSavedRecords" + keyPerfParamNotSavedRecords);
    }


    private Integer getUoMId(String uomName) {
        Optional<UoMEntity> uoMEntity = uoMRepo.findByUomNameEqualsIgnoreCase(uomName);
        if (uoMEntity.isPresent()) {
            return uoMEntity.get().getUomId();
        }
        return null;
    }

    private KeyPerfParamEntity convertKeyPerfParamCreateRequestToEntity(KeyPerfParamCreateRequest keyPerfParamCreateRequest) {
        KeyPerfParamEntity keyPerfParamEntity = new KeyPerfParamEntity();
        keyPerfParamEntity.setKppObjectiveNo(keyPerfParamCreateRequest.getKppObjectiveNo());
        keyPerfParamEntity.setKppObjective(keyPerfParamCreateRequest.getKppObjective());
        keyPerfParamEntity.setKppPerformanceIndi(keyPerfParamCreateRequest.getKppPerformanceIndi());
       // keyPerfParamEntity.setKppOverallTarget(keyPerfParamCreateRequest.getKppOverallTarget());
        keyPerfParamEntity.setKppTargetPeriod(keyPerfParamCreateRequest.getKppTargetPeriod());
        keyPerfParamEntity.setUomId(keyPerfParamCreateRequest.getUomId());
       // keyPerfParamEntity.setKppOverallWeightage(keyPerfParamCreateRequest.getKppOverallWeightage());
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
        keyPerfParamEntity.setKppObjectiveNo(keyPerfParamUpdateRequest.getKppObjectiveNo());
        keyPerfParamEntity.setKppObjective(keyPerfParamUpdateRequest.getKppObjective());
        keyPerfParamEntity.setKppPerformanceIndi(keyPerfParamUpdateRequest.getKppPerformanceIndi());
       // keyPerfParamEntity.setKppOverallTarget(keyPerfParamUpdateRequest.getKppOverallTarget());
        keyPerfParamEntity.setKppTargetPeriod(keyPerfParamUpdateRequest.getKppTargetPeriod());
        keyPerfParamEntity.setUomId(keyPerfParamUpdateRequest.getUomId());
        //keyPerfParamEntity.setKppOverallWeightage(keyPerfParamUpdateRequest.getKppOverallWeightage());
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