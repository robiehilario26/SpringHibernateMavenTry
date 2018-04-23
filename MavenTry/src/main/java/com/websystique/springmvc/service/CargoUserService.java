package com.websystique.springmvc.service;

import java.util.List;

import com.websystique.springmvc.model.CargoUser;

public interface CargoUserService {
	
	CargoUser findById(int id);

	void saveCargoEmployee(CargoUser cargoUser);
	
	void updateCargoEmployee(CargoUser cargoUser);
	
	void deleteCargoEmployeeById(int id);
	
	List<CargoUser> cargoList();
	
	
}
