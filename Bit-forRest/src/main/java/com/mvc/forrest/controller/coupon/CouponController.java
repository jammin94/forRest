package com.mvc.forrest.controller.coupon;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mvc.forrest.service.coupon.CouponService;
import com.mvc.forrest.service.domain.Coupon;
import com.mvc.forrest.service.domain.Page;
import com.mvc.forrest.service.domain.Search;

@Controller
@RequestMapping("/coupon/*")
public class CouponController {

	@Autowired
	private CouponService couponService;
	
	@Value("5")
	int pageUnit;
	@Value("10")
	int pageSize;
	
	public CouponController() {
	}
	
	@RequestMapping("manageCoupon")
	public String manageCoupon(@ModelAttribute("search") Search search , Model model ) throws Exception{
	
		System.out.println("/coupon/manageCoupon : GET / POST");
		
		if(search.getCurrentPage() ==0 ){
			search.setCurrentPage(1);
		}
		search.setPageSize(pageSize);
		
		Map<String , Object> map=couponService.getCouponList(search);
			
			System.out.println("# map : "+map);
			
		Page resultPage = new Page( search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		
		// Model 과 View 연결
		model.addAttribute("list", map.get("list"));
		model.addAttribute("resultPage", resultPage);
		model.addAttribute("search", search);
				
		return "/coupon/manageCoupon";
	}
		
	@PostMapping("addCoupon")
	public String addCoupon(@ModelAttribute("coupon")Coupon coupon ) throws Exception {
	
		System.out.println("/coupon/addCoupon : POST");
		
		couponService.addCoupon(coupon);
		
		return "redirect:/coupon/manageCoupon";
	}
	
	
	@PostMapping("updateCoupon")
	public void updateCoupon(@ModelAttribute("coupon")Coupon coupon) throws Exception {
		
		System.out.println("/coupon/updateCoupon : POST");
		
		couponService.updateCoupon(coupon);
		
	}
		
	@PostMapping("addOwnCoupon")
	public String addOwnCoupon(@ModelAttribute Coupon coupon) throws Exception{
		
		System.out.println("coupon/addOwnCoupon : POST");
		
		return null;
	}
	
}
