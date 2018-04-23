package com.websystique.springmvc.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.websystique.springmvc.model.CargoUser;
import com.websystique.springmvc.model.Employee;

@Repository("cargoUserDao")
public class CargoUserDaoImpl extends AbstractDao<Integer, CargoUser> implements
		CargoUserDao {

	@Override
	public CargoUser findById(int id) {
		return getByKey(id);
	}

	@Override
	public void saveCargoEmployee(CargoUser cargoUser) {
		persist(cargoUser);

	}

	@Override
	public void deleteCargoEmployeeById(int cargo_id) {
		Query query = getSession().createQuery(
				"delete from table_cargo where cargo_id = :cargo_id");
		query.setInteger("cargo_id", cargo_id);
		query.executeUpdate();

	}

	// List all cargo users
	@SuppressWarnings("unchecked")
	@Override
	public List<CargoUser> cargoList() {
		Criteria criteria = createEntityCriteria();
		return (List<CargoUser>) criteria.list();
	}

}
