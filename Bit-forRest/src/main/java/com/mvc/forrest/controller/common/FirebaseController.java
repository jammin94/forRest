package com.mvc.forrest.controller.common;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mvc.forrest.service.domain.Old;
import com.mvc.forrest.service.domain.Search;
import com.mvc.forrest.service.old.OldService;

@Controller
@RequestMapping("/firebase/*")
public class FirebaseController {

	
	
	@GetMapping("message")
	public String getAccessToken() throws Exception{

		
		return "common/firebase";
	} 
	
	
	
}
