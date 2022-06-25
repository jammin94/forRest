package com.mvc.forrest.service.rentalreview;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mvc.forrest.dao.rentalreview.RentalReviewDAO;
import com.mvc.forrest.dao.user.UserDAO;
import com.mvc.forrest.service.domain.RentalReview;

@Service
public class RentalReviewService {

	@Autowired
	private RentalReviewDAO rentalReviewDAO;
	
	@Autowired
	private UserDAO userDAO;
	
	
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
	public Map<String, Object> getRentalReviewList(String prodNo) throws Exception{
		
		List<RentalReview> list= rentalReviewDAO.getRentalReviewList(prodNo);		
				
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list );
		
		return map;
	}
	
	
	//렌탈리뷰 수정(업데이트)
	public void updateRentalReview(RentalReview rentalReview) throws Exception{
		System.out.println("updateRentalReview 실행 됨");
		rentalReviewDAO.updateRentalReview(rentalReview);
	}
	
	//렌탈리뷰 GET해오기
	public RentalReview getRentalReview(RentalReview rentalReview) throws Exception{
		System.out.println("getRentalReview 실행 됨");
		return rentalReviewDAO.getRentalReview(rentalReview);
	}
	
	
}
