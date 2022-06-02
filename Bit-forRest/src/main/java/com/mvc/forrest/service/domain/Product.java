package com.mvc.forrest.service.domain;

import java.io.File;
import java.sql.Date;
import java.util.List;

import lombok.Data;

@Data
public class Product {
	
	private int prodNo;
	private int width;
	private int length;
	private int height;
	private String userId;
	private String prodCondition;
	private int prodQuantity;
	private String prodName;
	private String prodDetail;
	private int isRental;
	private int rentalCounting;
	private int rentalPrice;
	private String account;
	private int deposit ;
	private String category;
	private String returnAddress;
	private String prodImg;
	private String recentImg;
	//주소 따로or jsp에서??

	

	
}