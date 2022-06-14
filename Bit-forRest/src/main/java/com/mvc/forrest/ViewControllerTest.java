package com.mvc.forrest;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;


import com.mvc.forrest.service.old.OldService;
import com.mvc.forrest.service.product.ProductService;

@Controller
public class ViewControllerTest {
	
	@Autowired
	public OldService oldService;
	
	@Autowired
	public ProductService productService;
	
	@GetMapping("/")
	public String room(Model model) {
		
//		list<Old> listOld = oldService.getOldListForIndex();
//		list<product> listProduct = productService.getProductListForIndex();
//		model.addAttribute("listOld", listOld);
//		model.addAttribute("listProd", listProduct);
		
		return "main/index";
	}
}
