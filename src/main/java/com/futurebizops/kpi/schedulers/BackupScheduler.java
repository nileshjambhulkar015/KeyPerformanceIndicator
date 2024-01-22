package com.futurebizops.kpi.schedulers;

import com.futurebizops.kpi.service.BackupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Component
@Slf4j
public class BackupScheduler {
    @Autowired
    BackupService backupService;
    @Scheduled(cron = "*/2 * * * * ?") // Execute every day at 5:30 PM
    public void doDatabaseBackupDaily() {
        log.info("Executing daily scheduled task...");
        backupService.getBckupCommand();
    }
}
