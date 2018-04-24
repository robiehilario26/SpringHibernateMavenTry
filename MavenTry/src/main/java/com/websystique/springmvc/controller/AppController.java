package com.websystique.springmvc.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
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
import org.springframework.web.bind.annotation.SessionAttributes;

import antlr.Utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.websystique.springmvc.model.Employee;
import com.websystique.springmvc.model.JsonResponse;
import com.websystique.springmvc.model.User;
import com.websystique.springmvc.model.UserProfile;
import com.websystique.springmvc.service.EmployeeService;
import com.websystique.springmvc.service.UserProfileService;
import com.websystique.springmvc.service.UserService;

@Controller
@RequestMapping("/")
@SessionAttributes("roles")
public class AppController {

	private List<Employee> employeeList = new ArrayList<Employee>();

	@Autowired
	UserService userService;

	@Autowired
	UserProfileService userProfileService;

	@Autowired
	PersistentTokenBasedRememberMeServices persistentTokenBasedRememberMeServices;

	@Autowired
	AuthenticationTrustResolver authenticationTrustResolver;

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

		/* Display login name in view page */
		model.addAttribute("loggedinuser", getPrincipal());

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
	@RequestMapping(value = { "/delete-employee-by-ajax" }, method = RequestMethod.GET, produces = "application/json")
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

	@RequestMapping(value = "/ajaxAddEmployee", method = RequestMethod.GET, produces = "application/json")
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
	@RequestMapping(value = "/ajaxEditEmployee", method = RequestMethod.GET, produces = "application/json")
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

		ValidationUtils.rejectIfEmptyOrWhitespace(result, "name", "Name can not be empty");

		ValidationUtils
				.rejectIfEmptyOrWhitespace(result, "address", "address not be empty");

		ValidationUtils.rejectIfEmptyOrWhitespace(result, "salary",
				"Salary can not be empty.");

		ValidationUtils.rejectIfEmptyOrWhitespace(result, "joiningDate",
				"Date can not be empty.");

		ValidationUtils.rejectIfEmptyOrWhitespace(result, "ssn", "Ssn can not be empty");

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

	/**
	 * This method will provide the medium to add a new user.
	 */
	@RequestMapping(value = { "/newuser" }, method = RequestMethod.GET)
	public String newUser(ModelMap model) {
		User user = new User();
		model.addAttribute("user", user);
		model.addAttribute("edit", false);
		model.addAttribute("loggedinuser", getPrincipal());
		return "registration";
	}

	/**
	 * This method will be called on form submission, handling POST request for
	 * saving user in database. It also validates the user input
	 */
	@RequestMapping(value = { "/newuser" }, method = RequestMethod.POST)
	public String saveUser(@Valid User user, BindingResult result,
			ModelMap model) {

		if (result.hasErrors()) {
			return "registration";
		}

		/*
		 * Preferred way to achieve uniqueness of field [sso] should be
		 * implementing custom @Unique annotation and applying it on field [sso]
		 * of Model class [User].
		 * 
		 * Below mentioned peace of code [if block] is to demonstrate that you
		 * can fill custom errors outside the validation framework as well while
		 * still using internationalized messages.
		 */
		if (!userService.isUserSSOUnique(user.getId(), user.getSsoId())) {
			FieldError ssoError = new FieldError("user", "ssoId",
					messageSource.getMessage("non.unique.ssoId",
							new String[] { user.getSsoId() },
							Locale.getDefault()));
			result.addError(ssoError);
			return "registration";
		}

		userService.saveUser(user);

		model.addAttribute("success", "User " + user.getFirstName() + " "
				+ user.getLastName() + " registered successfully");
		model.addAttribute("loggedinuser", getPrincipal());
		// return "success";
		return "registrationsuccess";
	}

