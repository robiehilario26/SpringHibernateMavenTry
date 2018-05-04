package com.websystique.springmvc.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.websystique.springmvc.model.Beeding;

@SuppressWarnings("unchecked")
@Repository("beedingDao")
public class BeedingDaoImpl extends AbstractDao<Integer, Beeding> implements
		BeedingDao {

	@Override
	public Beeding findBeedingById(Integer id) {

		return getByKey(id);
	}

	@Override
	public void saveBeedingRequest(Beeding beeding) {
		persist(beeding);

	}

	@Override
	public void deleteBeedingRequest(Integer id) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("beeding_id", id));
		Beeding beeding = (Beeding) criteria.uniqueResult();
		delete(beeding);

	}

	@Override
	public List<Beeding> listBeedingRequest() {
		Criteria criteria = createEntityCriteria();
		return (List<Beeding>) criteria.list();
	}

	@Override
	public Beeding findBeedingRequestById(Integer id) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("beeding_id", id));
		return (Beeding) criteria.uniqueResult();
		
	}

	@Override
	public List<Beeding> listBeedingRequestByDeliveryId(Integer id) {
		Criteria criteria = createEntityCriteria().addOrder(
				Order.asc("beeding_delivery_date"));
		criteria.add(Restrictions.eq("beeding_delivery_id", id));
		return (List<Beeding>) criteria.list();
	}


}
