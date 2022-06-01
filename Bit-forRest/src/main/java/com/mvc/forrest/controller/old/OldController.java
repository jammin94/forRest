package com.mvc.forrest.controller.old;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mvc.forrest.service.domain.Old;
import com.mvc.forrest.service.domain.User;
import com.mvc.forrest.service.old.OldService;
import com.mvc.forrest.service.oldlike.OldLikeService;
import com.mvc.forrest.service.user.UserService;

@Controller
@RequestMapping("/old/*")
public class OldController {

	//Field
	@Autowired
	public OldService oldService;
	
	public UserService userservice;
	
	public OldLikeService oldLikeService;
	
	// 중고거래 게시물 등록 화면 네비게이터
	@RequestMapping( value="addOld", method= RequestMethod.GET )
	public String addOld(@ModelAttribute("old") Old old, Model model ) throws Exception {
	
		System.out.println(this.getClass());
		return "/old/addOld";	
		
	}
	
	// 중고거래 게시물 등록 후 DB 저장
	@RequestMapping (value="getOld", method = RequestMethod.POST)
	public String getOld(@ModelAttribute("old") Old old, HttpServletRequest request) throws Exception{
		
		User user = new User();
		//old.setUserId("admin");
		old.setOldNo(1);
		
		
		return "/old/getOld";
	}
}
