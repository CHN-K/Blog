package com.gkwang.blog.domain;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

/**
 * 	vote实体类
 * @Title: Vote.java
 * @Package:com.gkwang.blog.domain
 * @author:Wanggk 
 * @date:2018年10月25日
 * @version:V1.0
 */
@Entity
public class Vote implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id // 主键
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 自增长策略
	private Integer id; // 用户的唯一标识
 
	@OneToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
	@JoinColumn(name="user_id")
	private User user;
	
	@Column(nullable = false) // 映射为字段，值不能为空
	@org.hibernate.annotations.CreationTimestamp  // 由数据库自动创建时间
	private Timestamp createTime;
	
	

	protected Vote() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Vote(User user) {
		super();
		this.user = user;
	}

	public Vote(Integer id, User user, Timestamp createTime) {
		super();
		this.id = id;
		this.user = user;
		this.createTime = createTime;
	}


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "Vote [id=" + id + ", user=" + user + ", createTime=" + createTime + "]";
	}
	
}
