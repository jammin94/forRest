package com.mvc.forrest.service.domain;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class Old {
	private String oldNo;
	private int oldPrice;
	private String oldTitle;
	private String oldDetail;
	private Timestamp oldDate;
	private int oldView;
	private String category;
	private short oldState;
	private String oldImg;
	private String userId;
	private String oldAddr;
	private OldReview oldReview;
	}
