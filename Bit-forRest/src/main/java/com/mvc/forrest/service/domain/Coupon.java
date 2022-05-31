package com.mvc.forrest.service.domain;

import java.sql.Timestamp;

import lombok.Data;

//==>ÄíÆùÀ» ¸ğµ¨¸µ(Ãß»óÈ­/Ä¸½¶È­)ÇÑ Bean
@Data
public class Coupon {
	
	///Field
	private int couponNo;
	private String couponName;
	private double discount;
	private Timestamp couponCreDate;
	private Timestamp couponDelDate;

}