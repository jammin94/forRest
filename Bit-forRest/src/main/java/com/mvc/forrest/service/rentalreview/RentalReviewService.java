package com.mvc.forrest.service.rentalreview;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.mvc.forrest.dao.rental.RentalDAO;
import com.mvc.forrest.dao.rentalreview.RentalReviewDAO;
import com.mvc.forrest.service.domain.Product;
import com.mvc.forrest.service.domain.RentalReview;
import com.mvc.forrest.service.domain.Search;

@Service
public class RentalReviewService {

	@Autowired
	private RentalReviewDAO rentalReviewDAO;
	
	
	//렌탈리뷰 추가 
	public void addRentalReview(RentalReview rentalReview) throws Exception{
		System.out.println("addRentalReview 실행 됨");
		rentalReviewDAO.addRentalReview(rentalReview);
	}
	
	//렌탈리뷰 삭제
	public void deleteRentalReview(int reviewNo) throws Exception{
		System.out.println("deleteRentalReview 실행 됨");
		rentalReviewDAO.deleteRentalReview(reviewNo);
	}
	
	//렌탈리뷰 리스트
	public Map<String, Object> getRentalReviewList(Search search) throws Exception{
		List<Product> list= rentalReviewDAO.getRentalReviewList(search);
		
		int totalCount = rentalReviewDAO.getTotalCount(search);
		
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list );
		map.put("totalCount", totalCount);
		
		return map;
	}
	
	
	//렌탈리뷰 수정(업데이트)
	public void updateRentalReview(RentalReview rentalReview) throws Exception{
		System.out.println("updateRentalReview 실행 됨");
		rentalReviewDAO.updateRentalReview(rentalReview);
	}
}
