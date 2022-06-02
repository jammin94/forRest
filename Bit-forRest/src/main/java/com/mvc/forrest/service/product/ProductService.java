package com.mvc.forrest.service.product;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.mvc.forrest.dao.product.ProductDAO;
import com.mvc.forrest.service.domain.Product;
import com.mvc.forrest.service.domain.Search;




@Service
public class ProductService {
	
	@Autowired
	private ProductDAO productDAO;
		
	public void addProduct(Product product) throws Exception{
		productDAO.addProduct(product);
	}
	
	public Product getProduct(int prodNo) throws Exception{
		 return productDAO.getProduct(prodNo);
	}
	
	public void updateProduct(Product product) throws Exception{
		productDAO.updateProduct(product);
	}
	
	
	public void updateProductCondition(Product product) throws Exception{
		productDAO.updateProductCondition(product);	
	}
	
	//대여가능하면서 현재보관중인 물품들의 리스트
	public Map<String, Object> getProductList(Search search) throws Exception{
		List<Product> list= productDAO.getProductList(search);
		
		int totalCount = productDAO.getTotalCount(search);
		
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list );
		map.put("totalCount", totalCount);
		
		return map;
	}
	
		
}