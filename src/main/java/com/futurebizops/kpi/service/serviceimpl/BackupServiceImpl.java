package com.futurebizops.kpi.service.serviceimpl;

import com.futurebizops.kpi.service.BackupService;
import com.futurebizops.kpi.utils.DBBackup;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
@Service
public class BackupServiceImpl implements BackupService {


    @Override
    public String getBckupCommand() {
        DBBackup.executeCommand("keyperformance", "root", "backup", "D:\\DB_Backup", "C:\\Program Files\\PostgreSQL\\14\\bin\\pg_dump", "");
    return "Backup success fully completed";
    }
}