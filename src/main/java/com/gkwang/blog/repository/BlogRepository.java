package com.gkwang.blog.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gkwang.blog.domain.Blog;
import com.gkwang.blog.domain.Catalog;
import com.gkwang.blog.domain.User;
/**
 * 	博客仓库类
 * @Title: BlogRepository.java
 * @Package:com.gkwang.blog.repository
 * @author:Wanggk 
 * @date:2018年10月30日
 * @version:V1.0
 */
@Repository
public interface BlogRepository extends JpaRepository<Blog,Integer>{
	
	
	/**
	 * 	根据用户名分页查询用户列表（最新）
	 * @param:@param title
	 * @param:@param user
	 * @param:@return   
	 * @return:Page<Blog>  
	 * @author:wanggk
	 * @date:2018年10月25日
	 * @version:V1.0
	 */
	Page<Blog> findByUserAndTitleLikeOrderByCreateTimeDesc(User user,String title,Pageable pageable);
	/**
	 * 	根据用户名、博客标题分页查询博客
	 * @param:@param user
	 * @param:@param title
	 * @param:@return   
	 * @return:Page<Blog>  
	 * @author:wanggk
	 * @date:2018年10月25日
	 * @version:V1.0
	 */
	Page<Blog> findByUserAndTitleLike(User user,String title,Pageable pageable);
	
	/**
	 * 	根据用户名分页查询用户列表
	 * @param user
	 * @param title
	 * @param sort
	 * @param pageable
	 * @return
	 */
	Page<Blog> findByTitleLikeAndUserOrTagsLikeAndUserOrderByCreateTimeDesc(String title,User user,String tags,User user2,Pageable pageable);
	/**
	 * 	根据用户名分页查询用户列表
	 * @param user
	 * @param title
	 * @param sort
	 * @param pageable
	 * @return
	 */
	Page<Blog> findByCatalog(Catalog catalog, Pageable pageable);
	
	/**
	 * 	管理界面根据标题查询
	 * @param:@param title
	 * @param:@param pageable
	 * @param:@return   
	 * @return:Page<Blog>  
	 * @author:wanggk
	 * @date:2018年10月30日
	 * @version:V1.0
	 */
	Page<Blog> findByTitleLike(String title, Pageable pageable);
	
}
