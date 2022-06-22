package com.mvc.forrest.dao.product;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;


import com.mvc.forrest.service.domain.Product;
import com.mvc.forrest.service.domain.Search;



@Mapper
public interface ProductDAO {
		
		void addProduct(Product product) throws Exception;
		
		Product getProduct(String prodNo) throws Exception;
		
		void updateProduct(Product product) throws Exception;
		
		void updateProductCondition(Product product) throws Exception;
		
		void updateRecentImg(Product product) throws Exception;
		
		List<Product> getProductList(Search search) throws Exception;
		
		List<Product> getProductNames() throws Exception;
		
		List<Product> getProductListForIndex() throws Exception;
		
		int getTotalCount(Search search) throws Exception;
		
		List<Product> getProductListHasUser(Map<String,Object> map) throws Exception;

}