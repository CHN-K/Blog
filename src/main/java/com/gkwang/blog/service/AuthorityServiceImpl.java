
package com.gkwang.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gkwang.blog.domain.Authority;
import com.gkwang.blog.repository.AuthorityRepository;


/**
 * Authority 服务
 * @Title: AuthorityServiceImpl.java
 * @Package:com.gkwang.blog.service
 * @author:Wanggk 
 * @date:2018年10月30日
 * @version:V1.0
 */
@Service
public class AuthorityServiceImpl  implements AuthorityService {
	
	@Autowired
	private AuthorityRepository authorityRepository;
	
	@Override
	public Authority getAuthorityById(Integer id) {
		return authorityRepository.findOne(id);
	}

	@Override
	public Page<Authority> listRolesByNameLike(String name, Pageable pageable) {
		// TODO Auto-generated method stub
		name = "%" + name + "%";
		Page<Authority> authorities = authorityRepository.findByNameLike(name,pageable);
		return authorities;
	}

	@Override
	public void saveAuthority(Authority authority) {
		// TODO Auto-generated method stub
		authorityRepository.save(authority);
	}

	@Override
	public void removeRole(Integer id) {
		// TODO Auto-generated method stub
		authorityRepository.delete(id);
	}


}