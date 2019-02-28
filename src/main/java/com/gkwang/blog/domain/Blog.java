package com.gkwang.blog.domain;


import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.NotEmpty;
import com.github.rjeschke.txtmark.Processor;

/**
 * 	Blog实体类
 * @Title: Blog.java
 * @Package:com.gkwang.blog.domain
 * @author:Wanggk 
 * @date:2018年10月25日
 * @version:V1.0
 */
@Entity
public class Blog  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@NotEmpty(message="标题不能为空")
	@Size(min=2,max=50)
	@Column(nullable=false,length=50)
	private String title;
	
	@NotEmpty(message="摘要不能为空")
	@Size(min=2,max=300)
	@Column(nullable=false)
	private String summary;
	
	//存储markdown保存得内容
	@Lob
	@Basic(fetch=FetchType.LAZY)
	@NotEmpty(message="内容不能为空")
	@Size(min=2)
	@Column(nullable=false)
	private String content;
	
	//将markdown格式得内容转为html
	@Lob
	@Basic(fetch=FetchType.LAZY)
	@NotEmpty(message="内容不能为空")
	@Size(min=2)
	@Column(nullable=false)
	private String htmlContent;
	
	@OneToOne(cascade=CascadeType.DETACH,fetch=FetchType.LAZY)
	@JoinColumn(name="user_id")
	private User user;
	
	@Column(nullable=false)
	@CreationTimestamp //数据库自动创建时间
	private Timestamp createTime;
	
	@Column(name="readSize")
	private Integer readSize = 0; 
	 
	@Column(name="commentSize")
	private Integer commentSize = 0; 

	@Column(name="voteSize")
	private Integer voteSize = 0;  
	
	@Column(name="tags",length=100)
	private String tags; 
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "blog_comment", joinColumns = @JoinColumn(name = "blog_id", referencedColumnName = "id"), 
		inverseJoinColumns = @JoinColumn(name = "comment_id", referencedColumnName = "id"))
	private List<Comment> comments;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "blog_vote", joinColumns = @JoinColumn(name = "blog_id", referencedColumnName = "id"), 
		inverseJoinColumns = @JoinColumn(name = "vote_id", referencedColumnName = "id"))
	private List<Vote> votes;
	
	
	@OneToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
	@JoinColumn(name="catalog_id")
	private Catalog catalog;
	
	protected Blog() {
		
	}
	
	public Blog(String title, String summary,String content) {
		this.title = title;
		this.summary = summary;
		this.content = content;
	}
	
	public Blog(Integer id, String title, String summery, String content, String htmlContent, User user,
			Timestamp createTime, Integer readSize, Integer commentSize, Integer voteSize) {
		super();
		this.id = id;
		this.title = title;
		this.summary = summery;
		this.content = content;
		this.htmlContent = htmlContent;
		this.user = user;
		this.createTime = createTime;
		this.readSize = readSize;
		this.commentSize = commentSize;
		this.voteSize = voteSize;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
		this.htmlContent= Processor.process(content);
	}

	public String getHtmlContent() {
		return htmlContent;
	}

	public void setHtmlContent(String htmlContent) {
		this.htmlContent = htmlContent;
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

	public void setCreaTetime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Integer getReadSize() {
		return readSize;
	}

	public void setReadSize(Integer readSize) {
		this.readSize = readSize;
	}

	public Integer getCommentSize() {
		return commentSize;
	}

	public void setCommentSize(Integer commentSize) {
		this.commentSize = commentSize;
	}

	public Integer getVoteSize() {
		return voteSize;
	}

	public void setVoteSize(Integer voteSize) {
		this.voteSize = voteSize;
	}

	
	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
		this.commentSize = comments.size();
	}
	
	/**
	 * 添加评论
	 * @param comment
	 */
	public void addComment(Comment comment) {
		this.comments.add(comment);
		this.commentSize = this.comments.size();
	}
	/**
	 * 删除评论
	 * @param comment
	 */
	public void removeComment(Integer commentId) {
		for (int index=0; index < this.comments.size(); index ++ ) {
			if (comments.get(index).getId() == commentId) {
				this.comments.remove(index);
				break;
			}
		}
		
		this.commentSize = this.comments.size();
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	
	
	

	public List<Vote> getVotes() {
		return votes;
	}

	public void setVotes(List<Vote> votes) {
		this.votes = votes;
		this.voteSize = votes.size();
	}
	/**
	 * 点赞
	 * @param vote
	 * @return
	 */
	public boolean addVote(Vote vote) {
		boolean isExist = false;
		// 判断重复
		for (int index=0; index < this.votes.size(); index ++ ) {
			if (this.votes.get(index).getUser().getId() == vote.getUser().getId()) {
				isExist = true;
				break;
			}
		}
		
		if (!isExist) {
			this.votes.add(vote);
			this.voteSize = this.votes.size();
		}

		return isExist;
	}
	/**
	 * 取消点赞
	 * @param voteId
	 */
	public void removeVote(Integer voteId) {
		for (int index=0; index < this.votes.size(); index ++ ) {
			if (this.votes.get(index).getId() == voteId) {
				this.votes.remove(index);
				break;
			}
		}
		
		this.voteSize = this.votes.size();
	}
	
	public Catalog getCatalog() {
		return catalog;
	}
	public void setCatalog(Catalog catalog) {
		this.catalog = catalog;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "Blog [id=" + id + ", title=" + title + ", summary=" + summary + ", content=" + content
				+ ", htmlContent=" + htmlContent + ", user=" + user + ", createTime=" + createTime + ", readSize="
				+ readSize + ", commentSize=" + commentSize + ", voteSize=" + voteSize + ", tags=" + tags
				+ ", comments=" + comments + ", votes=" + votes + ", catalog=" + catalog + "]";
	}
	
	
	
}
