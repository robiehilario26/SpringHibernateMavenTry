package com.websystique.springmvc.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.websystique.springmvc.service.EmployeeService;
import com.websystique.springmvc.model.Employee;

@Controller
public class TestController {

	@Autowired
	EmployeeService service;

	@Autowired
	MessageSource messageSource;

	@RequestMapping(value = { "/test" }, method = RequestMethod.GET)
	public String success(ModelMap model) {
		return "testRest";
	}

	@RequestMapping(value = { "/search" }, method = RequestMethod.POST)
	public @ResponseBody Employee ajaxSearch(@RequestParam int id,
			HttpServletRequest request, HttpServletResponse response) {
		Employee employee = service.findById(id);
		return employee;
	}

}
