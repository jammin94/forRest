package com.mvc.forrest.controller.coupon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mvc.forrest.service.coupon.CouponService;
import com.mvc.forrest.service.domain.Coupon;

@Controller
@RequestMapping("/coupon/*")
public class CouponController {

	@Autowired
	private CouponService couponService;
	
	public CouponController() {
	}
	
	@GetMapping("manageCoupon")
	public String manageCoupon() throws Exception{
	
		System.out.println("/coupon/manageCoupon : GET");
		
		return null;
	}
		
	@PostMapping("addOwnCoupon")
	public String addOwnCoupon(@ModelAttribute Coupon coupon) throws Exception{
		
		System.out.println("coupon/addOwnCoupon : POST");
		
		return null;
	}
	
}
