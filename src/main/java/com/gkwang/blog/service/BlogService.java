package com.gkwang.blog.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gkwang.blog.domain.Blog;
import com.gkwang.blog.domain.Catalog;
import com.gkwang.blog.domain.User;

/**
 * 	博客管理接口
 * @Title: BlogService.java
 * @Package:com.gkwang.blog.service
 * @author:Wanggk 
 * @date:2018年10月25日
 * @version:V1.0
 */

public interface BlogService {
	
	/***
	 * 	保存博客
	 * @param:@param blog
	 * @param:@return   
	 * @return:Blog  
	 * @author:wanggk
	 * @date:2018年10月25日
	 * @version:V1.0
	 */
	Blog saveBlog(Blog blog); 
	
	/**
	 * 	删除博客
	 * @param:@param id   
	 * @return:void  
	 * @author:wanggk
	 * @date:2018年10月25日
	 * @version:V1.0
	 */
	void removeBlog(Integer id);
	
	
	/**
	 * 	根据ID查询博客
	 * @param:@param id
	 * @param:@return   
	 * @return:Blog  
	 * @author:wanggk
	 * @date:2018年10月25日
	 * @version:V1.0
	 */
	Blog getBlogById(Integer id);
	
	/**
	 * 	根据用户进行博客名称分页模糊查询(最新)
	 * @param:@param user
	 * @param:@param title
	 * @param:@param pageable
	 * @param:@return   
	 * @return:Page<Blog>  
	 * @author:wanggk
	 * @date:2018年10月25日
	 * @version:V1.0
	 */
	Page<Blog> listBlogsByTitleLike(User user,String title,Pageable pageable);
	
	/**
	 * 	根据用户进行博客名称分页模糊查询(最热)
	 * @param:@param user
	 * @param:@param title
	 * @param:@param pageable
	 * @param:@return   
	 * @return:Page<Blog>  
	 * @author:wanggk
	 * @date:2018年10月25日
	 * @version:V1.0
	 */
	Page<Blog> listBlogsByTitleLikeAndSort(User user,String title,Pageable pageable);
	
	/**
	 * 	阅读量递增
	 * @param:@param id   
	 * @return:void  
	 * @author:wanggk
	 * @date:2018年10月25日
	 * @version:V1.0
	 */
	void readsIncrease(Integer id);
	
	/**
	 * 	发表评论
	 * @param:@param blogId
	 * @param:@param commentContent
	 * @param:@return   
	 * @return:Blog  
	 * @author:wanggk
	 * @date:2018年10月25日
	 * @version:V1.0
	 */
	Blog createComment(Integer blogId,String commentContent);
	
	/**
	 * 	删除评论
	 * @param:@param blogId
	 * @param:@param commentId   
	 * @return:void  
	 * @author:wanggk
	 * @date:2018年10月25日
	 * @version:V1.0
	 */
	void removeComment(Integer blogId,Integer commentId);
	/**
	 * 	点赞
	 * @param:@param id
	 * @param:@return   
	 * @return:Blog  
	 * @author:wanggk
	 * @date:2018年10月25日
	 * @version:V1.0
	 */
	Blog createVote(Integer blogid);
	/**
	 * 取消点赞
	 * @param:@param id   
	 * @return:void  
	 * @author:wanggk
	 * @date:2018年10月25日
	 * @version:V1.0
	 */
	void removeVote(Integer blogid,Integer voteid);
	
	/**
	 * 根据分类进行查询
	 * @param:@param catalog
	 * @param:@param pageable
	 * @param:@return   
	 * @return:Page<Blog>  
	 * @author:wanggk
	 * @date:2018年10月26日
	 * @version:V1.0
	 */
	Page<Blog> listBlogsByCatalog(Catalog catalog,Pageable pageable);
	
	/**
	 * 	管理界面显示博客
	 * @param:@param name
	 * @param:@param pageable
	 * @param:@return   
	 * @return:Page<User>  
	 * @author:wanggk
	 * @date:2018年10月30日
	 * @version:V1.0
	 */
	Page<Blog> listBlogsByTitleLike(String title, Pageable pageable);
}
