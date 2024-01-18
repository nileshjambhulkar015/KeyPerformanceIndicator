package com.futurebizops.kpi.controller;

import com.futurebizops.kpi.response.DepartmentReponse;
import com.futurebizops.kpi.service.BackupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/db")
public class BackupController {
    @Autowired
    BackupService backupService;
    @GetMapping
    public String getDBBackup() {

        return backupService.getBckupCommand();
    }

}
