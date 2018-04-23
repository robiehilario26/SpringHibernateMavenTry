package com.websystique.springmvc.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.websystique.springmvc.model.CargoUser;
import com.websystique.springmvc.model.Employee;
import com.websystique.springmvc.model.User;
import com.websystique.springmvc.service.CargoUserService;
import com.websystique.springmvc.service.EmployeeService;
import com.websystique.springmvc.service.UserProfileService;
import com.websystique.springmvc.service.UserService;

@Controller
@RequestMapping("/")
public class DeliveryController {

	@Autowired
	CargoUserService cargoUserService;

	/**
	 * This method will list all existing cargo users.
	 */
	@RequestMapping(value = { "/cargo" }, method = RequestMethod.GET)
	public String listCargoUsers(ModelMap model) {

		List<CargoUser> users = cargoUserService.cargoList();
		model.addAttribute("users", users);

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

}
