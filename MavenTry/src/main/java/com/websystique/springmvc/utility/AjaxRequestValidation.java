package com.websystique.springmvc.utility;

import javax.servlet.http.HttpServletRequest;


public interface AjaxRequestValidation {

	 boolean isAjax(HttpServletRequest request);
	
}
