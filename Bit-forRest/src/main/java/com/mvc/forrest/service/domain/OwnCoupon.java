package com.mvc.forrest.service.domain;

import java.sql.Timestamp;

import lombok.Data;

//==>회원이 보유하고있는 쿠폰을 모델링(추상화/캡슐화)한 Bean
@Data
public class OwnCoupon {
	
	///Field
	private int ownCouponNo;
	private User ownuser;
	private Coupon owncoupon;
	private Timestamp ownCouponCreDate;
	private Timestamp ownCouponDelDate;

}