	/**
	 * This method will provide the medium to update an existing user.
	 */
	@RequestMapping(value = { "/edit-user-{ssoId}" }, method = RequestMethod.GET)
	public String editUser(@PathVariable String ssoId, ModelMap model) {
		User user = userService.findBySSO(ssoId);
		model.addAttribute("user", user);
		model.addAttribute("edit", true);
		model.addAttribute("loggedinuser", getPrincipal());
		return "registration";
	}

	/**
	 * This method will be called on form submission, handling POST request for
	 * updating user in database. It also validates the user input
	 */
	@RequestMapping(value = { "/edit-user-{ssoId}" }, method = RequestMethod.POST)
	public String updateUser(@Valid User user, BindingResult result,
			ModelMap model, @PathVariable String ssoId) {

		if (result.hasErrors()) {
			return "registration";
		}

		/*
		 * //Uncomment below 'if block' if you WANT TO ALLOW UPDATING SSO_ID in
		 * UI which is a unique key to a User.
		 * if(!userService.isUserSSOUnique(user.getId(), user.getSsoId())){
		 * FieldError ssoError =new
		 * FieldError("user","ssoId",messageSource.getMessage
		 * ("non.unique.ssoId", new String[]{user.getSsoId()},
		 * Locale.getDefault())); result.addError(ssoError); return
		 * "registration"; }
		 */

		userService.updateUser(user);

		model.addAttribute("success", "User " + user.getFirstName() + " "
				+ user.getLastName() + " updated successfully");
		model.addAttribute("loggedinuser", getPrincipal());
		return "registrationsuccess";
	}

	/**
	 * This method will delete an user by it's SSOID value.
	 */
	@RequestMapping(value = { "/delete-user-{ssoId}" }, method = RequestMethod.GET)
	public String deleteUser(@PathVariable String ssoId) {
		userService.deleteUserBySSO(ssoId);
		return "redirect:/list";
	}

	/**
	 * This method will provide UserProfile list to views
	 */
	@ModelAttribute("roles")
	public List<UserProfile> initializeProfiles() {
		return userProfileService.findAll();
	}

	/**
	 * This method handles Access-Denied redirect.
	 */
	@RequestMapping(value = "/Access_Denied", method = RequestMethod.GET)
	public String accessDeniedPage(ModelMap model) {
		model.addAttribute("loggedinuser", getPrincipal());
		return "accessDenied";
	}

	/**
	 * This method handles login GET requests. If users is already logged-in and
	 * tries to goto login page again, will be redirected to list page.
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginPage() {
		if (isCurrentAuthenticationAnonymous()) {
			return "login";
		} else {
			// return "redirect:/list"; // orignal code
			return "redirect:/getEmployeeList";
		}
	}

	/**
	 * This method will list all existing users.
	 */
	@RequestMapping(value = { "/", "/list" }, method = RequestMethod.GET)
	public String listUsers(ModelMap model) {

		// List<User> users = userService.findAllUsers();
		// model.addAttribute("users", users);
		model.addAttribute("loggedinuser", getPrincipal());
		System.out.println("getPrincipal " + getPrincipal().toString());
		// return "userslist";
		return "redirect:/getEmployeeList";
	}

	/**
	 * This method will list all existing users.
	 */
	@RequestMapping(value = { "/listV1" }, method = RequestMethod.GET)
	public String listUsersV1(ModelMap model) {

		List<User> users = userService.findAllUsers();
		model.addAttribute("users", users);
		model.addAttribute("loggedinuser", getPrincipal());
		/* return "userslist"; */
		return "userListV1";

	}

	/**
	 * This method handles logout requests. Toggle the handlers if you are
	 * RememberMe functionality is useless in your app.
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logoutPage(HttpServletRequest request,
			HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		if (auth != null) {
			// new SecurityContextLogoutHandler().logout(request, response,
			// auth);
			persistentTokenBasedRememberMeServices.logout(request, response,
					auth);
			SecurityContextHolder.getContext().setAuthentication(null);
		}
		return "redirect:/login?logout";
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
