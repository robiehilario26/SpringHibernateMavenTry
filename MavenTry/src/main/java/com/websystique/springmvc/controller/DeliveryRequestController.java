package com.websystique.springmvc.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.websystique.springmvc.model.DeliveryRequest;
import com.websystique.springmvc.model.DeliveryType;
import com.websystique.springmvc.model.JsonResponse;
import com.websystique.springmvc.model.User;
import com.websystique.springmvc.service.DeliveryRequestService;
import com.websystique.springmvc.service.DeliveryTypeService;
import com.websystique.springmvc.service.UserProfileService;
import com.websystique.springmvc.service.UserService;
import com.websystique.springmvc.utility.AjaxRequestValidation;

@Controller
@RequestMapping("/")
public class DeliveryRequestController {
	private List<DeliveryRequest> deliveryRequests = new ArrayList<DeliveryRequest>();

	@Autowired
	AjaxRequestValidation ajaxRequestValidation;

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

	/*
	 * This method will provide the medium to get Delivery Request details using
	 * ajax with annotation @RequestParam.
	 */
	@RequestMapping(value = { "/ajaxFetchRequestDetails" }, method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public DeliveryRequest ajaxRequestDetail(@RequestParam Integer id,
			HttpServletRequest request, HttpServletResponse response,
			Model model) {

		if (!ajaxRequestValidation.isAjax(request)) {
			/* Return null in web browser */
			return null;
		}

		DeliveryRequest deliveryRequest = deliveryRequestService.findById(id);
		return deliveryRequest;
	}

	/*
	 * This method will populate the dataTable with List of Delivery request
	 * using ajax response
	 */
	@RequestMapping(value = "/ajaxDeliveryRequestList", method = RequestMethod.GET)
	@ResponseBody
	public List<DeliveryRequest> listDeliveryRequest(ModelMap model) {
		/* Get username */
		String userName = getUserDetails();
		User user = userService.findBySSO(userName);

		/* Fetch all Delivery request equal to user id */
		List<DeliveryRequest> deliveryRequests = deliveryRequestService
				.deliveryRequestList(user.getId());
		return deliveryRequests;
	}

	/*
	 * This method will validate all input field in form and returning response
	 * If result has error detected. If no error occured in result it will
	 * insert the fields into database
	 */
	@RequestMapping(value = "/ajaxAddDeliveryRequest", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody JsonResponse addDeliveryRequest(
			@ModelAttribute(value = "deliveryRequest") DeliveryRequest deliveryRequest,
			BindingResult result) {

		/* Get username */
		String userName = getUserDetails();
		User user = userService.findBySSO(userName);

		/* Get username id */
		deliveryRequest.setUser_id(user.getId());

		/* Set delivery requesto to Pending */
		deliveryRequest.setDelivery_status("Pending");

		deliveryRequest.setUser_beed_choice(0);

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
	 * This method will validate all input field in form and returning response
	 * If result has error detected. If no error occured in result it will
	 * insert the fields into database
	 */
	@RequestMapping(value = "/ajaxUpdateDeliveryRequest", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody JsonResponse updateDeliveryRequest(
			@ModelAttribute(value = "deliveryRequest") DeliveryRequest deliveryRequest,
			BindingResult result) {

		/* Get username */
		String userName = getUserDetails();
		User user = userService.findBySSO(userName);

		/* Get username id */
		deliveryRequest.setUser_id(user.getId());

		/* Set delivery requesto to Pending */
		// deliveryRequest.setDelivery_status("Pending");

		System.out.println("deliveryRequest " + deliveryRequest.toString());

		/* Create new json response */
		JsonResponse res = new JsonResponse();

		/* Call method for validating the input values */
		jsonResponse(res, result, deliveryRequest);

		/* If result is success it will insert into the employee table */
		if (res.getStatus().equalsIgnoreCase("success")) {

			/* Update Delivery request into database */
			deliveryRequestService.updateDeliveryRequest(deliveryRequest);

		}
		return res;
	}

	/*
	 * This method will delete an delivery request by it's id value using ajax
	 * with annotation @RequestParam.
	 */
	@RequestMapping(value = { "/ajaxDeleteDeliveryRequest" }, method = RequestMethod.GET, produces = "application/json")
	public String ajaxDeleteDeliveryRequest(@RequestParam Integer id, 
			ModelMap model) {
		/* Delete delivery request by id */
		deliveryRequestService.deleteDeliveryRequest(id);
		return "redirect:/deliveryRequest";
	}

	/*
	 * This method will update an user beeding entry choice by it's id value
	 * using ajax with annotation @RequestParam.
	 */
	@RequestMapping(value = "/ajaxChooseBeedEntry", method = RequestMethod.GET)
	public String ajaxChooseBeedEntry(@RequestParam Integer id,Integer deliver_id) {
		System.out.println("beed entry id: " + id + " deliver id: " + deliver_id);
		deliveryRequestService.updateUserBeedChoice(deliver_id, id);
		return "redirect:/deliveryRequest";
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

		ValidationUtils.rejectIfEmptyOrWhitespace(result, "item_details",
				"Item details cannot be empty.");

		ValidationUtils.rejectIfEmptyOrWhitespace(result,
				"delivery_pickup_address", "Pick-up Address cannot be empty.");

		ValidationUtils.rejectIfEmptyOrWhitespace(result,
				"delivery_destination", "Destination Address cannot be empty.");
		
		ValidationUtils.rejectIfEmptyOrWhitespace(result,
				"preferred_date", "Preferred Date cannot be empty.");
		
		

		if (result.hasErrors()) {

			/* Set status to fail */
			res.setStatus("FAIL");

			/*
			 * Collect all error messages for text field that not properly
			 * assign value
			 */
			res.setResult(result.getAllErrors());

		} else if (deliveryRequest.getDelivery_type() == 0) {
			System.out.println("delivery type "
					+ deliveryRequest.getDelivery_type());

			/* Set status to fail */
			res.setStatus("FAIL");

			/* Set error message if delivery type is empty */
			result.rejectValue("delivery_type", "Delivery Type cannot be empty");
			res.setResult(result.getAllErrors());
		}

		else {

			deliveryRequests.clear(); /* Clear array list */
			deliveryRequests.add(deliveryRequest);
			/*
			 * Add cargoUser model object into list
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
