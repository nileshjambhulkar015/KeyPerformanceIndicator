package com.futurebizops.kpi.schedulers;

import com.futurebizops.kpi.service.BackupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class BackupScheduler {
    @Autowired
    BackupService backupService;
    @Scheduled(cron = "0 30 17 * * ?") // Execute every day at 5:30 PM
    public void doDatabaseBackupDaily() {
        System.out.println("Executing daily scheduled task...");
        backupService.getBckupCommand();
    }
}
