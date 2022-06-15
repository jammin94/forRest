package com.mvc.forrest.controller.rental;

import java.util.HashMap;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.mvc.forrest.common.utils.FileNameUtils;
import com.mvc.forrest.config.auth.LoginUser;
import com.mvc.forrest.service.coupon.CouponService;
import com.mvc.forrest.service.domain.Coupon;
import com.mvc.forrest.service.domain.Page;
import com.mvc.forrest.service.domain.Product;
import com.mvc.forrest.service.domain.Rental;
import com.mvc.forrest.service.domain.RentalReview;
import com.mvc.forrest.service.domain.Search;
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
	
	@Autowired
	public CouponService couponService;
	
//	@Autowired
//	public CouponService couponService;  ( 대기 )
	
	@Value("5")
	int pageUnit;
	
	@Value("10")
	int pageSize;
	
	
	
	//------------대여물품add 화면구현------------//
	@GetMapping("addRental")
	public String addRentalView(@ModelAttribute("search") Search search,@RequestParam("prodNo") String prodNo, Model model) throws Exception{
		
		//prodNo를 받아서 productService.getProduct 로 product객체를 받고, html에서 product.~~로 페이지내에서 사용
		Product product = productService.getProduct(prodNo);		
		
		//암호화된 유저아이디를 받아옴
		LoginUser loginUser= (LoginUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userId= loginUser.getUser().getUserId();
		System.out.println("userId: "+userId);
		
		//회원의 보유쿠폰리스트를 받아옴
		Map<String,Object> map =couponService.getOwnCouponList(userId);
		
		System.out.println("쿠폰쿠폰"+map.get("list"));
		
		model.addAttribute("product",product);
		model.addAttribute("list",map.get("list"));
		
		return "rental/addRental";
	}
	
	
	//------------대여물품add 기능구현------------//
	@PostMapping("addRental")
	public String addRental(@ModelAttribute("rental") Rental rental, @ModelAttribute("product") Product product,Model model ) throws Exception {
		
	//	Product product = null;
	//	product = productService.getProduct(rental.getProdNo());	
//		userService.getUser(rental.getUserId());	  ( 대기 )	
		System.out.println("addRental Post Start");
		//0. i'm port에서 나온 값 + 화면상 입력값들 ModelAttribute("rental")에 담겨있음.
		
		
		//암호화된 유저아이디를 받아옴
		LoginUser loginUser= (LoginUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userId= loginUser.getUser().getUserId();

        //랜덤으로 생성한 tranNo
        String tranNo = FileNameUtils.getRandomString();
        
        System.out.println("tranNo"+tranNo);
        
        rental.setPaymentNo("100"); //임시 결제 번호
        rental.setProdName(product.getProdName()); 
        rental.setProdImg("2.jpg"); //임시 프로덕트 이미지
        rental.setOriginPrice(10000); // 임시 오리진 프라이스
        
        rental.setTranNo(tranNo);
		rental.setPeriod(3);
        
		//1. i'm port에서 나온 값 + 화면상 입력값들 transaction 테이블에 insert
		rentalService.addRental(rental);		
		
		//2. getRental에서 쓰기위해 model을 통해 전달
		model.addAttribute("rental",rental);
//		model.addAttribute("product",product);
//		model.addAttribute("user",user);
		
		//3. getRental.jsp 에서 model들 다 뽑아쓰면됨
		 return null;
	}
	
	
	//------------결제완료 상세 화면------------//
//	public String getRental(@RequestParam("tranNo") int tranNo) throws Exception {
	@GetMapping("getRental")
	public String getRental() throws Exception {
		System.out.println("형산");
		 return "rental/getRental";
	}
	
	
	//------------대여물품리스트 화면------------//
	@GetMapping("listRental")
	public String listProductView(@ModelAttribute("search") Search search, HttpSession httpSession, Model model) throws Exception{
		
		if (search.getCurrentPage() == 0) {
			search.setCurrentPage(1);
		}
		
		search.setPageSize(pageSize);
				
		//암호화된 유저아이디를 받아옴
		LoginUser loginUser= (LoginUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userId= loginUser.getUser().getUserId();

		
		Map<String, Object> map = new HashMap<>();
		map.put("search", search);
		map.put("userId", userId);
		
		Map<String, Object> mapRental = rentalService.getRentalList(map);
		
		Page resultPage = new Page(search.getCurrentPage(), ((Integer)mapRental.get("totalCount")).intValue(), pageUnit, pageSize );
		
		//System.out.println("디버그 "+mapStorage.get("list"));
		
		model.addAttribute("list", mapRental.get("list"));
		model.addAttribute("resultPage", resultPage);
		model.addAttribute("search", search);
		
		return "rental/listRental";
	}
	
	@PostMapping("listRental")
	public String listProduct( ) throws Exception{
		
		return null;
	}
	
	//------------대여물품리스트 관리자 화면------------//
	@RequestMapping("listRentalForAdmin")
	public String listRentalForAdmin(@ModelAttribute("search") Search search, Model model) throws Exception {
		
		if (search.getCurrentPage() == 0) {
			search.setCurrentPage(1);
		}
		
		//더좋은 방법이 있을듯
		//전체 보관물품을 볼때 SearchProductCondition을 null로 만들기위한코드
		if(search.getSearchProductCondition() == "") {
			search.setSearchProductCondition(null);
		}
		
		if(search.getSearchKeyword() == "") {
			search.setSearchKeyword(null);
		}
		
		if(search.getSearchCondition() == "") {
			search.setSearchCondition(null);
		}
		
		//디버깅
		System.out.println("serarch in RentalController:" + search);
		
		search.setPageSize(pageSize);
		
		Map<String, Object> map = rentalService.getRentalListForAdmin(search);
		Page resultPage = new Page(search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize );
		
	
		
		model.addAttribute("list", map.get("list"));
		model.addAttribute("resultPage", resultPage);
		model.addAttribute("search", search);
		
		return "rental/listRentalForAdmin";
	}
	
	//------------대여 수익 확인------------//
	@GetMapping("listRentalProfit")
	public String listRentalProfitView( @ModelAttribute("search") Search search , Model model,HttpSession session) throws Exception{
		
		System.out.println("listRentalProfitView 테스트");
		
		if(search.getCurrentPage() ==0 ){
			search.setCurrentPage(1);
		}
		search.setPageSize(pageSize);
		
		//암호화된 유저아이디를 받아옴
		LoginUser loginUser= (LoginUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userId= loginUser.getUser().getUserId();
		System.out.println(userId);
		Map<String, Object> mapRental = rentalService.getRentalListProfit(search,userId);
		System.out.println(mapRental);
		Page resultPage = new Page(search.getCurrentPage(), ((Integer)mapRental.get("totalCount")).intValue(), pageUnit, pageSize );
		
		// Model 과 View 연결
		
		model.addAttribute("list", mapRental.get("list"));
		model.addAttribute("resultPage", resultPage);
		model.addAttribute("search", search);
		
		return "rental/listRentalProfit";
	}
	
	
	@PostMapping("listRentalProfit")
	public String listRentalProfit( ) throws Exception{
		
		return null;
	}
	

	
	
	
	
}