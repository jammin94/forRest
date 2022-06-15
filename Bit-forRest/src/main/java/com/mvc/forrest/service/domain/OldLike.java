package com.mvc.forrest.service.domain;

import lombok.Data;

@Data
public class OldLike {
	
	private int oldLikeNo;
	private String userId;
	private Old old; 
	private int likeCount;
	
}
