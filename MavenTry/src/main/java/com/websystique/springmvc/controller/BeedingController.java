package com.websystique.springmvc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.websystique.springmvc.model.DeliveryRequest;
import com.websystique.springmvc.model.DeliveryType;
import com.websystique.springmvc.service.BeedingService;
import com.websystique.springmvc.service.DeliveryRequestService;
import com.websystique.springmvc.service.DeliveryTypeService;
import com.websystique.springmvc.service.UserProfileService;
import com.websystique.springmvc.service.UserService;


@Controller
@RequestMapping("/")
public class BeedingController {
	
	
	
	@Autowired
	UserService userService;

	@Autowired
	UserProfileService userProfileService;
	
	@Autowired
	DeliveryTypeService deliveryTypeService;
	
	@Autowired
	DeliveryRequestService deliveryRequestService;
	
	@Autowired
	BeedingService beedingService;
	
	@RequestMapping(value = "/deliveryBeeding")
	public String deliveryBeeding(ModelMap model) {
		List<DeliveryType> deliveryTypes = deliveryTypeService
				.listDeliveryType();
		model.addAttribute("deliveryTypes", deliveryTypes);
		DeliveryRequest deliveryRequest = new DeliveryRequest();
		model.addAttribute("deliveryRequest", deliveryRequest);
		return "deliveryBeeding";
	}
	
	
	@RequestMapping(value ="/beeding/ajaxDeliveryRequestList", method = RequestMethod.GET)
	@ResponseBody
	public List<DeliveryRequest> listDeliveryRequest(){
		List<DeliveryRequest> deliveryRequests = deliveryRequestService.deliveryRequestList();
		return deliveryRequests;
	}

}
