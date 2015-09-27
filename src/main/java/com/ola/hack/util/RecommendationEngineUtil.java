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
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int dayHours = cal.get(Calendar.HOUR_OF_DAY);
		int dayMins = cal.get(Calendar.MINUTE);
		int mins=0;
		for(int i=0;i<dayHours; i++) {
			mins +=60;
		}
		mins += dayMins;
		return mins;
	}

	public static Date getTimeFromMinutesOfTheDay(int finalMin) {
		Calendar cal = Calendar.getInstance();
		int hr = finalMin/60;
		int min = finalMin-(hr*60);
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE), hr, min);
		return cal.getTime();
	}

	public static void main(String args[]) {
		
	}
}
