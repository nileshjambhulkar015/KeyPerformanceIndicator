package com.futurebizops.kpi.service.serviceimpl;

import com.futurebizops.kpi.entity.EvidenceEntity;
import com.futurebizops.kpi.exception.KPIException;
import com.futurebizops.kpi.repository.EvidenceRepo;
import com.futurebizops.kpi.response.EvidenceResponse;
import com.futurebizops.kpi.response.KPIResponse;
import com.futurebizops.kpi.service.EvidenceService;
import com.futurebizops.kpi.utils.DateTimeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class EvidenceServiceImpl implements EvidenceService {

    @Autowired
    EvidenceRepo evidenceRepo;

    @Override
    @Retryable(include = {KPIException.class}, maxAttemptsExpression = "${retry-max-attempts}")
    public KPIResponse uploadFile(MultipartFile multipartFile, Integer empId, String evMonth) {
        log.debug("Inside EvidenceServiceImpl >> uploadFile() empId :{}, evMonth : {}", empId, evMonth);

        KPIResponse kpiResponse = new KPIResponse();
        Instant evDate = null != evMonth ? DateTimeUtils.convertStringToInstant(evMonth) : Instant.now();
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
        } catch (Exception ex) {
            log.error("Inside EvidenceServiceImpl >> uploadFile()");
            throw new KPIException("EvidenceServiceImpl", false, ex.getMessage());
        }
        return kpiResponse;
    }

    @Transactional
    @Override
    @Retryable(include = {KPIException.class}, maxAttemptsExpression = "${retry-max-attempts}")
    public KPIResponse deleteEvidenceFile(Integer empId) {
        log.debug("Inside EvidenceServiceImpl >> deleteEvidenceFile() empId :{}", empId);
        KPIResponse kpiResponse = new KPIResponse();
        try {
            evidenceRepo.deleteByEmpId(empId);
            kpiResponse.setResponseMessage("Delete Uploaded file successfully");
            kpiResponse.setSuccess(true);
            return kpiResponse;
        } catch (Exception ex) {
            log.error("Inside EvidenceServiceImpl >> deleteEvidenceFile()");
            throw new KPIException("EvidenceServiceImpl >> deleteEvidenceFile", false, ex.getMessage());
        }
    }

    @Override
    @Retryable(include = {KPIException.class}, maxAttemptsExpression = "${retry-max-attempts}")
    public KPIResponse getEmpoyeeEvidenceDetails(Integer empId) {
        KPIResponse kpiResponse = new KPIResponse();
        try {
            List<Object[]> evidenceData = evidenceRepo.getEvidenceDetails(empId, "A");

            if (evidenceData.size() > 0) {
                List<EvidenceResponse> evidenceResponses = evidenceData.stream().map(EvidenceResponse::new).collect(Collectors.toList());
                kpiResponse.setResponseMessage("Data fetch successfully");
                kpiResponse.setResponseData(evidenceResponses.get(0));
                kpiResponse.setSuccess(true);
                return kpiResponse;
            }
        } catch (Exception ex) {
            log.error("Inside EvidenceServiceImpl >> getEmpoyeeEvidenceDetails()");
            throw new KPIException("EvidenceServiceImpl >>getEmpoyeeEvidenceDetails()", false, ex.getMessage());
        }
        kpiResponse.setResponseMessage("Data not found");
        kpiResponse.setSuccess(false);
        return kpiResponse;
    }

    @Override
    @Retryable(include = {KPIException.class}, maxAttemptsExpression = "${retry-max-attempts}")
    public KPIResponse getEmpoyeeEvidenceDetailsByEmpId(Integer empId) {
        KPIResponse kpiResponse = new KPIResponse();
        try {
            List<Object[]> evidenceData = evidenceRepo.getEvidenceDetailsByEmpId(empId, "A");
            if (evidenceData.size() > 0) {
                List<EvidenceResponse> evidenceResponses = evidenceData.stream().map(EvidenceResponse::new).collect(Collectors.toList());
                kpiResponse.setSuccess(true);
                kpiResponse.setResponseMessage("Evidence Data fetch successfully");
                kpiResponse.setResponseData(evidenceResponses.get(0));
                return kpiResponse;
            }
        } catch (Exception ex) {
            log.error("Inside EvidenceServiceImpl >> getEmpoyeeEvidenceDetailsByEmpId()");
            throw new KPIException("EvidenceServiceImpl >> getEmpoyeeEvidenceDetailsByEmpId()", false, ex.getMessage());
        }
        kpiResponse.setResponseMessage("Data not found");
        kpiResponse.setSuccess(false);
        return kpiResponse;
    }
}
