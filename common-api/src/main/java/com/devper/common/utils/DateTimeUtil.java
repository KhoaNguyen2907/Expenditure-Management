package com.devper.common.utils;

import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@UtilityClass
public class DateTimeUtil {
        public  final String DATE_FORMAT = "yyyy-MM-dd";
        public  final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
        public String nowDateTime() {
            return LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT));
        }

        public DateTimeFormatter getDateTimeFormatter() {
            return DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
        }

    public DateTimeFormatter getDateFormatter() {
        return DateTimeFormatter.ofPattern(DATE_FORMAT);
    }
}
