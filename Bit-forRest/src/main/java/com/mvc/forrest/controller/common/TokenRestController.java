package com.mvc.forrest.controller.common;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
		User loginUser = (User)session.getAttribute("user");
		System.out.println(token);
		loginUser.setPushToken(token);
		fcmService.saveToken(loginUser);
	} 
	
	
	
}
