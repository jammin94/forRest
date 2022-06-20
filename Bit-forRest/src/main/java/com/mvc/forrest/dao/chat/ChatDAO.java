package com.mvc.forrest.dao.chat;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mvc.forrest.service.domain.Chat;

@Mapper
public interface ChatDAO {
	
	public List<Chat> getListChat(int chatRoomNo)  throws Exception;
	
}
