package com.websystique.springmvc.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "TABLE_MAINTENANCE")
public class DeliveryType {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Size(min = 1, max = 100)
	@Column(name = "DELIVERY_TYPE", nullable = false)
	private String delivery_type;
	
	@NotNull
	@Digits(integer = 8, fraction = 2)
	@Column(name="DELIVERY_PRICE", nullable= false)
	private BigDecimal delivery_price;
	
	@NotNull
	@Digits(integer = 8, fraction = 2)
	@Column(name = "DELIVERY_WEIGHT", nullable =false)
	private BigDecimal delivery_weight;


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((delivery_type == null) ? 0 : delivery_type.hashCode());
		result = prime * result + id;
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
		DeliveryType other = (DeliveryType) obj;
		if (delivery_type == null) {
			if (other.delivery_type != null)
				return false;
		} else if (!delivery_type.equals(other.delivery_type))
			return false;
		if (id != other.id)
			return false;
		return true;
	}



	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getDelivery_type() {
		return delivery_type;
	}


	public void setDelivery_type(String delivery_type) {
		this.delivery_type = delivery_type;
	}


	public BigDecimal getDelivery_price() {
		return delivery_price;
	}


	public void setDelivery_price(BigDecimal delivery_price) {
		this.delivery_price = delivery_price;
	}


	public BigDecimal getDelivery_weight() {
		return delivery_weight;
	}


	public void setDelivery_weight(BigDecimal delivery_weight) {
		this.delivery_weight = delivery_weight;
	}


	@Override
	public String toString() {
		return "DeliveryType [id=" + id + ", delivery_type=" + delivery_type
				+ ", delivery_price=" + delivery_price + ", delivery_weight="
				+ delivery_weight + "]";
	}
	
	
}
