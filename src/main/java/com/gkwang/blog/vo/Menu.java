package com.gkwang.blog.vo;

import java.io.Serializable;

/**
 * 	菜单 值对象.
 * @Title: Menu.java
 * @Package:com.gkwang.blog.vo
 * @author:Wanggk 
 * @date:2018年10月30日
 * @version:V1.0
 */
public class Menu implements Serializable{
 
	private static final long serialVersionUID = 1L;
	
	private String name;
	private String url;
	
	public Menu(String name, String url) {
		this.name = name;
		this.url = url;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
}
