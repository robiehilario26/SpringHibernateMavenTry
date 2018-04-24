package com.websystique.springmvc.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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

		// List<CargoUser> users = cargoUserService.cargoList();
		// model.addAttribute("users", users);

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

		ValidationUtils.rejectIfEmpty(result, "cargo_driver",
				"Name cannot be empty.");

		ValidationUtils.rejectIfEmpty(result, "cargo_vehicletype",
				"Vehicle type cannot be empty.");

		ValidationUtils.rejectIfEmpty(result, "cargo_company",
				"Company cannot be empty.");

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
			cargoList.add(cargoUser); /* Add employee model object into list */
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
	
	
	/**
	 * This method returns the principal[user-name] of logged-in user.
	 */
	private String getPrincipal() {
		String userName = null;
		Object principal = SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) {
			userName = ((UserDetails) principal).getUsername();
		} else {
			userName = principal.toString();
		}
		return userName;
	}

	/**
	 * This method returns true if users is already authenticated [logged-in],
	 * else false.
	 */
	private boolean isCurrentAuthenticationAnonymous() {
		final Authentication authentication = SecurityContextHolder
				.getContext().getAuthentication();
		return authenticationTrustResolver.isAnonymous(authentication);
	}

}
