package com.mvc.forrest.config;

import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.mvc.forrest.config.auth.LoginUser;
import com.mvc.forrest.config.auth.userInfo.KakaoUserInfo;
import com.mvc.forrest.config.auth.userInfo.NaverUserInfo;
import com.mvc.forrest.config.auth.userInfo.OAuth2UserInfo;
import com.mvc.forrest.service.coupon.CouponService;
import com.mvc.forrest.service.domain.Coupon;
import com.mvc.forrest.service.domain.OwnCoupon;
import com.mvc.forrest.service.domain.Role;
import com.mvc.forrest.service.domain.User;
import com.mvc.forrest.service.user.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    @Autowired 
    private UserService userService;
    @Autowired
    private CouponService couponService;
    @Autowired 
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        
        OAuth2UserInfo oAuth2UserInfo = null;
     	User snsUser = null;
     	String uuid = UUID.randomUUID().toString().substring(0, 13);
     	
        String provider = userRequest.getClientRegistration().getRegistrationId();
        System.out.println("provider : "+provider);
        
        if(provider.equals("naver")){	
        	//////////////////////////////////// 네이버 로그인 //////////////////////////////////////
            oAuth2UserInfo = new NaverUserInfo(oAuth2User.getAttributes());
            System.out.println("getAttributes : "+oAuth2User.getAttributes());
            
         	String naverEmail = oAuth2UserInfo.getAttributes().get("email").toString();
         	String naverName = oAuth2UserInfo.getAttributes().get("name").toString();
         	String naverPhone = oAuth2UserInfo.getAttributes().get("mobile").toString();
         	String password = bCryptPasswordEncoder.encode(uuid);
         	String nickname = uuid;
         	String userAddr = "주소를 변경해주세요";
         	Role role = Role.user;
         	
         	try {
    			snsUser = userService.getUser(naverEmail);
//    				### 회원가입 ###
    			if(snsUser == null){
    				snsUser = User.oauth2Register()
    	                    .userName(naverName).password(password).email(naverEmail).role(role)
    	                    .provider(provider).id(naverEmail).nickname(nickname).userAddr(userAddr)
    	                    .phone(naverPhone)
    	                    .build();
    	            userService.addUser(snsUser);

//    	            ### 신규가입 쿠폰발급 ###
    	            OwnCoupon oc = new OwnCoupon();
    				Coupon coupon = couponService.getCoupon("2");	//2번 쿠폰 = 신규회원 쿠폰
    				Calendar cal= Calendar.getInstance();
    				cal.add(Calendar.DATE,30);
    				Timestamp ts1 = new Timestamp(System.currentTimeMillis());
    				Timestamp ts2 = new Timestamp(cal.getTimeInMillis());
    				oc.setOwnUser(snsUser);
    				oc.setOwnCoupon(coupon);
    				oc.setOwnCouponCreDate(ts1);
    				oc.setOwnCouponDelDate(ts2);
    				couponService.addOwnCoupon(oc);
    				System.out.println("### 신규회원 쿠폰발급 ###");
    				
    	            System.out.println("  snsUser : "+snsUser);
    	        }
    		} catch (Exception e) {
    			e.printStackTrace();
    		}//end of naverLogin
         	 
        }else if(provider.equals("kakao")){
        	//////////////////////////////////// 카카오 로그인 //////////////////////////////////////
            oAuth2UserInfo = new KakaoUserInfo(oAuth2User.getAttributes());
            String kakaoEmail = oAuth2UserInfo.getEmail();
            
         	String userName = "Kakao_"+UUID.randomUUID().toString().substring(0,6);
         	String Phone = "Kakao_"+UUID.randomUUID().toString().substring(0,6);
         	String password = bCryptPasswordEncoder.encode(uuid);
         	String nickname = uuid;
         	String userAddr = "주소를 변경해주세요";
         	Role role = Role.user;
         	
         	try {
    			snsUser = userService.getUser(kakaoEmail);
//    				### 회원가입 ###
    			if(snsUser == null){
    				snsUser = User.oauth2Register()
    	                    .userName(userName).password(password).email(kakaoEmail).role(role)
    	                    .provider(provider).id(kakaoEmail).nickname(nickname).userAddr(userAddr)
    	                    .phone(Phone)
    	                    .build();
    	            userService.addUser(snsUser);

//    	            ### 신규가입 쿠폰발급 ###
    	            OwnCoupon oc = new OwnCoupon();
    				Coupon coupon = couponService.getCoupon("2");	//2번 쿠폰 = 신규회원 쿠폰
    				Calendar cal= Calendar.getInstance();
    				cal.add(Calendar.DATE,30);
    				Timestamp ts1 = new Timestamp(System.currentTimeMillis());
    				Timestamp ts2 = new Timestamp(cal.getTimeInMillis());
    				oc.setOwnUser(snsUser);
    				oc.setOwnCoupon(coupon);
    				oc.setOwnCouponCreDate(ts1);
    				oc.setOwnCouponDelDate(ts2);
    				couponService.addOwnCoupon(oc);
    				System.out.println("### 신규회원 쿠폰발급 ###");
    				
    	            System.out.println("  snsUser : "+snsUser);
    	        }
    		} catch (Exception e) {
    			e.printStackTrace();
    		}

        }//end of kakaoLogin
        
		///////////////////////////////////////////////////////////////
        try {
        	System.out.println("  채팅시도 시작");
		String reqURL = "http://192.168.0.42:3001/sessionLoginLogout/login/"+snsUser.getUserId();
		URL url = new URL(reqURL);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setConnectTimeout(500);
		int responseCode = conn.getResponseCode();
		System.out.println("채팅 responseCode : " + responseCode);
        }catch(Exception e){
        	System.out.println("Node server is Dead ..");
        }
    	System.out.println("  채팅시도 끝");
		
		////////////////////////////////////////////////////////////////
        
		httpSession.setAttribute("login_info", oAuth2UserInfo);
        return new LoginUser(snsUser, oAuth2User.getAttributes());
    }//end of method
}//end of class