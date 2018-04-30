package com.websystique.springmvc.dao;

import java.util.List;

import com.websystique.springmvc.model.DeliveryRequest;

public interface DeliveryRequestDao {
	
	DeliveryRequest findById(int id);
	
	void saveDeliveryRequest(DeliveryRequest deliveryRequest);
	
	void deleteDeliveryRequest(int id);
	
	List<DeliveryRequest> deliveryRequestList();


}
