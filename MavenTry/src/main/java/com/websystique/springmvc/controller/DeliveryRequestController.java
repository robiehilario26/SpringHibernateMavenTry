package com.websystique.springmvc.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.websystique.springmvc.model.CargoUser;
import com.websystique.springmvc.model.DeliveryRequest;
import com.websystique.springmvc.model.DeliveryType;
import com.websystique.springmvc.model.JsonResponse;
import com.websystique.springmvc.model.User;
import com.websystique.springmvc.service.DeliveryRequestService;
import com.websystique.springmvc.service.DeliveryTypeService;
import com.websystique.springmvc.service.UserProfileService;
import com.websystique.springmvc.service.UserService;

@Controller
@RequestMapping("/")
public class DeliveryRequestController {
	private List<DeliveryRequest> deliveryRequests = new ArrayList<DeliveryRequest>();

	@Autowired
	UserService userService;

	@Autowired
	UserProfileService userProfileService;

	@Autowired
	PersistentTokenBasedRememberMeServices persistentTokenBasedRememberMeServices;

	@Autowired
	AuthenticationTrustResolver authenticationTrustResolver;

	@Autowired
	DeliveryRequestService deliveryRequestService;

	@Autowired
	DeliveryTypeService deliveryTypeService;

	@RequestMapping(value = "/deliveryRequest")
	public String deliveryRequest(ModelMap model) {
		List<DeliveryType> deliveryTypes = deliveryTypeService
				.listDeliveryType();
		model.addAttribute("deliveryTypes", deliveryTypes);
		DeliveryRequest deliveryRequest = new DeliveryRequest();
		model.addAttribute("deliveryRequest", deliveryRequest);
		model.addAttribute("userId", getUserDetails());

		return "deliveryRequest";
	}

	@RequestMapping(value = "/deliveryBeeding")
	public String deliveryBeeding(ModelMap model) {
		List<DeliveryType> deliveryTypes = deliveryTypeService
				.listDeliveryType();
		model.addAttribute("deliveryTypes", deliveryTypes);
		DeliveryRequest deliveryRequest = new DeliveryRequest();
		model.addAttribute("deliveryRequest", deliveryRequest);
		return "deliveryBeeding";
	}

	/*
	 * This method will populate the dataTable with List of Delivery request
	 * using ajax response
	 */
	@RequestMapping(value = "/ajaxDeliveryRequestList", method = RequestMethod.GET)
	@ResponseBody
	public List<DeliveryRequest> ListDeliveryType(ModelMap model) {
		List<DeliveryRequest> deliveryRequests = deliveryRequestService
				.deliveryRequestList();
		return deliveryRequests;
	}

	/*
	 * This method will validate all input field in form and returning response
	 * If result has error detected. If no error occured in result it will
	 * insert the fields into database
	 */
	@RequestMapping(value = "/ajaxAddDeliveryRequest", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody JsonResponse AddCargoUser(
			@ModelAttribute(value = "deliveryRequest") DeliveryRequest deliveryRequest,
			BindingResult result) {

		/* Get username */
		String userName = getUserDetails();
		User user = userService.findBySSO(userName);

		/* Get username id */
		deliveryRequest.setUser_id(user.getId());

		/* Set delivery requesto to Pending */
		deliveryRequest.setDelivery_status("Pending");

		System.out.println("deliveryRequest " + deliveryRequest.toString());

		/* Create new json response */
		JsonResponse res = new JsonResponse();

		/* Call method for validating the input values */
		jsonResponse(res, result, deliveryRequest);

		/* If result is success it will insert into the employee table */
		if (res.getStatus().equalsIgnoreCase("success")) {

			/* Add Delivery request into database */
			deliveryRequestService.saveDeliveryRequest(deliveryRequest);

		}
		return res;
	}

	/*
	 * This method will validate all input field in form and returning response.
	 * If result has error detected it will set the status to "FAIL". If no
	 * error occured in result it will set the status to "SUCCESS".
	 */
	public JsonResponse jsonResponse(JsonResponse res, BindingResult result,
			DeliveryRequest deliveryRequest) {

		/* Set error message if text field is empty */

		ValidationUtils.rejectIfEmptyOrWhitespace(result, "delivery_type",
				"Delivery Type cannot be empty.");

		ValidationUtils.rejectIfEmptyOrWhitespace(result,
				"delivery_pickup_address", "Pick-up Address cannot be empty.");

		ValidationUtils.rejectIfEmptyOrWhitespace(result,
				"delivery_destination", "Destination Address cannot be empty.");

		if (result.hasErrors()) {

			/* Set status to fail */
			res.setStatus("FAIL");

			/*
			 * Collect all error messages for text field that not properly
			 * assign value
			 */
			res.setResult(result.getAllErrors());

		} else {

			System.out.println("deliveryRequest " + deliveryRequest.toString());

			deliveryRequests.clear(); /* Clear array list */
			deliveryRequests.add(deliveryRequest); /*
													 * Add cargoUser model
													 * object into list
													 */
			res.setStatus("SUCCESS"); /* Set status to success */
			res.setResult(deliveryRequests); /* Return object into list */

		}

		return res;
	}

	public String getUserDetails() {
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		String username = authentication.getName();
		return username;
	}

}
