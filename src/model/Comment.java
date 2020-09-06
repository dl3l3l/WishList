package model;

import java.util.Date;

public class Comment {
	private int commentnum;
	private int postnum;
	private String id;
	private String content;
	private Date regdate;
	
	public int getCommentnum() {
		return commentnum;
	}
	public void setCommentnum(int commentnum) {
		this.commentnum = commentnum;
	}
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
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getRegdate() {
		return regdate;
	}
	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}
	
	@Override
	public String toString() {
		return "Comment [commentnum=" + commentnum + ", postnum=" + postnum + ", id=" + id + ", content=" + content
				+ ", regdate=" + regdate + "]";
	}
}
