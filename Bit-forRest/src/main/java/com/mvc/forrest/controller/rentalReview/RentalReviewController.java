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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.mvc.forrest.service.domain.RentalReview;
import com.mvc.forrest.service.rentalreview.RentalReviewService;




//==> ȸ������ Controller
@Controller
@RequestMapping("/rentalReview/*")
public class RentalReviewController {

		@Autowired
		public RentalReviewService rentalReviewService;
		
		//리뷰등록하기 view 화면 (네비게이션용)
		@RequestMapping( value="addRentalReviewView", method=RequestMethod.GET )
		public String addRentalReviewView() throws Exception {
			 return "rentalReview/addRentalReview";
		}
		
		//리뷰등록하기 기능구현
		@RequestMapping( value="addRentalReview", method=RequestMethod.POST )
		public String addRentalReview(@ModelAttribute("rentalReview") RentalReview rentalReview, Model model) throws Exception {
		
			rentalReview.setReviewImg("텐트.jpg");
			rentalReview.setProdNo(3); // 참고: 무결성제약조건 prodNo는 기존값 존재해야함
			rentalReview.setUserId("user01@naver.com"); // 참고: 무결성제약조건 userId는 기존값 존재해야함
			
			rentalReviewService.addRentalReview(rentalReview);
			
			System.out.println("렌탈리뷰 추가하기 테스트 중");
			
			 return null;
		}
}