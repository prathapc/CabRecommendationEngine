package com.ola.hack.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "customer_ride_details")
@DynamicUpdate
public class CustomerRideDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@JoinColumn(name = "customer_id")
    @ManyToOne
	private Customer customer;
	
	@Column(name="from_latitude",columnDefinition="decimal(9,6)")
	private Float fromLatitude;
	
	@Column(name="to_latitude",columnDefinition="decimal(9,6)")
	private Float toLatitude;
	
	@Column(name="from_langitude",columnDefinition="decimal(9,6)")
	private Float fromLangitude;
	
	@Column(name="to_langitude",columnDefinition="decimal(9,6)")
	private Float toLangitude;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="ride_time")
	private Date rideTime;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_at")
	private Date createdAt;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Float getFromLatitude() {
		return fromLatitude;
	}

	public void setFromLatitude(Float fromLatitude) {
		this.fromLatitude = fromLatitude;
	}

	public Float getToLatitude() {
		return toLatitude;
	}

	public void setToLatitude(Float toLatitude) {
		this.toLatitude = toLatitude;
	}

	public Float getFromLangitude() {
		return fromLangitude;
	}

	public void setFromLangitude(Float fromLangitude) {
		this.fromLangitude = fromLangitude;
	}

	public Float getToLangitude() {
		return toLangitude;
	}

	public void setToLangitude(Float toLangitude) {
		this.toLangitude = toLangitude;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getRideTime() {
		return rideTime;
	}

	public void setRideTime(Date rideTime) {
		this.rideTime = rideTime;
	}
	
}
