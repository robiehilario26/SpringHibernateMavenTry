package com.websystique.springmvc.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
 

import com.websystique.springmvc.model.Employee;

@Repository("employeeDao")
public class EmployeeDaoImpl extends AbstractDao<Integer, Employee> implements
		EmployeeDao {

	@Override
	public Employee findById(int id) {
		return getByKey(id);
	}

	// Insert employee data
	@Override
	public void saveEmployee(Employee employee) {
		persist(employee);
	}

	
	// Delete employee by SSn id
	@Override
	public void deleteEmployeeBySsn(String ssn) {
		Query query = getSession().createQuery(
				"delete from Employee where ssn = :ssn");
		query.setString("ssn", ssn);
		query.executeUpdate();

	}

	// List all employee
	@SuppressWarnings("unchecked")
	@Override
	public List<Employee> findAllEmployee() {
		Criteria criteria = createEntityCriteria();
		return (List<Employee>) criteria.list();
	}

	// Find employee by SSn id
	@Override
	public Employee findEmployeeBySsn(String ssn) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("ssn", ssn));
		return (Employee) criteria.uniqueResult();
	}

}
