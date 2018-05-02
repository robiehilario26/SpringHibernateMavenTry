package com.websystique.springmvc.service;

import java.util.List;

import com.websystique.springmvc.model.Beeding;
import com.websystique.springmvc.model.DeliveryRequest;

public interface BeedingService {

Beeding findBeedingById(Integer id);
	
	void saveBeedingRequest(Beeding beeding);
	
	void deleteBeedingRequest(Integer id);
	
	void updateBeedingRequest(Beeding beeding);
	
	List<Beeding> listBeedingRequest();
	
		
	Beeding findBeedingRequestById(Integer id);

}
