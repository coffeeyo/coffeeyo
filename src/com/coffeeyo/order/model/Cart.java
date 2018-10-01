package com.coffeeyo.order.model;

import com.coffeeyo.common.model.Common;

public class Cart extends Common {
	private long cidx;
	private String userid;
	private long pidx;
	private String buychk;
	private String mode;
	
	public long getCidx() {
		return cidx;
	}
	public void setCidx(long cidx) {
		this.cidx = cidx;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public long getPidx() {
		return pidx;
	}
	public void setPidx(long pidx) {
		this.pidx = pidx;
	}
	public String getBuychk() {
		return buychk;
	}
	public void setBuychk(String buychk) {
		this.buychk = buychk;
	}
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
}
