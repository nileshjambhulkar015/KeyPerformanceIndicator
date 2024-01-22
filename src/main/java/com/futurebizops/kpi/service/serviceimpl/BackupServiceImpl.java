package com.futurebizops.kpi.service.serviceimpl;

import com.futurebizops.kpi.service.BackupService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class BackupServiceImpl implements BackupService {

    @Value("databsename")
    private String databaseName;

    @Value("dbusername")
    private String userName;

    @Value("dbuserpassword")
    private String userPassword;

    @Value("storagepath")
    private String storagePath;

    @Value("type")
    private String generationType;   //values are sql file or backup file

    @Value("pgdumppath")
    private String pgDumpPath;   //where postgres sql software is install

    @Value("pgrestorepath")
    private String pgRestorePath;   //where postgres sql software is install


    @Override
    public String getBckupCommand() {
        //executeCommand("keyperformance", "postgres", "backup", "D:\\DB_Backup", "C:\\Program Files\\PostgreSQL\\14\\bin\\pg_dump", "");
    /*  File backupFilePath = new File(System.getProperty("user.home")
                + File.separator + "backup_" + databaseName);
*/
        File backupFilePath = new File(storagePath
                + File.separator + "backup_" + databaseName);
        if (!backupFilePath.exists()) {
            File dir = backupFilePath;
            dir.mkdirs();
        }
        System.out.println("===>backupFilePath ====> " + backupFilePath);
        //   SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");

        // String backupFileName = "backup_" + databaseName + "_" + sdf.format(new Date()) + ".sql";
        String backupFileName = "backup_" + databaseName + "_" + sdf.format(new Date()) + ".backup";

        List<String> commands = getPgComands(databaseName, backupFilePath, backupFileName, generationType, pgDumpPath, pgRestorePath);
        if (!commands.isEmpty()) {
            try {
                ProcessBuilder pb = new ProcessBuilder(commands);
                pb.environment().put("PGPASSWORD", userPassword);

                Process process = pb.start();

                try (BufferedReader buf = new BufferedReader(
                        new InputStreamReader(process.getErrorStream()))) {
                    String line = buf.readLine();
                    while (line != null) {
                        System.err.println(line);
                        line = buf.readLine();
                    }
                }

                process.waitFor();
                process.destroy();

                System.out.println("===> Success on " + generationType + " process.");
            } catch (IOException | InterruptedException ex) {
                System.out.println("Exception: " + ex);
            }
        } else {
            System.out.println("Error: Invalid params.");
        }
        return "Backup success fully completed";
    }

    private static List<String> getPgComands(String databaseName, File backupFilePath, String backupFileName, String type, String pgdumppath, String pgrestorepath) {

        ArrayList<String> commands = new ArrayList<>();
        switch (type) {
            case "backup":
                commands.add(pgdumppath);
                // commands.add("C:\\Program Files\\PostgreSQL\\10\\bin\\pg_dump");
                commands.add("-h"); //database server host
                commands.add("localhost");
                commands.add("-p"); //database server port number
                commands.add("5432");
                commands.add("-U"); //connect as specified database user
                commands.add("postgres");
                commands.add("-F"); //output file format (custom, directory, tar, plain text (default))
                commands.add("c");
                commands.add("-b"); //include large objects in dump
                commands.add("-v"); //verbose mode
                commands.add("-f"); //output file or directory name
                commands.add(backupFilePath.getAbsolutePath() + File.separator + backupFileName);
                commands.add("-d"); //database name
                commands.add(databaseName);
                break;
            case "restore":
                commands.add(pgrestorepath);
                // commands.add("C:\\Program Files\\PostgreSQL\\10\\bin\\pg_restore");
                commands.add("-h");
                commands.add("localhost");
                commands.add("-p");
                commands.add("5432");
                commands.add("-U");
                commands.add("postgres");
                commands.add("-d");
                commands.add(databaseName);
                commands.add("-v");
                commands.add(backupFilePath.getAbsolutePath() + File.separator + backupFileName);
                break;
            default:
                return Collections.EMPTY_LIST;
        }
        return commands;
    }

}