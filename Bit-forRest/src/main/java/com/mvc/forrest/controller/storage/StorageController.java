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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.mvc.forrest.service.domain.Product;
import com.mvc.forrest.service.domain.Storage;
import com.mvc.forrest.service.domain.User;
import com.mvc.forrest.service.product.ProductService;
import com.mvc.forrest.service.storage.StorageService;
import com.mvc.forrest.service.user.UserService;





@Controller
@RequestMapping("/storage/*")
public class StorageController {
	
	@Autowired
	public StorageService storageService ;
	
	@Autowired
	public ProductService productService ;
	
	@Autowired
	public UserService userService;
	
	public StorageController() {
		System.out.println(this.getClass());
	}
	
	//보관 메인화면 단순 네비게이션
	@GetMapping("storageMain")
	public String storageMain() throws Exception  {
		
		return "storage/storageMain";
	}
	
	@GetMapping("addStorage")
	public String addStorageGet(Model model, HttpSession session) throws Exception {
		
		String userId = ((User) session.getAttribute("user")).getUserId();
		model.addAttribute("user", userService.getUser(userId));
		
		return "storage/addStorage";
	}
	
	//검토필요
	@PostMapping("addStorage")
	public String addStoragePost(@RequestParam("imp_uid") int imp_uid,
												@RequestParam("merchant_uid") String merchant_uid,
												@ModelAttribute("product") Product product,
												@ModelAttribute("storage") Storage storage,
												HttpSession session, Model model) throws Exception {

		product.setUserId(((User) session.getAttribute("user")).getUserId());
		productService.addProduct(product);
		
		storage.setPaymentNo(merchant_uid);
		storage.setTranNo(imp_uid);
		
		storageService.addStorage(storage);
		
		model.addAttribute("product", product);
		model.addAttribute("storage", storage);
		
		return "storage/getStorage";
	}
	
	@RequestMapping("listStorage")
	public String listStorage() {
		
		return null;
	}
	
	@RequestMapping("listStorageForAdmin")
	public String listStorageForAdmin() {
		
		return null;
	}
		
	@GetMapping("extendStorage")
	public String extendStorageGet() {
		
		return null;
	}
	
	@PostMapping("extendStorage")
	public String extendStoragePost() {
		
		return null;
	}
	
	@GetMapping("getStorage")
	public String getStorage() {
		
		return null;
	}

}