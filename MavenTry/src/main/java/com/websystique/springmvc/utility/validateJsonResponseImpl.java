package com.websystique.springmvc.utility;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ValidationUtils;

import com.websystique.springmvc.model.JsonResponse;

@Repository("validateJsonResponse")
@Transactional
public class validateJsonResponseImpl implements validateJsonResponse {

	@Override
	public <T> JsonResponse jsonResponse(JsonResponse res,
			BindingResult result, Object object, List<T> modelList) {
		Object someObject = object;

		for (Field field : someObject.getClass().getDeclaredFields()) {
			field.setAccessible(true); // You might want to set modifier to
										// public first.
			Object value = null;
			try {
				value = field.get(someObject);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
			if (value == null || value == "") {
				System.out.println("null list: " + field.getName() + "="
						+ value);

				ValidationUtils.rejectIfEmptyOrWhitespace(result,
						field.getName(), field.getName() + " cannot be empty.");

			}
		}

		if (result.hasErrors()) {

			/* Set status to fail */
			res.setStatus("FAIL");

			/*
			 * Collect all error messages for text field that not properly
			 * assign value
			 */
			res.setResult(result.getAllErrors());

		}
		
		else {

			modelList.clear(); /* Clear array list */
			modelList.add((T) object);
			/*
			 * Add cargoUser model object into list
			 */
			res.setStatus("SUCCESS"); /* Set status to success */
			res.setResult(modelList); /* Return object into list */

		}

		return res;
	}

}
