package com.gkwang.blog.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gkwang.blog.domain.Authority;


/**
 * Authority 仓库类
 * @Title: AuthorityRepository.java
 * @Package:com.gkwang.blog.repository
 * @author:Wanggk 
 * @date:2018年10月30日
 * @version:V1.0
 */
@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Integer>{

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
	Page<Authority> findByNameLike(String name,Pageable pageable);
}
