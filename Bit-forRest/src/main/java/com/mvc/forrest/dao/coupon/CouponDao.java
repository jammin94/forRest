package com.mvc.forrest.dao.coupon;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.mvc.forrest.service.domain.Coupon;

@Repository
@Mapper
public interface CouponDao {
	
	public void addCoupon(Coupon coupon) throws Exception ;
	
	public Coupon getCoupon(int couponNo) throws Exception ;
	
	public void updateCoupon(Coupon coupon) throws Exception ;
	
	public void deleteCoupon(int couponNo) throws Exception ;
	
	public List<Coupon> getCouponList() throws Exception ;
	
	public int getTotalCount() throws Exception ;
}
