package com.mvc.forrest.controller.report;

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
@RequestMapping("/token/*")
public class TokenController {
	
		
	public TokenController(){
	}
	
	@RequestMapping("register")
	public String userIdValid(Model model) throws Exception {
		System.out.println(model);
		return null;
	}
}