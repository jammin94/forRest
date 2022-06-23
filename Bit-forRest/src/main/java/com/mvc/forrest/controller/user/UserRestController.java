package com.mvc.forrest.controller.user;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mvc.forrest.config.auth.LoginUser;
import com.mvc.forrest.service.domain.User;
import com.mvc.forrest.service.user.UserService;

@RestController
@RequestMapping("/user/*")
public class UserRestController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private PasswordEncoder passwordEncoder;	
	
	public UserRestController(){
	}
	
	@RequestMapping("json/checkUserId")
	public int userIdValid(@RequestParam String userId) throws Exception {
		
		System.out.println("user/json/checkUserId : GET");
		
		if(userService.getUser(userId)==null) {
			return 0;
		}else {
			return 1;
		}
		
	}
	
	@RequestMapping(value="json/checkNickname")
	public int nicknameValid(String nickname) throws Exception {
		
		System.out.println("user/json/checkNickname : GET");

		if(userService.getUserByNickname(nickname)==null) {
			return 0;
		}else {
			return 1;
		}

		
	}
	
	@PostMapping("json/checkPWD")		//유저, 관리자
	public int deleteUser(@RequestParam("password77") String password, HttpSession session)throws Exception {
		
		System.out.println("/user/json/deleteUser : POST");

		LoginUser sessionUser= (LoginUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = userService.getUser(sessionUser.getUser().getUserId());
		
		if(passwordEncoder.matches(password, user.getPassword())) {
			return 0;
		}else {
			return 1;
		}
		
	}
	
	
	@RequestMapping(value="json/checkInfo")
	public int checkInfo(String phone, String name) throws Exception {
			User userByPhone = userService.getUserByPhone(phone);
			if(userByPhone!=null) {
				if(userByPhone.getUserName().equals(name)) {
					return 1;
				}
			}
		return 0;
	}
	
	@RequestMapping(value="json/checkIdPhone")
	public int checkIdPhone(String phone, String userId) throws Exception {
			User userByPhone = userService.getUserByPhone(phone);
			User userById = userService.getUser(userId);
			
			if(userById!=null && userByPhone!=null) {
				if(userById.getUserId().equals(userByPhone.getUserId())) {
					return 1;
				}
			}
		return 0;
	}

	@RequestMapping(value="json/smsValid")
	public String smsValid(String sms) throws Exception {
		return null;
	}
	

	
	@RequestMapping(value="json/updateUser")
	public User updateUser(@ModelAttribute User user) throws Exception {
		return null;
	}
}