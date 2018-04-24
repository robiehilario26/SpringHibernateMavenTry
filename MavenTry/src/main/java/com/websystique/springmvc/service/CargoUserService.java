package com.websystique.springmvc.service;

import java.util.List;

import com.websystique.springmvc.model.CargoUser;

public interface CargoUserService {
	
	CargoUser findById(Integer id);

	void saveCargoEmployee(CargoUser cargoUser);
	
	void updateCargoEmployee(CargoUser cargoUser);
	
	void deleteCargoEmployeeById(Integer id);
	
	List<CargoUser> cargoList();
	
	CargoUser findCargoUserByPlateNumber(String id);
	
	boolean isPlateNumberUnique(Integer id, String plateNumber);
	
	
	
	
}
