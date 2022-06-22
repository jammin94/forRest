package com.mvc.forrest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.mvc.forrest.dao.owncoupon.OwnCouponDAO;
import com.mvc.forrest.service.coupon.CouponService;
import com.mvc.forrest.service.domain.Coupon;
import com.mvc.forrest.service.domain.OwnCoupon;
import com.mvc.forrest.service.domain.Search;
import com.mvc.forrest.service.domain.User;
import com.mvc.forrest.service.user.UserService;


//@RunWith(SpringJUnit4ClassRunner.class)


@SpringBootTest 
public class CouponServiceTest {

	@Autowired
	private CouponService couponService;
	@Autowired
	private OwnCouponDAO ownCouponDao;
	
	@Test
	public void testAddCoupon() throws Exception{
//		Coupon c = new Coupon();
//		Calendar cal = Calendar.getInstance();
//		Timestamp ts = new Timestamp(System.currentTimeMillis());
//		Timestamp ts2 = new Timestamp(System.currentTimeMillis());
//		
//		cal.setTime(ts);
//		cal.add(Calendar.DATE,30);
//		ts2.setTime(cal.getTime().getTime());
//
//		System.out.println("###ts : "+ts);
//		System.out.println("###ts2 : "+ts2);
//		
//		c.setCouponCreDate(ts);
//		c.setCouponDelDate(ts2);
//		c.setCouponName("[TEST]테스트용 쿠폰");
//		c.setDiscount(0.9);
//		
//		couponService.addCoupon(c);
		
		OwnCoupon oc = new OwnCoupon();
		Calendar cal = Calendar.getInstance();
		Timestamp t1 = new Timestamp(System.currentTimeMillis());
		Timestamp t2 = new Timestamp(System.currentTimeMillis());
		
		cal.setTime(t1);
		cal.add(Calendar.DATE, 30);
		t2.setTime(cal.getTime().getTime());
		
		System.out.println(t1);
		System.out.println(t2);
	}

//	@Test
	public void testGetCoupon() throws Exception{
		Coupon c = new Coupon();
		c = couponService.getCoupon("4");
		
		System.out.println(c);
		
	}
	
//	@Test
	public void testGetCouponList() throws Exception{
		
		Search search = new Search();
		
		Map<String,Object> map = couponService.getCouponList(search);
		 
		System.out.println("\n"+map+"\n");
		System.out.println("\n"+map.get("list")+"\n");
	}
	
//	@Test
	public void testGetOwnCouponList() throws Exception{

		Map<String,Object> map = new HashMap<String, Object>();
		User user = new User();
		String userId = "user01@naver.com"; 
		user.setUserId(userId);
		
		List<OwnCoupon> list =ownCouponDao.getOwnCouponList(userId);
		int totalCount = ownCouponDao.getTotalCount(userId);
		

		
		map.put("list", list);
		map.put("totalCount", totalCount);
		
		
		System.out.println("map : "+ map);
	}
	
	
//	@Test
	public void testNewMapperTest() throws Exception{
		
		OwnCoupon oc = new OwnCoupon();
		oc = couponService.getOwnCoupon(1);
		
		System.out.println(oc);
		
	}

}