package com.mvc.forrest.controller.kakao;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.mvc.forrest.service.domain.User;
import com.mvc.forrest.service.kakao.KakaoLoginService;
import com.mvc.forrest.service.user.UserService;

@Controller
@RequestMapping("/kakaoLogin/*")

public class KakaoLoginController {

	@Autowired
	KakaoLoginService kakaoLoginService;
	@Autowired
	UserService userService;
	
	public KakaoLoginController(){
		System.out.println(this.getClass());
	}
	
	@RequestMapping(value="/kakaoLogin", method=RequestMethod.GET)
	public String kakaoLogin(@RequestParam(value = "code", required = false) String code) throws Exception {

		System.out.println("/kakaoLogin/kakaoLogin : GET");

		String access_Token = kakaoLoginService.getAccessToken(code);
		
		Map<String, Object> userInfo = kakaoLoginService.getUserInfo(access_Token);

		String kakaoId = userInfo.get("kakaoEmail").toString();
		String dbUserId = userService.getUser((String)userInfo.get("kakaoEmail")).getUserId();
		
		if(kakaoId!=null && dbUserId!=null && kakaoId.equals(dbUserId)) {
			User newUser = new User();
			newUser.setUserId(kakaoId);
			newUser.setPassword(kakaoId);
			newUser.setPhone("00000000000");
			newUser.setUserName("userName");
			newUser.setUserImg(userInfo.get("kakaoImg").toString());
			newUser.setJoinPath("kakao");
			
		}
		
		return "forward:/user/test.jsp";
	}

	
}
