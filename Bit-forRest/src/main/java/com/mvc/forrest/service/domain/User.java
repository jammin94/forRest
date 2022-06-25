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
	private int reportedCount;
	private String reviewedCount;
	private double userRate;
	private int profit;
	private int couponCount;
	private String email;
	private Timestamp leaveApplyDate;
	private Timestamp leaveDate;
	private Timestamp recentDate;
	private Timestamp joinDate;
	private boolean isAccountNonLocked;
	private boolean isDisabled;
	
	
	
    public boolean isDisabled() {
    	try {
	    	if(this.role.equals("leave")) {
	    		return false;
	    	}else {
	    		return true;
	    	}
    	}catch(Exception e) {
    		return true;
    	}
    }

	public void setDisabled(boolean isDisabled) {
		if(this.role.equals("leave")) {
			this.isDisabled = false;
		}else {
			this.isDisabled = true;
		}
	}

	public boolean isAccountNonLocked() {
    	if(this.reportedCount>=3) {
    		return false;
    	}else {
    		return true;
    	}
	}

	public void setAccountNonLocked(boolean isAccountNonLocked) {
    	if(this.reportedCount>=3) {
    		this.isAccountNonLocked = false;
    	}else {
    		this.isAccountNonLocked = true;
    	}
		
	}

	@Builder(builderClassName = "UserDetailRegister", builderMethodName = "userDetailRegister")
    public User(String username, String password, String email, Role role) {
        this.userName = username;
        this.password = password;
        this.userId = email;
        this.role = "user";
    }
    
    @Builder(builderClassName = "OAuth2Register", builderMethodName = "oauth2Register")
    public User(String userName, String password, String email, Role role, String provider
    			, String providerId, String id, String nickname, String userAddr, String phone) {
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.role = "user";
        this.joinPath = provider;
        this.userId=id;
        this.nickname=nickname;
        this.userAddr=userAddr;
        this.phone=phone;
    }

	public User() {
		
	}
}