package com.gkwang.blog.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gkwang.blog.domain.Comment;

/**
 * 	 评论仓库类
 * @Title: CommentRepository.java
 * @Package:com.gkwang.blog.repository
 * @author:Wanggk 
 * @date:2018年10月25日
 * @version:V1.0
 */
@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer>{
	/**
	 * 	根据内容模糊查询评论
	 * @param:@param content
	 * @param:@param pageable
	 * @param:@return   
	 * @return:Page<Comment>  
	 * @author:wanggk
	 * @date:2018年10月30日
	 * @version:V1.0
	 */
	Page<Comment> findByContentLike(String content, Pageable pageable);

}
