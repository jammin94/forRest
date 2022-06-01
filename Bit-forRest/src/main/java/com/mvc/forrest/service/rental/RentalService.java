package com.mvc.forrest.service.rental;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mvc.forrest.dao.rental.RentalDAO;
import com.mvc.forrest.service.domain.Rental;


@Service
public class RentalService {
	
	@Autowired
	private RentalDAO rentalDAO;
	
	// 물품대여추가
	public void addRental(Rental rental) throws Exception{
		System.out.println("getRental 실행 됨");
		rentalDAO.addRental(rental);
	}

}
