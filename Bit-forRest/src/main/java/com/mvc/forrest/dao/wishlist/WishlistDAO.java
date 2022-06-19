package com.mvc.forrest.dao.wishlist;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.mvc.forrest.service.domain.Search;
import com.mvc.forrest.service.domain.Wishlist;


@Mapper
public interface WishlistDAO {
	
	void addWishlist(Wishlist wishlist) throws Exception;
	
	void deleteWishlist(int wishlistNo) throws Exception;
	
	void updateWishList(Wishlist wishlist) throws Exception;
	
	Wishlist getWish(int wishlistNo) throws Exception;
	
	List<Wishlist> getWishlist(Map<String,Object> map) throws Exception;
	
	int getTotalCount(Search search) throws Exception;	
	
	Wishlist wishlistDuplicationCheck(Wishlist wishlist) throws Exception;
	
	int getWishlistTotalSum(String userId) throws Exception;

	void deleteOldLikeOnList(Wishlist wishlist);

}