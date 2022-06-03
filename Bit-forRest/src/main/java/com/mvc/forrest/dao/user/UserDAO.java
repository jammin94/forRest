package com.mvc.forrest.dao.user;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.mvc.forrest.service.domain.Search;
import com.mvc.forrest.service.domain.User;

@Repository
@Mapper
public interface UserDAO {

	public void addUser(User user) throws Exception;
	
	public User getUser(String userId) throws Exception ;
	
	public void updateUser(User user) throws Exception ;
	
	public void leaveUser(User user) throws Exception ;
	
	public void restrictUser(User user) throws Exception ;
	
	public void updateRecentDate(User user) throws Exception;
	
	public List<User> getUserList(Search search) throws Exception ;
	
	public int getTotalCount() throws Exception ;
	

	
	
}
