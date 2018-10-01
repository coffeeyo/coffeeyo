package com.coffeeyo.member.model;

import com.coffeeyo.common.model.Common;

public class Member extends Common {
	private String userid;
	private String passwd;
	private String uname;
	private String nick;
	private String hp;
	private int gender;
	private String birthday;
	private int job;
	private int ulevel;
	
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	public String getNick() {
		return nick;
	}
	public void setNick(String nick) {
		this.nick = nick;
	}
	public String getHp() {
		return hp;
	}
	public void setHp(String hp) {
		this.hp = hp;
	}
	public int getGender() {
		return gender;
	}
	public void setGender(int gender) {
		this.gender = gender;
	}
	
	public String getStrGender() {
		if(this.gender == 1 || this.gender == 3) {
			return "남";
		}
		else if(this.gender == 2 || this.gender == 4) {			
			return "여";
		}
		else {
			return "";
		}
	}
	
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public int getJob() {
		return job;
	}
	public void setJob(int job) {
		this.job = job;
	}
	public int getUlevel() {
		return ulevel;
	}
	public void setUlevel(int ulevel) {
		this.ulevel = ulevel;
	}
	public String getStrLevel() {
		if(this.ulevel < 10)
			return "회원";
		else 
			return "운영자";
	}
}
