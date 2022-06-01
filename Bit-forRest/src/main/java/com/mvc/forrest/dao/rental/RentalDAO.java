package com.mvc.forrest.dao.rental;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.mvc.forrest.service.domain.Rental;
import com.mvc.forrest.service.domain.RentalReview;



@Mapper
public interface RentalDAO {
	
	void addRental(Rental rental) throws Exception;
	

}

