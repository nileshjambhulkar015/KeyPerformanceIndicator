package com.futurebizops.kpi.service.serviceimpl;

import com.futurebizops.kpi.constants.KPIConstants;
import com.futurebizops.kpi.entity.ReportEvidenceEntity;
import com.futurebizops.kpi.exception.KPIException;
import com.futurebizops.kpi.repository.ReportEvidenceRepo;
import com.futurebizops.kpi.request.ReportEvidenceCreateRequest;
import com.futurebizops.kpi.response.KPIResponse;
import com.futurebizops.kpi.service.ReportEvidenceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ReportEvidenceServiceImpl implements ReportEvidenceService {

    @Autowired
    ReportEvidenceRepo evidenceRepo;

    @Override
    public KPIResponse saveReportEvidence(ReportEvidenceCreateRequest reportEvidenceCreateRequest) {

        KPIResponse kpiResponse = new KPIResponse();
        try {
            ReportEvidenceEntity evidenceEntity = new ReportEvidenceEntity();
            evidenceEntity.setRemark(reportEvidenceCreateRequest.getRemark());
            evidenceEntity.setEmpId(reportEvidenceCreateRequest.getEmpId());
            evidenceEntity.setEvContentType(reportEvidenceCreateRequest.getEvContentType());
            evidenceEntity.setEvMonth(reportEvidenceCreateRequest.getEvMonth());
            evidenceEntity.setEvFileName(reportEvidenceCreateRequest.getEvFileName());
            evidenceEntity.setEvFile(reportEvidenceCreateRequest.getEvFile());
            evidenceEntity.setStatusCd("A");
            evidenceRepo.save(evidenceEntity);
            kpiResponse.setSuccess(true);
            kpiResponse.setResponseMessage(KPIConstants.RECORD_SUCCESS);
            return kpiResponse;
        } catch (Exception ex) {
            log.error("Inside ReportEvidenceServiceImpl >> saveReportEvidence()");
            throw new KPIException("ReportEvidenceServiceImpl", false, ex.getMessage());
        }
    }

    @Override
    public byte[] findReportEvidenceByEmpIdAndMonth(Integer empId, String evMonth) {
        return null;
    }
}
