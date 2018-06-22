package com.websystique.springmvc.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.websystique.springmvc.model.Beeding;
import com.websystique.springmvc.model.DeliveryRequest;
import com.websystique.springmvc.model.JsonResponse;
import com.websystique.springmvc.model.User;
import com.websystique.springmvc.service.BeedingService;
import com.websystique.springmvc.service.DeliveryRequestService;
import com.websystique.springmvc.service.DeliveryTypeService;
import com.websystique.springmvc.service.UserProfileService;
import com.websystique.springmvc.service.UserService;
import com.websystique.springmvc.utility.AjaxRequestValidation;

@Controller
@RequestMapping("/")
public class BeedingController {

	@Autowired
	AjaxRequestValidation ajaxRequestValidation;

	@Autowired
	UserService userService;

	@Autowired
	UserProfileService userProfileService;

	@Autowired
	DeliveryTypeService deliveryTypeService;

	@Autowired
	DeliveryRequestService deliveryRequestService;

	@Autowired
	AuthenticationTrustResolver authenticationTrustResolver;

	@Autowired
	BeedingService beedingService;

	private List<Beeding> beedings = new ArrayList<Beeding>();

	@RequestMapping(value = "/deliveryBeeding")
	public String deliveryBeeding(ModelMap model) {
		Beeding beeding = new Beeding();
		model.addAttribute("beedingRequest", beeding);

		String userName = getUserDetails();
		User user = userService.findBySSO(userName);
		model.addAttribute("userId", user.getId());
		return "deliveryBeeding";
	}

	@RequestMapping(value = "/beeding/ajaxDeliveryRequestList", method = RequestMethod.GET)
	@ResponseBody
	public List<DeliveryRequest> listDeliveryRequest() {
		List<DeliveryRequest> deliveryRequests = deliveryRequestService
				.deliveryRequestList();
		return deliveryRequests;
	}

	/*
	 * This method will validate all input field in form and returning response
	 * If result has error detected. If no error occured in result it will
	 * insert the fields into database
	 */
	@RequestMapping(value = "/ajaxAddBeedingRequest", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody JsonResponse addDeliveryRequest(
			@ModelAttribute(value = "beedingRequest") Beeding beeding,
			BindingResult result) {

		// /* Get username */
		String userName = getUserDetails();
		User user = userService.findBySSO(userName);

		// /* Get username id */
		beeding.setUser_beeder_id(user.getId());

		/* Set beeding status to pending */
		beeding.setBeeding_status("Pending");

		System.out.println("beeding " + beeding.toString());

		/* Create new json response */
		JsonResponse res = new JsonResponse();

		/* Call method for validating the input values */
		jsonResponse(res, result, beeding);

		/* If result is success it will insert into the table */
		if (res.getStatus().equalsIgnoreCase("success")) {

			/* Add beeding request into database */
			beedingService.saveBeedingRequest(beeding);

		}
		return res;
	}

	/*
	 * This method will provide the medium to get beeding request details using
	 * ajax with annotation @RequestParam.
	 */
	@RequestMapping(value = "/ajaxSearchBeedingRequest", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<Beeding> LisBeedingDetail(@RequestParam Integer id,
			HttpServletRequest request, HttpServletResponse response) {

		if (!ajaxRequestValidation.isAjax(request)) {
			/* Return null in web browser */
			return null;
		}

		List<Beeding> beeding = beedingService
				.listBeedingRequestByDeliveryId(id);
		return beeding;
	}

	/*
	 * This method will validate all input field in form and returning response.
	 * If result has error detected it will set the status to "FAIL". If no
	 * error occured in result it will set the status to "SUCCESS".
	 */
	public JsonResponse jsonResponse(JsonResponse res, BindingResult result,
			Beeding beeding) {

		/* Set error message if text field is empty */

		ValidationUtils.rejectIfEmptyOrWhitespace(result,
				"beeding_startingprice", "Beeding price should be more than 0");

		if (result.hasErrors()) {

			/* Set status to fail */
			res.setStatus("FAIL");

			/*
			 * Collect all error messages for text field that not properly
			 * assign value
			 */
			res.setResult(result.getAllErrors());

		}

		else {

			beedings.clear(); /* Clear array list */
			beedings.add(beeding);
			/*
			 * Add cargoUser model object into list
			 */
			res.setStatus("SUCCESS"); /* Set status to success */
			res.setResult(beedings); /* Return object into list */

		}

		return res;
	}

	public String getUserDetails() {
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		String username = authentication.getName();
		return username;
	}

	/* Generate JasperReport test */
	@RequestMapping(value = "/helloReport5", method = RequestMethod.GET)
	public ModelAndView getRpt5(ModelMap modelMap, ModelAndView modelAndView) {

		List<DeliveryRequest> deliveryRequests = deliveryRequestService
				.deliveryRequestList();
		
		JRDataSource JRdataSource = new JRBeanCollectionDataSource(
				deliveryRequests, false);
		modelMap.put("datasource", JRdataSource);
		modelMap.put("format", "pdf");
		modelAndView = new ModelAndView("rpt_beeding", modelMap);
		
		System.out.println("Beeding data: "+ deliveryRequestService.deliveryRequestList() );
		return modelAndView;
	}

}
