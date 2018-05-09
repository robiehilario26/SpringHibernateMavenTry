package com.websystique.springmvc.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "TABLE_BEEDING")
public class Beeding {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer beeding_id;

	@Column(name = "BEEDING_DELIVERY_ID", nullable = false)
	private Integer beeding_delivery_id;

	@Column(name = "USER_BEEDER_ID", nullable = false)
	private Integer user_beeder_id;

	@Column(name = "BEEDING_STARTINGPRICE", nullable = false)
	private Integer beeding_startingprice;

	@Column(name = "BEEDING_STATUS", nullable = false)
	private String beeding_status;

	@ManyToOne
	@JoinColumn(name = "BEEDING_DELIVERY_ID", insertable = false, updatable = false, nullable = false)
	private DeliveryRequest deliveryRequest;

	public DeliveryRequest getDeliveryRequest() {
		return deliveryRequest;
	}

	public void setDeliveryRequest(DeliveryRequest deliveryRequest) {
		this.deliveryRequest = deliveryRequest;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((beeding_delivery_id == null) ? 0 : beeding_delivery_id
						.hashCode());
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
		Beeding other = (Beeding) obj;
		if (beeding_delivery_id == null) {
			if (other.beeding_delivery_id != null)
				return false;
		} else if (!beeding_delivery_id.equals(other.beeding_delivery_id))
			return false;
		return true;
	}

	public Integer getBeeding_id() {
		return beeding_id;
	}

	public void setBeeding_id(Integer beeding_id) {
		this.beeding_id = beeding_id;
	}

	public Integer getBeeding_delivery_id() {
		return beeding_delivery_id;
	}

	public void setBeeding_delivery_id(Integer beeding_delivery_id) {
		this.beeding_delivery_id = beeding_delivery_id;
	}

	public Integer getUser_beeder_id() {
		return user_beeder_id;
	}

	public void setUser_beeder_id(Integer user_beeder_id) {
		this.user_beeder_id = user_beeder_id;
	}

	public Integer getBeeding_startingprice() {
		return beeding_startingprice;
	}

	public void setBeeding_startingprice(Integer beeding_startingprice) {
		this.beeding_startingprice = beeding_startingprice;
	}

	public String getBeeding_status() {
		return beeding_status;
	}

	public void setBeeding_status(String beeding_status) {
		this.beeding_status = beeding_status;
	}

	@Override
	public String toString() {
		return "Beeding [beeding_id=" + beeding_id + ", beeding_delivery_id="
				+ beeding_delivery_id + ", user_beeder_id=" + user_beeder_id
				+ ", beeding_startingprice=" + beeding_startingprice
				+ ", beeding_status=" + beeding_status + "]";
	}

}
