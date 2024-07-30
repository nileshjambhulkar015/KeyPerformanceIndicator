package com.futurebizops.kpi.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

@Service
@Slf4j
public class EmailUtils {

    @Autowired
    JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private static String fromMail;


    public void sendEmail(String senderMailId,String mailSubject, String htmlBody){
        MimeMessage message = javaMailSender.createMimeMessage();
        try{
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(senderMailId);
            helper.setFrom("akshay@futurebizops.com");
            helper.setSubject(mailSubject);
            helper.setText(htmlBody, true); // Set to true to enable HTML
            javaMailSender.send(message);
            log.info("Mail send successfully to mail id : {}", senderMailId);
        } catch (Exception ex){
         log.error("Exception inside EmailUtils >> sendEmail() : ",ex);
        }
    }
}
