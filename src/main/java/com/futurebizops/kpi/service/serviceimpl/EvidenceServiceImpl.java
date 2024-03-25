package com.futurebizops.kpi.service.serviceimpl;

import com.futurebizops.kpi.entity.EvidenceEntity;
import com.futurebizops.kpi.exception.KPIException;
import com.futurebizops.kpi.repository.EvidenceRepo;
import com.futurebizops.kpi.response.KPIResponse;
import com.futurebizops.kpi.service.EvidenceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;

@Service
@Slf4j
public class EvidenceServiceImpl implements EvidenceService {

    @Autowired
    EvidenceRepo evidenceRepo;

    @Override
    public KPIResponse uploadFile(MultipartFile multipartFile, Integer empId) {
        KPIResponse kpiResponse = null;
        try {
            EvidenceEntity evidenceEntity = new EvidenceEntity();
            evidenceEntity.setEvFileName(multipartFile.getOriginalFilename());
            evidenceEntity.setEvContentType(multipartFile.getContentType());
            evidenceEntity.setEvMonth(Instant.now());
            evidenceEntity.setEmpId(empId);
            evidenceEntity.setEvFile(multipartFile.getBytes());
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
}
