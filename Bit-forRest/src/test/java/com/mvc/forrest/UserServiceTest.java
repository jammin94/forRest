package com.mvc.forrest;



import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.mvc.forrest.service.domain.User;
import com.mvc.forrest.service.user.UserService;




@SpringBootTest 
public class UserServiceTest {

	@Autowired
	private UserService userService;

	@Test
	public void testGetUser() throws Exception {
		
		
		userService.getUser("admin");
		
		User user = userService.getUser("admin");

		//==> console 확인
		System.out.println(user);
		
		//==> API 확인
		assertEquals("admin", user.getUserId());

	}
	

}