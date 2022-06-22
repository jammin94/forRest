package com.mvc.forrest.service.domain;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class Board {

		private int boardNo;
		private String boardTitle;
		private String boardDetail;
		private Timestamp boardDate;
		private int boardPin;
		private String boardFlag;
		private String category;
		private String couponURL;
		//private String boardImg; 
		//this should be considered... didn't understand how to relate with imgTable
}
