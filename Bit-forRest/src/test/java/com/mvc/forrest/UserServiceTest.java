package com.mvc.forrest;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;

import com.mvc.forrest.service.domain.Search;
import com.mvc.forrest.service.domain.User;
import com.mvc.forrest.service.user.UserService;

@SpringBootTest 
public class UserServiceTest {

	@Autowired
	private UserService userService;
	
//	@Test
	public void testAddUser() throws Exception {
		
		User user = new User();
		user.setUserId("testUserId");
		user.setUserName("testUserName");
		user.setPassword("testPasswd");
		user.setNickname("testNickname");
		user.setPhone("testPhone");
		user.setUserAddr("testAddr");
		user.setJoinPath("testPath");
		user.setUserRate(4);

		userService.addUser(user);
		user = userService.getUser(user.getUserId());

		//==> console 확인
		System.out.println(user);
		
		//==> API 확인
		assertEquals("testUserId", user.getUserId());
		assertEquals("testUserName", user.getUserName());
		assertEquals("testPasswd", user.getPassword());
		assertEquals("testPhone", user.getPhone());
		assertEquals("testNickname", user.getNickname());
		assertEquals("testAddr", user.getUserAddr());
		assertEquals("testPath", user.getJoinPath());
		assertEquals(4, 4, user.getUserRate());
		
	}

//	@Test
	public void testGetUser() throws Exception{
		User user = new User();
		user = userService.getUser("admin");

		userService.getUser("admin");
				
	}
	
	
//	@Test
	public void testUpdateUser() throws Exception{
		
		//테스트 아이디가 있는지 확인
		User user = userService.getUser("testUserId");
		
		//기존정보가 맞는지 확인
		assertEquals("testUserId", user.getUserId());
		assertEquals("testUserName", user.getUserName());
		assertEquals("testPasswd", user.getPassword());
		assertEquals("testPhone", user.getPhone());
		assertEquals("testNickname", user.getNickname());
		assertEquals("testAddr", user.getUserAddr());
		assertEquals("testPath", user.getJoinPath());
		assertEquals(4, 4, user.getUserRate());
		
		//새로운 데이터 입력
		user.setNickname("newNickname");
		user.setUserAddr("newUserAddr");
		user.setPhone("newPhone");
		user.setUserImg("newImg");
		
		//업데이트
		userService.updateUser(user);
		
		//업데이트 내용 확인
		user = userService.getUser("testUserId");
		assertEquals("newNickname", user.getNickname());
		assertEquals("newUserAddr", user.getUserAddr());
		assertEquals("newPhone", user.getPhone());
		assertEquals("newImg", user.getUserImg());

		
		assertEquals("admin", user.getUserId());

	}
	
//	@Test
	@Test
	public void testGetUserList() throws Exception{
		
		Search search = new Search();
		Map<String, Object> map = userService.getUserList(search);
		
		System.out.println(map);
		
	}

//	@Test
	public void testUpdateRecentDate() throws Exception{
		User user = new User();
		user = userService.getUser("admin");
		
		System.out.println("@@@날짜전 : "+user.getRecentDate()+"@@@");
		
		userService.updateRecentDate(user);
		
		System.out.println("@@@날짜후 : "+user.getRecentDate()+"@@@");

	}
	
	
}