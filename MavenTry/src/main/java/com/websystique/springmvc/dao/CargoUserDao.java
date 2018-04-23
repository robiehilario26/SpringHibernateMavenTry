package com.websystique.springmvc.dao;

import java.util.List;

import com.websystique.springmvc.model.CargoUser;

public interface CargoUserDao {

	CargoUser findById(int id);

	void saveCargoEmployee(CargoUser cargoUser);

	void deleteCargoEmployeeById(int id);

	List<CargoUser> cargoList();
	
	CargoUser findCargoUserById(int id);

}
