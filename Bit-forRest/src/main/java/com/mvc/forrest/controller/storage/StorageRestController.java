package com.mvc.forrest.controller.storage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mvc.forrest.service.domain.Storage;
import com.mvc.forrest.service.storage.StorageService;

@RestController
@RequestMapping("/storage/json/*")
public class StorageRestController {
	
	@Autowired
	public StorageService storageService ;
	
	public StorageRestController() {
		System.out.println(this.getClass());
	} 
	
	//보관물품의 기간을 연장
	//회원, 어드민 가능
	@PostMapping("extendStorage")
	public boolean extendStoragePost(@RequestBody Storage storage,
													@RequestParam("paymentNo") String paymentNo,
													Model model) throws Exception {
		
		//디버깅
		System.out.println("extendStorage Post Start");
		System.out.println("storage: "+storage);
		System.out.println("paymentNo:"+paymentNo);
	
		
		//기존에 보관한 물품에 변경되는 정보를 업데이트
		storage.setPaymentNo(paymentNo);
		storageService.updateStorage(storage);
	
		model.addAttribute("storage", storageService.getStorage(storage.getTranNo()));
		
		return true;
	}
	

}