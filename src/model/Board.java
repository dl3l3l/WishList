package model;

import java.util.Date;

public class Board {
	private int postnum;
	private String id;
	private String subject;
	private String content;
	private String file;
	private Date regdate;
	private int readcnt;
	private int type;
	
	public int getPostnum() {
		return postnum;
	}
	public void setPostnum(int postnum) {
		this.postnum = postnum;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = file;
	}
	public Date getRegdate() {
		return regdate;
	}
	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}
	public int getReadcnt() {
		return readcnt;
	}
	public void setReadcnt(int readcnt) {
		this.readcnt = readcnt;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	@Override
	public String toString() {
		return "Board [postnum=" + postnum + ", id=" + id + ", subject=" + subject + ", content=" + content
				+ ", file=" + file + ", regdate=" + regdate + ", readcnt=" + readcnt + ", type=" + type + "]";
	}
		
}
