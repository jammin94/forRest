package com.mvc.forrest.controller.product;

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

import com.mvc.forrest.service.domain.Page;
import com.mvc.forrest.service.domain.Product;
import com.mvc.forrest.service.domain.Search;
import com.mvc.forrest.service.domain.User;
import com.mvc.forrest.service.product.ProductService;
import com.mvc.forrest.service.user.UserService;





@Controller
@RequestMapping("/product/*")
public class ProductController {
	
	@Autowired
	public ProductService productService;
	
	@Autowired
	public UserService userService;
	
	public ProductController() {
		System.out.println(this.getClass());
	}
	
	@Value("5")
	int pageUnit;
	
	@Value("10")
	int pageSize;
	
	
	
		
	@PostMapping("updateRecentImg")
	public String updateRecentImg() throws Exception {
	
		return null;
	}
	
	@GetMapping("updateProduct")
	public String updateProductGet(@RequestParam("prodNo") int prodNo, Model model) throws Exception {
		
		System.out.println("updateProductGet start");
		
		Product product = productService.getProduct(prodNo);
		System.out.println("product:" + product);
		
		model.addAttribute("product", product);	
		
	
		return "product/updateProduct";
	}
	
	//이미지여러개 어케함???
	@PostMapping("updateProduct")
	public String updateProductPost(@ModelAttribute("product") Product product) throws Exception {
		
		//디버깅
		System.out.println("updateProductPost start");
		System.out.println("product: "+product);
		
		productService.updateProduct(product);
	
		return null;
	}
	
	@RequestMapping("updateProductCondition")
	public String updateProductCondition(@RequestParam("prodNo") int prodNo, @RequestParam("productCondition") String productCondition) throws Exception {
		
		Product product = productService.getProduct(prodNo);
		productService.getProduct(prodNo).setProdCondition(productCondition);
		
		productService.updateProduct(product);
	
		return "redirect: /storage/listStorageForAdmin";
	}
	
	
	@GetMapping("getProduct")
	public String getProduct(@RequestParam("prodNo") int prodNo, Model model, HttpSession httpsession) throws Exception {
		
		Product product = productService.getProduct(prodNo);
		
		//TEST용: getProduct에서 물품주인과 구매자에 따라 다른화면출력 Test를 위한 세션생성	
		User sessionUser = userService.getUser("user01@naver.com");
		httpsession.setAttribute("sessionUser", sessionUser);
		
		//실제구현용: 세션아이디와 물품의 유저아이디가 일치할때 다른화면을 표시하기위한 코드
		//User sessionUser = (User) httpsession.getAttribute("user");
		
		model.addAttribute("product", product);
		model.addAttribute("sessionUser", sessionUser);
		
		return "product/getProduct";
	}
	
	@RequestMapping("listProduct")
	public String listProduct(@ModelAttribute("search") Search search, Model model) throws Exception {
		
		if(search.getCurrentPage()==0) {
			search.setCurrentPage(1);
		}
		search.setPageSize(pageSize);
		
		Map<String, Object> map = productService.getProductList(search);
		
		Page resultPage = new Page( search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		
		model.addAttribute("list", map.get("list"));
		model.addAttribute("resultPage", resultPage);
		model.addAttribute("search", search);
		
		return "product/listProduct";
	}

}