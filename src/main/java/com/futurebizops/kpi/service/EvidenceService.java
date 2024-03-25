package com.futurebizops.kpi.service;

import com.futurebizops.kpi.response.KPIResponse;
import org.springframework.web.multipart.MultipartFile;

public interface EvidenceService {

    public KPIResponse uploadFile(MultipartFile multipartFile, Integer empId);

}
