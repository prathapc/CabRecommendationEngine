package com.ola.hack.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ola.hack.model.CustomerRideDetails;

@Repository
public class CustomerDao {

	@Autowired
	private SessionFactory sessionFactory;

	/*public void setSessionFactory(SessionFactory sf){
		this.sessionFactory = sf;
	}*/

	public List<CustomerRideDetails> listCustomerRideDetails(Integer userId) {
		// TODO Auto-generated method stub
		Session session = this.sessionFactory.getCurrentSession();
		List<CustomerRideDetails> customerRideDetails = session.createQuery("from "+CustomerRideDetails.class.getName()+" cd where cd.customer.id="+userId).list();
		return customerRideDetails;
	}
	
	public List<CustomerRideDetails> listCustomerRideDetailsForRecommendation(Integer userId, Date tillTime) {
		// TODO Auto-generated method stub
		Session session = this.sessionFactory.getCurrentSession();
		//List<CustomerRideDetails> customerRideDetails = session.createQuery("from "+CustomerRideDetails.class.getName()+" cd where cd.customer.id="+userId+" and cd.createdAt >"+tillTime).list();
		
		Query query = session.createQuery("select cd from CustomerRideDetails cd where cd.customer.id=:customerId and cd.createdAt > :tillTime");
		query.setParameter("customerId", userId);
		query.setTimestamp("tillTime", tillTime);
		List<CustomerRideDetails> customerRideDetails = query.list();
		query.list();
		
		return customerRideDetails;
	}

}
