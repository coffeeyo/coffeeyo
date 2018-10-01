package com.coffeeyo.product.comment.model;

import com.coffeeyo.common.model.Common;

public class ProductComm extends Common {
	private long pcidx;
	private long pidx;
	private String userid;
	private long pcpoint;
	
	public long getPcidx() {
		return pcidx;
	}
	public void setPcidx(long pcidx) {
		this.pcidx = pcidx;
	}
	public long getPidx() {
		return pidx;
	}
	public void setPidx(long pidx) {
		this.pidx = pidx;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public long getPcpoint() {
		return pcpoint;
	}
	public void setPcpoint(long pcpoint) {
		this.pcpoint = pcpoint;
	}
}
