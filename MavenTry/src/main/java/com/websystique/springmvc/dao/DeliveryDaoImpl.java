package com.websystique.springmvc.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.websystique.springmvc.model.DeliveryType;

@Repository("deliveryDao")
public class DeliveryDaoImpl extends AbstractDao<Integer, DeliveryType>
		implements DeliveryDao {

	@Override
	public DeliveryType findById(int id) {
		return getByKey(id);
	}

	@Override
	public void saveDeliveryType(DeliveryType deliveryType) {
		persist(deliveryType);

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DeliveryType> listDeliveryType() {
		Criteria criteria = createEntityCriteria();
		return (List<DeliveryType>) criteria.list();
	}

	@Override
	public DeliveryType findDeliveryByType(String type) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("mainte_delivery_type", type));
		return (DeliveryType) criteria.uniqueResult();
	}

	@Override
	public void deleteDeliveryType(int id) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("id", id));
		DeliveryType deliveryType = (DeliveryType) criteria.uniqueResult();
		delete(deliveryType);

	}

}
