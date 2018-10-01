package com.coffeeyo.board.comment.model;

import com.coffeeyo.common.model.Common;

public class Comment extends Common {
	private long bcidx;
	private long bidx;
	private String userid;
	
	public long getBcidx() {
		return bcidx;
	}
	public void setBcidx(long bcidx) {
		this.bcidx = bcidx;
	}
	public long getBidx() {
		return bidx;
	}
	public void setBidx(long bidx) {
		this.bidx = bidx;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	
}
