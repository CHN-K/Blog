package com.gkwang.blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.gkwang.blog.domain.Blog;
import com.gkwang.blog.domain.EsBlog;
import com.gkwang.blog.domain.User;
import com.gkwang.blog.service.BlogService;
import com.gkwang.blog.service.EsBlogService;
import com.gkwang.blog.vo.Response;
import com.gkwang.blog.vo.TagVO;

/**
 * Blog控制器
 * @Title: BlogController.java
 * @Package:com.gkwang.blog.controller
 * @author:Wanggk
 * @date:2018年10月18日
 * @version:V1.0
 */
@Controller
@RequestMapping("/blogs")
public class BlogController {
	@Autowired
	private BlogService blogService;
	@Autowired
    private EsBlogService esBlogService;
	/***
	 * 	首页显示博客
	 * @param:@param order
	 * @param:@param keyword
	 * @param:@param async
	 * @param:@param pageIndex
	 * @param:@param pageSize
	 * @param:@param model
	 * @param:@return   
	 * @return:String  
	 * @author:wanggk
	 * @date:2018年10月30日
	 * @version:V1.0
	 */
	@RequestMapping(value="",method=RequestMethod.GET)
	public String listEsBlogs(
			@RequestParam(value="order",required=false,defaultValue="new") String order,
			@RequestParam(value="keyword",required=false,defaultValue="" ) String keyword,
			@RequestParam(value="async",required=false) boolean async,
			@RequestParam(value="pageIndex",required=false,defaultValue="0") int pageIndex,
			@RequestParam(value="pageSize",required=false,defaultValue="5") int pageSize,
			Model model) {
		Page<EsBlog> page = null;
		List<EsBlog> list = null;
		boolean isEmpty = true; // 系统初始化时，没有博客数据
		try {
			if (order.equals("hot")) { // 最热查询
				Sort sort = new Sort(Direction.DESC,"readSize","commentSize","voteSize","createTime"); 
				Pageable pageable = new PageRequest(pageIndex, pageSize, sort);
				page = esBlogService.listHotestEsBlogs(keyword, pageable);
			} else if (order.equals("new")) { // 最新查询
				Sort sort = new Sort(Direction.DESC,"createTime"); 
				Pageable pageable = new PageRequest(pageIndex, pageSize, sort);
				page = esBlogService.listNewestEsBlogs(keyword, pageable);
			}
			
			isEmpty = false;
		} catch (Exception e) {
			Pageable pageable = new PageRequest(pageIndex, pageSize);
			page = esBlogService.listEsBlogs(pageable);
		}  
 
		list = page.getContent();	// 当前所在页面数据列表
 

		model.addAttribute("order", order);
		model.addAttribute("keyword", keyword);
		model.addAttribute("page", page);
		model.addAttribute("blogList", list);
		
		// 首次访问页面才加载
		if (!async && !isEmpty) {
			List<EsBlog> newest = esBlogService.listTop5NewestEsBlogs();
			model.addAttribute("newest", newest);
			List<EsBlog> hotest = esBlogService.listTop5HotestEsBlogs();
			model.addAttribute("hotest", hotest);
			List<TagVO> tags = esBlogService.listTop30Tags();
			model.addAttribute("tags", tags);
			List<User> users = esBlogService.listTop12Users();
			model.addAttribute("users", users);
		}
		
		return (async==true?"index :: #mainContainerRepleace":"index");
	}
	/**
	 * 	获取最新的博客
	 * @param:@param model
	 * @param:@return   
	 * @return:String  
	 * @author:wanggk
	 * @date:2018年10月30日
	 * @version:V1.0
	 */
	@RequestMapping(value="/newest",method=RequestMethod.GET)
	public String listNewestEsBlogs(Model model) {
		System.out.println("BlogController-listNewestEsBlogs");
		List<EsBlog> newest = esBlogService.listTop5NewestEsBlogs();
		model.addAttribute("newest", newest);
		return "newest";
	}
	/**
	 * 	获取最热的博客
	 * @param:@param model
	 * @param:@return   
	 * @return:String  
	 * @author:wanggk
	 * @date:2018年10月30日
	 * @version:V1.0
	 */
	@RequestMapping(value="/hotest",method=RequestMethod.GET)
	public String listHotestEsBlogs(Model model) {
		System.out.println("BlogController-listHotestEsBlogs");
		List<EsBlog> hotest = esBlogService.listTop5HotestEsBlogs();
		model.addAttribute("hotest", hotest);
		return "hotest";
	}
	
	
	/**
	 * 	管理界面删除博客
	 * @param:@param username
	 * @param:@param id
	 * @param:@return   
	 * @return:ResponseEntity<Response>  
	 * @author:wanggk
	 * @date:2018年10月26日
	 * @version:V1.0
	 */
	@RequestMapping(value = "/blogm/{id}", method = RequestMethod.DELETE)
	@PreAuthorize("authentication.name.equals('admin')")
	public ResponseEntity<Response> deleteBlog(@PathVariable("id") Integer id) {
		System.out.println("BlogController-deleteBlog");
		try {
			blogService.removeBlog(id);
			esBlogService.removeByBlogId(id);
		} catch (Exception e) {
			return ResponseEntity.ok().body(new Response(false, e.getMessage()));
		}

		String redirectUrl = "/admins";
		return ResponseEntity.ok().body(new Response(true, "处理成功", redirectUrl));
	}
	
	/**
	 *	 管理界面显示博客
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
	@RequestMapping(value = "/blogm", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam(value = "async", required = false) boolean async,
			@RequestParam(value = "pageIndex", required = false, defaultValue = "0") int pageIndex,
			@RequestParam(value = "pageSize", required = false, defaultValue = "5") int pageSize,
			@RequestParam(value = "title", required = false, defaultValue = "") String title, Model model) {
		System.out.println("BlogController-listBlogmByOrder");
		Pageable pageable = new PageRequest(pageIndex, pageSize);
		Page<Blog> page = blogService.listBlogsByTitleLike(title, pageable);
		List<Blog> list = page.getContent(); // 当前所在页面数据列表
		model.addAttribute("page", page);
		model.addAttribute("blogList", list);
		return new ModelAndView(async == true ? "admins/blog :: #mainContainerRepleace" : "admins/blog", "blogModel",
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
	public ModelAndView viewFormBlog(@PathVariable("id") Integer id, Model model) {
		System.out.println("AdminController-viewFormBlog");
		Blog blog = blogService.getBlogById(id);
		model.addAttribute("blog", blog);
		return new ModelAndView("admins/blog-view", "blogModel", model);
	}
}
