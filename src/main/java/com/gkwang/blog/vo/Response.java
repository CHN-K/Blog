package com.gkwang.blog.vo;

/**
 * 	封装返回对象
 * @Title: Response.java
 * @Package:com.gkwang.blog.vo
 * @author:Wanggk 
 * @date:2018年10月18日
 * @version:V1.0
 */
public class Response {
	private boolean success;
	private String message;
	private Object body;
	
	public Response() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Response(boolean success) {
		super();
		this.success = success;
	}

	public Response(boolean success, String message) {
		super();
		this.success = success;
		this.message = message;
	}

	public Response(boolean success, String message, Object body) {
		super();
		this.success = success;
		this.message = message;
		this.body = body;
	}

	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getBody() {
		return body;
	}
	public void setBody(Object body) {
		this.body = body;
	}
	
}
