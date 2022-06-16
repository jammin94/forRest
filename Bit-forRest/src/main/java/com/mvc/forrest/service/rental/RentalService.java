package com.mvc.forrest.service.rental;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mvc.forrest.dao.rental.RentalDAO;
import com.mvc.forrest.service.domain.Old;
import com.mvc.forrest.service.domain.Rental;
import com.mvc.forrest.service.domain.Search;
import com.mvc.forrest.service.domain.Storage;


@Service
public class RentalService {
	
	@Autowired
	private RentalDAO rentalDAO;
	
	// 물품대여추가
	public void addRental(Rental rental) throws Exception{
		System.out.println("getRental 실행 됨");
		rentalDAO.addRental(rental);
	}
	
	// 결제상세조회
		public Rental getRental(String tranNo) throws Exception{
			System.out.println("getRental 실행 됨");
			return rentalDAO.getRental(tranNo);
		}
	
	// 물품대여 리스트
		public Map<String, Object> getRentalList(Map<String,Object> map) throws Exception{
			
			List<Rental> list = rentalDAO.getRentalList(map);
			int totalCount = rentalDAO.getTotalCount((Search) map.get("search"));
			map.put("list", list);
			map.put("totalCount", totalCount);
			
			return map;
		}
	
	//물품대여 리스트 ( Admin 용 )
	public Map<String, Object> getRentalListForAdmin(Search search) throws Exception{
		
		List<Rental> list = rentalDAO.getRentalListForAdmin(search);
		int totalCount = rentalDAO.getTotalCount(search);
		
		Map<String, Object> map = new HashMap<>();
		map.put("list", list);
		map.put("totalCount", totalCount);
		
		return map;
	}
	
	// 대여 수익 총계 출력 
	public int getTotalRentalProfit(String userId) throws Exception{
		
		int totalCount = rentalDAO.getTotalRentalProfit(userId);
		
		return totalCount ;
	}
	
	//물품대여 리스트 ( Admin 용 )
		public Map<String, Object> getRentalListProfit(Search search, String userId) throws Exception{
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("search", search);
			map.put("userId", userId);
			
			List<Rental> list = rentalDAO.getRentalListProfit(map);
			int totalCount = rentalDAO.getTotalCount(search);
			
			map.put("list", list);
			map.put("totalCount", totalCount);
			
			return map;
		}
		
		public Map<String,Object> getRentalProfitTotal(String userId) throws Exception{
			
			Map<String, Object> map = new HashMap<String, Object>(); 
			map.put("userId", userId);
			
			List<Rental> list = rentalDAO.getRentalProfitTotal(userId);
			
			map.put("list", list);
			
			return map;
			
		}
	

}
