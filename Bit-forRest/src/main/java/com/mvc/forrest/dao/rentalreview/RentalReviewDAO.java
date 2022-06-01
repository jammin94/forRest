package com.mvc.forrest.dao.rentalreview;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.mvc.forrest.service.domain.OldLike;
import com.mvc.forrest.service.domain.RentalReview;



@Mapper
public interface RentalReviewDAO {
	
	void addRentalReview(RentalReview rentalReview) throws Exception;
	

}
