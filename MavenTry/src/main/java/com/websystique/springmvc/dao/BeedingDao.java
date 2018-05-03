package com.websystique.springmvc.dao;

import java.util.List;

import com.websystique.springmvc.model.Beeding;
import com.websystique.springmvc.model.DeliveryRequest;

public interface BeedingDao {
	
	Beeding findBeedingById(Integer id);
	
	void saveBeedingRequest(Beeding beeding);
	
	void deleteBeedingRequest(Integer id);
	
	List<Beeding> listBeedingRequest();
	
	List<Beeding> listBeedingRequestByDeliveryId(Integer id);
		
	Beeding findBeedingRequestById(Integer id);

	
}
