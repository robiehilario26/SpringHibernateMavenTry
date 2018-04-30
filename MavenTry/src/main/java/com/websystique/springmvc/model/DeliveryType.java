package com.websystique.springmvc.model;

import java.math.BigDecimal;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "TABLE_MAINTENANCE")
public class DeliveryType {

	@Id
	@Column(name="ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Size(min = 1, max = 100)
	@Column(name = "MAINTE_DELIVERY_TYPE", nullable = false)
	private String mainte_delivery_type;
	
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
		result = prime * result + id;
		result = prime
				* result
				+ ((mainte_delivery_type == null) ? 0 : mainte_delivery_type
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
		DeliveryType other = (DeliveryType) obj;
		if (id != other.id)
			return false;
		if (mainte_delivery_type == null) {
			if (other.mainte_delivery_type != null)
				return false;
		} else if (!mainte_delivery_type.equals(other.mainte_delivery_type))
			return false;
		return true;
	}


	public String getMainte_delivery_type() {
		return mainte_delivery_type;
	}


	public void setMainte_delivery_type(String mainte_delivery_type) {
		this.mainte_delivery_type = mainte_delivery_type;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
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
		return "DeliveryType [id=" + id + ", mainte_delivery_type=" + mainte_delivery_type
				+ ", delivery_price=" + delivery_price + ", delivery_weight="
				+ delivery_weight + "]";
	}
	
	
}
