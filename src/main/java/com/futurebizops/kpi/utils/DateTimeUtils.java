package com.futurebizops.kpi.utils;

import com.futurebizops.kpi.exception.KPIException;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.time.temporal.ChronoUnit;

@Slf4j
public class DateTimeUtils {
    DateTimeUtils() {
    }

    public static String getFirstDayOfCurrentMonth() {
        LocalDate now = LocalDate.now();
        return now.withDayOfMonth(1).toString();
    }

    public static Instant convertStringToInstant(String dateTime) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return dateFormatter.parse(dateTime).toInstant();
        } catch (ParseException ex) {
            log.error("Inside DateTimeUtils >> convertStringToInstant()");
            throw new KPIException("DateTimeUtils", false, ex.getMessage());
        }
    }



    //Addding one day becuase when cummulative date is getting it's reducing one day in that
    public static Instant addOneDayToInstant(String dateTime) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return dateFormatter.parse(dateTime).toInstant().plus(1, ChronoUnit.DAYS);
        } catch (ParseException ex) {
            log.error("Inside DateTimeUtils >> convertStringToInstant()");
            throw new KPIException("DateTimeUtils", false, ex.getMessage());
        }
    }


    //Get first day of year
    public static String getFirstDateOfYear() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(5,1);
        calendar.set(2,0);
        Date date = calendar.getTime();
        return date.toInstant().toString();

    }

    public static String extractMonthName(String dateString) {
        try {
            SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
            Date date = dateFormatter.parse(dateString);

        LocalDate localDate =  date.toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
            return localDate.getMonth().toString();
        } catch (Exception ex) {
            log.error("Inside DateTimeUtils >> convertStringToInstant() : {}", ex);
            throw new KPIException("DateTimeUtils", false, ex.getMessage());
        }
    }


    public static Integer extractMonthValue(String dateString) {
        try {
            SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
            Date date = dateFormatter.parse(dateString);

            LocalDate localDate =  date.toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
            return localDate.getMonthValue();
        } catch (Exception ex) {
            log.error("Inside DateTimeUtils >> convertStringToInstant() : {}", ex);
            throw new KPIException("DateTimeUtils", false, ex.getMessage());
        }
    }


    public static Integer extractYear(String dateString) {
        try {
            SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
            Date date = dateFormatter.parse(dateString);

            LocalDate localDate =  date.toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
            return localDate.getYear();
        } catch (Exception ex) {
            log.error("Inside DateTimeUtils >> convertStringToInstant() : {}", ex);
            throw new KPIException("DateTimeUtils", false, ex.getMessage());
        }
    }

}
