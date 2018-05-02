package com.websystique.springmvc.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.websystique.springmvc.model.DeliveryRequest;

@Repository("deliveryRequestDaoImpl")
public class DeliveryRequestDaoImpl extends
		AbstractDao<Integer, DeliveryRequest> implements DeliveryRequestDao {

	@Override
	public DeliveryRequest findById(int id) {
		return getByKey(id);
	}

	@Override
	public void saveDeliveryRequest(DeliveryRequest deliveryRequest) {
		persist(deliveryRequest);

	}

	@Override
	public void deleteDeliveryRequest(int id) {
		Criteria crit = createEntityCriteria();
		crit.add(Restrictions.eqOrIsNull("deliver_id", id));
		DeliveryRequest deliveryRequest = (DeliveryRequest) crit.uniqueResult();
		delete(deliveryRequest);

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DeliveryRequest> deliveryRequestList(Integer id) {
		Criteria crit = createEntityCriteria();
		crit.add(Restrictions.eq("user_id", id));
		return (List<DeliveryRequest>) crit.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DeliveryRequest> deliveryRequestList() {
		Criteria crit = createEntityCriteria();
		return (List<DeliveryRequest>) crit.list();

	}

}
