package com.mvc.forrest.service.rentalreview;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mvc.forrest.dao.rental.RentalDAO;
import com.mvc.forrest.dao.rentalreview.RentalReviewDAO;
import com.mvc.forrest.service.domain.RentalReview;

@Service
public class RentalReviewService {

	@Autowired
	private RentalReviewDAO rentalReviewDAO;
	
	//렌탈리뷰 추가 
	public void addRentalReview(RentalReview rentalReview) throws Exception{
		System.out.println("addRentalReview 실행 됨");
		rentalReviewDAO.addRentalReview(rentalReview);
	}
}
