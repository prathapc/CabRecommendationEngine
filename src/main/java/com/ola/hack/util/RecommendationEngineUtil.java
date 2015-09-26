package com.ola.hack.util;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class RecommendationEngineUtil {

	public static long getDifferenceDays(Date d1, Date d2) {
	    long diff = d2.getTime() - d1.getTime();
	    return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
	}
	
	public static int getMinuteOfTheDay(Date date) {
		Calendar now = Calendar.getInstance();
		now.setTime(date);
		int dayHours = now.get(Calendar.HOUR_OF_DAY);
		int dayMins = now.get(Calendar.MINUTE);
		int mins=0;
		for(int i=0;i<dayHours; i++) {
			mins +=60;
		}
		mins += dayMins;
		return mins;
	}
}
