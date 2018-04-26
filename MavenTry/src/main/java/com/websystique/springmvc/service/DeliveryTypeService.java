package com.websystique.springmvc.service;

import java.util.List;

import com.websystique.springmvc.model.DeliveryType;

public interface DeliveryTypeService {
	
DeliveryType findById(int id);
	
	void saveDeliveryType(DeliveryType deliveryType);
	
	void deleteDeliveryType(int id);
	
	void updateDeliveryType(DeliveryType deliveryType);
	
	List<DeliveryType> listDeliveryType();
	
	DeliveryType findDeliveryByType(String type);
	
	boolean isDeliveryTypeUnique(Integer id, String type);

}
