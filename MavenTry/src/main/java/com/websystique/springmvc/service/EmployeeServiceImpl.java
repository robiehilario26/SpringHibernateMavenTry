package com.websystique.springmvc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.websystique.springmvc.dao.EmployeeDao;
import com.websystique.springmvc.model.Employee;

@Service("employeeService")
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeDao dao;

	@Override
	public Employee findById(int id) {
		return dao.findById(id);
	}

	@Override
	public void saveEmployee(Employee employee) {
		dao.saveEmployee(employee);

	}

	/*
	 * Since the method is running with Transaction, No need to call hibernate
	 * update explicitly. Just fetch the entity from db and update it with
	 * proper values within transaction. It will be updated in db once
	 * transaction ends.
	 */

	@Override
	public void updateEmployee(Employee employee) {
		Employee entity = dao.findById(employee.getId());
		entity.setName(employee.getName());
		entity.setJoiningDate(employee.getJoiningDate());
		entity.setSalary(employee.getSalary());
		entity.setAddress(employee.getAddress());
		entity.setSsn(employee.getSsn());

	}

	@Override
	public void deleteEmployeeBySsn(String ssn) {
		dao.deleteEmployeeBySsn(ssn);
	}

	@Override
	public List<Employee> findAllEmployee() {
		return dao.findAllEmployee();
	}

	@Override
	public Employee findEmployeeBySsn(String ssn) {
		return dao.findEmployeeBySsn(ssn);
	}

	@Override
	public boolean isEmployeeUnique(Integer id, String ssn) {
		Employee employee = findEmployeeBySsn(ssn);
		return (employee == null || (id != null) && (employee.getId() == id));
	}

}
