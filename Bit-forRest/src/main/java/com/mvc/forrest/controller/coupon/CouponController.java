package com.mvc.forrest.controller.coupon;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mvc.forrest.common.utils.FileNameUtils;
import com.mvc.forrest.common.utils.FileUtils;
import com.mvc.forrest.service.coupon.CouponService;
import com.mvc.forrest.service.domain.Coupon;
import com.mvc.forrest.service.domain.Page;
import com.mvc.forrest.service.domain.Search;
import com.mvc.forrest.service.domain.User;
import com.mvc.forrest.service.user.UserService;

@Controller
@RequestMapping("/coupon/*")
public class CouponController {

	@Autowired
	private CouponService couponService;
	@Autowired
	private UserService userService;

	public FileNameUtils fileNameUtils ;
	
	@Value("5")
	int pageUnit;
	@Value("10")
	int pageSize;
	
	public CouponController() {
	}
	
	@RequestMapping("manageCoupon")
	public String manageCoupon(@ModelAttribute("search") Search search , Model model,
								HttpSession session) throws Exception{
	
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
		
		coupon.setCouponNo(fileNameUtils.getRandomString());
		
		couponService.addCoupon(coupon);
		
		return "redirect:/coupon/manageCoupon";
	}
	
	
	@PostMapping("updateCoupon")
	public String updateCoupon(@ModelAttribute("coupon")Coupon coupon) throws Exception {
		
		System.out.println("/coupon/updateCoupon : POST");
		
		couponService.updateCoupon(coupon);
		
		return "redirect:/coupon/manageCoupon";
	}
	
	@RequestMapping("ownCouponList")
	public String ownCouponList( Model model, HttpSession session, Search search) throws Exception{
		
		System.out.println("/coupon/ownCouponList : GET");
		
		User sessionUser = (User)session.getAttribute("user");
		
		Map<String , Object> map=couponService.getOwnCouponList(sessionUser.getUserId());
		
		model.addAttribute("map", map.get("list"));

		return "/coupon/ownCouponList";
	}
	
	@PostMapping("deleteCoupon")
	public String deleteCoupon(@RequestParam String couponNo) throws Exception{
		
		System.out.println("/coupon/deleteCoupon : POST");
		
		System.out.println("요청받은 쿠폰번호 : "+couponNo);
		
		couponService.deleteCoupon(couponNo);

		return "redirect:/coupon/manageCoupon";
	}
		
	@PostMapping("addOwnCoupon")
	public String addOwnCoupon(@ModelAttribute Coupon coupon) throws Exception{
		
		System.out.println("coupon/addOwnCoupon : POST");
		
		return null;
	}
	
}
