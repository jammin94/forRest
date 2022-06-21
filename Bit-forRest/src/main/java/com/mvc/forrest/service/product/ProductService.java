package com.mvc.forrest.service.product;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.mvc.forrest.dao.product.ProductDAO;
import com.mvc.forrest.service.domain.Old;
import com.mvc.forrest.service.domain.Product;
import com.mvc.forrest.service.domain.Search;




@Service
public class ProductService {
	
	@Autowired
	private ProductDAO productDAO;
		
	public void addProduct(Product product) throws Exception{
		productDAO.addProduct(product);
	}
	
	public Product getProduct(String prodNo) throws Exception{
		 return productDAO.getProduct(prodNo);
	}
	
	public void updateProduct(Product product) throws Exception{
		productDAO.updateProduct(product);
	}
	
	
	public void updateProductCondition(Product product) throws Exception{
		productDAO.updateProductCondition(product);	
	}
	
	//관리자가 물품의 최근 보관사진을 업데이트
	public void updateRecentImg(Product product) throws Exception{
		productDAO.updateRecentImg(product);	
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
	
	public List<Product> getProductNames() throws Exception{
		
		return productDAO.getProductNames();
	}
	
	public List<Product> getProductListHasUser(Search search, String userId) throws Exception{
		System.out.println("getProductListHasUser 성공");
		System.out.println("search in service: "+search);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("search", search);
		
		return productDAO.getProductListHasUser(map);
	}
	
	//렌탈마켓 상품중 최신순 4개를 메인화면에 출력
	public List<Product> getProductListForIndex() throws Exception{
		List<Product> list= productDAO.getProductListForIndex();
		
		return list;
	}

	public int getTotalCount(Search search) throws Exception {
		
		return productDAO.getTotalCount(search);
	}
	
	
		
}