package com.ola.hack.recommender;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;
import com.ola.hack.model.CustomerRideDetails;
import com.ola.hack.service.CustomerService;

@Controller
public class BookingTimeBasedRecommender {
	
	@Autowired
	private CustomerService customerService;

	public void initiatePushNotificationsBasedOnBookingTime(Integer userId) {
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
	}
}
