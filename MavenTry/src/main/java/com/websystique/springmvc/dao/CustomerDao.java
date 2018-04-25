package com.websystique.springmvc.dao;

import java.util.List;

import com.websystique.springmvc.model.Customer;

public interface CustomerDao {
	
	Customer findById(Integer id);
	
	void saveCustomerDetail(Customer customer);
	
	void deleteCustomerById(Integer id);
	
	List<Customer> customerList();
	
	
	
	

}
