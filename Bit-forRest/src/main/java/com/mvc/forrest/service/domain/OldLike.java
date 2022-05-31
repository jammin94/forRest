package com.mvc.forrest.service.domain;

import lombok.Data;

@Data
public class OldLike {
	
	private int likeNo;
	private String userId;
	private int oldNo; // Merge 후 Data type old로 변경 예정 
	private int likeCount;
	
}
