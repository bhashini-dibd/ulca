package com.ulca.dataset.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DateUtil {
	
	public static String getCurrentDate() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS");
		Date date = cal.getTime();
		return df1.format(date);
	}
	
	public static boolean timeInHhMmSsFormat(String time) {
		
        DateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
        try {
			Date d = dateFormat.parse(time);
		} catch (ParseException e) {
			log.info("time :: " + time + " is not in hh:mm:ss format");
			return false;
		}
        return true;
	}

}
