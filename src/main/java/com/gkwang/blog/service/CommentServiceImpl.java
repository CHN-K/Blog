package com.gkwang.blog.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gkwang.blog.domain.Comment;
import com.gkwang.blog.repository.CommentRepository;

/**
 * 	评论管理接口实现
 * @Title: CommentServiceImpl.java
 * @Package:com.gkwang.blog.service
 * @author:Wanggk 
 * @date:2018年10月25日
 * @version:V1.0
 */
@Service
public class CommentServiceImpl implements CommentService {
	
	@Autowired
	private CommentRepository commentRepository;

	@Override
	public Comment getCommentById(Integer id) {
		// TODO Auto-generated method stub
		Comment comment = commentRepository.findOne(id);
		return comment;
	}

	@Override
	public void removeComment(Integer id) {
		// TODO Auto-generated method stub
		commentRepository.delete(id);
	}

	@Override
	public Page<Comment> listCommentsByContentLike(String content, Pageable pageable) {
		// TODO Auto-generated method stub
		content = "%" + content + "%";
		Page<Comment> comments = commentRepository.findByContentLike(content,pageable);
		return comments;
	}

}
