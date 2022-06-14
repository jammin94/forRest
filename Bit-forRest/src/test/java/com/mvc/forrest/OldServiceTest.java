package com.mvc.forrest;



import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mvc.forrest.service.domain.Old;
import com.mvc.forrest.service.domain.OldReview;
import com.mvc.forrest.service.domain.Search;
import com.mvc.forrest.service.old.OldService;
import com.mvc.forrest.service.oldreview.OldReviewService;




/*
 *	FileName :  UserServiceTest.java
 * ㅇ JUnit4 (Test Framework) 과 Spring Framework 통합 Test( Unit Test)
 * ㅇ Spring 은 JUnit 4를 위한 지원 클래스를 통해 스프링 기반 통합 테스트 코드를 작성 할 수 있다.
 * ㅇ @RunWith : Meta-data 를 통한 wiring(생성,DI) 할 객체 구현체 지정
 * ㅇ @ContextConfiguration : Meta-data location 지정
 * ㅇ @Test : 테스트 실행 소스 지정
 */
//@RunWith(SpringJUnit4ClassRunner.class)

//==> Meta-Data 를 다양하게 Wiring 하자...

//@ContextConfiguration(locations = { "classpath:config/context-common.xml" })

@SpringBootTest
public class OldServiceTest {

	//==>@RunWith,@ContextConfiguration 이용 Wiring, Test 할 instance DI
	
	@Autowired
	public OldService oldService;
	public OldReviewService oldReviewService;

//	@Test
	public void testUpdateOld() throws Exception {
		
		
		Old old = oldService.getOld("1");
		
		assertEquals(20000, old.getOldPrice());
		assertEquals("1인침대", old.getOldTitle());
		assertEquals("접이식", old.getOldDetail());
		assertEquals("침대", old.getCategory());
		assertEquals((short) 1 ,old.getOldState());
		assertEquals("wow.jpg", old.getOldImg());
		assertEquals("대치동", old.getOldAddr());
		
		old.setOldPrice(40000);
		old.setOldTitle("원터치 텐트");
		old.setOldDetail("가성비");
		old.setCategory("텐트");
		old.setOldState((short) 1);
		old.setOldImg("tent.jpg");
		old.setOldAddr("청담동");
		
		
		//updateOld(2)로 하면 데이터 전부 2로 변함
		oldService.updateOld(old);
		old = oldService.getOld("1");
		
		assertEquals(40000, old.getOldPrice());
		assertEquals("원터치 텐트", old.getOldTitle());
		assertEquals("가성비", old.getOldDetail());
		assertEquals("텐트", old.getCategory());
		assertEquals((short) 1 ,old.getOldState());
		assertEquals("tent.jpg", old.getOldImg());
		assertEquals("청담동", old.getOldAddr());
		
		
		System.out.println("updateOldtest"+old);
		
		//user = userService.getUser("testUserId");

		//==> console 확인
		//System.out.println(user);
		
		//==> API 확인
		
	}
	
	//@Test
	public void testgetOldList() throws Exception{
		
		Search search = new Search();
		search.setSearchKeyword("야전");
		search.setSearchCategory("");
		search.setStartRowNum(1);
		search.setEndRowNum(5);
		
		System.out.println(search);
		Map<String,Object> map = oldService.getOldList(search);
		
		
		System.out.println(map);
	
		
	}
	
	//@Test
	public void testAddOld() throws Exception{
		System.out.println("등록");
		Old old = new Old();
		
		
		old.setUserId("user01@naver.com");
		old.setOldPrice(999);
		old.setOldTitle("구구");
		old.setOldDetail("디");
		old.setOldDate(null);
		
		old.setOldView(0);
		old.setCategory("텐트");
		old.setOldState((short) 0);
		old.setOldImg("j.jpg");
		old.setOldAddr("대홍동");
		oldService.addOld(old);
		
		
		
		assertEquals("user01@naver.com", old.getUserId());
		assertEquals(999, old.getOldPrice());
		assertEquals("구구", old.getOldTitle());
		assertEquals("디", old.getOldDetail());
		assertEquals(null, old.getOldDate());
		assertEquals(0, old.getOldView());
		assertEquals("텐트", old.getCategory());
		assertEquals((short) 0, old.getOldState());
		assertEquals("j.jpg", old.getOldImg());
		assertEquals("대홍동", old.getOldAddr());
		
		
		
	}
	
	//@Test
	public void testDeleteOld() throws Exception{
		Old old = new Old();
		old.setOldNo("1");
		oldService.deleteOld("1");
		assertEquals(10, old.getOldNo());
	}
	
//	@Test
//	public void testGetOld() throws Exception{
//		System.out.println("겟");
//		Old old = new Old();
//		old = oldService.getOld(3);
//		
//	
//	
//		assertEquals(3, old.getOldNo());
//	}

	
}