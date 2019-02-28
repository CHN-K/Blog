package com.gkwang.blog.service;
 

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gkwang.blog.domain.EsBlog;
import com.gkwang.blog.domain.User;
import com.gkwang.blog.vo.TagVO;


/**
 * Blog 服务接口.
 * @Title: EsBlogService.java
 * @Package:com.gkwang.blog.service
 * @author:Wanggk 
 * @date:2018年10月29日
 * @version:V1.0
 */
public interface EsBlogService {
 	
	/**
	 *  	删除Blog
	 * @param:@param id   
	 * @return:void  
	 * @author:wanggk
	 * @date:2018年10月29日
	 * @version:V1.0
	 */
	void removeEsBlog(String id);
	
	/**
	 * 	更新 EsBlog
	 * @param:@param esBlog
	 * @param:@return   
	 * @return:EsBlog  
	 * @author:wanggk
	 * @date:2018年10月29日
	 * @version:V1.0
	 */
	EsBlog updateEsBlog(EsBlog esBlog);
	
	/**
	 * 	根据id获取Blog
	 * @param:@param blogid
	 * @param:@return   
	 * @return:EsBlog  
	 * @author:wanggk
	 * @date:2018年10月29日
	 * @version:V1.0
	 */
	EsBlog getEsBlogByBlogId(Integer blogid);
 
	/**
	 * 	最新博客列表，分页
	 * @param:@param keyword
	 * @param:@param pageable
	 * @param:@return   
	 * @return:Page<EsBlog>  
	 * @author:wanggk
	 * @date:2018年10月29日
	 * @version:V1.0
	 */
	Page<EsBlog> listNewestEsBlogs(String keyword, Pageable pageable);
 
	/**
	 * 	最热博客列表，分页
	 * @param:@param keyword
	 * @param:@param pageable
	 * @param:@return   
	 * @return:Page<EsBlog>  
	 * @author:wanggk
	 * @date:2018年10月29日
	 * @version:V1.0
	 */
	Page<EsBlog> listHotestEsBlogs(String keyword, Pageable pageable);
	
	/**
	 * 	博客列表，分页
	 * @param:@param pageable
	 * @param:@return   
	 * @return:Page<EsBlog>  
	 * @author:wanggk
	 * @date:2018年10月29日
	 * @version:V1.0
	 */
	Page<EsBlog> listEsBlogs(Pageable pageable);
	/**
	 * 	最新前5
	 * @param:@return   
	 * @return:List<EsBlog>  
	 * @author:wanggk
	 * @date:2018年10月29日
	 * @version:V1.0
	 */
	List<EsBlog> listTop5NewestEsBlogs();
	
	/**
	 * 	最热前5
	 * @param:@return   
	 * @return:List<EsBlog>  
	 * @author:wanggk
	 * @date:2018年10月29日
	 * @version:V1.0
	 */
	List<EsBlog> listTop5HotestEsBlogs();
	
	/**
	 * 	最热前 30 标签
	 * @param:@return   
	 * @return:List<TagVO>  
	 * @author:wanggk
	 * @date:2018年10月29日
	 * @version:V1.0
	 */
	List<TagVO> listTop30Tags();

	/**
	 * 	最热前12用户
	 * @param:@return   
	 * @return:List<User>  
	 * @author:wanggk
	 * @date:2018年10月29日
	 * @version:V1.0
	 */
	List<User> listTop12Users();
	
	void removeByBlogId(Integer blogid);
}
