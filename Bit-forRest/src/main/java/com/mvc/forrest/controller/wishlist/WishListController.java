
package com.mvc.forrest.controller.wishlist;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import java.util.Map;



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
import com.mvc.forrest.service.domain.Search;
import com.mvc.forrest.service.domain.Wishlist;
import com.mvc.forrest.service.user.UserService;
import com.mvc.forrest.service.wishlist.WishlistService;



@Controller
@RequestMapping("/wishlist/*")
public class WishListController {
	
	@Autowired
	public WishlistService wishlistService;
	
	@Autowired
	public UserService userService;
	
	@Autowired
	public CouponService couponService;
	

//	@Autowired
//	public CouponService couponService;  ( 대기 )
	
	@Value("5")
	int pageUnit;
	
	@Value("10")
	int pageSize;
	
	
	
	//-----------장바구니 추가 ------------//
	//회원, 어드민가능 
//		@PostMapping("addWishList")
		public String addWishList( ) throws Exception{
			
			return null;
		}
	
	//-----------장바구니 삭제------------//
		//회원, 어드민가능 
//		@PostMapping("deleteWishList")
		public String deleteWishList( ) throws Exception{
			
			return null;
		}
			

	//-----------장바구니 리스트 화면------------//
		//회원, 어드민가능 
		@GetMapping("getWishlist")
		public String getWishListView(@ModelAttribute("search") Search search , Model model ) throws Exception{

			System.out.println("listRentalProfitView 테스트");
			
			if(search.getCurrentPage() ==0 ){
				search.setCurrentPage(1);
			}
			search.setPageSize(pageSize);
		
//			String userId = ((User)session.getAttribute("user")).getUserId();
			
			//세션에 있는 유저아이디 꺼낸다
			LoginUser loginUser= (LoginUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		    String userId= loginUser.getUser().getUserId();
		      
			System.out.println("userId:"+userId);
			
			//"세션아이디" 와 일치하는 것의 리스트들을 꺼낸다 (장바구니 목록)
			Map<String, Object> mapWishList =	wishlistService.getWishlist(search,userId);
		    Page resultPage = new Page(search.getCurrentPage(), ((Integer)mapWishList.get("totalCount")).intValue(), pageUnit, pageSize );
			
		    int totalPrice = wishlistService.getWishlistTotalSum(userId);
		    
			// Model 과 View 연결
			
			model.addAttribute("list", mapWishList.get("list"));
			model.addAttribute("resultPage", resultPage);
			model.addAttribute("search", search);
			model.addAttribute("totalPrice",totalPrice);
			 
			System.out.println("모델리스트"+mapWishList.get("list"));
			
			return "wishList/wishList";
		}
		
		
		//-------------장바구니에서의 add 구현 --------------------//
		@PostMapping("addWishRental")
		public String addWishRentalView(@RequestParam("wishlistNo") int[] wishlistNo, @RequestParam("period") int[] period, @RequestParam("rentalPrice") int[] rentalPrice, Model model) throws Exception{
			
			System.out.println("period"+period);			
			
			//암호화된 유저아이디를 받아옴
			LoginUser loginUser= (LoginUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			String userId= loginUser.getUser().getUserId();
			System.out.println("userId: "+userId);
			//회원의 보유쿠폰리스트를 받아옴
			Map<String,Object> map =couponService.getOwnCouponList(userId);
			//장바구니 배열로받아서 넘기기
			List<Wishlist> listA = new ArrayList<Wishlist>();
			Wishlist wishlist = new Wishlist();
			for(int i=0; i<wishlistNo.length;i++) {
				wishlist=wishlistService.getWish(wishlistNo[i]); //wishlist 객체반환	
				wishlist.setPeriod(period[i]);
				wishlist.getProduct().setRentalPrice(rentalPrice[i]);
				listA.add(wishlist);
			}
			//결제가 이루어지기전에 tranNo가 필요하기때문에 예비 tranNo를 생성 
			 String reserveTranNo = FileNameUtils.getRandomString();
		
			
			System.out.println(listA);
			
			
			model.addAttribute("wishlist",listA);
			model.addAttribute("list",map.get("list"));
			model.addAttribute("reserveTranNo", reserveTranNo);
			
			return "rental/addWishRental";
		}
		
		
}