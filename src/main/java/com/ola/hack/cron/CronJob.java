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

	@Scheduled(fixedDelay = 5000)
	public void fixedDelayTask() {
		System.out.println(new Date() + " This runs in a fixed delay");
		
		Date analyseTillDate = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(analyseTillDate);
		c.add(Calendar.DATE, -7);
		analyseTillDate.setTime( c.getTime().getTime() );

		//hit db and get all customer ride details
		//then write an algo to fetch customer ids for which we need to send notification
		List<CustomerRideDetails> customerRideDetails =  customerService.listCustomerRideDetailsForRecommendation(101, analyseTillDate);
		long analyseingPastDays = RecommendationEngineUtil.getDifferenceDays(new Date(), analyseTillDate);
		List<Integer> pastRideTimes = new ArrayList<Integer>();
		for(CustomerRideDetails customerRideDetail : customerRideDetails) {
			//check whether already booked can in last 3 hours, if not proceed
			pastRideTimes.add(RecommendationEngineUtil.getMinuteOfTheDay(customerRideDetail.getRideTime()));
		}
		Collections.sort(pastRideTimes);
		int prevMin,currMinute;
		int count=0,prevCount=0;
		int finalMin=-1;
		int i,j;
		Set<Integer> notificationTriggerTimings = new HashSet<Integer>();
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
				notificationTriggerTimings.add(finalMin);
			}
		}
		if(notificationTriggerTimings.size() > 0) {
			//recommendation found for the customer
			bookingTimeBasedRecommender.initiatePushNotificationsBasedOnBookingTime(101);
		}
	}
	
	/*@Scheduled(cron = "10 * * * * *")
	public void cronTask(){
		System.out.println(new Date() + " This runs in a cron schedule");
	}*/

}
