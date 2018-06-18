package com.websystique.springmvc.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.websystique.springmvc.model.DeliveryType;
import com.websystique.springmvc.model.JsonResponse;
import com.websystique.springmvc.service.DeliveryTypeService;
import com.websystique.springmvc.utility.AjaxRequestValidation;
import com.websystique.springmvc.utility.validateJsonResponse;

@Controller
@RequestMapping("/")
public class MaintenanceController {

	@Autowired
	AjaxRequestValidation ajaxRequestValidation;

	@Autowired
	DeliveryTypeService deliveryTypeService;

	@Autowired
	validateJsonResponse validateJson;

	private List<DeliveryType> deliveryTypes = new ArrayList<DeliveryType>();

	/*
	 * This method will populate the dataTable with List of Delivery type using
	 * ajax response
	 */
	@RequestMapping(value = "/ajaxDeliveryTypeList", method = RequestMethod.GET)
	@ResponseBody
	public List<DeliveryType> ListDeliveryType(ModelMap model) {
		List<DeliveryType> deliveryTypes = deliveryTypeService
				.listDeliveryType();
		return deliveryTypes;
	}

	/*
	 * This method will redirect user to maintenance delivery page.
	 */
	@RequestMapping(value = "/deliveryType", method = RequestMethod.GET)
	public String deliveryType(ModelMap model) {
		DeliveryType deliveryType = new DeliveryType();
		model.addAttribute("delivery", deliveryType);
		return "maintenanceDeliveryType";

	}

	/*
	 * This method will provide the medium to get delivery type details using
	 * ajax with annotation @RequestParam.
	 */
	@RequestMapping(value = "/search-delivery-type-by-ajax", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public DeliveryType ListDeliveryTypeDetail(@RequestParam Integer id,
			HttpServletRequest request, HttpServletResponse response) {

		if (!ajaxRequestValidation.isAjax(request)) {
			/* Return null in web browser */
			return null;
		}

		DeliveryType deliveryType = deliveryTypeService.findById(id);
		return deliveryType;
	}

	/*
	 * This method will provide the medium to add a new delivery type. It
	 * validate all the input before inserting to database
	 */

	@RequestMapping(value = "/ajaxAddDeliveryType", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public JsonResponse addDeliveryType(
			@ModelAttribute(value = "delivery") DeliveryType deliveryType,
			BindingResult result) {

		System.out.println("deliveryType " + deliveryType.toString());

		JsonResponse res = new JsonResponse();

		/* Validate all the input. it return "SUCCESS" or "FAIL" status */
		// jsonResponse(res, result, deliveryType);

		/* Call service method for validating the input values */
		validateJson.jsonResponse(res, result, deliveryType, deliveryTypes,
				true, "mainte_delivery_type", deliveryTypeService
						.isDeliveryTypeUnique(deliveryType.getId(),
								deliveryType.getMainte_delivery_type()));

		if (res.getStatus().equalsIgnoreCase("success")) {
			/*
			 * If result status is Success insert the data into database
			 */
			deliveryTypeService.saveDeliveryType(deliveryType);
		}

		return res;
	}

	/*
	 * This method will provide the medium to update a delivery type. It
	 * validate all the input before update to database
	 */
	@RequestMapping(value = "/ajaxEditDeliveryType", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public JsonResponse editDeliveryType(
			@ModelAttribute(value = "delivery") DeliveryType deliveryType,
			BindingResult result) {

		JsonResponse res = new JsonResponse();
		/* Validate all the input. it return "SUCCESS" or "FAIL" status */
		// jsonResponse(res, result, deliveryType);

		/* Call service method for validating the input values */
		validateJson.jsonResponse(res, result, deliveryType, deliveryTypes,
				true, "mainte_delivery_type", deliveryTypeService
						.isDeliveryTypeUnique(deliveryType.getId(),
								deliveryType.getMainte_delivery_type()));

		if (res.getStatus().equalsIgnoreCase("success")) {
			/*
			 * If result status is Success update the data into database
			 */
			deliveryTypeService.updateDeliveryType(deliveryType);
		}

		return res;
	}

	/*
	 * This method will delete an delivery type by it's id value using ajax with
	 * annotation @RequestParam.
	 */
	@RequestMapping(value = "ajaxDeleteDeliveryType", method = RequestMethod.GET)
	public String deleteDeliveryType(@RequestParam Integer id) {
		deliveryTypeService.deleteDeliveryType(id);
		return "redirect:/deliveryType";
	}

	/*
	 * This method will validate all input field in form and returning response.
	 * If result has error detected it will set the status to "FAIL". If no
	 * error occured in result it will set the status to "SUCCESS".
	 */
	public JsonResponse jsonResponse(JsonResponse res, BindingResult result,
			DeliveryType deliveryType) {

		/* Set error message if text field is empty */

		ValidationUtils.rejectIfEmptyOrWhitespace(result,
				"mainte_delivery_type", "Delivery type can not be empty");

		ValidationUtils.rejectIfEmptyOrWhitespace(result, "delivery_weight",
				"Delivery weight not be empty");

		ValidationUtils.rejectIfEmptyOrWhitespace(result, "delivery_price",
				"Delivery price can not be empty.");

		if (!deliveryTypeService.isDeliveryTypeUnique(deliveryType.getId(),
				deliveryType.getMainte_delivery_type())) {

			/* Set status to fail */
			res.setStatus("FAIL");

			/* Set error message if delivery type already exist */
			result.rejectValue("mainte_delivery_type",
					"Delivery type already exists. Please fill in different value");

			res.setResult(result.getAllErrors());

		}

		if (result.hasErrors()) {

			/* Set status to fail */
			res.setStatus("FAIL");

			/*
			 * Collect all error messages for text field that not properly
			 * assign value
			 */
			res.setResult(result.getAllErrors());

		}

		/* Validate if the weight or price is more than zero / 0 */
		else if (deliveryType.getDelivery_price().toString()
				.equalsIgnoreCase("0")
				|| deliveryType.getDelivery_weight().toString()
						.equalsIgnoreCase("0")) {

			/* Set status to fail */
			res.setStatus("FAIL");

			/* Set error message if delivery type already exist */
			result.rejectValue("delivery_price",
					"Delivery weight/price value should be more than '0'");

			res.setResult(result.getAllErrors());

		}

		else {

			deliveryTypes.clear(); /* Clear array list */
			deliveryTypes.add(deliveryType);
			/*
			 * Add employee model object into list
			 */
			res.setStatus("SUCCESS"); /* Set status to success */
			res.setResult(deliveryTypes); /* Return object into list */

		}

		return res;
	}

	@RequestMapping(value = "/helloReport4", method = RequestMethod.GET)
	public ModelAndView getRpt4(ModelMap modelMap, ModelAndView modelAndView) {

	
		
//		List<DeliveryType> deliveryTypes = deliveryTypeService
//				.listDeliveryType();
		
		JRDataSource JRdataSource = new JRBeanCollectionDataSource(deliveryTypeService
				.listDeliveryType(),false);
		modelMap.put("datasource", JRdataSource);
		modelMap.put("format", "pdf");
		modelAndView = new ModelAndView("rpt_mainte", modelMap);
		System.out.println("deliveryTypes: "
				+ deliveryTypes.toString());
		return modelAndView;
	}

}
