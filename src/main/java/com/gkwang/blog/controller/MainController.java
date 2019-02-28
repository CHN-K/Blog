package com.gkwang.blog.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.gkwang.blog.domain.Authority;
import com.gkwang.blog.domain.User;
import com.gkwang.blog.service.AuthorityService;
import com.gkwang.blog.service.UserService;

/**
 * 	主页控制器
 * @Title: MainController.java
 * @Package:com.gkwang.blog.controller
 * @author:Wanggk 
 * @date:2018年10月18日
 * @version:V1.0
 */
@Controller
public class MainController {
	
	private static final Integer ROLE_USER_AUTHORITY_ID = 2;

	@Autowired
	private AuthorityService authorityService;
	
	@Autowired
	private UserService userservice;
	/**
	 * 	访问根目录时跳转到index界面
	 * @param:@return   
	 * @return:String  
	 * @author:wanggk
	 * @date:2018年10月18日
	 * @version:V1.0
	 */
	@RequestMapping(value="/",method=RequestMethod.GET)
	public String root() {
		System.out.println("MainController-root");
		return "redirect:/index";
	}
	/**
	 * 	访问/index跳转到index界面
	 * @param:@return   
	 * @return:String  
	 * @author:wanggk
	 * @date:2018年10月18日
	 * @version:V1.0
	 */
	@RequestMapping(value="/index",method=RequestMethod.GET)
	public String index() {
		System.out.println("MainController-index");
		return "redirect:/blogs";
	}
	/**
	 * 	处理登陆请求，跳转到登陆界面
	 * @param:@return   
	 * @return:String  
	 * @author:wanggk
	 * @date:2018年10月18日
	 * @version:V1.0
	 */
	@RequestMapping(value="/login",method=RequestMethod.GET)
	public String login() {
		System.out.println("MainController-login");
		return "login";
	}
	/**
	 *	 处理出错的请求，跳转到出错界面
	 * @param:@param model
	 * @param:@return   
	 * @return:String  
	 * @author:wanggk
	 * @date:2018年10月18日
	 * @version:V1.0
	 */
	@RequestMapping(value="/error",method=RequestMethod.GET)
	public String error(Model model) {
		System.out.println("MainController-error");
		model.addAttribute("loginerror", true);
		model.addAttribute("errorMsg", "登陆失败，用户名或者密码错误");
		return "login";
	}
	/**
	 * 	处理注册请求，跳转到注册界面
	 * @param:@return   
	 * @return:String  
	 * @author:wanggk
	 * @date:2018年10月18日
	 * @version:V1.0
	 */
	@RequestMapping(value="/register",method=RequestMethod.GET)
	public String register() {
		System.out.println("MainController-register");
		return "register";
	}
	/**
	 * 	注册新博主
	 * @param:@param user
	 * @param:@return   
	 * @return:String  
	 * @author:wanggk
	 * @date:2018年10月30日
	 * @version:V1.0
	 */
	@RequestMapping(value="/register",method=RequestMethod.POST)
	public String registerUser(User user) {
		System.out.println("MainController-registerUser");
		List<Authority> authorities = new ArrayList<>();
		authorities.add(authorityService.getAuthorityById(ROLE_USER_AUTHORITY_ID));
		user.setAuthorities(authorities);
		userservice.saveUser(user); 
		return "redirect:/login";
	}
}
