package com.gkwang.blog.service;

import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gkwang.blog.domain.User;

/**
 * 	用户服务接口
 * @Title: UserService.java
 * @Package:com.gkwang.blog.service
 * @author:Wanggk 
 * @date:2018年10月18日
 * @version:V1.0
 */
public interface UserService {
	/**
	 * 保存用户
	 * @param:@param user
	 * @param:@return   
	 * @return:User  
	 * @author:wanggk
	 * @date:2018年10月18日
	 * @version:V1.0
	 */
	void saveUser(User user);
	
	/**
	 * 删除用户
	 * @param:@param id   
	 * @return:void  
	 * @author:wanggk
	 * @date:2018年10月18日
	 * @version:V1.0
	 */
	void removeUser(Integer id);
	
	/**
	 * 删除列表里的用户
	 * @param:@param users   
	 * @return:void  
	 * @author:wanggk
	 * @date:2018年10月18日
	 * @version:V1.0
	 */
	void removeUsersInBatch(List<User> users);
	
	/**
	 * 更新用户
	 * @param:@param user
	 * @param:@return   
	 * @return:User  
	 * @author:wanggk
	 * @date:2018年10月18日
	 * @version:V1.0
	 */
	User updateUser(User user);
	
	/**
	 * 通过ID获取用户
	 * @param:@param id
	 * @param:@return   
	 * @return:User  
	 * @author:wanggk
	 * @date:2018年10月18日
	 * @version:V1.0
	 */
	User getUserById(Integer id);
	
	/**
	 * 获取用户列表
	 * @param:@return   
	 * @return:List<User>  
	 * @author:wanggk
	 * @date:2018年10月18日
	 * @version:V1.0
	 */
	List<User> listUsers();
	
	/**
	 * 根据用户名进行分页模糊查询
	 * @param:@param name
	 * @param:@param pageable
	 * @param:@return   
	 * @return:Page<User>  
	 * @author:wanggk
	 * @date:2018年10月18日
	 * @version:V1.0
	 */
	Page<User> listUsersByNameLike(String name, Pageable pageable);
	/**
	 * 	根据名称列表查询
	 * @param:@param usernames
	 * @param:@return   
	 * @return:List<User>  
	 * @author:wanggk
	 * @date:2018年10月29日
	 * @version:V1.0
	 */
	List<User> listUsersByUsernames(Collection<String> usernames);
}

