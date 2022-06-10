package com.mvc.forrest.controller.common;

import static com.mvc.forrest.common.utils.RandomNumberGenerator.makeRandomOldNumber;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;



@Controller
@RequestMapping("/firebase/*")
public class FirebaseController {
	
	
	@GetMapping("message")
	public String getAccessToken() throws Exception{
		
		return "common/firebase";
	} 
	
	
	
}
