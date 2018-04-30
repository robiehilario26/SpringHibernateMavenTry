package com.websystique.springmvc.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.websystique.springmvc.dao.DeliveryRequestDao;
import com.websystique.springmvc.model.DeliveryRequest;

@Repository("deliveryRequestService")
@Transactional
public class DeliveryRequestServiceImpl implements DeliveryRequestService {

	@Autowired
	private DeliveryRequestDao dao;

	@Override
	public DeliveryRequest findById(int id) {
		return dao.findById(id);
	}

	@Override
	public void saveDeliveryRequest(DeliveryRequest deliveryRequest) {
		dao.saveDeliveryRequest(deliveryRequest);

	}

	@Override
	public void deleteDeliveryRequest(int id) {
		dao.deleteDeliveryRequest(id);

	}

	@Override
	public void updateDeliveryRequest(DeliveryRequest deliveryRequest) {
		DeliveryRequest entity = dao.findById(deliveryRequest.getDeliver_id());
		entity.setUser_id(deliveryRequest.getUser_id());
		entity.setDelivery_type(deliveryRequest.getDelivery_type());
		entity.setDelivery_pickup_address(deliveryRequest
				.getDelivery_pickup_address());
		entity.setDelivery_destination(deliveryRequest
				.getDelivery_destination());
		entity.setItem_details(deliveryRequest.getItem_details());
		entity.setDelivery_status(deliveryRequest.getDelivery_status());

	}

	@Override
	public List<DeliveryRequest> deliveryRequestList() {
		return dao.deliveryRequestList();
	}

}
