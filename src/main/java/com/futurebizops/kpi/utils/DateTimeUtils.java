package com.futurebizops.kpi.utils;

import com.futurebizops.kpi.exception.KPIException;
import lombok.extern.slf4j.Slf4j;

import java.text.DateFormat;
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
        log.debug("Inside DateTimeUtils >> convertStringToInstant() dateTime : {}", dateTime);
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return dateFormatter.parse(dateTime).toInstant();
        } catch (ParseException ex) {
            log.error("Inside DateTimeUtils >> convertStringToInstant() : {}", ex);
            throw new KPIException("DateTimeUtils >> convertStringToInstant()", false, ex.getMessage());
        }
    }


    //used thos method when emloye resolve complaint
    public static Instant convertResolveDateStringToInstant(String dateTime) {
        log.debug("Inside DateTimeUtils >> convertResolveDateStringToInstant() dateTime : {}", dateTime);
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        try {
            return dateFormatter.parse(dateTime).toInstant();
        } catch (ParseException ex) {
            log.error("Inside DateTimeUtils >> convertResolveDateStringToInstant() : {}", ex);
            throw new KPIException("DateTimeUtils >> convertResolveDateStringToInstant()", false, ex.getMessage());
        }
    }

    //Addding one day becuase when cummulative date is getting it's reducing one day in that
    public static Instant addOneDayToInstant(String dateTime) {
        log.debug("Inside DateTimeUtils >> addOneDayToInstant() dateTime : {}", dateTime);
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return dateFormatter.parse(dateTime).toInstant().plus(1, ChronoUnit.DAYS);
        } catch (ParseException ex) {
            log.error("Inside DateTimeUtils >> addOneDayToInstant() : {}", ex);
            throw new KPIException("DateTimeUtils >> addOneDayToInstant()", false, ex.getMessage());
        }
    }


    //Get first day of year
    public static String getFirstDateOfYear() {
        log.debug("Inside DateTimeUtils >> getFirstDateOfYear()");
        try{
        Calendar calendar = Calendar.getInstance();
        calendar.set(5,1);
        calendar.set(2,0);
        Date date = calendar.getTime();
        return date.toInstant().toString();
        } catch (Exception ex) {
            log.error("Inside DateTimeUtils >> getFirstDateOfYear() : {}", ex);
            throw new KPIException("DateTimeUtils >> getFirstDateOfYear()", false, ex.getMessage());
        }
    }

    public static String extractMonthName(String dateString) {
        log.debug("Inside DateTimeUtils >> extractMonthName() dateString : {}", dateString);
        try {
            SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
            Date date = dateFormatter.parse(dateString);

        LocalDate localDate =  date.toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
            return localDate.getMonth().toString();
        } catch (Exception ex) {
            log.error("Inside DateTimeUtils >> extractMonthName() : {}", ex);
            throw new KPIException("DateTimeUtils >> extractMonthName()", false, ex.getMessage());
        }
    }


    public static Integer extractMonthValue(String dateString) {
        log.debug("Inside DateTimeUtils >> extractMonthValue() dateString : {}", dateString);
        try {
            SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
            Date date = dateFormatter.parse(dateString);

            LocalDate localDate =  date.toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
            return localDate.getMonthValue();
        } catch (Exception ex) {
            log.error("Inside DateTimeUtils >> extractMonthValue() : {}", ex);
            throw new KPIException("DateTimeUtils >> extractMonthValue()", false, ex.getMessage());
        }
    }


    public static Integer extractYear(String dateString) {
        log.debug("Inside DateTimeUtils >> extractYear() dateString : {}", dateString);
        try {
            SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
            Date date = dateFormatter.parse(dateString);

            LocalDate localDate =  date.toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
            return localDate.getYear();
        } catch (Exception ex) {
            log.error("Inside DateTimeUtils >> extractYear() : {}", ex);
            throw new KPIException("DateTimeUtils >> extractYear()", false, ex.getMessage());
        }
    }

    public static String extractDateInDDMMYYY(String inputDate) {
        log.debug("Inside DateTimeUtils >> extractDateInDDMMYYY() dateTime : {}", inputDate);
        try {
            // define formatter
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm");
            // This is the format date we want
            DateFormat mSDF = new SimpleDateFormat("dd MMM, yyyy hh:mm a");

            return mSDF.format(formatter.parse(inputDate));
        } catch (Exception ex) {
            log.error("Inside DateTimeUtils >> extractDateInDDMMYYY() : {}", ex);
            throw new KPIException("DateTimeUtils >> extractDateInDDMMYYY()", false, ex.getMessage());
        }
    }
}
