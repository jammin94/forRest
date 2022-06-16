package com.mvc.forrest.dao.rental;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.mvc.forrest.service.domain.Rental;
import com.mvc.forrest.service.domain.RentalReview;
import com.mvc.forrest.service.domain.Search;
import com.mvc.forrest.service.domain.Storage;



@Mapper
public interface RentalDAO {
	
	void addRental(Rental rental) throws Exception;
	
	Rental getRental(String tranNo) throws Exception;
	
	List<Rental> getRentalList(Map<String,Object> map) throws Exception;
	
	List<Rental> getRentalListForAdmin(Search search) throws Exception;
	
	List<Rental> getRentalListProfit(Map<String,Object> map) throws Exception;
	
	int getRentalProfitTotal(String userId) throws Exception;
	
	int getTotalCount(Search search) throws Exception;	
	
	int getTotalRentalProfit(String userId) throws Exception;	
	
}

