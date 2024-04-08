package com.futurebizops.kpi.controller;

import com.futurebizops.kpi.entity.ReportEvidenceEntity;
import com.futurebizops.kpi.exception.KPIException;
import com.futurebizops.kpi.repository.ReportEvidenceRepo;
import com.futurebizops.kpi.service.ReportEvidenceService;
import com.futurebizops.kpi.utils.DateTimeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@CrossOrigin
@RestController
@Slf4j
@RequestMapping(value = "/report-evidence")
public class ReportEvidenceController {

    @Autowired
    ReportEvidenceService evidenceService;

    @Autowired
    ReportEvidenceRepo reportEvidenceRepo;

    @GetMapping
    public ResponseEntity<byte[]> findReportEvidenceByEmpIdAndMonth(
            @RequestParam(required = false) Integer empId,
            @RequestParam(required = false) String evMonth) {
        Optional<ReportEvidenceEntity> optionalEvidenceEntity = reportEvidenceRepo.findByEmpIdAndEvMonth(empId, DateTimeUtils.convertStringToInstant(evMonth));

        if (optionalEvidenceEntity.isPresent()) {
            ReportEvidenceEntity fileEntity = optionalEvidenceEntity.get();
            HttpHeaders header = new HttpHeaders();
            header.setContentType(MediaType.valueOf(fileEntity.getEvContentType()));
            header.setContentLength(fileEntity.getEvFile().length);
            header.set("Content-Disposition", "attachment; filename=" + fileEntity.getEvFileName());

            return new ResponseEntity<>(fileEntity.getEvFile(), header, HttpStatus.OK);
        } else {
            log.error("Inside ReportEvidenceServiceImpl >> saveReportEvidence()");
            throw new KPIException("ReportEvidenceServiceImpl", false, "File not found");
        }

    }
}
