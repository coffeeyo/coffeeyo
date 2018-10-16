package com.coffeeyo.product.model;

import com.coffeeyo.common.model.Common;

public class Product extends Common {
	private long pidx;
	private long cidx;
	private String userid;
	private String pname;
	private int maketm;
	private long recomm;
	private float pcPointAvg;
	
	public long getPidx() {
		return pidx;
	}
	public void setPidx(long pidx) {
		this.pidx = pidx;
	}
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
	public String getPname() {
		return pname;
	}
	public String getPnameShort() {
		return pname.substring(0,10)+"...";
	}
	public void setPname(String pname) {
		this.pname = pname;
	}
	public int getMaketm() {
		return maketm;
	}
	public void setMaketm(int maketm) {
		this.maketm = maketm;
	}
	public long getRecomm() {
		return recomm;
	}
	public void setRecomm(long recomm) {
		this.recomm = recomm;
	}
	public float getPcPointAvg() {
		return pcPointAvg;
	}
	public void setPcPointAvg(float pcPointAvg) {
		this.pcPointAvg = pcPointAvg;
	}
	
}
