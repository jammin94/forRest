package com.mvc.forrest.common.validation;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import lombok.Data;

@Data
public class ProductUpdateForm {
	
	@NotBlank
	private String prodNo;
	
	@NotBlank
	@Length(min = 1, max = 30)
	private String prodName;
	
	@Range(min = 1, max = 500)
	private int width;
	
	@Range(min = 1, max = 500)
	private int length;
	
	@Range(min = 1, max = 500)
	private int height;
	
	@Range(min = 1, max = 9999)
	private int prodQuantity;
	
	@NotBlank
	private String category;
	
	@NotBlank
	@Length(min = 10, max = 200)
	private String prodDetail;
	
	@NotNull
	@Range(min = 0, max = 1)
	private int isRental;
	
	@NotNull
	private int rentalPrice;
	
	@NotNull
	private String account;
	
	@NotBlank
	private String divyAddress;
	
	@NotBlank
	private String prodImg;
}
