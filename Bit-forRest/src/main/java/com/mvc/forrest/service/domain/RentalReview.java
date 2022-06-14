package com.mvc.forrest.service.domain;


import java.sql.Date;

import lombok.Data;

@Data
public class RentalReview {
	
	private int reviewNo;
	private String reviewImg;
	private String reviewDetail;
	private int reviewScore;
	private String prodNo;
	private String userId;
	private Date regDate;
	private User user;
	
	public RentalReview(){
	}

	
}
