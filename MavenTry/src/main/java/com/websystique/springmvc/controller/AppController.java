package com.websystique.springmvc.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import antlr.Utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.websystique.springmvc.model.Employee;
import com.websystique.springmvc.model.JsonResponse;
import com.websystique.springmvc.service.EmployeeService;

@Controller
@RequestMapping("/")
public class AppController {

	private List<Employee> employeeList = new ArrayList<Employee>();

	@Autowired
	EmployeeService service;

	@Autowired
	MessageSource messageSource;

	/*
	 * This method will redirect user to home page
	 */
	@RequestMapping(value = { "/home" }, method = RequestMethod.GET)
	public String index() {
		return "index";
	}

	/*
	 * This method will redirect user to employee page This method will provide
	 * the medium to add a new employee. This method will list all existing
	 * employees
	 */
	@RequestMapping(value = { "/getEmployeeList" }, method = RequestMethod.GET)
	public String employeeList(ModelMap model) {

		/*
		 * TODO: The method below is the original code for populating DataTable
		 * in the view page using spring tag libraries. The code will be for
		 * future references.
		 */
		/* Populate DataTable */
		/*
		 * List<Employee> employees = service.findAllEmployee();
		 * model.addAttribute("employees", employees);
		 */

		/* Add new employee */
		Employee employee = new Employee();
		model.addAttribute("employee", employee);
		model.addAttribute("edit", false);
		model.addAttribute("errorStatus", false);

		return "employeeList";
	}

	/*
	 * This method will redirect user to employee page This method will provide
	 * the medium to add a new employee. This method will list all existing
	 * employees using ajax
	 */
	@RequestMapping(value = { "/ajaxEmployeeList" }, method = RequestMethod.GET)
	@ResponseBody
	public List<Employee> ajaxEmployeeList(ModelMap model) {
		/* Populate DataTable */
		List<Employee> employees = service.findAllEmployee();

		return employees;
	}

	/*
	 * This method will provide the medium to get employee details using ajax
	 * with annotation @RequestParam.
	 */
	@RequestMapping(value = { "/search-employee-by-ajax" }, method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Employee ajaxEmployeeDetail(@RequestParam String ssn,
			HttpServletRequest request, HttpServletResponse response,
			Model model) {
		Employee employee = service.findEmployeeBySsn(ssn);
		return employee;
	}

	/*
	 * This method will delete an employee by it's SSN value sing ajax with
	 * annotation @RequestParam.
	 */
	@RequestMapping(value = { "/delete-employee-by-ajax" }, method = RequestMethod.POST)
	public String ajaxDeleteEmployee(@RequestParam String ssn, ModelMap model) {
		/* Delete employee by Ssn id */
		service.deleteEmployeeBySsn(ssn);
		return "redirect:/getEmployeeList";
	}

	/*
	 * This method will validate all input field in form and returning response
	 * If result has error detected. If no error occured in result it will
	 * insert the fields into database
	 */
	@RequestMapping(value = "/ajaxAddEmployee", method = RequestMethod.POST)
	public @ResponseBody JsonResponse AddEmployee(
			@ModelAttribute(value = "employee") Employee employee,
			BindingResult result) {

		System.out.println("employee " + employee.toString());

		/* Create new json response */
		JsonResponse res = new JsonResponse();

		/* Call method for validating the input values */
		jsonResponse(res, result, employee);

		/* If result is success it will insert into the employee table */
		if (res.getStatus().equalsIgnoreCase("success")) {

			/* Add Employee details into database */
			service.saveEmployee(employee);

		}
		return res;
	}

	/*
	 * This method will validate all input field in form and returning response
	 * If result has error detected. If no error occured in result it will
	 * insert the fields into database
	 */
	@RequestMapping(value = "/ajaxEditEmployee", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody JsonResponse updateEmployee(
			@ModelAttribute(value = "employee") @Valid Employee employee,
			BindingResult result) {

		System.out.println("employee " + employee.toString());

		/* Create new json response */
		JsonResponse res = new JsonResponse();

		/* Call method for validating the input values */
		jsonResponse(res, result, employee);

		/* If result is success it will insert into the employee table */
		if (res.getStatus().equalsIgnoreCase("success")) {
			/* Update Employee details into database */
			service.updateEmployee(employee);
		}
		return res;
	}

	/*
	 * This method will validate all input field in form and returning response.
	 * If result has error detected it will set the status to "FAIL". If no
	 * error occured in result it will set the status to "SUCCESS".
	 */
	public JsonResponse jsonResponse(JsonResponse res, BindingResult result,
			Employee employee) {

		/* Set error message if text field is empty */

		ValidationUtils.rejectIfEmpty(result, "name", "Name can not be empty");

		ValidationUtils
				.rejectIfEmpty(result, "address", "address not be empty");

		ValidationUtils.rejectIfEmpty(result, "salary",
				"Salary can not be empty.");

		ValidationUtils.rejectIfEmpty(result, "joiningDate",
				"Date can not be empty.");

		ValidationUtils.rejectIfEmpty(result, "ssn", "Ssn can not be empty");

		if (!service.isEmployeeUnique(employee.getId(), employee.getSsn())) {

			/* Set status to fail */
			res.setStatus("FAIL");

			/* Set error message if Ssn id already exist */
			result.rejectValue("ssn",
					"Ssn already exists. Please fill in different value");
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

			System.out.println("employeee " + employee.toString());

			employeeList.clear(); /* Clear array list */
			employeeList.add(employee); /* Add employee model object into list */
			res.setStatus("SUCCESS"); /* Set status to success */
			res.setResult(employeeList); /* Return object into list */

		}

		return res;
	}

}
