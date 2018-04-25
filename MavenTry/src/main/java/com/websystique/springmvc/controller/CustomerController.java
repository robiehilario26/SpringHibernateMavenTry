package com.websystique.springmvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.websystique.springmvc.model.CargoUser;

@Controller
@RequestMapping("/")
public class CustomerController {

	@RequestMapping(value = "/customer/list", method = RequestMethod.GET)
	public String customerList(ModelMap model) {
		CargoUser cargoUser = new CargoUser();
		model.addAttribute("cargoUser", cargoUser);
		return "customerList";
	}
}
