package com.mvc.forrest.controller.common;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mvc.forrest.config.auth.LoginUser;
import com.mvc.forrest.service.domain.User;
import com.mvc.forrest.service.firebase.FCMService;



@RestController
@RequestMapping("/token/*")
public class TokenRestController {
	
	@Autowired
	private FCMService fcmService;

	
	
	@PostMapping("saveToken")
	public void saveToken(@RequestBody  String token, HttpSession session) throws Exception{
		System.out.println("/token/saveToken");
		if(isAuthenticated()) {
			LoginUser loginUser= (LoginUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			String userId= loginUser.getUser().getUserId();
			System.out.println(token);
			loginUser.getUser().setPushToken(token);
			fcmService.saveToken(loginUser.getUser());
		}

		System.out.println("저장 됨");
	} 
	
	//로그인여부 판별
	private boolean isAuthenticated() {
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    if (authentication == null || AnonymousAuthenticationToken.class.
	      isAssignableFrom(authentication.getClass())) {
	        return false;
	    }
	    return authentication.isAuthenticated();
	}
	
}
