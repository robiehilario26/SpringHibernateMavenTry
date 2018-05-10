package com.websystique.springmvc.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "TABLE_DELIVER")
public class DeliveryRequest {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int deliver_id;

	@Column(name = "USER_ID", nullable = false)
	private int user_id;

	@Column(name = "USER_BEED_CHOICE", nullable = true)
	private int user_beed_choice;

	@Column(name = "DELIVERY_TYPE", nullable = false)
	private int delivery_type;

	@Size(min = 1, max = 100)
	@Column(name = "DELIVERY_PICKUP_ADDRESS", nullable = false)
	private String delivery_pickup_address;

	@Size(min = 1, max = 100)
	@Column(name = "DELIVERY_DESTINATION", nullable = false)
	private String delivery_destination;

	@Size(min = 1, max = 100)
	@Column(name = "ITEM_DETAILS", nullable = false)
	private String item_details;

	@NotNull
	@JsonFormat(pattern = "YYYY-MM-dd")
	@DateTimeFormat(pattern = "YYYY-MM-dd")
	@Column(name = "PREFERRED_DATE", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date preferred_date;

	@Column(name = "DELIVERY_STATUS", nullable = false)
	private String delivery_status;

	@ManyToOne
	@JoinColumn(name = "DELIVERY_TYPE", insertable = false, updatable = false, nullable = false)
	private DeliveryType deliveryType;

	public DeliveryType getDeliveryType() {
		return deliveryType;
	}

	public void setDeliveryType(DeliveryType deliveryType) {
		this.deliveryType = deliveryType;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + deliver_id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DeliveryRequest other = (DeliveryRequest) obj;
		if (deliver_id != other.deliver_id)
			return false;
		return true;
	}

	public int getDeliver_id() {
		return deliver_id;
	}

	public void setDeliver_id(int deliver_id) {
		this.deliver_id = deliver_id;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	

	public int getUser_beed_choice() {
		return user_beed_choice;
	}

	public void setUser_beed_choice(int user_beed_choice) {
		this.user_beed_choice = user_beed_choice;
	}

	public int getDelivery_type() {
		return delivery_type;
	}

	public void setDelivery_type(int delivery_type) {
		this.delivery_type = delivery_type;
	}

	public String getDelivery_pickup_address() {
		return delivery_pickup_address;
	}

	public void setDelivery_pickup_address(String delivery_pickup_address) {
		this.delivery_pickup_address = delivery_pickup_address;
	}

	public String getDelivery_destination() {
		return delivery_destination;
	}

	public void setDelivery_destination(String delivery_destination) {
		this.delivery_destination = delivery_destination;
	}

	public String getItem_details() {
		return item_details;
	}

	public void setItem_details(String item_details) {
		this.item_details = item_details;
	}

	public Date getPreferred_date() {
		return preferred_date;
	}

	public void setPreferred_date(Date preferred_date) {
		this.preferred_date = preferred_date;
	}

	public String getDelivery_status() {
		return delivery_status;
	}

	public void setDelivery_status(String delivery_status) {
		this.delivery_status = delivery_status;
	}

	@Override
	public String toString() {
		return "DeliveryRequest [deliver_id=" + deliver_id + ", user_id="
				+ user_id + ", delivery_type=" + delivery_type
				+ ", delivery_pickup_address=" + delivery_pickup_address
				+ ", delivery_destination=" + delivery_destination
				+ ", item_details=" + item_details + ", preffered_date="
				+ preferred_date + ", delivery_status=" + delivery_status
				+ ", deliveryType=" + deliveryType + "]";
	}

}
