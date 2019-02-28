package com.gkwang.blog.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gkwang.blog.domain.Comment;

public interface CommentService {
	
	/**
	 * 	根据ID获取评论
	 * @param:@param id
	 * @param:@return   
	 * @return:Comment  
	 * @author:wanggk
	 * @date:2018年10月25日
	 * @version:V1.0
	 */
	Comment getCommentById(Integer id);
	
	/**
	 * 	根据ID删除评论
	 * @param:@param id   
	 * @return:void  
	 * @author:wanggk
	 * @date:2018年10月25日
	 * @version:V1.0
	 */
	void removeComment(Integer id);
	/**
	 * 	管理界面根据内容查询评论
	 * @param:@param content
	 * @param:@param pageable
	 * @param:@return   
	 * @return:Page<Comment>  
	 * @author:wanggk
	 * @date:2018年10月30日
	 * @version:V1.0
	 */
	Page<Comment> listCommentsByContentLike(String content, Pageable pageable);
}
