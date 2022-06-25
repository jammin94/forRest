package com.mvc.forrest.dao.rental;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.mvc.forrest.service.domain.Rental;
import com.mvc.forrest.service.domain.Search;



@Mapper
public interface RentalDAO {
	
	void addRental(Rental rental) throws Exception;
	
	void updateReviewDone(Rental rental) throws Exception;
	
	void updateCancelDone(Rental rental) throws Exception;
	
	void updateComplete(Rental rental) throws Exception;
	
	Rental getRental(String tranNo) throws Exception;
	
	List<Rental> getRentalList(Map<String,Object> map) throws Exception;
	
	List<Rental> getRentalListForAdmin(Search search) throws Exception;
	
	List<Rental> getRentalListProfit(Map<String,Object> map) throws Exception;
	
	int getRentalProfitTotal(String userId) throws Exception;
	
	int getTotalCountAdmin(Search search) throws Exception;	
	
	int getTotalCountUser(Map<String,Object> map) throws Exception;	
	
	int getTotalRentalProfit(String userId) throws Exception;
	
	int getTotalCountRentalListProfit(Map<String,Object> map) throws Exception;
	
	List<Rental> getPaymentList(Map<String,Object> map) throws Exception;

	List<Rental> getPayment(String paymentNo) throws Exception;
}

