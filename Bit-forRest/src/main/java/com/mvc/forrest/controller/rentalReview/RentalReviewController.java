package com.mvc.forrest.controller.rentalReview;


import java.text.SimpleDateFormat;
import java.util.Date;
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
import com.mvc.forrest.service.domain.RentalReview;
import com.mvc.forrest.service.domain.User;
import com.mvc.forrest.service.rentalreview.RentalReviewService;




@Controller
@RequestMapping("/rentalReview/*")
public class RentalReviewController {

		@Autowired
		public RentalReviewService rentalReviewService;
		
		//리뷰등록하기 view 화면 (네비게이션용)
		@GetMapping("addRentalReview")
		public String addRentalReviewView() throws Exception {
			 return "rentalReview/addRentalReview";
		}
		

		//------------대여물품add  view 화면 (네비게이션용) ------------//
//		@GetMapping("addRentalReview")
//		public String addRentalReView(@ModelAttribute("product") Product product, @ModelAttribute("user") User user, Model model ) throws Exception {
//			
			//0. RequestParam("tranNo") 는 i'm port 때문에 미리 받아야함 ( 아직 결정안남 )
			
			//0-1. couponService.getCouponList(user.getUserId());	쿠폰리스트 (대기)
			
			//1. 해당 product return 되었음
			//productService.getProduct(product.getProdNo());		
			//2. 해당 user return 되었음
			//userService.getUser(user.getUserId());
			
			//3. addRentalView.jsp에서 product와 user 정보 꺼내쓰면됨
//			model.addAttribute("product",product);
//			model.addAttribute("user",user);
//			
//			 return "rentalReview/addRentalReview";
//		}
		
		
		//리뷰등록하기 기능구현
		@PostMapping("addRentalReview")
		public String addRentalReview(@ModelAttribute("rentalReview") RentalReview rentalReview, Model model) throws Exception {
		
			rentalReview.setReviewImg("텐트.jpg");
			rentalReview.setProdNo(3); // 참고: 무결성제약조건 prodNo는 기존값 존재해야함
			rentalReview.setUserId("user01@naver.com"); // 참고: 무결성제약조건 userId는 기존값 존재해야함
			
			rentalReviewService.addRentalReview(rentalReview);
			
			System.out.println("렌탈리뷰 추가하기 테스트 중");
			
			 return null;
		}
		
		//리뷰 리스트 출력
		@GetMapping("listReview")
		public String listReviewView(@ModelAttribute("rentalReview") RentalReview rentalReview, Model model) throws Exception {
		
			 return null;
		}
		
		@PostMapping("listReview")
		public String listReview(@ModelAttribute("rentalReview") RentalReview rentalReview, Model model) throws Exception {
		
			 return null;
		}
		
		//리뷰 수정 ( 업데이트 )
		@GetMapping("updateReview")
		public String updateReviewView(@ModelAttribute("rentalReview") RentalReview rentalReview, Model model) throws Exception {
		
			 return null;
		}
		
		@PostMapping("updateReview")
		public String updateReview(@ModelAttribute("rentalReview") RentalReview rentalReview, Model model) throws Exception {
		
			 return null;
		}
		
		
		// 리뷰 삭제 
		@GetMapping("deleteReview")
		public String deleteReviewView(@ModelAttribute("rentalReview") RentalReview rentalReview, Model model) throws Exception {
		
			 return null;
		}
		
		@PostMapping("deleteReview")
		public String deleteReview(@ModelAttribute("rentalReview") RentalReview rentalReview, Model model) throws Exception {
		
			 return null;
		}
		
}