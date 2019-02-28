package com.gkwang.blog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gkwang.blog.domain.Catalog;
import com.gkwang.blog.domain.User;
/**
 * 	分类仓库类
 * @Title: CatalogRepository.java
 * @Package:com.gkwang.blog.repository
 * @author:Wanggk 
 * @date:2018年10月30日
 * @version:V1.0
 */
@Repository
public interface CatalogRepository extends JpaRepository<Catalog, Integer> {
	/**
	 * 	根据用户查询
	 * @param:@param user
	 * @param:@return   
	 * @return:List<Catalog>  
	 * @author:wanggk
	 * @date:2018年10月26日
	 * @version:V1.0
	 */
	List<Catalog> findByUser(User user); 
	/**
	 * 	根据用户和名字查询
	 * @param:@param user
	 * @param:@param name
	 * @param:@return   
	 * @return:List<Catalog>  
	 * @author:wanggk
	 * @date:2018年10月26日
	 * @version:V1.0
	 */
	List<Catalog> findByUserAndName(User user,String name);
}	
