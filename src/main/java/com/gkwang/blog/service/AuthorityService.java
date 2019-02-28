package com.gkwang.blog.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gkwang.blog.domain.Authority;

/**
 * Authority 服务接口.
 * @Title: AuthorityService.java
 * @Package:com.gkwang.blog.service
 * @author:Wanggk 
 * @date:2018年10月30日
 * @version:V1.0
 */
public interface AuthorityService {
	 
	
	/**
	 * 	根据id获取 Authority
	 * @param:@param id
	 * @param:@return   
	 * @return:Authority  
	 * @author:wanggk
	 * @date:2018年10月30日
	 * @version:V1.0
	 */
	Authority getAuthorityById(Integer id);
	/**
	 * 	根据名字模糊查找角色
	 * @param:@param name
	 * @param:@param pageable
	 * @param:@return   
	 * @return:Page<Authority>  
	 * @author:wanggk
	 * @date:2018年10月30日
	 * @version:V1.0
	 */
	Page<Authority> listRolesByNameLike(String name, Pageable pageable);
	/**
	 * 	保存角色
	 * @param:@param authority   
	 * @return:void  
	 * @author:wanggk
	 * @date:2018年10月30日
	 * @version:V1.0
	 */
	void saveAuthority(Authority authority);
	/**
	 * 	删除角色
	 * @param:@param id   
	 * @return:void  
	 * @author:wanggk
	 * @date:2018年10月30日
	 * @version:V1.0
	 */
	void removeRole(Integer id);
}
