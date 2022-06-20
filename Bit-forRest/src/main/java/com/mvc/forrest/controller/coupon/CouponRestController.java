package com.mvc.forrest.controller.coupon;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mvc.forrest.service.coupon.CouponService;
import com.mvc.forrest.service.domain.Coupon;
import com.mvc.forrest.service.domain.Page;
import com.mvc.forrest.service.domain.Search;


@RestController
@RequestMapping("/coupon/*")
public class CouponRestController {

	@Autowired
	private CouponService couponService;
	
	@Value("5")
	int pageUnit;
	@Value("10")
	int pageSize;
	
	public CouponRestController() {
	}
	
	
	@ResponseBody
	@RequestMapping("json/addCoupon")
	public void addCoupon(@ModelAttribute("coupon")Coupon coupon) throws Exception{
		
		System.out.println("/json/addCoupon : POST");
		
		System.out.println("들어온 쿠폰정보 :: "+coupon);
		
		couponService.addCoupon(coupon);
		
		System.out.println("저장된 쿠폰정보 :: "+coupon);

	}
	
	@PostMapping("json/updateCoupon")
	public void updateCoupon() throws Exception{

	}	
	
	@RequestMapping("json/listCoupon")
	public Map<String, Object> getListCoupon( Search search , Model model
										) throws Exception{
		
		System.out.println("/user/listUser : GET / POST");
		
		if(search.getCurrentPage() ==0 ){
			search.setCurrentPage(1);
		}
		search.setPageSize(pageSize);
		
		// Business logic 수행
		Map<String , Object> map=couponService.getCouponList(search);
		
		Page resultPage = new Page( search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println(resultPage);
		
		// Model 과 View 연결
		map.put("list", map.get("list"));
		map.put("resultPage", resultPage);
		map.put("search", search);
		
		return map;
	}
	
	public void deleteCoupon() throws Exception{
		
	}
	
	public Coupon getCoupon() throws Exception{
		return null;
	}
	
	
}
