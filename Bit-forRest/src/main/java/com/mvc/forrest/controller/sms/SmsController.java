package com.mvc.forrest.controller.sms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.auto.value.AutoAnnotation;
import com.mvc.forrest.service.sms.SmsService;

@Controller
@RequestMapping("/sms/*")
public class SmsController {

	@Autowired
	SmsService smsService;
	
	@RequestMapping("sendSMS")
	public String sendSMS(@RequestParam String phone) throws Exception {
		
		String timestamp = Long.toString(System.currentTimeMillis());
		System.out.println("컨트롤러 타임스탬프 : "+timestamp);
		smsService.makeMassage();
		System.out.println("sendSMS END");
		
		return "user/addUser";
	}
	
}
