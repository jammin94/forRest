package com.mvc.forrest.common.utils;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;

import com.mvc.forrest.dao.old.OldDAO;
import com.mvc.forrest.dao.product.ProductDAO;
import com.mvc.forrest.service.domain.Old;
import com.mvc.forrest.service.domain.Product;

public class RandomNumberGenerator {
	
	@Autowired
	private OldDAO oldDAO;
	
	@Autowired
	private ProductDAO productDAO;	
	
    public int makeRandomOldNumber() throws Exception {  	
        Random random = new Random();
        int ranNo = 100000+ random.nextInt(900000);
        Old old = oldDAO.getOld(ranNo);
	        while(old == null) {
	        	ranNo = 100000+ random.nextInt(900000);
	        }
        return ranNo;
    }
    
    public int makeRandomProductNumber() throws Exception {
        Random random = new Random();
        int ranNo = 100000+ random.nextInt(900000);
        Product product = productDAO.getProduct(ranNo);
	        while(product == null) {
	        	ranNo = 100000+ random.nextInt(900000);
	        }
	        return ranNo;
    }    
}
