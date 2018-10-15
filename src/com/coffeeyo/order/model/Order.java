package com.coffeeyo.order.model;



import java.sql.Date;

import com.coffeeyo.common.model.Common;

public class Order extends Common {
	private String orderno;
	private String userid;
	private long total;
	private int payyn;
	private String readytm;
	private Date orddt;
	public String getOrderno() {
		return orderno;
	}
	public void setOrderno(String orderno) {
		this.orderno = orderno;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}
	public int getPayyn() {
		return payyn;
	}
	public void setPayyn(int payyn) {
		this.payyn = payyn;
	}
	public String getReadytm() {
		return readytm;
	}
	public void setReadytm(String readytm) {
		this.readytm = readytm;
	}
	public Date getOrddt() {
		return orddt;
	}
	public void setOrddt(Date orddt) {
		this.orddt = orddt;
	}
}
