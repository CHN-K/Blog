package com.gkwang.blog.controller;

import java.util.List;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.gkwang.blog.domain.Blog;
import com.gkwang.blog.domain.Comment;
import com.gkwang.blog.domain.User;
import com.gkwang.blog.service.BlogService;
import com.gkwang.blog.service.CommentService;
import com.gkwang.blog.util.ConstraintViolationExceptionHandler;
import com.gkwang.blog.vo.Response;
 /**
  * 	评论管理控制器
  * @Title: CommentController.java
  * @Package:com.gkwang.blog.controller
  * @author:Wanggk 
  * @date:2018年10月25日
  * @version:V1.0
  */
@Controller
@RequestMapping("/comments")
public class CommentController {
	
	@Autowired
	private BlogService blogService;
	
	@Autowired
	private CommentService commentService;
	
	/**
	 * 	获取评论列表
	 * @param:@param blogId
	 * @param:@param model
	 * @param:@return   
	 * @return:String  
	 * @author:wanggk
	 * @date:2018年10月30日
	 * @version:V1.0
	 */
	@RequestMapping(value="",method=RequestMethod.GET)
	public String listComments(@RequestParam(value="blogId",required=true) Integer blogId, Model model) {
		System.out.println("CommentController-listComments");
		Blog blog = blogService.getBlogById(blogId);
		List<Comment> comments = blog.getComments();
		
		// 判断操作用户是否是评论的所有者
		String commentOwner = "";
		if (SecurityContextHolder.getContext().getAuthentication() !=null && SecurityContextHolder.getContext().getAuthentication().isAuthenticated()
				 &&  !SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString().equals("anonymousUser")) {
			User principal = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal(); 
			if (principal !=null) {
				commentOwner = principal.getUsername();
			} 
		}
		
		model.addAttribute("commentOwner", commentOwner);
		model.addAttribute("comments", comments);
		return "/userspace/blog :: #mainContainerRepleace";
	}
	/**
	 * 	发表评论
	 * @param:@param blogId
	 * @param:@param commentContent
	 * @param:@return   
	 * @return:ResponseEntity<Response>  
	 * @author:wanggk
	 * @date:2018年10月30日
	 * @version:V1.0
	 */
	@RequestMapping(value="",method=RequestMethod.POST)
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")  // 指定角色权限才能操作方法
	public ResponseEntity<Response> createComment(Integer blogId, String commentContent) {
		System.out.println("CommentController-createComment");
		try {
			blogService.createComment(blogId, commentContent);
		} catch (ConstraintViolationException e)  {
			return ResponseEntity.ok().body(new Response(false, ConstraintViolationExceptionHandler.getMessage(e)));
		} catch (Exception e) {
			return ResponseEntity.ok().body(new Response(false, e.getMessage()));
		}
		
		return ResponseEntity.ok().body(new Response(true, "处理成功", null));
	}
	
	/**
	 * 	删除评论
	 * @param:@param id
	 * @param:@param blogId
	 * @param:@return   
	 * @return:ResponseEntity<Response>  
	 * @author:wanggk
	 * @date:2018年10月30日
	 * @version:V1.0
	 */
	@RequestMapping(value="/{id}",method=RequestMethod.DELETE)
	public ResponseEntity<Response> deleteBlog(@PathVariable("id") Integer id, Integer blogId) {
		System.out.println("CommentController-deleteBlog");
		boolean isOwner = false;
		User user = commentService.getCommentById(id).getUser();
		
		// 判断操作用户是否是博客的所有者
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
			blogService.removeComment(blogId, id);
			commentService.removeComment(id);
		} catch (ConstraintViolationException e)  {
			return ResponseEntity.ok().body(new Response(false, ConstraintViolationExceptionHandler.getMessage(e)));
		} catch (Exception e) {
			return ResponseEntity.ok().body(new Response(false, e.getMessage()));
		}
		
		return ResponseEntity.ok().body(new Response(true, "处理成功", null));
	}
	/**
	 * 	管理界面删除评论
	 * @param:@param username
	 * @param:@param id
	 * @param:@return   
	 * @return:ResponseEntity<Response>  
	 * @author:wanggk
	 * @date:2018年10月26日
	 * @version:V1.0
	 */
	@RequestMapping(value = "/commentm/{id}", method = RequestMethod.DELETE)
	@PreAuthorize("authentication.name.equals('admin')")
	public ResponseEntity<Response> deleteComment(@PathVariable("id") Integer id,Integer blogId) {
		System.out.println("CommentController-deleteComment");
		try {
			blogService.removeComment(blogId, id);
			commentService.removeComment(id);
		} catch (Exception e) {
			return ResponseEntity.ok().body(new Response(false, e.getMessage()));
		}

		String redirectUrl = "/admins";
		return ResponseEntity.ok().body(new Response(true, "处理成功", redirectUrl));
	}
	
	/**
	 *	 管理界面显示评论
	 * @param:@param username
	 * @param:@param order
	 * @param:@param catalogId
	 * @param:@param keyword
	 * @param:@param async
	 * @param:@param pageIndex
	 * @param:@param pageSize
	 * @param:@param model
	 * @param:@return   
	 * @return:String  
	 * @author:wanggk
	 * @date:2018年10月26日
	 * @version:V1.0
	 */
	@RequestMapping(value = "/commentm", method = RequestMethod.GET)
	public ModelAndView listComment(@RequestParam(value = "async", required = false) boolean async,
			@RequestParam(value = "pageIndex", required = false, defaultValue = "0") int pageIndex,
			@RequestParam(value = "pageSize", required = false, defaultValue = "5") int pageSize,
			@RequestParam(value = "content", required = false, defaultValue = "") String content, Model model) {
		System.out.println("CommentController-listComment");
		Pageable pageable = new PageRequest(pageIndex, pageSize);
		Page<Comment> page = commentService.listCommentsByContentLike(content, pageable);
		List<Comment> list = page.getContent(); // 当前所在页面数据列表
		model.addAttribute("page", page);
		model.addAttribute("commentList", list);
		return new ModelAndView(async == true ? "admins/comment :: #mainContainerRepleace" : "admins/comment", "commentModel",
				model);
	}
	/**
	 * 	查看博客详情
	 * @param:@param id
	 * @param:@param model
	 * @param:@return   
	 * @return:ModelAndView  
	 * @author:wanggk
	 * @date:2018年10月30日
	 * @version:V1.0
	 */
	@RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
	public ModelAndView viewFormComment(@PathVariable("id") Integer id, Model model) {
		System.out.println("CommentController-viewFormComment");
		Comment comment = commentService.getCommentById(id);
		model.addAttribute("comment", comment);
		return new ModelAndView("admins/comment-view", "commentModel", model);
	}
}
