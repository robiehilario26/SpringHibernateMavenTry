package com.websystique.springmvc.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.websystique.springmvc.model.CargoUser;
import com.websystique.springmvc.model.User;


@Repository("cargoUserDao")
public class CargoUserDaoImpl extends AbstractDao<Integer, CargoUser> implements
		CargoUserDao {

	@Override
	public CargoUser findById(Integer id) {
		return getByKey(id);
	}

	@Override
	public void saveCargoEmployee(CargoUser cargoUser) {
		persist(cargoUser);

	}

	@Override
	public void deleteCargoEmployeeById(Integer cargo_id) {
		Criteria crit = createEntityCriteria();
		crit.add(Restrictions.eq("cargo_id", cargo_id));
		CargoUser cargoUser = (CargoUser)crit.uniqueResult();
		delete(cargoUser);

	}

	// List all cargo users
	@SuppressWarnings("unchecked")
	@Override
	public List<CargoUser> cargoList() {
		Criteria criteria = createEntityCriteria();
		return (List<CargoUser>) criteria.list();
	}

	@Override
	public CargoUser findCargoUserByPlateNumber(String plateNumber) {
		Criteria crit = createEntityCriteria();
		crit.add(Restrictions.eq("truck_plate_number", plateNumber));
		return (CargoUser) crit.uniqueResult();
		 
	}

	
	
	

}
