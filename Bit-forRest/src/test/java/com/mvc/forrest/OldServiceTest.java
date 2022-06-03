package com.mvc.forrest;


import java.util.Map;

import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mvc.forrest.service.domain.Old;
import com.mvc.forrest.service.domain.Search;
import com.mvc.forrest.service.old.OldService;




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

	//@org.junit.jupiter.api.Test
	public void testUpdateOld() throws Exception {
		
		Old old = new Old();
		
		old.setOldPrice(40000);
		old.setOldTitle("야전침대");
		old.setOldDetail("접이식");
		old.setCategory("텐트");
		//old.setOldState(0);
		old.setOldImg("aaa.jpg");
		
		
		
		
		oldService.updateOld(old);
		System.out.println("updateOldtest"+old);
		
		//user = userService.getUser("testUserId");

		//==> console 확인
		//System.out.println(user);
		
		//==> API 확인
		
		Assert.assertEquals(40000, old.getOldPrice());
		Assert.assertEquals("야전침대", old.getOldTitle());
		Assert.assertEquals("접이식", old.getOldDetail());
		Assert.assertEquals("텐트", old.getCategory());
		Assert.assertEquals("aaa.jpg", old.getOldImg());
	
		
	}
	
	//@org.junit.jupiter.api.Test
	public void getOldList() throws Exception{
		
		Search search = new Search();
		search.setSearchKeyword("야전");
		search.setSearchCategory("");
		search.setStartRowNum(1);
		search.setEndRowNum(5);
		
		System.out.println(search);
		Map<String,Object> map = oldService.getOldList(search);
		
		
		System.out.println(map);
	
		
	}
	
	//@org.junit.jupiter.api.Test
	public void addOld() throws Exception{
		System.out.println("등록");
		Old old = new Old();
		old.setOldNo(11);
		old.setUserId("user09@naver.com");
		old.setOldPrice(999);
		old.setOldTitle("구구");
		old.setOldDetail("디");
		//old.setOldDate(TIMESTAMP);
	
		old.setOldImg("j.jpg");
		
		Assert.assertEquals(11, old.getOldNo());
		Assert.assertEquals("user09@naver.com", old.getUserId());
		Assert.assertEquals(999, old.getOldPrice());
		Assert.assertEquals("구구", old.getOldTitle());
		Assert.assertEquals("디", old.getOldDetail());
		Assert.assertEquals("j.jpg", old.getOldImg());
	}
	
	//@org.junit.jupiter.api.Test
	public void deleteOld() throws Exception{
		Old old = new Old();
		old.setOldNo(11);
		Assert.assertEquals(11, old.getOldNo());
	}
	
	@org.junit.jupiter.api.Test
	public void getOld() throws Exception{
		System.out.println("겟");
		Old old = new Old();
		old.setOldNo(11);
		Assert.assertEquals(11, old.getOldNo());
	}

	
}