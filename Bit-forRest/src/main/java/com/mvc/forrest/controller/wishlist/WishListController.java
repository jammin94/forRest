package com.mvc.forrest.controller.wishlist;

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


//==> ȸ������ Controller
//@Controller
//@RequestMapping("/user/*")
public class WishListController {
	
	//-----------장바구니 추가 ------------//
	
		@PostMapping("addWishList")
		public String addWishList( ) throws Exception{
			
			return null;
		}
	
	//-----------장바구니 삭제------------//
	
		@PostMapping("deleteWishList")
		public String deleteWishList( ) throws Exception{
			
			return null;
		}
			

	//-----------장바구니 리스트 화면------------//
		@GetMapping("getWishList")
		public String getWishListView( ) throws Exception{
			
			return null;
		}
		
		@PostMapping("getWishList")
		public String getWishList( ) throws Exception{
			
			return null;
		}
}