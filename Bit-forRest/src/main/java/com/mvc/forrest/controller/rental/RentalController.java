package com.mvc.forrest.controller.rental;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


import com.mvc.forrest.service.domain.Product;
import com.mvc.forrest.service.domain.Rental;
import com.mvc.forrest.service.domain.RentalReview;
import com.mvc.forrest.service.domain.User;
import com.mvc.forrest.service.product.ProductService;
import com.mvc.forrest.service.rental.RentalService;
import com.mvc.forrest.service.rentalreview.RentalReviewService;
import com.mvc.forrest.service.user.UserService;




//==> ȸ������ Controller
@Controller
@RequestMapping("/rental/*")
public class RentalController {

	@Autowired
	public RentalService rentalService;
	
	@Autowired
	public UserService userService;
	
	@Autowired
	public ProductService productService;
	
//	@Autowired
//	public CouponService couponService;  ( 대기 )
	
	
	
	//------------대여물품add  view 화면 (네비게이션용) ------------//
	@GetMapping("addRental")
	public String addRentalView(@ModelAttribute("product") Product product, @ModelAttribute("user") User user, Model model ) throws Exception {
		
		//0. RequestParam("tranNo") 는 i'm port 때문에 미리 받아야함 ( 아직 결정안남 )
		
		//0-1. couponService.getCouponList(user.getUserId());	쿠폰리스트 (대기)
		
		//1. 해당 product return 되었음
		//productService.getProduct(product.getProdNo());		
		//2. 해당 user return 되었음
		//userService.getUser(user.getUserId());
		
		//3. addRentalView.jsp에서 product와 user 정보 꺼내쓰면됨
		model.addAttribute("product",product);
		model.addAttribute("user",user);
		
		 return "rentalReview/addRentalReview";
	}
	
	//------------대여물품add 기능구현------------//
	@PostMapping("addRental")
	public String addRental(@ModelAttribute("rental") Rental rental, Model model ) throws Exception {
		
		Product product = null;
		product = productService.getProduct(rental.getProdNo());	
//		userService.getUser(rental.getUserId());	  ( 대기 )	
		
		//0. i'm port에서 나온 값 + 화면상 입력값들 ModelAttribute("rental")에 담겨있음.
		
		//1. i'm port에서 나온 값 + 화면상 입력값들 transaction 테이블에 insert
		rentalService.addRental(rental);		
		
		//2. getRental에서 쓰기위해 model을 통해 전달
		model.addAttribute("rental",rental);
		model.addAttribute("product",product);
//		model.addAttribute("user",user);
		
		//3. getRental.jsp 에서 model들 다 뽑아쓰면됨
		 return "rental/getRental";
	}
	
	
	//------------결제완료 상세 화면------------//
	@GetMapping("getRental")
	public String getRental(@RequestParam("tranNo") int tranNo) throws Exception {
	
		 return "rental/getRental";
	}
	
	
	//------------대여물품리스트 화면------------//
	@GetMapping("listRental")
	public String listProductView( ) throws Exception{
		
		return null;
	}
	
	@PostMapping("listRental")
	public String listProduct( ) throws Exception{
		
		return null;
	}
	
	//------------대여물품리스트 관리자 화면------------//
	@GetMapping("listRentalMng")
	public String listRentalMngView( ) throws Exception{
		
		return null;
	}
	
	@PostMapping("listRentalMng")
	public String listRentalMng( ) throws Exception{
		
		return null;
	}
	
	//------------대여 수익 확인------------//
	@GetMapping("listRentalProfit")
	public String listRentalProfitView( ) throws Exception{
		
		return null;
	}
	
	@PostMapping("listRentalProfit")
	public String listRentalProfit( ) throws Exception{
		
		return null;
	}
	
	
	
	
}