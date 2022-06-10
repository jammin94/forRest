package com.mvc.forrest.service.domain;

import java.util.List;

import lombok.Data;

@Data
public class Img {

		private int imgNo;
		private int contentsNo;
		private String fileName;
		private String contentsFlag;
		//private String boardImg; 
		//this should be considered... didn't understand how to relate with imgTable
		//user 나 img 나 똑같음
}
