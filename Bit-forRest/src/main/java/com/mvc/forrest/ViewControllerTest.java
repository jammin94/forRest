package com.mvc.forrest;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.mvc.forrest.service.domain.Old;
import com.mvc.forrest.service.domain.Product;
import com.mvc.forrest.service.old.OldService;
import com.mvc.forrest.service.product.ProductService;

@Controller
public class ViewControllerTest {
	
	@Autowired
	public OldService oldService;
	
	@Autowired
	public ProductService productService;
	
	@GetMapping("/")
	public String room(Model model) throws Exception {
		
		List<Old> listOld = oldService.getOldListForIndex();
		List<Product> listProduct = productService.getProductListForIndex();
		model.addAttribute("listOld", listOld);
		model.addAttribute("listProd", listProduct);
		
		return "main/index";
	}
}
