package com.mvc.forrest.dao.chat;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mvc.forrest.service.domain.ChatRoom;

@Mapper
public interface ChatRoomDAO {
	
	public List<ChatRoom> getListChatRoom(int oldNo)  throws Exception;
	
}
