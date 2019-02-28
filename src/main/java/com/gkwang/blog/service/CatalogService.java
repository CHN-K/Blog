package com.gkwang.blog.service;

import java.util.List;

import com.gkwang.blog.domain.Catalog;
import com.gkwang.blog.domain.User;

public interface CatalogService {
	/**
	 * 保存catalog
	 * @param:@param catalog
	 * @param:@return   
	 * @return:Catalog  
	 * @author:wanggk
	 * @date:2018年10月26日
	 * @version:V1.0
	 */
	Catalog saveCatalog(Catalog catalog);
	/**
	 * 删除catalog
	 * @param:@param id   
	 * @return:void  
	 * @author:wanggk
	 * @date:2018年10月26日
	 * @version:V1.0
	 */
	void removeCatalog(Integer id);
	/**
	 * 根据ID查找catalog
	 * @param:@param id
	 * @param:@return   
	 * @return:Catalog  
	 * @author:wanggk
	 * @date:2018年10月26日
	 * @version:V1.0
	 */
	Catalog getById(Integer id);
	/**
	 * 获取用户的全部catalog
	 * @param:@param user
	 * @param:@return   
	 * @return:List<Catalog>  
	 * @author:wanggk
	 * @date:2018年10月26日
	 * @version:V1.0
	 */
	List<Catalog> listCatalogs(User user);
}
