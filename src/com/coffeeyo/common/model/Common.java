package com.coffeeyo.common.model;

import java.util.Date;

public class Common {
	private int status;
	private Date createdt;
	private String createDay;
	private Date updatedt;
	private String options;
	private long price;
	private String comm;
	private String pname;
	private String image;
	private int amount;
	private long rnum;
	private String nickName;
	private String cateName;
	private int optprice;
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Date getCreatedt() {
		return createdt;
	}
	public void setCreatedt(Date createdt) {
		this.createdt = createdt;
	}
	public String getCreateDay() {
		return createDay;
	}
	public void setCreateDay(String createDay) {
		this.createDay = createDay;
	}
	public Date getUpdatedt() {
		return updatedt;
	}
	public void setUpdatedt(Date updatedt) {
		this.updatedt = updatedt;
	}
	public String getOptions() {
		return options;
	}
	public void setOptions(String options) {
		this.options = options;
	}
	public long getPrice() {
		return price;
	}
	public void setPrice(long price) {
		this.price = price;
	}
	public String getComm() {
		return comm;
	}
	public void setComm(String comment) {
		this.comm = comment;
	}
	public String getPname() {
		return pname;
	}
	public void setPname(String pname) {
		this.pname = pname;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public long getRnum() {
		return rnum;
	}
	public void setRnum(long rnum) {
		this.rnum = rnum;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nick) {
		this.nickName = nick;
	}
	public String getCateName() {
		return cateName;
	}
	public void setCateName(String cateName) {
		this.cateName = cateName;
	}
	public int getOptprice() {
		return optprice;
	}
	public void setOptprice(int optprice) {
		this.optprice = optprice;
	}
	
}
