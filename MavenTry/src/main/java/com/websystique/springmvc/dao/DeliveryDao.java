package com.websystique.springmvc.dao;

import java.util.List;

import com.websystique.springmvc.model.DeliveryType;

public interface DeliveryDao {

	DeliveryType findById(int id);
	
	void saveDeliveryType(DeliveryType deliveryType);
	
	void deleteDeliveryType(int id);
	
	List<DeliveryType> listDeliveryType();
	
	DeliveryType findDeliveryByType(String type);
	
	
}
