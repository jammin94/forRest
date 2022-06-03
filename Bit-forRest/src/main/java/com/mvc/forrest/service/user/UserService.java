package com.mvc.forrest.service.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mvc.forrest.dao.user.UserDAO;
import com.mvc.forrest.service.domain.Search;
import com.mvc.forrest.service.domain.User;

@Service
public class UserService {
	
	@Autowired
	private UserDAO userdao;
	
	public void addUser(User user) throws Exception {
		userdao.addUser(user);
		System.out.println("UserService // addUser");
	}
	
	public User getUser(String userId) throws Exception {
		return userdao.getUser(userId);
	}
	
	public User getMyPage(String userId) throws Exception {
		return userdao.getUser(userId);
	}
	
	public Map<String, Object> getUserList(Search search) throws Exception {
		List<User> list = userdao.getUserList(search);
		int totalCount = userdao.getTotalCount();
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);
		map.put("totalcount", totalCount);
		
		return map ;
	}
	
	public void updateUser(User user) throws Exception {
		userdao.updateUser(user);
	}
	
	public void updateRecentDate(User user) throws Exception{
		userdao.updateRecentDate(user);
	}
	
	public void leaverUser(User user) throws Exception {
		userdao.leaveUser(user);
	}
	
	public void restrictUser(User user) throws Exception {
		userdao.restrictUser(user);
	}
	
	public boolean checkDuplication(String userId) throws Exception {
		boolean result=true;
		User user=userdao.getUser(userId);
		if(user != null) {
			result=false;
		}
		return result;
	}
	


}