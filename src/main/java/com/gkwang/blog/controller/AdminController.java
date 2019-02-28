package com.gkwang.blog.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.gkwang.blog.domain.Authority;
import com.gkwang.blog.service.AuthorityService;
import com.gkwang.blog.util.ConstraintViolationExceptionHandler;
import com.gkwang.blog.vo.Menu;
import com.gkwang.blog.vo.Response;
/**
 * 	管理员控制器
 * @Title: AdminController.java
 * @Package:com.gkwang.blog.controller
 * @author:Wanggk 
 * @date:2018年10月23日
 * @version:V1.0
 */
@Controller
public class AdminController {
	
	@Autowired
	private AuthorityService authorityService;

	/**
	 * 	用户管理界面获取角色
	 * @param:@param async
	 * @param:@param pageIndex
	 * @param:@param pageSize
	 * @param:@param name
	 * @param:@param model
	 * @param:@return   
	 * @return:ModelAndView  
	 * @author:wanggk
	 * @date:2018年10月30日
	 * @version:V1.0
	 */
	@RequestMapping(value = "/roles", method = RequestMethod.GET)
	public ModelAndView listrole(@RequestParam(value = "async", required = false) boolean async,
			@RequestParam(value = "pageIndex", required = false, defaultValue = "0") int pageIndex,
			@RequestParam(value = "pageSize", required = false, defaultValue = "5") int pageSize,
			@RequestParam(value = "name", required = false, defaultValue = "") String name, Model model) {
		System.out.println("AdminController-listrole");
		Pageable pageable = new PageRequest(pageIndex, pageSize);
		Page<Authority> page = authorityService.listRolesByNameLike(name, pageable);
		List<Authority> list = page.getContent(); // 当前所在页面数据列表
		model.addAttribute("page", page);
		model.addAttribute("roleList", list);
		return new ModelAndView(async == true ? "admins/role :: #mainContainerRepleace" : "admins/role", "roleModel",
				model);
	}

	/**
	 * 	将form加载到模态框中
	 * @param:@param model
	 * @param:@return   
	 * @return:ModelAndView  
	 * @author:wanggk
	 * @date:2018年10月30日
	 * @version:V1.0
	 */
	@RequestMapping(value = "/roles/add", method = RequestMethod.GET)
	public ModelAndView createFormRole(Model model) {
		System.out.println("AdminController-createFormRole");
		model.addAttribute("role", new Authority(null, null));
		return new ModelAndView("admins/role-add", "roleModel", model);
	}
	/**
	 * 	创建/更新 新的角色
	 * @param:@param authority
	 * @param:@return   
	 * @return:ResponseEntity<Response>  
	 * @author:wanggk
	 * @date:2018年10月30日
	 * @version:V1.0
	 */

	@RequestMapping(value ="/roles", method = RequestMethod.POST)
	public ResponseEntity<Response> createRole(Authority authority) {
		System.out.println("AdminController-createRole");
		try {
			authorityService.saveAuthority(authority);
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
		return ResponseEntity.ok().body(new Response(true, "处理成功", authority));
	}

	/**
	 * 	删除角色
	 * @param:@param id
	 * @param:@param model
	 * @param:@return   
	 * @return:ResponseEntity<Response>  
	 * @author:wanggk
	 * @date:2018年10月30日
	 * @version:V1.0
	 */
	@RequestMapping(value = "/roles/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Response> deleterole(@PathVariable("id") Integer id, Model model) {
		System.out.println("AdminController-deleterole");
		try {
		authorityService.removeRole(id);
		} catch (Exception e) {
			return  ResponseEntity.ok().body( new Response(false, e.getMessage()));
		}
		return ResponseEntity.ok().body(new Response(true, "处理成功"));
	}
	/**
	 * 	将数据加载到模态框中
	 * @param:@param id
	 * @param:@param model
	 * @param:@return   
	 * @return:ModelAndView  
	 * @author:wanggk
	 * @date:2018年10月30日
	 * @version:V1.0
	 */
	
	@RequestMapping(value = "/roles/edit/{id}", method = RequestMethod.GET)
	public ModelAndView modifyFormRole(@PathVariable("id") Integer id, Model model) {
		System.out.println("AdminController-modifyFormRole");
		Authority authority = authorityService.getAuthorityById(id);
		model.addAttribute("role", authority);
		return new ModelAndView("admins/role-add", "roleModel", model);
	}
	/**
	 * 	初始化管理界面
	 * @param:@param model
	 * @param:@return   
	 * @return:ModelAndView  
	 * @author:wanggk
	 * @date:2018年10月30日
	 * @version:V1.0
	 */
	@RequestMapping(value = "/admins", method = RequestMethod.GET)
	public ModelAndView userspace(Model model) {
		System.out.println("AdminController-userspace");
		List<Menu> list = new ArrayList<>();
		list.add(new Menu("用户管理", "/users"));
		list.add(new Menu("角色管理", "/roles"));
		list.add(new Menu("博客管理", "/blogs/blogm"));
		list.add(new Menu("评论管理", "/comments/commentm"));
		model.addAttribute("list", list);
		return new ModelAndView("/admins/index", "model", model);

	}
}
