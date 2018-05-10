package com.websystique.springmvc.service;

import java.util.List;

import com.websystique.springmvc.model.DeliveryRequest;

public interface DeliveryRequestService {

	DeliveryRequest findById(int id);
	
	void saveDeliveryRequest(DeliveryRequest deliveryRequest);
	
	void deleteDeliveryRequest(int id);
	
	void updateDeliveryRequest(DeliveryRequest deliveryRequest);
	
	List<DeliveryRequest> deliveryRequestList(Integer id);
	
	List<DeliveryRequest> deliveryRequestList();
	
	void updateUserBeedChoice(int delivey_id, int beed_id);

}
