package com.gkwang.blog.controller;

import java.util.List;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.gkwang.blog.domain.Catalog;
import com.gkwang.blog.domain.User;
import com.gkwang.blog.service.CatalogService;
import com.gkwang.blog.util.ConstraintViolationExceptionHandler;
import com.gkwang.blog.vo.CatalogVO;
import com.gkwang.blog.vo.Response;


/**
 * 	分类控制器
 * @Title: CatalogController.java
 * @Package:com.gkwang.blog.controller
 * @author:Wanggk 
 * @date:2018年10月26日
 * @version:V1.0
 */
@Controller
@RequestMapping("/catalogs")
public class CatalogController {
	
	@Autowired
	private CatalogService catalogService;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	/**
	 * 	获取分类列表
	 * @param:@param username
	 * @param:@param model
	 * @param:@return   
	 * @return:String  
	 * @author:wanggk
	 * @date:2018年10月26日
	 * @version:V1.0
	 */
	@RequestMapping(value="",method=RequestMethod.GET)
	public String listCatalogs(@RequestParam(value="username",required=true) String username, Model model) {
		System.out.println("CatalogController-listCatalogs");
		User user = (User)userDetailsService.loadUserByUsername(username);
		List<Catalog> catalogs = catalogService.listCatalogs(user);

		// 判断操作用户是否是分类的所有者
		boolean isOwner = false;
		
		if (SecurityContextHolder.getContext().getAuthentication() !=null && SecurityContextHolder.getContext().getAuthentication().isAuthenticated()
				 &&  !SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString().equals("anonymousUser")) {
			User principal = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal(); 
			if (principal !=null && user.getUsername().equals(principal.getUsername())) {
				isOwner = true;
			} 
		} 
		model.addAttribute("isCatalogsOwner", isOwner);
		model.addAttribute("catalogs", catalogs);
		return "/userspace/u :: #catalogRepleace";
	}
	/**
	 * 	发表分类
	 * @param:@param catalogVO
	 * @param:@return   
	 * @return:ResponseEntity<Response>  
	 * @author:wanggk
	 * @date:2018年10月26日
	 * @version:V1.0
	 */
	@RequestMapping(value="",method=RequestMethod.POST)
	@PreAuthorize("authentication.name.equals(#catalogVO.username)")// 指定用户才能操作方法
	public ResponseEntity<Response> create(@RequestBody CatalogVO catalogVO) {
		System.out.println("CatalogController-create");
		String username = catalogVO.getUsername();
		Catalog catalog = catalogVO.getCatalog();
		User user = (User)userDetailsService.loadUserByUsername(username);
		try {
			catalog.setUser(user);
			catalogService.saveCatalog(catalog);
		} catch (ConstraintViolationException e)  {
			return ResponseEntity.ok().body(new Response(false, ConstraintViolationExceptionHandler.getMessage(e)));
		} catch (Exception e) {
			return ResponseEntity.ok().body(new Response(false, e.getMessage()));
		}
		
		return ResponseEntity.ok().body(new Response(true, "处理成功", null));
	}
	
	/**
	 * 	删除分类
	 * @param:@param username
	 * @param:@param id
	 * @param:@return   
	 * @return:ResponseEntity<Response>  
	 * @author:wanggk
	 * @date:2018年10月26日
	 * @version:V1.0
	 */
	@RequestMapping(value="/{id}",method=RequestMethod.DELETE)
	@PreAuthorize("authentication.name.equals(#username)")  // 指定用户才能操作方法
	public ResponseEntity<Response> delete(String username, @PathVariable("id") Integer id) {
		System.out.println("CatalogController-delete");
		try {
			catalogService.removeCatalog(id);
		} catch (ConstraintViolationException e)  {
			return ResponseEntity.ok().body(new Response(false, ConstraintViolationExceptionHandler.getMessage(e)));
		} catch (Exception e) {
			return ResponseEntity.ok().body(new Response(false, e.getMessage()));
		}
		
		return ResponseEntity.ok().body(new Response(true, "处理成功", null));
	}
	
	/**
	 * 	获取分类编辑界面
	 * @param:@param model
	 * @param:@return   
	 * @return:String  
	 * @author:wanggk
	 * @date:2018年10月26日
	 * @version:V1.0
	 */
	@RequestMapping(value="/edit",method=RequestMethod.GET)
	public String getCatalogEdit(Model model) {
		System.out.println("CatalogController-getCatalogEdit");
		Catalog catalog = new Catalog(null, null);
		model.addAttribute("catalog",catalog);
		return "/userspace/catalogedit";
	}
	/**
	 * 	根据 Id 获取分类信息
	 * @param:@param id
	 * @param:@param model
	 * @param:@return   
	 * @return:String  
	 * @author:wanggk
	 * @date:2018年10月26日
	 * @version:V1.0
	 */
	@RequestMapping(value="/edit/{id}",method=RequestMethod.GET)
	public String getCatalogById(@PathVariable("id") Integer id, Model model) {
		System.out.println("CatalogController-getCatalogById");
		Catalog catalog = catalogService.getById(id);
		model.addAttribute("catalog",catalog);
		return "/userspace/catalogedit";
	}
}
