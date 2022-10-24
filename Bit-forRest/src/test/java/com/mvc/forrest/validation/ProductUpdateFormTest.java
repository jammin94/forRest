package com.mvc.forrest.validation;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.jupiter.api.Test;

import com.mvc.forrest.common.validation.ProductUpdateForm;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ProductUpdateFormTest {
	
	ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
	Validator validator = factory.getValidator();
	
	@Test
	void validationNullTest() {
		ProductUpdateForm form = new ProductUpdateForm();
		
		form.setProdNo(null);
		form.setCategory(null);
		form.setProdDetail(null);
		form.setIsRental(3);
		form.setDivyAddress(null);
		
		Set<ConstraintViolation<ProductUpdateForm>> violations = validator.validate(form);
		for(ConstraintViolation<ProductUpdateForm> violation : violations ) {
			log.info("violation = {}", violation);
			log.info("violationMsg = {}", violation.getMessage());
		}
	}
	
	@Test
	void validationBlankTest() {
		ProductUpdateForm form = new ProductUpdateForm();
		
		form.setProdNo(" ");
		form.setCategory("");
		form.setProdDetail(" ");
		form.setDivyAddress("     ");
		
		Set<ConstraintViolation<ProductUpdateForm>> violations = validator.validate(form);
		for(ConstraintViolation<ProductUpdateForm> violation : violations ) {
			log.info("violation = {}", violation);
			log.info("violationMsg = {}", violation.getMessage());
		}
	}
	
	@Test
	void 검증정상동작테스트() {
		ProductUpdateForm form = new ProductUpdateForm();
		
		form.setProdNo("dfjkjk-1231-vask");
		form.setCategory("텐트");
		form.setProdDetail("rkskefkdkakdjfkajfaksdj");
		form.setDivyAddress("경기도 성남시 수정구 신흥2동 123길");
		
		Set<ConstraintViolation<ProductUpdateForm>> violations = validator.validate(form);
		assertThat(violations).isEmpty();
	}

}
