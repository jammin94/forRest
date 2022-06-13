package com.mvc.forrest.controller.user;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mvc.forrest.service.domain.User;
import com.mvc.forrest.service.user.UserService;

@RestController
@RequestMapping("/user/*")
public class UserRestController {
	
	@Autowired
	private UserService userService;
		
	public UserRestController(){
	}
	
	@RequestMapping("json/checkUserId")
	public int userIdValid(@RequestParam String userId) throws Exception {
		
		System.out.println("user/json/checkUserId : GET");
		
		System.out.println("받은 값"+userId);
		
		if(userService.getUser(userId)==null) {
			System.out.println("아이디 없음 : "+userService.getUser(userId));
			return 0;
		}else {
			System.out.println("아이디 있음 : "+userService.getUser(userId));
			return 1;
		}
		
	}
	
	@RequestMapping(value="json/pwdValid")
	public String pwdValid(String password) throws Exception {
		return null;
	}
	
	@RequestMapping(value="json/sendSMS")
	public String sendSMS(String phone) throws Exception {
		return null;
	}

	@RequestMapping(value="json/smsValid")
	public String smsValid(String sms) throws Exception {
		return null;
	}
	
	@RequestMapping(value="json/nicknameValid")
	public String nicknameValid(String nickname) throws Exception {
		return null;
	}
	
	@RequestMapping(value="json/updateUser")
	public User updateUser(@ModelAttribute User user) throws Exception {
		return null;
	}
}