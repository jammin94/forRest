package com.mvc.forrest.controller.coupon;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mvc.forrest.config.auth.LoginUser;
import com.mvc.forrest.service.coupon.CouponService;
import com.mvc.forrest.service.domain.Coupon;
import com.mvc.forrest.service.domain.OwnCoupon;
import com.mvc.forrest.service.domain.Page;
import com.mvc.forrest.service.domain.Search;
import com.mvc.forrest.service.domain.User;
import com.mvc.forrest.service.user.UserService;


@RestController
@RequestMapping("/coupon/*")
public class CouponRestController {

	@Autowired
	private CouponService couponService;
	
	@Autowired
	private UserService userService;
	
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
	
	@RequestMapping("json/addOwnCoupon")
	public boolean addOwnCoupon(@RequestParam String couponNo, @RequestParam String boardNo) throws Exception{
		
	
		LoginUser sessionUser= (LoginUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = userService.getUser(sessionUser.getUser().getUserId());
		Coupon coupon = couponService.getCoupon(couponNo);
		OwnCoupon oc = new OwnCoupon();
		
		Calendar cal = Calendar.getInstance();
		Timestamp t1 = new Timestamp(System.currentTimeMillis());
		Timestamp t2 = new Timestamp(System.currentTimeMillis());
		
		cal.setTime(t1);
		cal.add(Calendar.DATE, 30);
		t2.setTime(cal.getTime().getTime());
		
		oc.setOwnCoupon(coupon);
		oc.setOwnUser(user);
		oc.setOwnCouponCreDate(t1);
		oc.setOwnCouponDelDate(t2);
		
		List<OwnCoupon> ocList =  couponService.checkOwnCoupon(oc);
		System.out.println(ocList);
		
		if(ocList.size()>0) {
//			쿠폰이 있는경우
			return true;
		}else {
//			쿠폰이 없는경우
			couponService.addOwnCoupon(oc);
			return false;

		}
	}
}