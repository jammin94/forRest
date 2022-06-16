package com.mvc.forrest.service.domain;

import java.sql.Date;

import lombok.Data;

@Data
public class Wishlist {
	
	private int wishlistNo;
	private Product product;
	private String wishedUserId;
	private int period;
	
	public Wishlist(){
	}

}
