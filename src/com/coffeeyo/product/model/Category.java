package com.coffeeyo.product.model;

import com.coffeeyo.common.model.Common;

public class Category extends Common {
	private long cidx;
	private String userid;
	private String cname;
	
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
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
}
