package com.mvc.forrest.service.domain;

import java.sql.Date;

import lombok.Data;

@Data
public class WishList {
	
	private int wishListNo;
	private int prodNo;
	private String userId;
	private int period;
	
	public WishList(){
	}

}
