package com.websystique.springmvc.restController;

import java.util.List;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import com.websystique.springmvc.model.User;
import com.websystique.springmvc.model.UserProfile;
import com.websystique.springmvc.service.UserProfileService;
import com.websystique.springmvc.service.UserService;

@RestController
@SessionAttributes("roles")
public class UserRestController {

	@Autowired
	UserService userService;

	@Autowired
	UserProfileService userProfileService;
	
	private static Logger log = Logger.getLogger(UserRestController.class);
	
	// -------------------Retrieve All
	// Users--------------------------------------------------------

	@RequestMapping(value = "/restUser", method = RequestMethod.GET)
	public ResponseEntity<List<User>> listAllUsers() {
		List<User> users = userService.findAllUsers();
		if (users.isEmpty()) {
			return new ResponseEntity<List<User>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<User>>(users, HttpStatus.OK);

	}
	
	// Retrieve all user profiles
	@RequestMapping(value = "/restUser/listProfiles", method = RequestMethod.GET)
	public ResponseEntity<List<UserProfile>> listProfiles(){
		List<UserProfile> profiles = userProfileService.findAll();
		return new ResponseEntity<List<UserProfile>>(profiles, HttpStatus.OK);
		
	}

	// -------------------Create a
	// User--------------------------------------------------------

	@RequestMapping(value = "/restUser", method = RequestMethod.POST)
	public ResponseEntity<Void> createUser(@RequestBody User user,
			UriComponentsBuilder ucBuilder) {
		System.out.println("Creating User " + user.getUsernameId());

		// if (userService.isUserExist(user)) {
		// System.out.println("A User with name " + user.getUsername() +
		// " already exist");
		// return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		// }
		
//		UserProfile userProfile = new UserProfile();
//		
//		userProfile.setId(1);
//		userProfile.setType("USER");
		

		userService.saveUser(user);
		System.out.println("Fetch Data: " + user);
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/restUser/{id}")
				.buildAndExpand(user.getId()).toUri());
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}

	// -------------------Retrieve Single
	// User--------------------------------------------------------

	@RequestMapping(value = "/restUser/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> getUser(@PathVariable("id") int id) {
		System.out.println("Fetching User with id " + id);
		User user = userService.findById(id);
		if (user == null) {
			System.out.println("User with id " + id + " not found");
			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
		}

		System.out.println("Fetch Data: " + user);
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	// ------------------- Update a User
	// --------------------------------------------------------

	@RequestMapping(value = "/restUser/{id}", method = RequestMethod.PUT)
	public ResponseEntity<User> updateUser(@PathVariable("id") int id,
			@RequestBody User user) {
		System.out.println("Updating User " + id);

		User currentUser = userService.findById(id);
		//
		// if (currentUser==null) {
		// System.out.println("User with id " + id + " not found");
		// return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
		// }

		// currentUser.setUsernameId(user.getUsernameId());
		// currentUser.setFirstName(user.getFirstName());
		// currentUser.setEmail(user.getEmail());

		System.out.println("USER: " + user);
//		BasicConfigurator.configure();
//		log.info(user);
		userService.updateUser(user);
		return new ResponseEntity<User>(currentUser, HttpStatus.OK);
	}
	
	/**
	 * This method will provide UserProfile list to views
	 */
	@ModelAttribute("roles")
	public List<UserProfile> initializeProfiles() {
		return userProfileService.findAll();
	}

}
