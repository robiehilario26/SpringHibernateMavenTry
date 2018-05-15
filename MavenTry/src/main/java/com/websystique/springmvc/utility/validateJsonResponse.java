package com.websystique.springmvc.utility;

import java.util.List;

import org.springframework.validation.BindingResult;

import com.websystique.springmvc.model.JsonResponse;

public interface validateJsonResponse {

	<T> JsonResponse jsonResponse(JsonResponse res,
			BindingResult result, Object object, List<T> modelList);
}
