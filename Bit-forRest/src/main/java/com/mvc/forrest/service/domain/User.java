package com.mvc.forrest.service.domain;

import java.sql.Timestamp;

import lombok.Builder;
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
	private int profit;
	private int couponCount;
	private String email;
	private Timestamp leaveApplyDate;
	private Timestamp leaveDate;
	private Timestamp recentDate;
	private Timestamp joinDate;
	
    @Builder(builderClassName = "UserDetailRegister", builderMethodName = "userDetailRegister")
    public User(String username, String password, String email, Role role) {
        this.userName = username;
        this.password = password;
        this.userId = email;
        this.role = "user";
    }
    
    @Builder(builderClassName = "OAuth2Register", builderMethodName = "oauth2Register")
    public User(String username, String password, String email, Role role, String provider, String providerId, String id) {
        this.userName = username;
        this.password = password;
        this.email = email;
        this.role = "user";
        this.joinPath = provider;
        this.userId=id;
//        this.providerId = providerId;
    }

	public User() {
		
	}

}