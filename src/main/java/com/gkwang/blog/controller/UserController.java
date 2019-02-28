package com.gkwang.blog.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.gkwang.blog.domain.Authority;
//import com.gkwang.blog.domain.Authority;
import com.gkwang.blog.domain.User;
import com.gkwang.blog.service.AuthorityService;
//import com.gkwang.blog.service.AuthorityService;
import com.gkwang.blog.service.UserService;
import com.gkwang.blog.util.ConstraintViolationExceptionHandler;
import com.gkwang.blog.vo.Response;

/**
 *	 用户控制器
 * @Title: UserController.java
 * @Package:com.gkwang.blog.controller
 * @author:Wanggk
 * @date:2018年10月18日
 * @version:V1.0
 */
@Controller
@RequestMapping("/users")
@PreAuthorize("hasAuthority('ROLE_ADMIN')")  // 指定角色权限才能操作方法
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private AuthorityService authorityService;

	/**
	 * 	查询所用用户
	 * @param:@param async
	 * @param:@param pageIndex
	 * @param:@param pageSize
	 * @param:@param name
	 * @param:@param model
	 * @param:@return
	 * @return:ModelAndView
	 * @author:wanggk
	 * @date:2018年10月18日
	 * @version:V1.0
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam(value = "async", required = false) boolean async,
			@RequestParam(value = "pageIndex", required = false, defaultValue = "0") int pageIndex,
			@RequestParam(value = "pageSize", required = false, defaultValue = "5") int pageSize,
			@RequestParam(value = "name", required = false, defaultValue = "") String name, Model model) {
		System.out.println("UserController-list");
		Pageable pageable = new PageRequest(pageIndex, pageSize);
		Page<User> page = userService.listUsersByNameLike(name, pageable);
		List<User> list = page.getContent(); // 当前所在页面数据列表

		model.addAttribute("page", page);
		model.addAttribute("userList", list);
		return new ModelAndView(async == true ? "users/list :: #mainContainerRepleace" : "users/list", "userModel",
				model);
	}

	/**
	 * 	获取 form 表单页面
	 * @param:@param model
	 * @param:@return
	 * @return:ModelAndView
	 * @author:wanggk
	 * @date:2018年10月18日
	 * @version:V1.0
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView createForm(Model model) {
		System.out.println("UserController-createForm");
		model.addAttribute("user", new User(null, null, null, null));
		return new ModelAndView("users/add", "userModel", model);
	}

	/**
	 * 	新建用户
	 * @param:@param user
	 * @param:@param authorityId
	 * @param:@return
	 * @return:ResponseEntity<Response>
	 * @author:wanggk
	 * @date:2018年10月18日
	 * @version:V1.0
	 */
	@RequestMapping(value = "", method = RequestMethod.POST)
	public ResponseEntity<Response> create(User user, Integer authorityId) {
		System.out.println("UserController-create");
		List<Authority> authorities = new ArrayList<>();
		authorities.add(authorityService.getAuthorityById(authorityId));
		user.setAuthorities(authorities);

		if (user.getId() == null) {
			user.setEncodePassword(user.getPassword()); // 加密密码
		} else {
			// 判断密码是否做了变更
			User originalUser = userService.getUserById(user.getId());
			String rawPassword = originalUser.getPassword();
			PasswordEncoder encoder = new BCryptPasswordEncoder();
			String encodePasswd = encoder.encode(user.getPassword());
			boolean isMatch = encoder.matches(rawPassword, encodePasswd);
			if (!isMatch) {
				user.setEncodePassword(user.getPassword());
			} else {
				user.setPassword(user.getPassword());
			}
		}
		try {
			userService.saveUser(user);
		} catch (ConstraintViolationException e) {
			return ResponseEntity.ok().body(new Response(false, ConstraintViolationExceptionHandler.getMessage(e)));
		} catch (TransactionSystemException e) {
			Throwable t = e.getCause();
			while ((t != null) && !(t instanceof ConstraintViolationException)) {
				t = t.getCause();
			}
			if (t instanceof ConstraintViolationException) {
				return ResponseEntity.ok().body(new Response(false,
						ConstraintViolationExceptionHandler.getMessage((ConstraintViolationException) t)));
			}
		}
		return ResponseEntity.ok().body(new Response(true, "处理成功", user));
	}

	/**
	 * 	删除用户
	 * @param:@param id
	 * @param:@param model
	 * @param:@return
	 * @return:ResponseEntity<Response>
	 * @author:wanggk
	 * @date:2018年10月18日
	 * @version:V1.0
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Response> delete(@PathVariable("id") Integer id, Model model) {
		System.out.println("UserController-delete");
		try {
		userService.removeUser(id);
		} catch (Exception e) {
			return  ResponseEntity.ok().body( new Response(false, e.getMessage()));
		}
		return ResponseEntity.ok().body(new Response(true, "处理成功"));
	}

	/**
	 *	 获取修改用户的界面，及数据
	 * @param:@param id
	 * @param:@param model
	 * @param:@return
	 * @return:ModelAndView
	 * @author:wanggk
	 * @date:2018年10月18日
	 * @version:V1.0
	 */
	@RequestMapping(value = "edit/{id}", method = RequestMethod.GET)
	public ModelAndView modifyForm(@PathVariable("id") Integer id, Model model) {
		System.out.println("UserController-modifyForm");
		User user = userService.getUserById(id);
		model.addAttribute("user", user);
		return new ModelAndView("users/edit", "userModel", model);
	}
}
