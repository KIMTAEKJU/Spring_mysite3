package com.douzone.mysite.vo;

import org.hibernate.validator.constraints.NotEmpty;

public class BoardVo 
{
	private Long no;
	@NotEmpty
	private String title;
	
	private String name;
	private Long hit;
	private String write_Date;
	private Long depth;
	@NotEmpty
	private String contents;
	private Long userNo;
	private Long gNo;
	private Long oNo;
	private Long totalCount;
	private Long commentCount;
	
	
	public Long getNo() {
		return no;
	}
	public void setNo(Long no) {
		this.no = no;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getHit() {
		return hit;
	}
	public void setHit(Long hit) {
		this.hit = hit;
	}
	public String getWrite_Date() {
		return write_Date;
	}
	public void setWrite_Date(String write_Date) {
		this.write_Date = write_Date;
	}
	public Long getDepth() {
		return depth;
	}
	public void setDepth(Long depth) {
		this.depth = depth;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public Long getUserNo() {
		return userNo;
	}
	public void setUserNo(Long userNo) {
		this.userNo = userNo;
	}
	public Long getgNo() {
		return gNo;
	}
	public void setgNo(Long gNo) {
		this.gNo = gNo;
	}
	public Long getoNo() {
		return oNo;
	}
	public void setoNo(Long oNo) {
		this.oNo = oNo;
	}
	public Long getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(Long totalCount) {
		this.totalCount = totalCount;
	}
	public Long getCommentCount() {
		return commentCount;
	}
	public void setCommentCount(Long commentCount) {
		this.commentCount = commentCount;
	}
	
	

	
	

	
}
