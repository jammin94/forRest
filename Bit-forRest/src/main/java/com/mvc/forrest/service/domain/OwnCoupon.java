package com.mvc.forrest.service.domain;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class OwnCoupon {
	
	///Field
	private int ownCouponNo;
	private User ownuser;
	private Coupon owncoupon;
	private Timestamp ownCouponCreDate;
	private Timestamp ownCouponDelDate;

}