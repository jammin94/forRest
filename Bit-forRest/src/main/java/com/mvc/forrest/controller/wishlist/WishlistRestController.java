package com.mvc.forrest.controller.wishlist;



import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.context.SecurityContextHolder;


import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mvc.forrest.config.auth.LoginUser;

import com.mvc.forrest.service.domain.Product;
import com.mvc.forrest.service.domain.Wishlist;

import com.mvc.forrest.service.wishlist.WishlistService;




@RestController
@RequestMapping("/wishlist/*")
public class WishlistRestController {
	
	@Autowired
	public WishlistService wishlistService;
	

	
	@PostMapping("addWishlist")
	public boolean addWishlist(@RequestBody String prodNo) throws Exception {
		
		Product product = new Product();
		Wishlist wishlist = new Wishlist();
		System.out.println(prodNo);
		LoginUser loginUser= (LoginUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userId= loginUser.getUser().getUserId();
		wishlist.setWishedUserId(userId);
		product.setProdNo(prodNo);
		wishlist.setProduct(product);
		
		System.out.println("중고거래 찜하기 테스트 중");
		
		if(!wishlistService.wishlistDuplicationCheck(wishlist)) {
		wishlistService.addWishlist(wishlist);
		return true;
		}
		
		
		return false;
	}
	
	@PostMapping("updateWishlist")
	public boolean updateWishlist(@RequestParam(value="period") int period,@RequestParam(value="toggle") int toggle,@RequestParam(value="wishlistNo") int wishlistNo) throws Exception {
		
		System.out.println("갓잇");
		
		Wishlist wishlist = new Wishlist();
		wishlist.setPeriod(period);
		wishlist.setToggle(0);
		wishlist.setWishlistNo(toggle);
			
		wishlistService.updateWishList(wishlist);
		
		return true;
	}
	
	
	
	@GetMapping("addWishlistOnDetail")
	public boolean addWishlistOnDetail(@RequestParam String prodNo, @RequestParam int period) throws Exception {
		
		Product product = new Product();
		Wishlist wishlist = new Wishlist();
		System.out.println(prodNo);
		LoginUser loginUser= (LoginUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userId= loginUser.getUser().getUserId();
		wishlist.setWishedUserId(userId);
		product.setProdNo(prodNo);
		wishlist.setProduct(product);
		wishlist.setPeriod(period);
		
		System.out.println("중고거래 찜하기 테스트 중");
		
		if(!wishlistService.wishlistDuplicationCheck(wishlist)) {
		wishlistService.addWishlist(wishlist);
		return true;
		}
		
		
		return false;
	}
	
	@PostMapping("deleteWishlistOnList")
	public boolean deleteOldLikeOnList(@RequestBody String prodNo) throws Exception {
		
		Product product = new Product();
		Wishlist wishlist = new Wishlist();
		System.out.println(prodNo);
		LoginUser loginUser= (LoginUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userId= loginUser.getUser().getUserId();
		wishlist.setWishedUserId(userId);
		product.setProdNo(prodNo);
		wishlist.setProduct(product);
		
		
		System.out.println("중고거래 찜 삭제 테스트 중");
		
		if(wishlistService.wishlistDuplicationCheck(wishlist)) {
			wishlistService.deleteWishlistOnList(wishlist);
			return true;
		}
		
		
		return false;
	}	
	

	

	
}