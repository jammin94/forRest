package com.mvc.forrest.service.coupon;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mvc.forrest.dao.coupon.CouponDAO;
import com.mvc.forrest.dao.owncoupon.OwnCouponDAO;
import com.mvc.forrest.service.domain.Coupon;
import com.mvc.forrest.service.domain.OwnCoupon;
import com.mvc.forrest.service.domain.Search;

@Service
public class CouponService {
	
	@Autowired
	private CouponDAO couponDao;
	@Autowired
	private OwnCouponDAO ownCouponDao;
	
	//Coupon Method
	public void addCoupon(Coupon coupon) throws Exception{
		couponDao.addCoupon(coupon);
	}
	
	public void updateCoupon(Coupon coupon) throws Exception{
		couponDao.updateCoupon(coupon);
	}
	
	public Coupon getCoupon(String couponNo) throws Exception{
		return couponDao.getCoupon(couponNo);
	}
	
	public Map<String, Object> getCouponList(Search search) throws Exception{
		List<Coupon> list =couponDao.getCouponList(search);
		int totalCount = couponDao.getTotalCount(search);
		
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("list", list);
		map.put("totalCount", totalCount);
		
		return map;	
	}
	
	public void deleteCoupon(String couponNo) throws Exception{
		couponDao.deleteCoupon(couponNo);
	}

	
	//OwnCoupon Method	
	public void addOwnCoupon(OwnCoupon ownCoupon) throws Exception{
		ownCouponDao.addOwnCoupon(ownCoupon);
	}
	
	public Map<String, Object> getOwnCouponList(String userId) throws Exception{
		List<OwnCoupon> list =ownCouponDao.getOwnCouponList(userId);
		int totalCount = ownCouponDao.getTotalCount(userId);
		
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("list", list);
		map.put("totalCount", totalCount);
		
		return map;	
	}

	public OwnCoupon getOwnCoupon(int ownCouponNo) throws Exception{
		return ownCouponDao.getOwnCoupon(ownCouponNo);
	}
	
	public void deleteOwnCoupon(int ownCouponNo) throws Exception{
		ownCouponDao.deleteOwnCoupon(ownCouponNo);
	}
	
	public List<OwnCoupon> checkOwnCoupon(OwnCoupon ownCoupon) throws Exception{
		return ownCouponDao.checkOwnCoupon(ownCoupon);
	}
	
	public void useCoupon(int ownCouponNo) throws Exception {
		if(ownCouponNo != 0) {
			deleteOwnCoupon(ownCouponNo);
		}
	}
}
