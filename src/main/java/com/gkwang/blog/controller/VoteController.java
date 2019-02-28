package com.gkwang.blog.controller;
import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.gkwang.blog.domain.User;
import com.gkwang.blog.service.BlogService;
import com.gkwang.blog.service.VoteService;
import com.gkwang.blog.util.ConstraintViolationExceptionHandler;
import com.gkwang.blog.vo.Response;

/**
 * 	点赞管理控制器
 * @Title: VoteController.java
 * @Package:com.gkwang.blog.controller
 * @author:Wanggk 
 * @date:2018年10月25日
 * @version:V1.0
 */
@Controller
@RequestMapping("/votes")
public class VoteController {
	@Autowired
	private BlogService blogService;
	
	@Autowired
	private VoteService voteService;
 
	/**
	 * 	点赞
	 * @param:@param blogId
	 * @param:@return   
	 * @return:ResponseEntity<Response>  
	 * @author:wanggk
	 * @date:2018年10月25日
	 * @version:V1.0
	 */
	@RequestMapping(value="",method=RequestMethod.POST)
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")  // 指定角色权限才能操作方法
	public ResponseEntity<Response> createVote(Integer blogId) {
		System.out.println("VoteController-createVote");
		try {
			blogService.createVote(blogId);
		} catch (ConstraintViolationException e)  {
			return ResponseEntity.ok().body(new Response(false, ConstraintViolationExceptionHandler.getMessage(e)));
		} catch (Exception e) {
			return ResponseEntity.ok().body(new Response(false, e.getMessage()));
		}
		return ResponseEntity.ok().body(new Response(true, "点赞成功", null));
	}
	
	/**
	 * 	删除点赞
	 * @param:@param id
	 * @param:@param blogId
	 * @param:@return   
	 * @return:ResponseEntity<Response>  
	 * @author:wanggk
	 * @date:2018年10月25日
	 * @version:V1.0
	 */
	@RequestMapping(value="/{id}",method=RequestMethod.DELETE)
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")  // 指定角色权限才能操作方法
	public ResponseEntity<Response> delete(@PathVariable("id") Integer id,@RequestParam(value="blogId")Integer blogId) {
		System.out.println("VoteController-delete");
		boolean isOwner = false;
		User user = voteService.getVoteById(id).getUser();
		// 判断操作用户是否是点赞的所有者
		if (SecurityContextHolder.getContext().getAuthentication() !=null && SecurityContextHolder.getContext().getAuthentication().isAuthenticated()
				 &&  !SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString().equals("anonymousUser")) {
			User principal = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal(); 
			if (principal !=null && user.getUsername().equals(principal.getUsername())) {
				isOwner = true;
			} 
		} 
		if (!isOwner) {
			return ResponseEntity.ok().body(new Response(false, "没有操作权限"));
		}
		try {
			blogService.removeVote(blogId, id);
			voteService.removeVote(id);
		} catch (ConstraintViolationException e)  {
			return ResponseEntity.ok().body(new Response(false, ConstraintViolationExceptionHandler.getMessage(e)));
		} catch (Exception e) {
			return ResponseEntity.ok().body(new Response(false, e.getMessage()));
		}
		
		return ResponseEntity.ok().body(new Response(true, "取消点赞成功", null));
	}
}
