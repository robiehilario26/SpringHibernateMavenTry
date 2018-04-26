package com.websystique.springmvc.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.websystique.springmvc.dao.DeliveryDao;
import com.websystique.springmvc.model.DeliveryType;

@Service("deliveryTypeService")
@Transactional
public class DeliveryTypeServiceImpl implements DeliveryTypeService {

	@Autowired
	private DeliveryDao dao;

	@Override
	public DeliveryType findById(int id) {
		return dao.findById(id);
	}

	@Override
	public void saveDeliveryType(DeliveryType deliveryType) {
		dao.saveDeliveryType(deliveryType);
	}

	@Override
	public void deleteDeliveryType(int id) {
		dao.deleteDeliveryType(id);
	}

	@Override
	public void updateDeliveryType(DeliveryType deliveryType) {
		DeliveryType entity = dao.findById(deliveryType.getId());
		entity.setDelivery_type(deliveryType.getDelivery_type());
		entity.setDelivery_price(deliveryType.getDelivery_price());
		entity.setDelivery_weight(deliveryType.getDelivery_weight());

	}

	@Override
	public List<DeliveryType> listDeliveryType() {
		return dao.listDeliveryType();
	}

	@Override
	public DeliveryType findDeliveryByType(String type) {
				return dao.findDeliveryByType(type);
	}

	@Override
	public boolean isDeliveryTypeUnique(Integer id, String type) {
		DeliveryType deliveryType = findDeliveryByType(type);
		return (deliveryType == null || (id != null) && (deliveryType.getId() == id));
	}

}
