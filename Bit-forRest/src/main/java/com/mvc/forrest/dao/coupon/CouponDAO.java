package com.mvc.forrest.dao.coupon;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.mvc.forrest.service.domain.Coupon;
import com.mvc.forrest.service.domain.Search;

@Repository
@Mapper
public interface CouponDAO {
	
	public void addCoupon(Coupon coupon) throws Exception ;
	
	public Coupon getCoupon(int couponNo) throws Exception ;
	
	public void updateCoupon(Coupon coupon) throws Exception ;
	
	public void deleteCoupon(String couponNo) throws Exception ;
	
	public List<Coupon> getCouponList(Search search) throws Exception ;
	
	public int getTotalCount(Search search) throws Exception ;
}
