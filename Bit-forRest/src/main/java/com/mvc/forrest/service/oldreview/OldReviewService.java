package com.mvc.forrest.service.oldreview;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mvc.forrest.dao.oldreview.OldReviewDAO;
import com.mvc.forrest.service.domain.OldReview;

@Service
public class OldReviewService {
	
	@Autowired
	private OldReviewDAO oldReviewDAO;
	
	//찜하기 추가
	public void addOldReview(OldReview oldReview) throws Exception{
		System.out.println("addOldReview 실행 됨");
		oldReviewDAO.addOldReview(oldReview);
	}
	
	public List<OldReview> getOldReviewList(String reviewedUser) throws Exception{
		System.out.println("getOldReviewList 실행 됨");
		return oldReviewDAO.getOldReviewList(reviewedUser);
	}	
	
	public double getUserRate(String reviewedUser) throws Exception{
		System.out.println("getUserRate 실행 됨");
		OldReview oldReview= oldReviewDAO.getUserRate(reviewedUser);
		int count = 10 + oldReview.getUserRateCount();
		int sum = 30 + oldReview.getUserRateSum();
		double avg = sum/count;
		return avg;
	}		

}
