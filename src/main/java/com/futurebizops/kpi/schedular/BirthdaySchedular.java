package com.futurebizops.kpi.schedular;

import com.futurebizops.kpi.service.BirthdayEmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class BirthdaySchedular {

    @Autowired
    private BirthdayEmailService birthdayEmailService;

    @Scheduled(cron = "0 0 1 * * ?") // Cron expression for running every minute
    public void execute() {
        try {
            log.info("Birthday scheduler started");
            birthdayEmailService.getBirthdays();
            log.info("Birthday scheduler completed");
        } catch (Exception ex){
            log.error("Excetion occurs in schedular : {}", ex);
        }
    }
}
