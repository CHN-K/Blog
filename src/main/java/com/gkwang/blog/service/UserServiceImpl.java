package com.gkwang.blog.service;

import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.gkwang.blog.domain.User;
import com.gkwang.blog.repository.UserRepository;


/**
 * 	User 服务实现
 * @Title: UserServiceImpl.java
 * @Package:com.gkwang.blog.service
 * @author:Wanggk 
 * @date:2018年10月30日
 * @version:V1.0
 */
@Service
public class UserServiceImpl implements UserService, UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	@Transactional
	@Override
	public void saveUser(User user) {
		userRepository.save(user);
	}

	@Transactional
	@Override
	public void removeUser(Integer id) {
		User user = userRepository.getOne(id);
		userRepository.delete(user);
	}

	@Transactional
	@Override
	public void removeUsersInBatch(List<User> users) {
		userRepository.deleteInBatch(users);
	}
	
	@Transactional
	@Override
	public User updateUser(User user) {
		User user1 = userRepository.save(user);
		return user1;
	}

	@Override
	public User getUserById(Integer id) {
		return userRepository.getOne(id);
	}

	@Override
	public List<User> listUsers() {
		return userRepository.findAll();
	}

	@Override
	public Page<User> listUsersByNameLike(String name, Pageable pageable) {
		// 模糊查询
		name = "%" + name + "%";
		Page<User> users = userRepository.findByNameLike(name, pageable);
		return users;
	}
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userRepository.findByUsername(username);
	}

	@Override
	public List<User> listUsersByUsernames(Collection<String> usernames) {
		// TODO Auto-generated method stub
		return userRepository.findByUsernameIn(usernames);
	}

}
