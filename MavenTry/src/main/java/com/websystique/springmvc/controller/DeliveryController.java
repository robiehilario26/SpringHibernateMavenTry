package com.websystique.springmvc.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationTrustResolver;
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
import org.springframework.web.bind.annotation.SessionAttributes;

import com.websystique.springmvc.model.CargoUser;
import com.websystique.springmvc.model.JsonResponse;
import com.websystique.springmvc.model.UserProfile;
import com.websystique.springmvc.service.CargoUserService;
import com.websystique.springmvc.service.UserProfileService;

@Controller
@RequestMapping("/")
@SessionAttributes("roles")
public class DeliveryController {

	private List<CargoUser> cargoList = new ArrayList<CargoUser>();
	private List<String> cargoType = new ArrayList<String>();

	@Autowired
	CargoUserService cargoUserService;

	@Autowired
	UserProfileService userProfileService;

	@Autowired
	PersistentTokenBasedRememberMeServices persistentTokenBasedRememberMeServices;

	@Autowired
	AuthenticationTrustResolver authenticationTrustResolver;

	/**
	 * This method will list all existing cargo users.
	 */
	@RequestMapping(value = { "/cargo" }, method = RequestMethod.GET)
	public String listCargoUsers(ModelMap model) {

		/* Call method */
		setTruckType();
		model.addAttribute("truckType", cargoType);

		CargoUser cargoUser = new CargoUser();
		model.addAttribute("cargoUser", cargoUser);
		return "cargoUser";
	}

	/**
	 * This method will list all existing cargo users.
	 */
	@RequestMapping(value = { "/cargo" }, method = RequestMethod.POST)
	public String createCargoUser(@Valid CargoUser user, BindingResult result,
			ModelMap model) {

		System.out.println("CargoUser " + user.toString());
		System.out.println("result " + result.toString());
		if (result.hasErrors()) {
			return "redirect:/cargo";
		}

		cargoUserService.saveCargoEmployee(user);

		return "redirect:/cargo";
	}

	/*
	 * This method will validate all input field in form and returning response
	 * If result has error detected. If no error occured in result it will
	 * insert the fields into database
	 */
	@RequestMapping(value = "/ajaxAddCargoUser", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody JsonResponse AddCargoUser(
			@ModelAttribute(value = "cargoUser") CargoUser cargoUser,
			BindingResult result) {

		System.out.println("cargoUser " + cargoUser.toString());

		/* Create new json response */
		JsonResponse res = new JsonResponse();

		/* Call method for validating the input values */
		jsonResponse(res, result, cargoUser);

		/* If result is success it will insert into the employee table */
		if (res.getStatus().equalsIgnoreCase("success")) {

			/* Add Employee details into database */
			cargoUserService.saveCargoEmployee(cargoUser);

		}
		return res;
	}

	/*
	 * This method will validate all input field in form and returning response
	 * If result has error detected. If no error occured in result it will
	 * insert the fields into database
	 */
	@RequestMapping(value = "/ajaxEditCargoUser", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody JsonResponse updateCargoUser(
			@ModelAttribute(value = "cargoUser") @Valid CargoUser cargoUser,
			BindingResult result) {

		System.out.println("cargoUser " + cargoUser.toString());

		/* Create new json response */
		JsonResponse res = new JsonResponse();

		/* Call method for validating the input values */
		jsonResponse(res, result, cargoUser);

		/* If result is success it will insert into the employee table */
		if (res.getStatus().equalsIgnoreCase("success")) {
			/* Update Employee details into database */
			cargoUserService.updateCargoEmployee(cargoUser);
		}
		return res;
	}

	/*
	 * This method will delete an cargo user by it's id value sing ajax with
	 * annotation @RequestParam.
	 */
	@RequestMapping(value = { "/delete-cargo-user-by-ajax" }, method = RequestMethod.GET, produces = "application/json")
	public String ajaxDeleteEmployee(@RequestParam Integer cargo_id,
			ModelMap model) {
		/* Delete cargo user by id */
		System.out.println("cargo_id " + cargo_id);
		cargoUserService.deleteCargoEmployeeById(cargo_id);
		return "redirect:/cargo";
	}

	/*
	 * This method will redirect cargo user page This method will provide
	 */
	@RequestMapping(value = { "/ajaxCargoList" }, method = RequestMethod.GET)
	@ResponseBody
	public List<CargoUser> ajaxCargoList(ModelMap model) {

		/* Populate DataTable */
		List<CargoUser> cargoUsers = cargoUserService.cargoList();

		return cargoUsers;
	}

	/*
	 * This method will provide the medium to get cargo user details using ajax
	 * with annotation @RequestParam.
	 */
	@RequestMapping(value = { "/search-cargo-user-by-ajax" }, method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public CargoUser ajaxEmployeeDetail(@RequestParam Integer id,
			HttpServletRequest request, HttpServletResponse response,
			Model model) {
		CargoUser cargoUser = cargoUserService.findById(id);
		return cargoUser;
	}

	/*
	 * This method will validate all input field in form and returning response.
	 * If result has error detected it will set the status to "FAIL". If no
	 * error occured in result it will set the status to "SUCCESS".
	 */
	public JsonResponse jsonResponse(JsonResponse res, BindingResult result,
			CargoUser cargoUser) {

		/* Set error message if text field is empty */

		ValidationUtils.rejectIfEmptyOrWhitespace(result, "cargo_driver",
				"Name cannot be empty.");

		ValidationUtils.rejectIfEmptyOrWhitespace(result, "cargo_vehicletype",
				"Vehicle type cannot be empty.");

		ValidationUtils.rejectIfEmptyOrWhitespace(result, "truck_plate_number",
				"Vehicle plate number cannot be empty.");

		ValidationUtils.rejectIfEmptyOrWhitespace(result, "cargo_company",
				"Company cannot be empty.");

		/* Check if plate number input is already exist or used by other */
		if (!cargoUserService.isPlateNumberUnique(cargoUser.getCargo_id(),
				cargoUser.getTruck_plate_number())) {

			 /* Set status to fail */
			 res.setStatus("FAIL");
			
			 /* Set error message if Ssn id already exist */
			 result.rejectValue("truck_plate_number",
			 "Plate number already exist, Please fill in different value");
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

		} else {

			System.out.println("cargoUser " + cargoUser.toString());

			cargoList.clear(); /* Clear array list */
			cargoList.add(cargoUser); /* Add cargoUser model object into list */
			res.setStatus("SUCCESS"); /* Set status to success */
			res.setResult(cargoList); /* Return object into list */

		}

		return res;
	}

	/**
	 * This method will provide UserProfile list to views
	 */
	@ModelAttribute("roles")
	public List<UserProfile> initializeProfiles() {
		return userProfileService.findAll();
	}

	/*
	 * Add different type of trucks into array list
	 * 
	 * Note: As for now the Type of trucks as project is int testing stage only
	 */
	public void setTruckType() {
		/* Clear Array list before adding items */
		cargoType.clear();

		/* Add items into array list */
		cargoType.add("Small truck");
		cargoType.add("Light truck");
		cargoType.add("Medium truck");
		cargoType.add("Heavy truck");
		cargoType.add("Heavy truck & transporters");
	}

}
