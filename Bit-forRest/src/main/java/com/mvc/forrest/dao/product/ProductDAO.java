package com.mvc.forrest.dao.product;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;


import com.mvc.forrest.service.domain.Product;
import com.mvc.forrest.service.domain.Search;



@Mapper
public interface ProductDAO {
		
		void addProduct(Product product) throws Exception;
		
		Product getProduct(int prodNo) throws Exception;
		
		void updateProduct(Product product) throws Exception;
		
		void updateProductCondition(Product product) throws Exception;
		
		List<Product> getProductList(Search search) throws Exception;
		
		List<Product> getProductListForIndex() throws Exception;
		
		int getTotalCount(Search search) throws Exception;
		
		

}