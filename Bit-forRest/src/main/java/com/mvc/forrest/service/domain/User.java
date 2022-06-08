package com.mvc.forrest.service.domain;

import java.sql.Timestamp;

import lombok.Data;

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
	private String reportedCount;
	private String reviewedCount;
	private double userRate;
	private Timestamp leaveApplyDate;
	private Timestamp leaveDate;
	private Timestamp recentDate;
	private Timestamp joinDate;

}