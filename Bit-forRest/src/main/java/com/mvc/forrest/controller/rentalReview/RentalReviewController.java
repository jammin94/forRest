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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.mvc.forrest.common.utils.FileNameUtils;
import com.mvc.forrest.config.auth.LoginUser;
import com.mvc.forrest.service.domain.Product;
import com.mvc.forrest.service.domain.Rental;
import com.mvc.forrest.service.domain.RentalReview;
import com.mvc.forrest.service.domain.User;
import com.mvc.forrest.service.rental.RentalService;
import com.mvc.forrest.service.rentalreview.RentalReviewService;




@Controller
@RequestMapping("/rentalReview/*")
public class RentalReviewController {

		@Autowired
		public RentalReviewService rentalReviewService;
		
		@Autowired
		public RentalService rentalService;
		
		
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
		//회원, 어드민가능 
		@PostMapping("addRentalReview")
		public String addRentalReview(
				@ModelAttribute("rentalReview") RentalReview rentalReview, 
				Model model,
				@RequestParam("fileName") MultipartFile file,
				@RequestParam("tranNo") String tranNo,
				@RequestParam("reviewDone") int reviewDone
				) throws Exception {
			System.out.println(rentalReview.getReviewImg());
			System.out.println("p넘버"+rentalReview.getProdNo());
			
			String temDir = "C:\\\\Users\\\\bitcamp\\\\git\\\\forRest\\\\Bit-forRest\\\\src\\\\main\\\\resources\\\\static\\\\images\\\\uploadFiles";
			String fileName = "";
			String fileConvert = FileNameUtils.fileNameConvert(file.getOriginalFilename());
			
			if(!file.getOriginalFilename().isEmpty()) {
				
				file.transferTo(new File(temDir, fileConvert));
				System.out.println("파일명 :: "+fileConvert);
				
				fileName =fileConvert;			
				rentalReview.setReviewImg(fileName);				
			}else {
				System.out.println("파일업로드 실패...?");
			}
			
			//암호화된 유저아이디를 받아옴
			LoginUser loginUser= (LoginUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			String userId= loginUser.getUser().getUserId();
			
			rentalReview.setProdNo(rentalReview.getProdNo()); // 참고: 무결성제약조건 prodNo는 기존값 존재해야함
			rentalReview.setUserId(userId); // 참고: 무결성제약조건 userId는 기존값 존재해야함
			
			rentalReviewService.addRentalReview(rentalReview);
			
			//리뷰 업데이트된 상태도 rental transaction에 업데이트 해준다.
			Rental rental = new Rental();
			rental.setReviewDone(reviewDone);
			rental.setTranNo(tranNo);
			rentalService.updateReviewDone(rental);
			
			System.out.println("reviewDone"+reviewDone);
			System.out.println("tranNo"+tranNo);
			System.out.println("렌탈리뷰 추가하기 테스트 중");
			
			model.addAttribute("rentalReview", rentalReview);
			
			return "redirect:/rental/listRental";
		}
		
		//리뷰 리스트 출력
		//비회원,회원, 어드민가능 
		@GetMapping("listReview")
		public String listReviewView(@ModelAttribute("rentalReview") RentalReview rentalReview, Model model) throws Exception {
		
			 return null;
		}

		@PostMapping("listReview")
		public String listReview(@ModelAttribute("rentalReview") RentalReview rentalReview, Model model) throws Exception {
		
			 return null;
		}
		
		//리뷰 수정 ( 업데이트 )
		//회원, 어드민가능 
		@GetMapping("updateReview")
		public String updateReviewView(@ModelAttribute("rentalReview") RentalReview rentalReview, Model model) throws Exception {
		
			 return null;
		}
		
		//리뷰 수정 ( 업데이트 )
		//회원, 어드민가능 
		@PostMapping("updateRentalReview")
		public String updateReview(@ModelAttribute("rentalReview") RentalReview rentalReview,@RequestParam("prodNo") String prodNo , @RequestParam("fileName") MultipartFile file, Model model) throws Exception {
			
			
			System.out.println("업데이트 리뷰 실행 !");
			//암호화된 유저아이디를 받아옴
			LoginUser loginUser= (LoginUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			String userId= loginUser.getUser().getUserId();
			
			
			String temDir = "C:\\\\Users\\\\bitcamp\\\\git\\\\forRest\\\\Bit-forRest\\\\src\\\\main\\\\resources\\\\static\\\\images\\\\uploadFiles";
			String fileName = "";
			String fileConvert = FileNameUtils.fileNameConvert(file.getOriginalFilename());
			
			if(!file.getOriginalFilename().isEmpty()) {
				
				file.transferTo(new File(temDir, fileConvert));
				System.out.println("파일명 :: "+fileConvert);
				
				fileName =fileConvert;			
				rentalReview.setReviewImg(fileName);				
			}else {
				System.out.println("파일업로드 실패...?");
			}
			
			
			rentalReview.setUserId(userId);
			rentalReview.setProdNo(prodNo);
			rentalReviewService.updateRentalReview(rentalReview);
			
			System.out.println(rentalReview);
			return "redirect:/rental/listRental";
		}
		
		
		// 리뷰 삭제 
		//회원, 어드민가능 
		@GetMapping("deleteReview")
		public String deleteReviewView(@ModelAttribute("rentalReview") RentalReview rentalReview, Model model) throws Exception {
		
			 return null;
		}
		
		// 리뷰 삭제 
		//회원, 어드민가능 
		@PostMapping("deleteReview")
		public String deleteReview(@ModelAttribute("rentalReview") RentalReview rentalReview, Model model) throws Exception {
		
			 return null;
		}
		
}