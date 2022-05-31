package com.mvc.forrest.service.domain;

import java.sql.Timestamp;

import lombok.Data;

//==>회원정보를 모델링(추상화/캡슐화)한 Bean
@Data
public class User {
	
	///Field
	private String userId;
	private String password;
	private String nickname;
	private String userName;
	private String userAddr;
	private String phone;
	private String role;
	private String joinPath;
	private String userImg;
	private String pushToken;
	private Timestamp leaveApplyDate;
	private Timestamp leaveDate;
	private Timestamp recentDate;
	private Timestamp joinDate;

}