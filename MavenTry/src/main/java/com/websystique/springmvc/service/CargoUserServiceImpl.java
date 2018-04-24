package com.websystique.springmvc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.websystique.springmvc.dao.CargoUserDao;
import com.websystique.springmvc.model.CargoUser;

@Service("cargoUserService")
@Transactional
public class CargoUserServiceImpl implements CargoUserService {

	@Autowired
	private CargoUserDao dao;

	@Override
	public CargoUser findById(Integer id) {
		return dao.findById(id);
	}

	@Override
	public void saveCargoEmployee(CargoUser cargoUser) {
		dao.saveCargoEmployee(cargoUser);

	}

	@Override
	public void updateCargoEmployee(CargoUser cargoUser) {
		CargoUser entity = dao.findById(cargoUser.getCargo_id());
		entity.setCargo_driver(cargoUser.getCargo_driver());
		entity.setCargo_vehicletype(cargoUser.getCargo_vehicletype());
		entity.setTruck_plate_number(cargoUser.getTruck_plate_number());
		entity.setCargo_company(cargoUser.getCargo_company());

	}

	@Override
	public void deleteCargoEmployeeById(Integer id) {
		dao.deleteCargoEmployeeById(id);

	}

	@Override
	public List<CargoUser> cargoList() {
		return dao.cargoList();
	}

	

	@Override
	public boolean isPlateNumberUnique(Integer id, String plateNumber) {
		CargoUser cargoUser = findCargoUserByPlateNumber(plateNumber);
		return (cargoUser == null || (id != null) && (cargoUser.getCargo_id() == id));
	}

	@Override
	public CargoUser findCargoUserByPlateNumber(String plate) {
		return dao.findCargoUserByPlateNumber(plate);
	}

}
