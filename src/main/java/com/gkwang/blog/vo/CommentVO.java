package com.gkwang.blog.vo;

import java.io.Serializable;


public class CommentVO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Integer blogid;
	private Integer commentid;
	public CommentVO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public CommentVO(Integer blogid, Integer commentid) {
		super();
		this.blogid = blogid;
		this.commentid = commentid;
	}
	public Integer getBlogid() {
		return blogid;
	}
	public void setBlogid(Integer blogid) {
		this.blogid = blogid;
	}
	public Integer getCommentid() {
		return commentid;
	}
	public void setCommentid(Integer commentid) {
		this.commentid = commentid;
	}
}
