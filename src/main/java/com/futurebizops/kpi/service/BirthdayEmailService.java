package com.futurebizops.kpi.service;

import com.futurebizops.kpi.response.KPIResponse;

import javax.mail.MessagingException;

public interface BirthdayEmailService {

    public KPIResponse getBirthdays() throws MessagingException;
}
