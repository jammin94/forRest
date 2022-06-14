package com.mvc.forrest.service.domain;

import java.sql.Date;

import lombok.Data;

@Data
public class OldReview {
	private int oldReviewNo;
	private String reviewUserId;
	private String reviewedUserId;
	private String oldNo;
	private String reviewDetail;
	private double userRate;
	private Date reviewDate;
	private int userRateSum;
	private int userRateCount;
}
