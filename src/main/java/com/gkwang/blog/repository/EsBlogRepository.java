package com.gkwang.blog.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.gkwang.blog.domain.EsBlog;


/**
 * 	ESBlog仓库类.
 * @Title: EsBlogRepository.java
 * @Package:com.gkwang.blog.repository
 * @author:Wanggk 
 * @date:2018年10月29日
 * @version:V1.0
 */
@Repository
public interface EsBlogRepository extends ElasticsearchRepository<EsBlog, String> {
 
	/**
	 * 	模糊查询(去重)
	 * @param:@param title
	 * @param:@param Summary
	 * @param:@param content
	 * @param:@param tags
	 * @param:@param pageable
	 * @param:@return   
	 * @return:Page<EsBlog>  
	 * @author:wanggk
	 * @date:2018年10月29日
	 * @version:V1.0
	 */
	Page<EsBlog> findDistinctEsBlogByTitleContainingOrSummaryContainingOrContentContainingOrTagsContaining(String title,String Summary,String content,String tags,Pageable pageable);
	/**
	 * 	根据Blog的ID查询ESBlog
	 * @param:@param blogId
	 * @param:@return   
	 * @return:EsBlog  
	 * @author:wanggk
	 * @date:2018年10月29日
	 * @version:V1.0
	 */
	EsBlog findByBlogId(Integer blogId);
	
	void deleteByBlogId(Integer blogId);
	
}
