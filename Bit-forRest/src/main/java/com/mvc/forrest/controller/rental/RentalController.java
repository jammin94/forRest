package com.mvc.forrest.controller.rental;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mvc.forrest.common.utils.FileNameUtils;
import com.mvc.forrest.config.auth.LoginUser;
import com.mvc.forrest.service.coupon.CouponService;
import com.mvc.forrest.service.domain.Page;
import com.mvc.forrest.service.domain.Product;
import com.mvc.forrest.service.domain.Rental;
import com.mvc.forrest.service.domain.RentalReview;
import com.mvc.forrest.service.domain.Search;
import com.mvc.forrest.service.product.ProductService;
import com.mvc.forrest.service.rental.RentalService;
import com.mvc.forrest.service.user.UserService;
import com.mvc.forrest.service.wishlist.WishlistService;




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
	public WishlistService wishlistService;
	
	@Autowired
	public CouponService couponService;
	
//	@Autowired
//	public CouponService couponService;  ( 대기 )
	
	@Value("5")
	int pageUnit;
	
	
	
	
	
	//------------대여물품add 화면구현------------//
	//회원, 어드민 가능
	@GetMapping("addRental")
	public String addRentalView(@RequestParam("prodNo") String prodNo,
											  @RequestParam("period") int period,
											  Model model) throws Exception{
		
		//prodNo를 받아서 productService.getProduct 로 product객체를 받고, html에서 product.~~로 페이지내에서 사용
		System.out.println("prodNo: "+prodNo);
		System.out.println("period: "+period);
		Product product = productService.getProduct(prodNo);		
		
		//암호화된 유저아이디를 받아옴
		LoginUser loginUser= (LoginUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userId= loginUser.getUser().getUserId();
		System.out.println("userId: "+userId);  
		
		//회원의 보유쿠폰리스트를 받아옴
		Map<String,Object> map =couponService.getOwnCouponList(userId);
		
		//결제가 이루어지기전에 tranNo가 필요하기때문에 예비 tranNo를 생성 
	   String reserveTranNo = FileNameUtils.getRandomString();
		
		System.out.println("쿠폰쿠폰"+map.get("list"));
		
		model.addAttribute("product",product);
		model.addAttribute("list",map.get("list"));
		model.addAttribute("reserveTranNo", reserveTranNo);
		model.addAttribute("period",period);
		
		return "rental/addRental";
	}
	
	
	//------------대여물품add 기능구현------------//
	//회원, 어드민 가능
	@PostMapping("addRental")
	public String addRental(@ModelAttribute("rental") Rental rental,
							@ModelAttribute("product") Product product,
							@RequestParam("paymentNo") String paymentNo,
							Model model ) throws Exception {
		
	//	Product product = null;
	//	product = productService.getProduct(rental.getProdNo());	
//		userService.getUser(rental.getUserId());	  ( 대기 )	
		System.out.println("addRental Post Start");
		//0. i'm port에서 나온 값 + 화면상 입력값들 ModelAttribute("rental")에 담겨있음.
		
		System.out.println("텟");
		//암호화된 유저아이디를 받아옴
		LoginUser loginUser= (LoginUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userId= loginUser.getUser().getUserId();

		
		System.out.println("useriduid"+userId);
        //랜덤으로 생성한 tranNo
        String tranNo = FileNameUtils.getRandomString();
        
        System.out.println("tranNo"+tranNo);
        System.out.println("텟2");
        rental.setPickupAddress(rental.getPickupAddress());
        rental.setDivyAddress(rental.getDivyAddress());
        rental.setReceiverPhone(rental.getReceiverPhone());
        rental.setUserId(userId);
        rental.setPaymentNo(paymentNo); //임시 결제 번호
        rental.setProdName(product.getProdName()); 
        rental.setProdImg(rental.getProdImg()); //임시 프로덕트 이미지
        rental.setOriginPrice(10000); // 임시 오리진 프라이스
        rental.setProdNo(rental.getProdNo());
        rental.setDiscountPrice(rental.getDiscountPrice());
        rental.setTranNo(tranNo);
  		rental.setPeriod(3);
  		rental.setTranCode(1);
  		
  		
  		product.setProdNo(rental.getProdNo());
  		product.setProdCondition("물품대여승인신청중");
  		
        productService.updateProductCondition(product);
        
        rental.setPurchaseProd(product);
  
		System.out.println("텟3");
		//1. i'm port에서 나온 값 + 화면상 입력값들 transaction 테이블에 insert
		rentalService.addRental(rental);		
		
		System.out.println(rental);
		//2. getRental에서 쓰기위해 model을 통해 전달
		 model.addAttribute("rental", rentalService.getRental(rentalService.getPayment(paymentNo).get(0).getTranNo()));
			/* model.addAttribute("rental",rental); */
//		model.addAttribute("product",product);
//		model.addAttribute("user",user);
		System.out.println("텟4");
		//3. getRental.jsp 에서 model들 다 뽑아쓰면됨
		return "rental/getRental";
	}
	
	@PostMapping("addWishRental")
	public String addWishRental(@ModelAttribute("rental") Rental rental,
							@RequestParam("paymentNo") String paymentNo,
							@RequestParam("wishlistNo") int[] wishlistNo,
							@RequestParam("prodNo") String[] prodNo,
							@RequestParam("period") int[] period,
							Model model ) throws Exception {

		System.out.println("addWishRental Post Start");
		System.out.println(rental);
		
		//유저아이디셋팅
		LoginUser loginUser= (LoginUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userId= loginUser.getUser().getUserId();
        rental.setUserId(userId);
        

        //tranCode 대여된걸로 변경
        rental.setTranCode(1);
        //결제번호 박음
        rental.setPaymentNo(paymentNo); 
        
        String reserveTranNo = null;
  		
        
        //-------------------------------------------
        // 폼입력값 꺼는 modelAttribute를 통해 rental객체에 다 박힘
        //-------------------------------------------
       
        	Product product = new Product();
        // 배열로 들어온 prodNo, wishlistNo, period 는 반복문을 활용
		    List<Rental> listA = new ArrayList<Rental>();
		    
		    
	    	for(int i=0; i<prodNo.length;i++) {

	    	Rental rentalArray = new Rental();
	    	rentalArray.setUserId(userId);
	    	rentalArray.setTranCode(1);
	    	rentalArray.setPaymentNo(paymentNo);
	    	rentalArray.setReceiverName(rental.getReceiverName());
	    	rentalArray.setReceiverPhone(rental.getReceiverPhone());
	    	rentalArray.setDivyAddress(rental.getDivyAddress());
	    	rentalArray.setPickupAddress(rental.getPickupAddress());
	    	rentalArray.setDivyRequest(rental.getDivyRequest());
	    	rentalArray.setDiscountPrice(rental.getDiscountPrice());
	    	rentalArray.setOriginPrice(rental.getOriginPrice());
	    	rentalArray.setResultPrice(rental.getResultPrice());
	    	
	    	
	    	//물품대여승인신청중으로 상태변경
			product = productService.getProduct(prodNo[i]);	
			System.out.println(product);
	  		product.setProdCondition("물품대여승인신청중");
	  		productService.updateProductCondition(product); 
	  		System.out.println("한바퀴돔1");
	  		//rental에 product 정보담음 + 기간설정 + prodNo
	  		
	  		reserveTranNo=FileNameUtils.getRandomString();
	  		
	  		rentalArray.setTranNo(reserveTranNo);
	  		rentalArray.setPurchaseProd(product);
	  		rentalArray.setProdName(product.getProdName());
	  		rentalArray.setProdImg(product.getProdImg());
	  		rentalArray.setPeriod(period[i]);
	  		rentalArray.setProdNo(prodNo[i]);
			System.out.println("한바퀴돔2");
			//렌탈에 add
			// => "rental" 은 폼양식박힌 + 반복문돌려진 product + 반복문 돌려진 period
			// =>  이 "rental"을 listA 에 담음
			rentalService.addRental(rentalArray);
			System.out.println("돔3위"+rentalArray);
			System.out.println("한바퀴돔3");
			listA.add(i,rentalArray); 
			System.out.println("한바퀴돔4");
			//장바구니 목록 삭제
			wishlistService.deleteWishlist(wishlistNo[i]);
			System.out.println("장바구니밑"+listA);
	    	}
		
	    System.out.println(" success ! ");
	
	    //list에서 사용할 rental
		model.addAttribute("rentalList",listA);
		//하단정보에 사용할 rental
		model.addAttribute("rental",rental);
		
		System.out.println("listAAA"+listA);
		System.out.println(" success !! ");
		
		return "rental/getWishRental";
	}
	
	
	//------------결제완료 상세 화면------------//
	//회원, 어드민 가능
	@GetMapping("getRental")
	public String getRental(@RequestParam("tranNo") String tranNo, Model model) throws Exception {
		System.out.println("getRental Start");
		System.out.println("tranNo: "+tranNo);
		
		model.addAttribute("rental", rentalService.getRental(tranNo));
		
		 return "rental/getRental";
	}
	
	//회원, 어드민 가능
	@GetMapping("getPayment")
	public String getPayment(@RequestParam("paymentNo") String paymentNo, Model model) throws Exception {
		System.out.println("getPayment Start");
		System.out.println("paymentNo: "+paymentNo);
		 
		model.addAttribute("payment", rentalService.getPayment(paymentNo));
		 model.addAttribute("rental", rentalService.getRental(rentalService.getPayment(paymentNo).get(0).getTranNo()));
		 return "rental/getRental";
	}
	

	
	//------------대여물품리스트 화면------------//
	//비회원,회원, 어드민 가능
	@RequestMapping("listRental")
	public String listProductView(@ModelAttribute("search") Search search,@ModelAttribute("rentalReview") RentalReview rentalReview, HttpSession httpSession, Model model) throws Exception{
		
		System.out.println("1111");
		
		if (search.getCurrentPage() == 0) {
			search.setCurrentPage(1);
		}
		
		int pageSize = 5;
		
		search.setPageSize(pageSize);
		
		System.out.println("search:"+search);
				
		//암호화된 유저아이디를 받아옴
		LoginUser loginUser= (LoginUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userId= loginUser.getUser().getUserId();

		Map<String, Object> map = new HashMap<>();
		map.put("search", search);
		map.put("userId", userId);
		
		Map<String, Object> mapRental = rentalService.getRentalList(map);
		rentalService.getPaymentList(search, userId);
		
		Page resultPage = new Page(search.getCurrentPage(), ((Integer)mapRental.get("totalCount")).intValue(), pageUnit, pageSize );
		System.out.println(rentalService.getPaymentList(search, userId));
		//System.out.println("디버그 "+mapStorage.get("list"));
		
		//rentalReview.setReviewImg(userId);
		
		model.addAttribute("list", rentalService.getPaymentList(search, userId));
		model.addAttribute("resultPage", resultPage);
		model.addAttribute("search", search);
		model.addAttribute("rentalReview",rentalReview);
		
		System.out.println("resultPage:"+resultPage);
		
		return "rental/listRental";
	}
	
	
	//------------대여물품리스트 관리자 화면------------//
	//어드민 가능
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
		
		int pageSize = 10;
		
		search.setPageSize(pageSize);
		
		Map<String, Object> map = rentalService.getRentalListForAdmin(search);
		Page resultPage = new Page(search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize );
		
	
		
		model.addAttribute("list", map.get("list"));
		model.addAttribute("resultPage", resultPage);
		model.addAttribute("search", search);
		
		System.out.println("list보기"+map.get("list"));
		
		
		return "rental/listRentalForAdmin";
	}
	
	//------------대여 수익 확인------------//
	//회원, 어드민가능 
	@GetMapping("listRentalProfit")
	public String listRentalProfitView( @ModelAttribute("search") Search search , Model model,HttpSession session) throws Exception{
		
		System.out.println("listRentalProfitView 테스트");
		
		if(search.getCurrentPage() ==0 ){
			search.setCurrentPage(1);
		}
		
		int pageSize = 10;
		
		search.setPageSize(pageSize);
		System.out.println("search:"+search);
		
		//암호화된 유저아이디를 받아옴
		LoginUser loginUser= (LoginUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userId= loginUser.getUser().getUserId();
		//System.out.println(userId);
		Map<String, Object> mapRental = rentalService.getRentalListProfit(search,userId);
		
		int profitTotal = rentalService.getRentalProfitTotal(userId);
		
		//System.out.println(mapRental);
		Page resultPage = new Page(search.getCurrentPage(), ((Integer)mapRental.get("totalCount")).intValue(), pageUnit, pageSize );
					
		// Model 과 View 연결
		//System.out.println( mapRental.get("list"));
		
		model.addAttribute("list", mapRental.get("list"));
		model.addAttribute("resultPage", resultPage);
		model.addAttribute("search", search);
		model.addAttribute("profitTotal",profitTotal);
		
		System.out.println("resultPage:"+resultPage);
		
		return "rental/listRentalProfit";
	}
	

	
	
	
}