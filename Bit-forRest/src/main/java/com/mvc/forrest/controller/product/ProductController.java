package com.mvc.forrest.controller.product;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.mvc.forrest.config.auth.LoginUser;
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
	
	
	
	//어드민만 접근가능
	@PostMapping("updateRecentImg")
	public String updateRecentImg() throws Exception {
	
		return null;
	}
	
	//회원, 어드민 가능
	@GetMapping("updateProduct")
	public String updateProductGet(@RequestParam("prodNo") String prodNo, Model model) throws Exception {
		
		System.out.println("updateProductGet start");
		
		Product product = productService.getProduct(prodNo);
		System.out.println("product:" + product);
		
		model.addAttribute("product", product);	
		
	
		return "product/updateProduct";
	}
	
	//이미지여러개 어케함???
	//회원, 어드민 가능
	@PostMapping("updateProduct")
	public String updateProductPost(@ModelAttribute("product") Product product) throws Exception {
		
		//디버깅
		System.out.println("updateProductPost start");
		System.out.println("product: "+product);
		
		productService.updateProduct(product);
	
		return "forward:/product/getProduct?prodNo="+product.getProdNo();
	}
	
	//회원, 어드민 가능
	@RequestMapping("updateProductCondition")
	public String updateProductCondition(@RequestParam("prodNo") String prodNo, @RequestParam("productCondition") String productCondition) throws Exception {
		
		System.out.println("updateProductCondition start");
		
		Product product = productService.getProduct(prodNo);
		
		System.out.println("prodNo:"+prodNo);
		System.out.println("productCondition"+productCondition);
		
		if(productCondition.equals("물품보관승인신청중")) {
			product.setProdCondition("입고중");
		} else if (productCondition.equals("입고중")){
			product.setProdCondition("보관중");
		} else if (productCondition.equals("출고승인신청중")){
			product.setProdCondition("출고완료");
		//보관관련
			
		} else if(productCondition.equals("물품대여승인신청중")) {
			product.setProdCondition("배송중");
		} else if(productCondition.equals("배송중")) {
			product.setProdCondition("대여중");
		}
		
		System.out.println("after:"+product.getProdCondition());
		
		productService.updateProductCondition(product);
	
		return "redirect:/storage/listStorageForAdmin";
	}
	
	//관리자가 물품상태를 일괄처리하기위한 코드
	//어드민만 가능
	@RequestMapping("updateProductAllCondition")
	public String updateProductAllCondition(@RequestParam("prodNo") String[] prodNo) throws Exception {
		
		//디버깅
		for(String no: prodNo) {
			System.out.println(no);
		}
		
		//prodNo를 통해 productCondition배열에 값을 셋팅
		String[] productCondition =  new String[prodNo.length];
		for(int i=0; i<prodNo.length; i++) {
			productCondition[i] = productService.getProduct(prodNo[i]).getProdCondition();
		}
		
		for(String proCon: productCondition) {
			System.out.println(proCon);
		}
		
		for(int i=0; i<prodNo.length; i++) {
			
			Product product = productService.getProduct(prodNo[i]);
	
			if(productCondition[i].equals("물품보관승인신청중")) {
				product.setProdCondition("입고중");
			} else if (productCondition[i].equals("입고중")){
				product.setProdCondition("보관중");
			} else if (productCondition[i].equals("출고승인신청중")){
				product.setProdCondition("출고완료");
			//보관관련
				
			} else if(productCondition[i].equals("물품대여승인신청중")) {
				product.setProdCondition("배송중");
			} else if(productCondition[i].equals("배송중")) {
				product.setProdCondition("대여중");
			}
			//대여관련
			
			productService.updateProductCondition(product);
		}
		
		
	
		return "redirect:/storage/listStorageForAdmin";
	}
	
	//회원, 어드민 가능
	@RequestMapping("getProduct")
	public String getProduct(@RequestParam("prodNo") String prodNo, Model model, HttpSession httpsession) throws Exception {
		
		//디버깅
		System.out.println("getProduct Start");
		
		Product product = productService.getProduct(prodNo);
		
		//암호화된 유저아이디를 받아옴
		LoginUser loginUser= (LoginUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userId= loginUser.getUser().getUserId();
		System.out.println("userId: "+userId);
		
		//TEST용: getProduct에서 물품주인과 구매자에 따라 다른화면출력 Test를 위한 세션생성	
		User sessionUser = userService.getUser(userId);
	
		//실제구현용: 세션아이디와 물품의 유저아이디가 일치할때 다른화면을 표시하기위한 코드
		//User sessionUser = (User) httpsession.getAttribute("user");

		model.addAttribute("product", product);
		model.addAttribute("sessionUser", sessionUser);
		
		return "product/getProduct";
	}
	
	//비회원가능
	@RequestMapping("listProduct")
	public String listProduct(@ModelAttribute("search") Search search, Model model) throws Exception {
		
		System.out.println("search: "+ search);
		
		//카테고리중 전체를 클릭했을때 서치카테고리의 value를 null로 만듬
		if(search.getSearchCategory()=="") {
			search.setSearchCategory(null);
		}
		
		if(search.getSearchKeyword()=="") {
			search.setSearchKeyword(null);
		}
		
		System.out.println("search2: "+ search);
		
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