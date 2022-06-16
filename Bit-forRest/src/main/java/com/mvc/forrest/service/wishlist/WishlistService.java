package com.mvc.forrest.service.wishlist;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mvc.forrest.dao.rentalreview.RentalReviewDAO;
import com.mvc.forrest.dao.wishlist.WishlistDAO;
import com.mvc.forrest.service.domain.OldLike;
import com.mvc.forrest.service.domain.Rental;
import com.mvc.forrest.service.domain.RentalReview;
import com.mvc.forrest.service.domain.Search;
import com.mvc.forrest.service.domain.Wishlist;

@Service
public class WishlistService {

	@Autowired
	private WishlistDAO wishlistDAO;
	
		//장바구니 추가 
		public void addWishlist(Wishlist wishList) throws Exception{
			System.out.println("addWishlist 실행 됨");
			wishlistDAO.addWishlist(wishList);
		}
		
		
		
		//장바구니 삭제
		public void deleteWishlist(int wishListNo) throws Exception{
			System.out.println("deleteWishlist 실행 됨");
			wishlistDAO.deleteWishlist(wishListNo);
		}
		
		
		
		//장바구니 리스트
		public Map<String, Object> getWishlist(Search search, String userId) throws Exception{
			
			Map<String, Object> map = new HashMap<String, Object>();
			
			map.put("search",search);
			map.put("userId", userId);
			
			System.out.println("map:"+map);
		
			//mapper에 있는 sql을 통해 return값을 얻음
			List<Wishlist> list =wishlistDAO.getWishlist(map);
			System.out.println("위시리스트 서비스");
			int totalCount = wishlistDAO.getTotalCount(search);
		
			//map에 담음
			map.put("list", list);
			map.put("totalCount", totalCount);
			
			return map;
		}
		
		public void deleteWishlistOnList(Wishlist wishlist) throws Exception{
			System.out.println("deleteWishlistOnList 실행 됨");
			wishlistDAO.deleteOldLikeOnList(wishlist);
		}
		
		public boolean wishlistDuplicationCheck(Wishlist wishlist) throws Exception{
			boolean result=false;
			wishlist = wishlistDAO.wishlistDuplicationCheck(wishlist);
			if(wishlist != null) {
				result=true;
			}
			return result;
		}
	
}
