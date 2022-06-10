package com.mvc.forrest.controller.common;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;


import com.mvc.forrest.common.utils.RandomNumberGenerator;


@Controller
@RequestMapping("/firebase/*")
public class FirebaseController {
	

	
	@GetMapping("message")
	public String getAccessToken() throws Exception{
		
//		int rng1 =rng.makeRandomOldNumber();
//		System.out.println(rng1);
		return "common/firebase";
	} 
	
	
	
}
