package com.mvc.forrest.dao.wishlist;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.mvc.forrest.service.domain.OldLike;
import com.mvc.forrest.service.domain.Product;
import com.mvc.forrest.service.domain.RentalReview;
import com.mvc.forrest.service.domain.Search;
import com.mvc.forrest.service.domain.Wishlist;


@Mapper
public interface WishlistDAO {
	
	void addWishlist(Wishlist wishlist) throws Exception;
	
	void deleteWishlist(int wishlistNo) throws Exception;
	
	void updateWishList(Wishlist wishlist) throws Exception;
	
	List<Wishlist> getWishlist(Map<String,Object> map) throws Exception;
	
	int getTotalCount(Search search) throws Exception;	
	
	Wishlist wishlistDuplicationCheck(Wishlist wishlist) throws Exception;
	
	int getWishlistTotalSum(String userId) throws Exception;

	void deleteOldLikeOnList(Wishlist wishlist);

}