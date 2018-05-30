package com.websystique.springmvc.restController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
@RequestMapping("/restController")
public class UserMappingController {
	
	@RequestMapping(method = RequestMethod.GET)
	public String getUserPage(){
		return "angularJsUserList";
	}

}
