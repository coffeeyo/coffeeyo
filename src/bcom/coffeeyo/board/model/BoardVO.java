package bcom.coffeeyo.board.model;

import java.sql.Date;

public class BoardVO {
	private int bidx;
	private String userid;
	private int cidx;
	private int pidx;
	private String subject;
	private String comm;
	private String image;
	private String notiyn;
	private int likecnt;
	private int readcnt;
	private Date createdt;
	private Date updatedt;
	private String pname;
	private String nick;
	private String cname;
	private int bcidx;
	private int status;
	private int likeidx;
	
	public int getBcidx() {
		return bcidx;
	}
	public void setBcidx(int bcidx) {
		this.bcidx = bcidx;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public String getNick() {
		return nick;
	}
	public void setNick(String nick) {
		this.nick = nick;
	}
	public String getPname() {
		return pname;
	}
	public void setPname(String pname) {
		this.pname = pname;
	}
	public int getBidx() {
		return bidx;
	}
	public void setBidx(int bidx) {
		this.bidx = bidx;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public int getCidx() {
		return cidx;
	}
	public void setCidx(int cidx) {
		this.cidx = cidx;
	}
	public int getPidx() {
		return pidx;
	}
	public void setPidx(int pidx) {
		this.pidx = pidx;
	}
	public String getSubject() {
		return subject;
	}
	public String getShortSubject() {
		String temp=subject;
		if(subject.length()>10) {			
			temp=subject.substring(0,10)+"...";
		}
		return temp;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getComm() {
		String temp=comm;
		if(temp!=null && temp.length()!=0) {
			temp=temp.replaceAll("\n", "<br/>");
		}
		return temp;
	}
	public String getCommNbr() {
		String temp=comm;
		if(temp!=null && temp.length()!=0) {
			temp=temp.replaceAll("<br/>","");
		}
		return temp;
	}
	public void setComm(String comm) {
		this.comm = comm;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getNotiyn() {
		return notiyn;
	}
	public void setNotiyn(String notiyn) {
		this.notiyn = notiyn;
	}
	public int getLikecnt() {
		return likecnt;
	}
	public void setLikecnt(int likecnt) {
		this.likecnt = likecnt;
	}
	public int getReadcnt() {
		return readcnt;
	}
	public void setReadcnt(int readcnt) {
		this.readcnt = readcnt;
	}
	public Date getCreatedt() {
		return createdt;
	}
	public void setCreatedt(Date createdt) {
		this.createdt = createdt;
	}
	public Date getUpdatedt() {
		return updatedt;
	}
	public void setUpdatedt(Date updatedt) {
		this.updatedt = updatedt;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getLikeidx() {
		return likeidx;
	}
	public void setLikeidx(int likeidx) {
		this.likeidx = likeidx;
	}
	
}
