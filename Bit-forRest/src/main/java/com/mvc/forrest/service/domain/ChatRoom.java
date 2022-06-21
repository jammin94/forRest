package com.mvc.forrest.service.domain;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class ChatRoom {

		private int chatRoomNo;
		private String oldNo;
		private String prodNo;
		private String inquireUserId;
		private String ownerUserId;
		private int inquireUserExit;
		private int ownerUserExit;
		private Timestamp createdAt;
		
}

