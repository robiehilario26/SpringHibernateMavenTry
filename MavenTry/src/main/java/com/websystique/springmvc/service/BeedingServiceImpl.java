package com.websystique.springmvc.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.websystique.springmvc.dao.BeedingDao;
import com.websystique.springmvc.model.Beeding;

@Service("beedingDaoService")
@Transactional
public class BeedingServiceImpl implements BeedingService {

	@Autowired
	private BeedingDao dao;
	
	
	@Override
	public Beeding findBeedingById(Integer id) {
		return dao.findBeedingById(id);
	}

	@Override
	public void saveBeedingRequest(Beeding beeding) {
		dao.saveBeedingRequest(beeding);

	}

	@Override
	public void deleteBeedingRequest(Integer id) {
		dao.deleteBeedingRequest(id);

	}


	@Override
	public List<Beeding> listBeedingRequest() {
				return dao.listBeedingRequest();
	}

	@Override
	public Beeding findBeedingRequestById(Integer id) {
			return dao.findBeedingRequestById(id);
	}

	@Override
	public void updateBeedingRequest(Beeding beeding) {
		Beeding entity = dao.findBeedingById(beeding.getBeeding_id());
		entity.setBeeding_delivery_id(beeding.getBeeding_delivery_id());
		entity.setUser_beeder_id(beeding.getUser_beeder_id());
		entity.setBeeding_startingprice(beeding.getBeeding_startingprice());
		entity.setBeeding_status(beeding.getBeeding_status());
	}

	@Override
	public List<Beeding> listBeedingRequestByDeliveryId(Integer id) {
			return dao.listBeedingRequestByDeliveryId(id);
	}



}
