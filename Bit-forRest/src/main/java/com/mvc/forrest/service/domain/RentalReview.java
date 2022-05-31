package com.mvc.forrest.service.domain;

import java.util.Date;

import lombok.Data;

@Data
public class RentalReview {
	
	private int reviewNo;
	private String reviewImg;
	private String reviewDetail;
	private String reviewScore;
	private int prodNo;
	private String userId;
	private Date regDate;
	
	public RentalReview(){
	}

	
}
