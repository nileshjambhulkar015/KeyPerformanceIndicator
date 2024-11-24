package com.futurebizops.kpi.controller.masterrecords;

import com.futurebizops.kpi.entity.FinancialYearEntity;
import com.futurebizops.kpi.repository.FinancialYearRepo;
import com.futurebizops.kpi.request.EmployeeTypeCreateRequest;
import com.futurebizops.kpi.request.EmployeeTypeUpdateRequest;
import com.futurebizops.kpi.request.FinancialYearCreateRequest;
import com.futurebizops.kpi.request.FinancialYearUpdateRequest;
import com.futurebizops.kpi.response.EmployeeTypeResponse;
import com.futurebizops.kpi.response.KPIResponse;
import com.futurebizops.kpi.service.EmployeeTypeService;
import com.futurebizops.kpi.service.FinancialYearService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping(value = "/financial-year")
public class FinacialYearController {

    @Autowired
    private FinancialYearService financialYearService;

@Autowired
    FinancialYearRepo financialYearRepo;

    @PostMapping
    public ResponseEntity<KPIResponse> saveFinancialYear(@RequestBody FinancialYearCreateRequest financialYearCreateRequest) {
        KPIResponse response = financialYearService.saveFinancialYear(financialYearCreateRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/by-finyear")
    public ResponseEntity<FinancialYearEntity> findFinancialYearById(
            @RequestParam(required = false) Integer finYearId) {
        Optional<FinancialYearEntity> response = financialYearRepo.findById(finYearId);
        return new ResponseEntity<>(response.get(), HttpStatus.OK);

    }

    @PutMapping
    public ResponseEntity<KPIResponse> updateFinancialYear(@RequestBody FinancialYearUpdateRequest financialYearUpdateRequest) {
        KPIResponse response = financialYearService.updateFinancialYear(financialYearUpdateRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<KPIResponse> findFinancialYear(
            @RequestParam(required = false) Integer finYearId,
            @RequestParam(required = false) String finYearName,
            @RequestParam(required = false) String statusCd) {
        KPIResponse response = financialYearService.findFinancialYear(finYearId, finYearName, statusCd);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }


    @DeleteMapping
    public ResponseEntity<KPIResponse> deleteFinancialYear(@RequestParam(required = false) Integer finYearId) {
        KPIResponse response = financialYearService.deleteFinancialYear(finYearId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
