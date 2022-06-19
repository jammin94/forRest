package com.mvc.forrest.service.chat;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mvc.forrest.dao.chat.ChatDAO;
import com.mvc.forrest.service.domain.Chat;



@Service
public class ChatService {
	
	@Autowired
	private ChatDAO chatDAO;
	
	public List<Chat> getListChat(int chatRoomNo) throws Exception{
		System.out.println("getListChat 실행 됨");
		return chatDAO.getListChat(chatRoomNo);
	}

}
