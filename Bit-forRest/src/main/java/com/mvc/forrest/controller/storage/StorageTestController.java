package com.mvc.forrest.controller.storage;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.mvc.forrest.service.domain.Storage;
import com.mvc.forrest.service.storage.StorageService;





@Controller
@RequestMapping("/storage/*")
public class StorageTestController {
	
	@Autowired
	public StorageService storageService ;
	
	@GetMapping("/addStorageTest")
	public String addStorageTest() {
		return "storage/addStorage";
	}
	
	@RequestMapping("addStorage")
	public String addStorage(@ModelAttribute("storage") Storage storage) throws Exception {
		
		storage.setTranNo(5);
		storage.setUserId("user05@naver.com");
		storage.setProdNo(3);
		storage.setDivyAddress("신흥1");
		storage.setPickupAddress("신흥2");
		storage.setPeriod(30);
		storage.setPaymentNo("imp-0132");
		storage.setPaymentWay("이니시스");
		storage.setReceiverPhone("1234");
		storage.setReceiverName("구범");
		storage.setProdName("test");
		storage.setProdImg("13.jpg");
		storage.setOriginPrice(5000);
		storage.setDiscountPrice(100);
		storage.setResultPrice(4000);
		
		storageService.addStorage(storage);
		
		
		return null;
	}
	
	@GetMapping("/getStorageTest")
	public String getStorageTest() {
		return "storage/getStorage";
	}
	
	@RequestMapping("getStorage")
	public String getStorage(@ModelAttribute("storage") Storage storage) throws Exception {
		
	System.out.println("getStorageTest"+storageService.getStorage(3));	
		
		return null;
	}
	
	@GetMapping("/storage/updateStorageTest")
	public String updateStorageTest() {
		return "storage/extendStorage";
	}
	
	@RequestMapping("updateStorage")
	public String updateStorage(@ModelAttribute("storage") Storage storage) throws Exception {
		
		System.out.println("updateStorageTest");
		storage = storageService.getStorage(1);
		
		storage.setPeriod(30);
		storage.setPaymentNo("imp-test");
		storage.setPaymentWay("test");
		storage.setOriginPrice(5000);
		storage.setDiscountPrice(1000);
		storage.setResultPrice(4000);
		
		storageService.updateStorage(storage);
		
		System.out.println(storage);
		
		return null;
	}
	
	
}