package com.futurebizops.kpi.controller;

import com.futurebizops.kpi.entity.EvidenceEntity;
import com.futurebizops.kpi.repository.EvidenceRepo;
import com.futurebizops.kpi.response.KPIResponse;
import com.futurebizops.kpi.service.EvidenceService;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.time.Instant;

@CrossOrigin
@RestController
@RequestMapping(value = "/evidence")
@Slf4j
public class EvidenceController {

    @Autowired
    EvidenceService evidenceService;

    @Autowired
    EvidenceRepo evidenceRepo;

    @PostMapping
    public ResponseEntity<KPIResponse> uploadNewFile(@NotNull @RequestParam("multipartFile") MultipartFile multipartFile,
                                                     @RequestParam(required = false) Integer empId,
                                                     @RequestParam(required = false)String evMonth
                                                     ) throws IOException {

        return ResponseEntity.ok(evidenceService.uploadFile(multipartFile, empId, evMonth));


    }

    @GetMapping
    public ResponseEntity<byte[]> getEvidenceFile( @RequestParam(required = false) Integer empId) {

        EvidenceEntity fileEntity = evidenceRepo.findById(empId).get();

        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.valueOf(fileEntity.getEvContentType()));
        header.setContentLength(fileEntity.getEvFile().length);
        header.set("Content-Disposition", "attachment; filename=" + fileEntity.getEvFileName());

        return new ResponseEntity<>(fileEntity.getEvFile(), header, HttpStatus.OK);

    }



    @DeleteMapping
    public ResponseEntity<KPIResponse> deleteEvidence(@RequestParam(required = false) Integer empId) throws IOException {
        log.info("Request");
        return ResponseEntity.ok(evidenceService.deleteEvidenceFile(empId));
    }

    @GetMapping(value="/by-empid")
    public ResponseEntity<KPIResponse> getEmpoyeeEvidenceByEmpIdDetails(
            @RequestParam(required = false) Integer empId
            ) {
        KPIResponse response = evidenceService.getEmpoyeeEvidenceDetailsByEmpId(empId);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @GetMapping(value="/by-empid-evmonth")
    public ResponseEntity<KPIResponse> getEmpoyeeEvidenceByDetails(
            @RequestParam(required = false) Integer empId
    ) {
        KPIResponse response = evidenceService.getEmpoyeeEvidenceDetails(empId);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

}
