package com.myproject.utils;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class DateAndTime {

	public static LocalDate date = LocalDate.now(ZoneId.of("Asia/Manila"));
    public static LocalTime time = LocalTime.now(ZoneId.of("Asia/Manila"));
    public static DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM-dd-yyyy");
    public static DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");

	public static String dateStamp(){
		return date.format(dateFormat);
	}

	public static String timeStamp(){
		return time.format(timeFormat);
	}
}
