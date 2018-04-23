package com.websystique.springmvc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.websystique.springmvc.dao.CargoUserDao;
import com.websystique.springmvc.model.CargoUser;
import com.websystique.springmvc.model.Employee;

@Service("cargoUserService")
@Transactional
public class CargoUserServiceImpl implements CargoUserService {
	
	@Autowired
	private CargoUserDao dao;

	@Override
	public CargoUser findById(int id) {
		return dao.findById(id);
	}

	@Override
	public void saveCargoEmployee(CargoUser cargoUser) {
		dao.saveCargoEmployee(cargoUser);

	}

	@Override
	public void updateCargoEmployee(CargoUser cargoUser) {
		CargoUser entity = dao.findById(cargoUser.getId());
		entity.setCargo_driver(cargoUser.getCargo_driver());
		entity.setCargo_vehicletype(cargoUser.getCargo_vehicletype());
		entity.setCargo_company(cargoUser.getCargo_company());


	}

	@Override
	public void deleteCargoEmployeeById(int id) {
		dao.deleteCargoEmployeeById(id);

	}

	@Override
	public List<CargoUser> cargoList() {
		return dao.cargoList();
	}

}
