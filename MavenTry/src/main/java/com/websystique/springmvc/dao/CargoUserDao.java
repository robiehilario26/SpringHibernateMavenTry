package com.websystique.springmvc.dao;

import java.util.List;

import com.websystique.springmvc.model.CargoUser;

public interface CargoUserDao {

	CargoUser findById(Integer id);

	void saveCargoEmployee(CargoUser cargoUser);

	void deleteCargoEmployeeById(Integer id);

	List<CargoUser> cargoList();
	
	CargoUser findCargoUserByPlateNumber(String id);

}
