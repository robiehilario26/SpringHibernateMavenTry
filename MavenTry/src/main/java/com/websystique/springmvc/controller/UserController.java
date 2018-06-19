package com.websystique.springmvc.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.AuthenticationTrustResolver;
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
import org.springframework.web.servlet.ModelAndView;

import com.websystique.springmvc.model.JsonResponse;
import com.websystique.springmvc.model.User;
import com.websystique.springmvc.model.UserProfile;
import com.websystique.springmvc.service.UserProfileService;
import com.websystique.springmvc.service.UserService;
import com.websystique.springmvc.utility.AjaxRequestValidation;
import com.websystique.springmvc.utility.validateJsonResponse;

@Controller
@RequestMapping("/")
@SessionAttributes("roles")
public class UserController {

	private List<User> userList = new ArrayList<User>();

	@Autowired
	AjaxRequestValidation ajaxRequestValidation;

	@Autowired
	UserService userService;

	@Autowired
	UserProfileService userProfileService;

	@Autowired
	MessageSource messageSource;

	@Autowired
	AuthenticationTrustResolver authenticationTrustResolver;

	@Autowired
	PersistentTokenBasedRememberMeServices persistentTokenBasedRememberMeServices;

	@Autowired
	validateJsonResponse validateJson;

	@RequestMapping(value = "/ajaxAddUser", method = RequestMethod.GET, headers = "Accept=*/*", produces = "application/json")
	public @ResponseBody JsonResponse AddUser(
			@ModelAttribute(value = "user") User user, BindingResult result) {

		System.out.println("user GET " + user.toString());

		/* Create new json response */
		JsonResponse res = new JsonResponse();

		/* Call method for validating the input values */
		// userJsonResponse(res, result, user);

		/* Call service method for validating the input values */
		validateJson
				.jsonResponse(
						res,
						result,
						user,
						userList,
						true,
						"usernameId",
						userService.isUserSSOUnique(user.getId(),
								user.getUsernameId()));

		/* If result is success it will insert into the employee table */
		if (res.getStatus().equalsIgnoreCase("success")) {
			System.out.println("user success GET " + user.toString());

			/* Set to null to be able to save new User data */
			user.setId(null);

			/* Add Employee details into database */
			// userService.saveUser(user);

		}
		return res;
	}

	/*
	 * This method will validate all input field in form and returning response
	 * If result has error detected. If no error occured in result it will
	 * insert the fields into database
	 */
	@RequestMapping(value = "/ajaxEditUser", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody JsonResponse updateUser(
			@ModelAttribute(value = "employee") @Valid User user,
			BindingResult result) {

		System.out.println("user " + user.toString());

		/* Create new json response */
		JsonResponse res = new JsonResponse();

		/* Call method for validating the input values */
		// userJsonResponse(res, result, user);

		/* Call service method for validating the input values */
		validateJson
				.jsonResponse(
						res,
						result,
						user,
						userList,
						true,
						"usernameId",
						userService.isUserSSOUnique(user.getId(),
								user.getUsernameId()));

		/* If result is success it will insert into the employee table */
		if (res.getStatus().equalsIgnoreCase("success")) {
			/* Update Employee details into database */
			userService.updateUser(user);
		}
		return res;
	}

	/*
	 * This method will delete an employee by it's SSN value sing ajax with
	 * annotation @RequestParam.
	 */
	@RequestMapping(value = { "/ajaxDeletetUser" }, method = RequestMethod.GET, produces = "application/json")
	public String deleteUser(@RequestParam Integer userId, ModelMap model) {
		/* Delete user by id */
		userService.deleteUserById(userId);
		return "redirect:/listV1";
	}

	public JsonResponse userJsonResponse(JsonResponse res,
			BindingResult result, User user) {

		/* Set error message if text field is empty */

		ValidationUtils.rejectIfEmptyOrWhitespace(result, "firstName",
				"firstName can not be empty");

		ValidationUtils.rejectIfEmptyOrWhitespace(result, "lastName",
				"lastName can not be empty");

		ValidationUtils.rejectIfEmptyOrWhitespace(result, "email",
				"email not be empty");

		ValidationUtils.rejectIfEmptyOrWhitespace(result, "usernameId",
				"ssoId can not be empty.");

		ValidationUtils.rejectIfEmptyOrWhitespace(result, "password",
				"password can not be empty.");

		if (!userService.isUserSSOUnique(user.getId(), user.getUsernameId())) {

			System.out.println("!userService.isUserSSOUnique(user.getId() "
					+ !userService.isUserSSOUnique(user.getId(), user
							.getUsernameId().toString()));

			/* Set status to fail */
			res.setStatus("FAIL");

			/* Set error message if Username already exist */
			result.rejectValue("usernameId",
					"Username already exists. Please fill in different value");
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

			System.out.println("user " + user.toString());

			userList.clear(); /* Clear array list */
			userList.add(user); /* Add employee model object into list */
			res.setStatus("SUCCESS"); /* Set status to success */
			res.setResult(userList); /* Return object into list */

		}

		return res;
	}

	/**
	 * This method will list all existing users. back here
	 */
	@RequestMapping(value = { "/listV1" }, method = RequestMethod.GET)
	public String listUsersV1(ModelMap model) {

		List<User> users = userService.findAllUsers();
		model.addAttribute("users", users);
		model.addAttribute("loggedinuser", getPrincipal());

		User user = new User();
		model.addAttribute("user", user);
		// return "userslist";
		return "userListV1";
		// return "registerUser";
	}

	/*
	 * This method will provide the medium to get cargo user details using ajax
	 * with annotation @RequestParam.
	 */
	@RequestMapping(value = { "/search-user-by-ajax" }, method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public User ajaxEmployeeDetail(@RequestParam Integer id,
			HttpServletRequest request, HttpServletResponse response,
			Model model) {

		if (!ajaxRequestValidation.isAjax(request)) {
			/* Return null in web browser */
			return null;
		}

		User user = userService.findById(id);
		return user;
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
	 * This method will provide UserProfile list to views
	 */
	@ModelAttribute("roles")
	public List<UserProfile> initializeProfiles() {
		return userProfileService.findAll();
	}
	
	/*Generate JasperReport test*/
	@RequestMapping(value = "/helloReport5", method = RequestMethod.GET)
	public ModelAndView getRpt5(ModelMap modelMap, ModelAndView modelAndView) {

		JRDataSource JRdataSource = new JRBeanCollectionDataSource(userService.findAllUsers(),false);
		modelMap.put("datasource", JRdataSource);
		modelMap.put("format", "pdf");
		modelAndView = new ModelAndView("rpt_sample", modelMap);
		return modelAndView;
	}

	

}
