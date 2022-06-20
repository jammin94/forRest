package com.mvc.forrest.controller.rentalReview;


import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mvc.forrest.common.utils.FileNameUtils;
import com.mvc.forrest.config.auth.LoginUser;
import com.mvc.forrest.service.domain.Product;
import com.mvc.forrest.service.domain.Rental;
import com.mvc.forrest.service.domain.RentalReview;
import com.mvc.forrest.service.domain.User;
import com.mvc.forrest.service.rental.RentalService;
import com.mvc.forrest.service.rentalreview.RentalReviewService;




@RestController
@RequestMapping("/restRentalReview/*")
public class RentalReviewRestController {

		@Autowired
		public RentalReviewService rentalReviewService;
		
		@Autowired
		public RentalService rentalService;
		
		//리뷰 수정 ( 업데이트 )
		//회원, 어드민가능 
		@PostMapping("editRentalReview")
		public String updateReview(@RequestBody String prodNo) throws Exception {
		
			
			//암호화된 유저아이디를 받아옴
			LoginUser loginUser= (LoginUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			String userId= loginUser.getUser().getUserId();
			
			RentalReview rentalReview= new RentalReview();
			rentalReview.setUserId(userId);
			rentalReview.setProdNo(prodNo);
			
			rentalReviewService.updateRentalReview(rentalReview);
			
			 return null;
		}
		
		@PostMapping("getRentalReview")
		public RentalReview getRentalReview(@RequestBody String prodNo,Model model) throws Exception {
		
			System.out.println(prodNo);
			
			//암호화된 유저아이디를 받아옴
			LoginUser loginUser= (LoginUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			String userId= loginUser.getUser().getUserId();
			
			RentalReview rentalReview= new RentalReview();
			rentalReview.setUserId(userId);
			rentalReview.setProdNo(prodNo);
			
			System.out.println("da1"+rentalReview.getUserId());
			System.out.println("da2"+rentalReview.getProdNo());
			rentalReview= rentalReviewService.getRentalReview(rentalReview);
			
			System.out.println("갹채"+rentalReview);
			
			return rentalReview;
		}
		
	

}