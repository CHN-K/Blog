package com.gkwang.blog.vo;

import java.io.Serializable;

import com.gkwang.blog.domain.Catalog;
/**
 * 	Catalog的值对象
 * @Title: CatalogVO.java
 * @Package:com.gkwang.blog.vo
 * @author:Wanggk 
 * @date:2018年10月30日
 * @version:V1.0
 */
public class CatalogVO implements Serializable{
private static final long serialVersionUID = 1L;
	
	private String username;
	private Catalog catalog;
	public CatalogVO() {
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Catalog getCatalog() {
		return catalog;
	}

	public void setCatalog(Catalog catalog) {
		this.catalog = catalog;
	}
}
