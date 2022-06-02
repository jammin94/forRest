package com.mvc.forrest.dao.oldreview;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import com.mvc.forrest.service.domain.OldLike;
import com.mvc.forrest.service.domain.OldReview;


@Repository
@Mapper
public interface OldReviewDAO {
	
	void addOldReview(OldReview oldReview) throws Exception;
	
	List<OldReview> getOldReviewList(String reviewedUser) throws Exception;
	
	OldReview getUserRate(String reviewedUser) throws Exception;

}