package com.websystique.springmvc.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name = "TABLE_CARGO")
public class CargoUser {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int cargo_id;

	@Size(min = 1, max = 50)
	@Column(name = "CARGO_DRIVER", nullable = false)
	private String cargo_driver;

	@Size(min = 1, max = 200)
	@Column(name = "CARGO_VEHICLETYPE", nullable = false)
	private String cargo_vehicletype;

	@Size(min = 1, max = 200)
	@Column(name = "CARGO_COMPANY", nullable = false)
	private String cargo_company;


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((cargo_company == null) ? 0 : cargo_company.hashCode());
		result = prime * result + cargo_id;
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
		CargoUser other = (CargoUser) obj;
		if (cargo_company == null) {
			if (other.cargo_company != null)
				return false;
		} else if (!cargo_company.equals(other.cargo_company))
			return false;
		if (cargo_id != other.cargo_id)
			return false;
		return true;
	}

	public int getCargo_id() {
		return cargo_id;
	}

	public void setCargo_id(int cargo_id) {
		this.cargo_id = cargo_id;
	}

	public String getCargo_driver() {
		return cargo_driver;
	}

	public void setCargo_driver(String cargo_driver) {
		this.cargo_driver = cargo_driver;
	}

	public String getCargo_vehicletype() {
		return cargo_vehicletype;
	}

	public void setCargo_vehicletype(String cargo_vehicletype) {
		this.cargo_vehicletype = cargo_vehicletype;
	}

	public String getCargo_company() {
		return cargo_company;
	}

	public void setCargo_company(String cargo_company) {
		this.cargo_company = cargo_company;
	}

	@Override
	public String toString() {
		return "CargoUser [cargo_id=" + cargo_id + ", cargo_driver=" + cargo_driver
				+ ", cargo_vehicletype=" + cargo_vehicletype
				+ ", cargo_company=" + cargo_company + "]";
	}

	public CargoUser() {
		
	}

	
	
}
