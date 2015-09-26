package com.ola.hack.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "customer")
@DynamicUpdate
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name="name")
	private String name;

	@OneToMany(fetch=FetchType.EAGER, mappedBy = "customer")
	private List<CustomerRideDetails> customerRideDetails;

	public List<CustomerRideDetails> getCustomerRideDetails() {
		return customerRideDetails;
	}

	public void setCustomerRideDetails(List<CustomerRideDetails> customerRideDetails) {
		this.customerRideDetails = customerRideDetails;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
