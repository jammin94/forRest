package com.mvc.forrest.service.domain;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class Chat {

		private int chatMessageNo;
		private int chatRoomNo;
		private String sendUserId;
		private String chatMessage;
		private Timestamp createdAt;
		private int readOrNot;
		
}

