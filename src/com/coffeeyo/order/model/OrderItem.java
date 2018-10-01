package com.coffeeyo.order.model;

import com.coffeeyo.common.model.Common;

public class OrderItem extends Common {
	private String orderno;
	private long itemno;
	private long pidx;
	private int amount;
	
	public String getOrderno() {
		return orderno;
	}
	public void setOrderno(String orderno) {
		this.orderno = orderno;
	}
	public long getItemno() {
		return itemno;
	}
	public void setItemno(long itemno) {
		this.itemno = itemno;
	}
	public long getPidx() {
		return pidx;
	}
	public void setPidx(long pidx) {
		this.pidx = pidx;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
}
