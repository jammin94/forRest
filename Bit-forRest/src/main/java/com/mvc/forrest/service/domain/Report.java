package com.mvc.forrest.service.domain;

import java.sql.Date;

import lombok.Data;

@Data
public class Report {
	
	private int reportNo;
	private String reportUser;
	private String reportedUser;
	private int reportOldNo;
	private int reportChatroomNo;
	private String reportCategory;
	private Date reportDate;
	private String reportChat;
	private int reportCode;
	
}
