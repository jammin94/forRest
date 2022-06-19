package com.mvc.forrest.controller.sms;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.auto.value.AutoAnnotation;
import com.mvc.forrest.service.sms.SmsService;

@RestController
@RequestMapping("/sms/*")
public class SmsRestController {

	@Autowired
	SmsService smsService;
	
	@RequestMapping("json/sendSMS")
	public int sendSMS(@RequestParam String phone) throws Exception {

		System.out.println("sms/json/sendSMS : GET");
    	
		Random rd = new Random();
		int authNum = rd.nextInt(888888)+111111;
		
		smsService.makeMassage(authNum, phone);
		
		return authNum;
	}
	
}
