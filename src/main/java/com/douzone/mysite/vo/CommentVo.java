package com.douzone.mysite.vo;

import javax.validation.constraints.AssertTrue;

import org.hibernate.validator.constraints.NotEmpty;

public class CommentVo 
{
	private long no;
	@NotEmpty
	private String name;
	
	@NotEmpty
	private String password;
	
	@NotEmpty
	private String contents;
	
	private long boardNo;
	private String userNo;
	private String writeDate;
	private long commentNo;
	private long gNo;
	private long oNo;
	private long depth;
	
	
	
	
	
	public long getgNo() {
		return gNo;
	}
	public void setgNo(long gNo) {
		this.gNo = gNo;
	}
	public long getoNo() {
		return oNo;
	}
	public void setoNo(long oNo) {
		this.oNo = oNo;
	}
	public long getDepth() {
		return depth;
	}
	public void setDepth(long depth) {
		this.depth = depth;
	}
	public long getCommentNo() {
		return commentNo;
	}
	public void setCommentNo(long commentNo) {
		this.commentNo = commentNo;
	}
	public String getWriteDate() {
		return writeDate;
	}
	public void setWriteDate(String writeDate) {
		this.writeDate = writeDate;
	}
	public String getUserNo() {
		return userNo;
	}
	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}
	public long getBoardNo() {
		return boardNo;
	}
	public void setBoardNo(long boardNo) {
		this.boardNo = boardNo;
	}
	public long getNo() {
		return no;
	}
	public void setNo(long no) {
		this.no = no;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}

	
	
}
