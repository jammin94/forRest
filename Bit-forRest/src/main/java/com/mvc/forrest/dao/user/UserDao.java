package com.mvc.forrest.dao.user;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.mvc.forrest.common.Search;
import com.mvc.forrest.service.domain.User;

@Repository
@Mapper
public interface UserDao {

	public void addUser(User user) throws Exception;
	
	public String getUser(String userId) throws Exception ;
	
	public void updateUser(User user) throws Exception ;
	
	public void leaveUser(User user) throws Exception ;
	
	public void restrictUser(User user) throws Exception ;
	
	public List<User> getUserList(String userId) throws Exception ;
	
	public int getTotalCount(Search search) throws Exception ;
	
	
}
