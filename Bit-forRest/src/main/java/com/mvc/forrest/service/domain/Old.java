package com.mvc.forrest.service.domain;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class Old {
	private int oldNo;
	private int oldPrice;
	private String oldTitle;
	private String oldDetail;
	private Timestamp oldDate;
	private int oldView;
	private String category;
	private short oldState;



	public int getOldNo() {
		return oldNo;
	}
	public void setOldNo(int oldNo) {
		this.oldNo = oldNo;
	}
	public int getOldPrice() {
		return oldPrice;
	}
	public void setOldPrice(int oldPrice) {
		this.oldPrice = oldPrice;
	}
	public String getOldTitle() {
		return oldTitle;
	}
	public void setOldTitle(String oldTitle) {
		this.oldTitle = oldTitle;
	}
	public String getOldDetail() {
		return oldDetail;
	}
	public void setOldDetail(String oldDetail) {
		this.oldDetail = oldDetail;
	}
	public Timestamp getOldDate() {
		return oldDate;
	}
	public void setOldDate(Timestamp oldDate) {
		this.oldDate = oldDate;
	}
	public int getOldView() {
		return oldView;
	}
	public void setOldView(int oldView) {
		this.oldView = oldView;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public short getOldState() {
		return oldState;
	}
	public void setOldState(short oldState) {
		this.oldState = oldState;
	}

	@Override
	public String toString() {
		return "Old  : [oldTitle]" + oldTitle
				+ "[oldDate]" + oldDate + "[oldPrice]" +oldPrice
				+ "[oldView]" + oldView + "[category]" +category
				+ "[oldState]" + oldState;}
	}
