package com.coffeeyo.board.model;

import com.coffeeyo.common.model.Common;

public class Board extends Common {
	private long bidx;
	private String userid;
	private long cidx;
	private long pidx;
	private String subject;
	private String notiyn;
	private long likecnt;
	private long readcnt;
	
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
	public long getCidx() {
		return cidx;
	}
	public void setCidx(long cidx) {
		this.cidx = cidx;
	}
	public long getPidx() {
		return pidx;
	}
	public void setPidx(long pidx) {
		this.pidx = pidx;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getNotiyn() {
		return notiyn;
	}
	public void setNotiyn(String notiyn) {
		this.notiyn = notiyn;
	}
	public long getLikecnt() {
		return likecnt;
	}
	public void setLikecnt(long likecnt) {
		this.likecnt = likecnt;
	}
	public long getReadcnt() {
		return readcnt;
	}
	public void setReadcnt(long readcnt) {
		this.readcnt = readcnt;
	}
}
