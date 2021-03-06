package com.websystique.springmvc.utility;

import java.lang.reflect.Field;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ValidationUtils;

import com.websystique.springmvc.model.JsonResponse;

@Service("validateJsonResponse")
@Transactional
public class validateJsonResponseImpl implements validateJsonResponse {

	@Override
	public <T> JsonResponse jsonResponse(JsonResponse res,
			BindingResult result, Object object, List<T> modelList,
			boolean hasUniquValidation, String uniqueParameter,
			boolean uniqueServiceValidation) {

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

		/* Check boolean value */
		if (hasUniquValidation) { // if true
			/* check the data using parameter if id is unique or not */
			if (!uniqueServiceValidation) { // if true
				res.setStatus("FAIL");
				result.rejectValue(uniqueParameter, uniqueParameter
						+ " already exists. Please fill in different value");
				res.setResult(result.getAllErrors());
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
