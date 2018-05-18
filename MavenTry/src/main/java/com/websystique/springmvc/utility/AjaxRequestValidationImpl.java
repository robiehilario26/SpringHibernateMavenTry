package com.websystique.springmvc.utility;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;


@Service("ajaxRequestValidation")
@Transactional
public class AjaxRequestValidationImpl implements AjaxRequestValidation {

	@Override
	public boolean isAjax(HttpServletRequest request) {

		return "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
	}

}
