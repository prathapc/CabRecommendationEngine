package com.ola.hack.recommender;

import java.util.Date;
import java.util.Set;

import org.json.simple.JSONObject;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Controller;

import com.ola.hack.configuration.RabbitConfiguration;
import com.ola.hack.service.CustomerService;

@Controller
public class BookingTimeBasedRecommender {

	@Autowired
	private CustomerService customerService;

	public void initiatePushNotificationsBasedOnBookingTime(Integer userId, String gcmId, Set<Date> notificationTriggerTimings) {
		ApplicationContext context =
				new AnnotationConfigApplicationContext(RabbitConfiguration.class);
		AmqpTemplate template = context.getBean(AmqpTemplate.class);
		JSONObject notificationMessage = null;
		if(notificationTriggerTimings != null) {
			for(Date date : notificationTriggerTimings) {
				notificationMessage = new JSONObject();
				notificationMessage.put("customerId", userId);
				notificationMessage.put("notificationTime", date.toString());
				notificationMessage.put("gcmId", gcmId);
				
				template.convertAndSend("CabRecommendationQueue", notificationMessage.toString());
			}
		}

		//String foo = (String) template.receiveAndConvert("myqueue");
		/*
		List<CustomerRideDetails> customerRideDetails =  customerService.listCustomerRideDetails(userId);
		for(CustomerRideDetails crd : customerRideDetails) {

			System.out.println(crd.getCustomer().getId());
		}

		Sender sender = new Sender("apiKey");
		Message message = new Message.Builder()
		    .addData("message", "this is the message")
		    .addData("other-parameter", "some value")
		    .build();
		//Result result = sender.send(message, registrationId, numOfRetries);
		 */}
}
