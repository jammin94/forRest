package com.mvc.forrest.service.domain;

import lombok.Data;

@Data
public class Wishlist {
	
	private int wishlistNo;
	private Product product;
	private String wishedUserId;
	private int toggle;
	private int period;
	
	public Wishlist(){
	}

}
