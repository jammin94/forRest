package com.mvc.forrest.service.wishlist;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mvc.forrest.dao.rentalreview.RentalReviewDAO;
import com.mvc.forrest.dao.wishlist.WishListDAO;
import com.mvc.forrest.service.domain.Rental;
import com.mvc.forrest.service.domain.RentalReview;
import com.mvc.forrest.service.domain.Search;
import com.mvc.forrest.service.domain.WishList;

@Service
public class WishListService {

	@Autowired
	private WishListDAO wishListDAO;
	
		//장바구니 추가 
		public void addWishList(WishList wishList) throws Exception{
			System.out.println("addRentalReview 실행 됨");
			wishListDAO.addWishList(wishList);
		}
		
		//장바구니 삭제
		public void deleteWishList(int wishListNo) throws Exception{
			System.out.println("addRentalReview 실행 됨");
			wishListDAO.deleteWishList(wishListNo);
		}
		
		//장바구니 리스트
		public Map<String, Object> getWishList(Search search, String userId) throws Exception{
			
			Map<String, Object> map = new HashMap<String, Object>();
			
			map.put("search",search);
			map.put("userId", userId);
			
			System.out.println("map:"+map);
		
			//mapper에 있는 sql을 통해 return값을 얻음
			List<WishList> list =wishListDAO.getWishList(map);
			System.out.println("위시리스트 서비스");
			int totalCount = wishListDAO.getTotalCount(search);
		
			//map에 담음
			map.put("list", list);
			map.put("totalCount", totalCount);
			
			return map;
		}
	
}
