package com.mvc.forrest.dao.wishlist;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.mvc.forrest.service.domain.Product;
import com.mvc.forrest.service.domain.RentalReview;
import com.mvc.forrest.service.domain.Search;
import com.mvc.forrest.service.domain.WishList;


@Mapper
public interface WishListDAO {
	
	void addWishList(WishList wishList) throws Exception;
	
	void deleteWishList(int wishListNo) throws Exception;
	
	List<WishList> getWishList(Map<String,Object> map) throws Exception;
	
	int getTotalCount(Search search) throws Exception;	
	
	

}