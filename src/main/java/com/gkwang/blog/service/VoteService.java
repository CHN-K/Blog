package com.gkwang.blog.service;

import com.gkwang.blog.domain.Vote;
/**
 * 	Vote服务接口
 * @Title: VoteService.java
 * @Package:com.gkwang.blog.service
 * @author:Wanggk 
 * @date:2018年10月30日
 * @version:V1.0
 */
public interface VoteService {
	
	/**
	 * 	根据ID获取vote
	 * @param:@param id
	 * @param:@return   
	 * @return:Vote  
	 * @author:wanggk
	 * @date:2018年10月25日
	 * @version:V1.0
	 */
	Vote getVoteById(Integer id);
	
	/**
	 * 	根据ID删除vote
	 * @param:@param id   
	 * @return:void  
	 * @author:wanggk
	 * @date:2018年10月25日
	 * @version:V1.0
	 */
	void removeVote(Integer id);
}
