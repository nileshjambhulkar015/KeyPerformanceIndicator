package com.futurebizops.kpi.service.serviceimpl;


import com.futurebizops.kpi.constants.KPIConstants;
import com.futurebizops.kpi.repository.EmployeeRepo;
import com.futurebizops.kpi.response.DesignationReponse;
import com.futurebizops.kpi.response.EmployeeBirthdayResponse;
import com.futurebizops.kpi.response.KPIResponse;
import com.futurebizops.kpi.service.BirthdayEmailService;
import com.futurebizops.kpi.utils.EmailUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BirthdayEmailServiceImpl implements BirthdayEmailService {



    @Autowired
    EmployeeRepo employeeRepo;

    @Autowired
    EmailUtils emailUtils;

    @Override
    public KPIResponse getBirthdays() throws MessagingException {

        List<Object[]> todaysBirthDays = employeeRepo.getTodaysBirthDaysList();

        List<EmployeeBirthdayResponse> employeeBirthdayResponses = todaysBirthDays.stream().map(EmployeeBirthdayResponse::new).collect(Collectors.toList());

        for (EmployeeBirthdayResponse employeeBirthdayResponse : employeeBirthdayResponses){

            if(StringUtils.isNotEmpty(employeeBirthdayResponse.getEmailId())) {
                String messageBody = "<html><body>DEAR " + employeeBirthdayResponse.getEmpFirstName() + " " + employeeBirthdayResponse.getEmpLastName() + ",<br><br>" +
                        "!!!!!!!!!🎂 WISH YOU MANY MANY HAPPY RETURNS OF THE DAY 🎂!!!!!!!!!<br><br>" +
                        "On your birthday, we send you our warmest and most heartfelt wishes. We are thrilled to be able to share this great day with you, and glad to have you as a valuable member of the team.<br><br>" +
                        "Taikisha management wishes you a very happy birthday.<br><br>" +
                        "Thanks & Regards,<br>" +
                        "Taikisha Engineering India Pvt. Ltd.<br><br>" +
                        "\"Please do not reply to this email as it is sent from an unattended mailbox\"<br><br>" +
                        "<img src='cid:imageId'/></body></html>";

                String mailSubject = "Birthday wishes";
               emailUtils.sendEmail(employeeBirthdayResponse.getEmailId(),mailSubject,messageBody);
                System.out.println("Email sent successfully with image!");
            }
        }

        KPIResponse kpiResponse = new KPIResponse();

        kpiResponse.setSuccess(true);
        kpiResponse.setResponseData(employeeBirthdayResponses);
        kpiResponse.setResponseMessage(KPIConstants.RECORD_FETCH);
        System.out.println("Todays Birtday::" + kpiResponse.getResponseData().toString());
     //   System.out.println("Todays Birtday API DATA::" + birthDays.size()
        //);
        return kpiResponse;

    }
}

