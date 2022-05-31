package com.mvc.forrest.service.domain;

import java.io.File;
import java.sql.Date;
import java.util.List;


public class Product {
	
	private int prodNo;
	private int width;
	private int length;
	private int height;
	private String userId;
	private int prodCondition;
	private int prodQuantity;
	private String prodName;
	private String prodDetail;
	private short isRental;
	private int rentalCounting;
	private int rentalPrice;
	private String account;
	private int deposit ;
	private String category;
	private String returnAddress;
	private String prodImg;
	private String recentImg;
	//주소 따로or jsp에서??

	
	public Product(){
	}


	public int getProdNo() {
		return prodNo;
	}


	public void setProdNo(int prodNo) {
		this.prodNo = prodNo;
	}


	public int getWidth() {
		return width;
	}


	public void setWidth(int width) {
		this.width = width;
	}


	public int getLength() {
		return length;
	}


	public void setLength(int length) {
		this.length = length;
	}


	public int getHeight() {
		return height;
	}


	public void setHeight(int height) {
		this.height = height;
	}


	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}


	public int getProdCondition() {
		return prodCondition;
	}


	public void setProdCondition(int prodCondition) {
		this.prodCondition = prodCondition;
	}


	public int getProdQuantity() {
		return prodQuantity;
	}


	public void setProdQuantity(int prodQuantity) {
		this.prodQuantity = prodQuantity;
	}


	public String getProdName() {
		return prodName;
	}


	public void setProdName(String prodName) {
		this.prodName = prodName;
	}


	public String getProdDetail() {
		return prodDetail;
	}


	public void setProdDetail(String prodDetail) {
		this.prodDetail = prodDetail;
	}

	public short getIsRental() {
		return isRental;
	}


	public void setIsRental(short isRental) {
		this.isRental = isRental;
	}


	public int getRentalCounting() {
		return rentalCounting;
	}


	public void setRentalCounting(int rentalCounting) {
		this.rentalCounting = rentalCounting;
	}


	public int getRentalPrice() {
		return rentalPrice;
	}


	public void setRentalPrice(int rentalPrice) {
		this.rentalPrice = rentalPrice;
	}


	public String getAccount() {
		return account;
	}


	public void setAccount(String account) {
		this.account = account;
	}


	public int getDeposit() {
		return deposit;
	}


	public void setDeposit(int deposit) {
		this.deposit = deposit;
	}


	public String getCategory() {
		return category;
	}


	public void setCategory(String category) {
		this.category = category;
	}


	public String getReturnAddress() {
		return returnAddress;
	}


	public void setReturnAddress(String returnAddress) {
		this.returnAddress = returnAddress;
	}


	public String getProdImg() {
		return prodImg;
	}


	public void setProdImg(String prodImg) {
		this.prodImg = prodImg;
	}


	public String getRecentImg() {
		return recentImg;
	}


	public void setRecentImg(String recentImg) {
		this.recentImg = recentImg;
	}


	@Override
	public String toString() {
		return "Product [prodNo=" + prodNo + ", width=" + width + ", length=" + length + ", height=" + height
				+ ", userId=" + userId + ", prodCondition=" + prodCondition + ", prodQuantity=" + prodQuantity
				+ ", prodName=" + prodName + ", prodDetail=" + prodDetail + ", isRental=" + isRental
				+ ", rentalCounting=" + rentalCounting + ", rentalPrice=" + rentalPrice + ", account=" + account
				+ ", deposit=" + deposit + ", category=" + category + ", returnAddress=" + returnAddress + ", prodImg="
				+ prodImg + ", recentImg=" + recentImg + "]";
	}
	

	
}