package com.mvc.forrest.dao.owncoupon;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.mvc.forrest.service.domain.OwnCoupon;

@Repository
@Mapper
public interface OwnCouponDAO {

	public void addOwnCoupon(OwnCoupon  ownCoupon) throws Exception ;
	
	public OwnCoupon getOwnCoupon(int ownCouponNo) throws Exception ;
	
	public void deleteOwnCoupon(int ownCouponNo) throws Exception ;
	
	public List<OwnCoupon> getOwnCouponList(String userId) throws Exception ;
	
	public int getTotalCount(String userId) throws Exception ;
}
