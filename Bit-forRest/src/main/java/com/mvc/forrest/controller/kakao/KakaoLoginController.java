package com.mvc.forrest.controller.kakao;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.mvc.forrest.common.utils.FileNameUtils;
import com.mvc.forrest.config.auth.PrincipalDetailsService;
import com.mvc.forrest.service.coupon.CouponService;
import com.mvc.forrest.service.domain.Coupon;
import com.mvc.forrest.service.domain.OwnCoupon;
import com.mvc.forrest.service.domain.User;
import com.mvc.forrest.service.kakao.KakaoLoginService;
import com.mvc.forrest.service.user.UserService;

@Controller
@RequestMapping("/snsLogin/*")

public class KakaoLoginController {

	@Autowired
	KakaoLoginService kakaoLoginService;
	@Autowired
	UserService userService;
	@Autowired
	PrincipalDetailsService principalDetailsService;
	@Autowired
	CouponService couponService;
	
	public KakaoLoginController(){
		System.out.println(this.getClass());
	}
	
	@RequestMapping(value="/kakaoLogin", method=RequestMethod.GET)
	public String kakaoLogin(@RequestParam(value = "code", required = false) String code) throws Exception {

		System.out.println("/kakaoLogin/kakaoLogin : GET");

		String access_Token = kakaoLoginService.getKakaoToken(code);
		
		Map<String, Object> userInfo = kakaoLoginService.getKakaoInfo(access_Token);
	    	System.out.println(userInfo);
	    	System.out.println(userInfo.get("kakaoEmail"));
	    	System.out.println(userInfo.get("kakaoImg"));

		String kakaoId = userInfo.get("kakaoEmail").toString();
		System.out.println(kakaoId);
		
		User newUser = new User();

		if(userService.getUser((String)userInfo.get("kakaoEmail"))==null) {
			newUser.setUserId(kakaoId);
			newUser.setNickname(FileNameUtils.getRandomString());
			newUser.setPassword(FileNameUtils.getRandomString());
			newUser.setPhone(FileNameUtils.getRandomString());
			newUser.setUserName("userName");
			newUser.setUserAddr("테스트용 주소");
			newUser.setJoinDate(new Timestamp(System.currentTimeMillis()));
			newUser.setRecentDate(new Timestamp(System.currentTimeMillis()));
			newUser.setJoinPath("kakao");
			newUser.setUserImg(userInfo.get("kakaoImg").toString());
			userService.addUser(newUser);
			System.out.println("### SNS 신규회원 ###");			

			OwnCoupon oc = new OwnCoupon();
			Coupon coupon = couponService.getCoupon("2");	//2번 쿠폰 = 신규회원 쿠폰
			Calendar cal= Calendar.getInstance();
			cal.add(Calendar.DATE,30);
			Timestamp ts1 = new Timestamp(System.currentTimeMillis());
			Timestamp ts2 = new Timestamp(cal.getTimeInMillis());
			oc.setOwnUser(newUser);
			oc.setOwnCoupon(coupon);
			oc.setOwnCouponCreDate(ts1);
			oc.setOwnCouponDelDate(ts2);
			couponService.addOwnCoupon(oc);
			System.out.println("### 신규회원 쿠폰발급 ###");			
			
			principalDetailsService.loadUserByUsername(newUser.getUserId());
			
		}else {
			User kakaoUser = userService.getUser(kakaoId);
			userService.updateRecentDate(newUser);
			
			System.out.println("           kakaoUser : "+kakaoUser);
			System.out.println("           kakaoUser.getUserId : "+kakaoUser.getUserId());
			
			principalDetailsService.loadUserByUsername(kakaoUser.getUserId());
		}
		
		
		return "redirect:/"; 
	}
	
	@GetMapping("naverLogin")
	public String naverLogin(@RequestParam String code, @RequestParam String state) throws Exception{
		
		System.out.println("받은 code : "+code+" 받은 state : "+state);
		
		String access_Token = kakaoLoginService.getNaverToken(code,state);
		System.out.println(access_Token);	
		
		Map<String, Object> userInfo = kakaoLoginService.getNaverInfo(access_Token);
		
		System.out.println(userInfo);
		System.out.println(userInfo.get("naverEmail"));
    	System.out.println(userInfo.get("naverImg"));
    	
    	String naverId = userInfo.get("naverEmail").toString();
		System.out.println(naverId);
		
		User newUser = new User();

		if(userService.getUser((String)userInfo.get("naverEmail"))==null) {
			newUser.setUserId(naverId);
			newUser.setNickname(FileNameUtils.getRandomString());
			newUser.setPassword(FileNameUtils.getRandomString());
			newUser.setPhone(FileNameUtils.getRandomString());
			newUser.setUserName("userName");
			newUser.setUserAddr(FileNameUtils.getRandomString());
			newUser.setJoinDate(new Timestamp(System.currentTimeMillis()));
			newUser.setRecentDate(new Timestamp(System.currentTimeMillis()));
			newUser.setJoinPath("naver");
			newUser.setUserImg(userInfo.get("naverImg").toString());
			userService.addUser(newUser);
			System.out.println("### SNS 신규회원 ###");			

			OwnCoupon oc = new OwnCoupon();
			Coupon coupon = couponService.getCoupon("2");	//2번 쿠폰 = 신규회원 쿠폰
			Calendar cal= Calendar.getInstance();
			cal.add(Calendar.DATE,30);
			Timestamp ts1 = new Timestamp(System.currentTimeMillis());
			Timestamp ts2 = new Timestamp(cal.getTimeInMillis());
			oc.setOwnUser(newUser);
			oc.setOwnCoupon(coupon);
			oc.setOwnCouponCreDate(ts1);
			oc.setOwnCouponDelDate(ts2);
			couponService.addOwnCoupon(oc);
			System.out.println("### 신규회원 쿠폰발급 ###");			
			
			principalDetailsService.loadUserByUsername(newUser.getUserId());
			
		}else {
			User naverUser = userService.getUser(naverId);
			userService.updateRecentDate(newUser);
			
			System.out.println("           kakaoUser : "+naverUser);
			System.out.println("           kakaoUser.getUserId : "+naverUser.getUserId());
			
			principalDetailsService.loadUserByUsername(naverUser.getUserId());
		}
		return "redirect:/";
	}
}
