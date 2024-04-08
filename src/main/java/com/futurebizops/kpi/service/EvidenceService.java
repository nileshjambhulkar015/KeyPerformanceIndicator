package com.futurebizops.kpi.service;

import com.futurebizops.kpi.response.KPIResponse;
import org.springframework.web.multipart.MultipartFile;

public interface EvidenceService {

    public KPIResponse uploadFile(MultipartFile multipartFile, Integer empId, String evMonth);

    public KPIResponse deleteEvidenceFile(Integer empId);

    public KPIResponse getEmpoyeeEvidenceDetails(Integer empId);
    public KPIResponse getEmpoyeeEvidenceDetailsByEmpId(Integer empId);



}
