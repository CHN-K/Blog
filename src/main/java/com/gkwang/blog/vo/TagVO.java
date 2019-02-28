package com.gkwang.blog.vo;


import java.io.Serializable;


/**
 * 	Tag值对象
 * @Title: TagVO.java
 * @Package:com.gkwang.blog.vo
 * @author:Wanggk 
 * @date:2018年10月29日
 * @version:V1.0
 */
public class TagVO implements Serializable {
 
	private static final long serialVersionUID = 1L;
	
	private String name;
	private Long count;
	
	public TagVO(String name, Long l) {
		this.name = name;
		this.count = l;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}
 
}
