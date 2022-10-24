package com.mvc.forrest.common.validation;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ProductValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		return ProductUpdateForm.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		ProductUpdateForm form = (ProductUpdateForm) target;
		
		if(form.getIsRental() == 1 && form.getRentalPrice()<=0) {
			errors.rejectValue("rentalPrice", "range", new Object[] {1}, null );
		}
		
		if(form.getIsRental() == 1 && validateStringField(form.getAccount())) {
			errors.rejectValue("account", "required", null);
		}
		
		if(validateStringField(form.getDivyAddress())) {
			errors.rejectValue("divyAddress", "required", null);
		}
		
		
	}

	private boolean validateStringField(String stringField) {
		String replacedField = stringField.replaceAll("/", " ");
		log.info("replacedField={}", replacedField);
		return !StringUtils.hasText(replacedField) || !replacedField.matches(".*[0-9].*");
	}
	
	

}
