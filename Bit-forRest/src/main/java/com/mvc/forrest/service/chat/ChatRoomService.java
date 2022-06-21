package com.mvc.forrest.service.chat;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mvc.forrest.dao.chat.ChatRoomDAO;
import com.mvc.forrest.service.domain.ChatRoom;

@Service
public class ChatRoomService {
	
	@Autowired
	private ChatRoomDAO chatRoomDAO;
	
	public List<ChatRoom> getListChatRoom(int oldNo) throws Exception{
		System.out.println("getListChatRoom 실행 됨");
		return chatRoomDAO.getListChatRoom(oldNo);
	}

}
