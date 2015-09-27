package com.ola.hack.cron;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.ola.hack.model.CustomerRideDetails;
import com.ola.hack.recommender.BookingTimeBasedRecommender;
import com.ola.hack.service.CustomerService;
import com.ola.hack.util.RecommendationEngineUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@EnableScheduling
public class CronJob {

	@Autowired
	private BookingTimeBasedRecommender bookingTimeBasedRecommender;

	@Autowired
	private CustomerService customerService;

	@Scheduled(fixedDelay = 3000)
	public void fixedDelayTask() {
		Date analyseTillDate = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(analyseTillDate);
		c.add(Calendar.DATE, -7);
		analyseTillDate.setTime( c.getTime().getTime() );

		//hit db and get all customer ride details
		//then write an algo to fetch customer ids for which we need to send notification
		List<CustomerRideDetails> customerRideDetails =  customerService.listCustomerRideDetailsForRecommendation(101, analyseTillDate);
		long analyseingPastDays = RecommendationEngineUtil.getDifferenceDays(analyseTillDate, new Date());
		List<Integer> pastRideTimes = new ArrayList<Integer>();
		for(CustomerRideDetails customerRideDetail : customerRideDetails) {
			//check whether already booked can in last 3 hours, if not proceed
			pastRideTimes.add(RecommendationEngineUtil.getMinuteOfTheDay(customerRideDetail.getRideTime()));
		}
		Collections.sort(pastRideTimes);
		int prevMin,currMinute;
		int count=0;
		int finalMin=-1;
		int i,j;
		Set<Date> notificationTriggerTimings = new HashSet<Date>();
		for(i=0;i<pastRideTimes.size(); i++) {
			prevMin = pastRideTimes.get(i);
			for(j=i+1;j<pastRideTimes.size(); j++) {
				currMinute = pastRideTimes.get(j);
				if(currMinute-prevMin <= 120)  //considering 2 hour window
					count++;
				else
					break;
			}
			if(count > (analyseingPastDays/3)) {
				notificationTriggerTimings.add(RecommendationEngineUtil.getTimeFromMinutesOfTheDay(finalMin));
			}
		}
		if(notificationTriggerTimings.size() > 0) {
			//recommendation found for the customer
			bookingTimeBasedRecommender.initiatePushNotificationsBasedOnBookingTime(101, "APA91bGxfcFj2fcQkFlVI5Fc-U2QjiD3co7Q6d3_HRD2x_n1jmNxilxyRtL4HWuJ2mAGY9utbsbSADjX3lzs9OjjgjmERPuGqGIgr4qcv0kTUoXlYGQyA44mgV8ZQOGtsZM6B1LeiRnP", notificationTriggerTimings);
		}
	}

	//@Scheduled(fixedDelay = 3000)
	//@Scheduled(cron = "0 0 12 1 1/1 ? *") /* 1st of every month */
	public void cronTask(){
		Date analyseTillDate = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(analyseTillDate);
		c.add(Calendar.DATE, -30);
		analyseTillDate.setTime( c.getTime().getTime() );

		List<CustomerRideDetails> customerRideDetails =  customerService.listCustomerRideDetailsForRecommendation(101, analyseTillDate);
		Set<Date> pastRideTimes = new HashSet<Date>();
		
		Float distBetweenTwoRideDestinations;
		int count=0;
		int i,j;
		Set<Date> tempPastRideTimes = new HashSet<Date>();
		for(i=0;i<customerRideDetails.size(); i++) {
			for(j=i+1;j<customerRideDetails.size(); j++) {
				distBetweenTwoRideDestinations = RecommendationEngineUtil.distBtweenLatLonPairs(customerRideDetails.get(i).getFromLatitude(), customerRideDetails.get(i).getFromLangitude(), customerRideDetails.get(j).getFromLatitude(), customerRideDetails.get(j).getFromLangitude());
				if(distBetweenTwoRideDestinations <= 200) {
					count++;
					tempPastRideTimes.add(customerRideDetails.get(i).getRideTime());
					tempPastRideTimes.add(customerRideDetails.get(j).getRideTime());
				}
			}
			if(count >= 2) {
				pastRideTimes.addAll(tempPastRideTimes);
			}
			count=0;
			tempPastRideTimes = new HashSet<Date>();
		}
		System.out.println(pastRideTimes);
		
	}
	
	public static void main(String args[]) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_WEEK, 1);
		System.out.println(cal.get(Calendar.DAY_OF_WEEK));
				
	}

}
