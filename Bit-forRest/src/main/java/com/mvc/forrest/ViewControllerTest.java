package com.mvc.forrest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewControllerTest {

	@GetMapping("/auth/signup")
	public String signupPage() {
		return "auth/signup";
	}
	
	@GetMapping("/addTest")
	public String add() {
		return "oldLike/addOldLikeTest";
	}	
	
	@GetMapping("/auth/signin")
	public String signinPage() {
		return "auth/signin";
	}
	
	@GetMapping("/image/story")
	public String storyPage() {
		return "image/story";
	}
	
	@GetMapping("/image/popular")
	public String popularPage() {
		return "image/popular";
	}
	
	@GetMapping("/image/upload")
	public String uploadPage() {
		return "image/upload";
	}
	
	@GetMapping("/user/profile")
	public String profilePage() {
		return "user/profile";
	}

	@GetMapping("/user/update")
	public String updatePage() {
		return "user/update";
	}
	
	
	
	@GetMapping("/toolbar/toolbar")
	public String toolbarView() {
		return "main/toolbar";
	}
	
	  @GetMapping("/")
	  public String room() {
	    return "main/index";
	  }
	  
	  @GetMapping("/leftbar")
	  public String mypage() {
		    return "main/leftbar2";
		  }
	  
}
