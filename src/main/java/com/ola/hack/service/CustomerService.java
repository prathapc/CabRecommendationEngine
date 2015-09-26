package com.ola.hack.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ola.hack.dao.CustomerDao;
import com.ola.hack.model.CustomerRideDetails;

@Service
public class CustomerService {
	
	@Autowired
	private CustomerDao customerDao;

	public void setCustomerDao(CustomerDao customerDao) {
		this.customerDao = customerDao;
	}
	
	@Transactional
	public List<CustomerRideDetails> listCustomerRideDetails(Integer userId) {
		return this.customerDao.listCustomerRideDetails(userId);
	}
	
	@Transactional
	public List<CustomerRideDetails> listCustomerRideDetailsForRecommendation(Integer userId, Date tillTime) {
		return this.customerDao.listCustomerRideDetailsForRecommendation(userId, tillTime);
	}
}
