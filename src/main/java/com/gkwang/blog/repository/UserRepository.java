package com.gkwang.blog.repository;


import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gkwang.blog.domain.User;

/**
 * 	用户仓库类
 * @Title: UserService.java
 * @Package:com.gkwang.blog.repository
 * @author:Wanggk 
 * @date:2018年10月18日
 * @version:V1.0
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
	/**
	 * 根据用户姓名分页查询用户列表
	 * @param:@param name
	 * @param:@param pageable
	 * @param:@return   
	 * @return:Page<User>  
	 * @author:wanggk
	 * @date:2018年10月18日
	 * @version:V1.0
	 */
	Page<User> findByNameLike(String name,Pageable pageable);
	/**
	 * 根据用户账号查询用户
	 * @param:@param username
	 * @param:@return   
	 * @return:User  
	 * @author:wanggk
	 * @date:2018年10月18日
	 * @version:V1.0
	 */
	User findByUsername(String username);
	
	/**
	 * 	根据名称列表查询用户列表
	 * @param:@param usernames
	 * @param:@return   
	 * @return:List<User>  
	 * @author:wanggk
	 * @date:2018年10月29日
	 * @version:V1.0
	 */
	List<User> findByUsernameIn(Collection<String> usernames);
}
