package com.mvc.forrest.controller.product;

import java.util.List;
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

import com.google.api.client.http.HttpRequest;
import com.mvc.forrest.common.utils.FileUtils;
import com.mvc.forrest.config.auth.LoginUser;
import com.mvc.forrest.service.domain.Img;
import com.mvc.forrest.service.domain.Old;
import com.mvc.forrest.service.domain.Page;
import com.mvc.forrest.service.domain.Product;
import com.mvc.forrest.service.domain.Search;
import com.mvc.forrest.service.domain.User;
import com.mvc.forrest.service.product.ProductService;
import com.mvc.forrest.service.rentalreview.RentalReviewService;
import com.mvc.forrest.service.user.UserService;





@Controller
@RequestMapping("/product/*")
public class ProductController {
	
	@Autowired
	public ProductService productService;
	
	@Autowired
	public UserService userService;
	
	@Autowired
	public RentalReviewService rentalReviewService;
	
	@Autowired
	public FileUtils fileUtils;
	
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
	
		return "forward:/product/getProduct";
	}
	
	//어드민 가능
	// 관리자가 물품의 상태변경 ( 보관 )
	@RequestMapping("updateProductCondition")
	public String updateProductCondition(@RequestParam("prodNo") String prodNo) throws Exception {
		Product product = productService.getProduct(prodNo);		
		
		if(product.getProdCondition().equals("물품보관승인신청중")) {
			product.setProdCondition("입고중");
		} else if (product.getProdCondition().equals("입고중")){
			product.setProdCondition("보관중");
		} else if (product.getProdCondition().equals("출고승인신청중")){
			product.setProdCondition("출고완료");
		//보관관련
		} 
		
		System.out.println("after:"+product.getProdCondition());
		
		productService.updateProductCondition(product);
	
		return "redirect:/storage/listStorageForAdmin";
	}
	
	//어드민 가능
		// 관리자가 물품의 상태변경 ( 대여 )
		@RequestMapping("updateRentalProductCondition")
		public String updateRentalProductCondition(@RequestParam("prodNo") String prodNo) throws Exception {
			Product product = productService.getProduct(prodNo);		

			//대여관련
			 if(product.getProdCondition().equals("물품대여승인신청중")) {
				product.setProdCondition("배송중");
			} else if(product.getProdCondition().equals("배송중")) {
				product.setProdCondition("대여중");
			}
			
			
			System.out.println("after:"+product.getProdCondition());
			
			productService.updateProductCondition(product);
		
			return "redirect:/rental/listRentalForAdmin";
		}
	
	//어드민만 가능
	//관리자가 물품상태를 일괄변경
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
			}
			//보관관련
	
			productService.updateProductCondition(product);
		}
		
		
	
		return "redirect:/storage/listStorageForAdmin";
	}
	
		//어드민만 가능
		//관리자가 물품상태를 일괄변경 ( 대여 )
		@RequestMapping("updateRentalProductAllCondition")
		public String updateRentalProductAllCondition(@RequestParam("prodNo") String[] prodNo) throws Exception {
			
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
		
				//대여관련
				
				if(productCondition[i].equals("물품대여승인신청중")) {
					product.setProdCondition("배송중");
				} else if(productCondition[i].equals("배송중")) {
					product.setProdCondition("대여중");
				}
			
				
				productService.updateProductCondition(product);
			}
			
			
		
			return "redirect:/rental/listRentalForAdmin";
		}
	
	//유저가 물품보관승인신청을 취소하거나 보관중인 물품을 되돌려받을때 사용
	@RequestMapping("cancleProduct")
	public String cancleProduct (@RequestParam("prodNo") String prodNo) throws Exception {
		
		Product product = productService.getProduct(prodNo);
		
		if(product.getProdCondition().equals("물품보관승인신청중")) {
			product.setProdCondition("취소완료");
		}
		
		if(product.getProdCondition().equals("보관중")) {
			product.setProdCondition("출고승인신청중");
		}
		
		productService.updateProductCondition(product);
		
		
		return "redirect:/storage/listStorage";
	}
	
	
	//회원, 어드민 가능
	@RequestMapping("getProduct")
	public String getProduct(@RequestParam("prodNo") String prodNo, Model model) throws Exception {
		
		//디버깅
		System.out.println("getProduct Start");
		
		System.out.println(prodNo);
		
		Product product = productService.getProduct(prodNo);
		
		//암호화된 유저아이디를 받아옴
		LoginUser loginUser= (LoginUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userId= loginUser.getUser().getUserId();
		System.out.println("userId: "+userId);
		
		//getProduct에서 물품주인과 구매자에 따라 다른화면출력을 위한 유저정보
		User sessionUser = userService.getUser(userId);
		
		//물품상세보기에서 리뷰리스트를 받아옴
		Map<String, Object> map = rentalReviewService.getRentalReviewList(prodNo);
		//System.out.println("map안에 list출력:"+map.get("list"));
		
		 List<Img> imglist = fileUtils.getProductImgList(prodNo);
		 System.out.println(imglist);
		
		model.addAttribute("product", product);
		model.addAttribute("sessionUser", sessionUser);
		model.addAttribute("list", map.get("list"));
		model.addAttribute("imglist", imglist);
		
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
	
	@RequestMapping("listProductAfterLogin")
	public String listProductAfterLogin(@ModelAttribute("search") Search search, Model model, HttpRequest httpRequest)
			throws Exception {

		System.out.println(this.getClass());
		
		if(search.getSearchCategory()=="") {
			search.setSearchCategory(null);
		}
		
		if(search.getSearchKeyword()=="") {
			search.setSearchKeyword(null);
		}
		
		if(search.getCurrentPage()==0) {
			search.setCurrentPage(1);
		}
		search.setPageSize(pageSize);

		LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userId = loginUser.getUser().getUserId();
		
		List<Product> list = productService.getProductListHasUser(search, userId);
		
		System.out.println(list);
		model.addAttribute("list", list);
		model.addAttribute("search", search);

		return "product/listProduct";
	}

}