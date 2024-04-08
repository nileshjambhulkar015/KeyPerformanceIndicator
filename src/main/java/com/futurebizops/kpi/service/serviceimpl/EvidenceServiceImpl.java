package com.futurebizops.kpi.service.serviceimpl;

import com.futurebizops.kpi.constants.KPIConstants;
import com.futurebizops.kpi.entity.EvidenceEntity;
import com.futurebizops.kpi.exception.KPIException;
import com.futurebizops.kpi.repository.EvidenceRepo;
import com.futurebizops.kpi.response.DepartmentReponse;
import com.futurebizops.kpi.response.EvidenceResponse;
import com.futurebizops.kpi.response.KPIResponse;
import com.futurebizops.kpi.service.EvidenceService;
import com.futurebizops.kpi.utils.DateTimeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class EvidenceServiceImpl implements EvidenceService {

    @Autowired
    EvidenceRepo evidenceRepo;

    @Override
    public KPIResponse uploadFile(MultipartFile multipartFile, Integer empId, String evMonth) {
        KPIResponse kpiResponse = new KPIResponse();

       /* if(evMonth==null){
            kpiResponse.setResponseMessage("Please select date once again");
            kpiResponse.setSuccess(false);
            return kpiResponse;
        }*/
        Instant evDate = null!=evMonth?DateTimeUtils.convertStringToInstant(evMonth):Instant.now();
        try {
            EvidenceEntity evidenceEntity = new EvidenceEntity();
            evidenceEntity.setEvFileName(multipartFile.getOriginalFilename());
            evidenceEntity.setEvContentType(multipartFile.getContentType());
            evidenceEntity.setEvMonth(evDate);
            evidenceEntity.setEmpId(empId);
            evidenceEntity.setEvFile(multipartFile.getBytes());
            evidenceEntity.setStatusCd("A");
            evidenceRepo.save(evidenceEntity);
            kpiResponse = KPIResponse.builder()
                    .responseMessage("File Uploaded Successfully")
                    .isSuccess(true)
                    .build();
        }
        catch (Exception ex){
            log.error("Inside EvidenceServiceImpl >> uploadFile()");
            throw new KPIException("EvidenceServiceImpl", false, ex.getMessage());
        }
        return  kpiResponse;
    }

    @Transactional
    @Override
    public KPIResponse deleteEvidenceFile(Integer empId) {
        KPIResponse kpiResponse = new KPIResponse();

        try {
            evidenceRepo.deleteByEmpId(empId);
            kpiResponse = KPIResponse.builder()
                    .responseMessage("Delete Uploaded file successfully")
                    .isSuccess(true)
                    .build();
        }
        catch (Exception ex){
            log.error("Inside EvidenceServiceImpl >> uploadFile()");
            throw new KPIException("EvidenceServiceImpl", false, ex.getMessage());
        }
        return  kpiResponse;
    }

    @Override
    public KPIResponse getEmpoyeeEvidenceDetails(Integer empId) {
        KPIResponse kpiResponse = new KPIResponse();
      /*  if(evMonth==null){
            kpiResponse.setResponseMessage("Please select date once again");
            kpiResponse.setSuccess(false);
            return kpiResponse;
        }*/
        try {

            List<Object[]> evidenceData =  evidenceRepo.getEvidenceDetails(empId,"A");

            if(evidenceData.size()>0) {
                List<EvidenceResponse> evidenceResponses = evidenceData.stream().map(EvidenceResponse::new).collect(Collectors.toList());
                kpiResponse = KPIResponse.builder()
                        .responseMessage("Data fetch successfully")
                        .responseData(evidenceResponses.get(0))
                        .isSuccess(true)
                        .build();
            } else {
                kpiResponse = KPIResponse.builder()
                        .responseMessage(KPIConstants.RECORD_NOT_FOUND)
                        .responseData(new EvidenceResponse())
                        .isSuccess(false)
                        .build();
            }
        }
        catch (Exception ex){
            log.error("Inside EvidenceServiceImpl >> uploadFile()");
            throw new KPIException("EvidenceServiceImpl", false, ex.getMessage());
        }
        return  kpiResponse;
    }

    @Override
    public KPIResponse getEmpoyeeEvidenceDetailsByEmpId(Integer empId) {
        KPIResponse kpiResponse = new KPIResponse();
        try {

            List<Object[]> evidenceData =  evidenceRepo.getEvidenceDetailsByEmpId(empId,"A");


            if(evidenceData.size()>0) {

               List<EvidenceResponse> evidenceResponses = evidenceData.stream().map(EvidenceResponse::new).collect(Collectors.toList());
                kpiResponse = KPIResponse.builder()
                        .responseMessage("Data fetch successfully")
                        .responseData(evidenceResponses.get(0))
                        .isSuccess(true)
                        .build();
            } else {
                kpiResponse = KPIResponse.builder()
                        .responseMessage(KPIConstants.RECORD_NOT_FOUND)
                        .responseData(new EvidenceResponse())
                        .isSuccess(false)
                        .build();
            }
        }
        catch (Exception ex){
            log.error("Inside EvidenceServiceImpl >> uploadFile()");
            throw new KPIException("EvidenceServiceImpl", false, ex.getMessage());
        }
        return  kpiResponse;
    }

}
