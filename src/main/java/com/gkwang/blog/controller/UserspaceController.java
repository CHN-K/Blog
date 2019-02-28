package com.gkwang.blog.controller;

import java.util.List;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.gkwang.blog.domain.Blog;
import com.gkwang.blog.domain.Catalog;
import com.gkwang.blog.domain.User;
import com.gkwang.blog.domain.Vote;
import com.gkwang.blog.service.BlogService;
import com.gkwang.blog.service.CatalogService;
import com.gkwang.blog.service.EsBlogService;
import com.gkwang.blog.service.UserService;
import com.gkwang.blog.util.ConstraintViolationExceptionHandler;
import com.gkwang.blog.vo.Response;

/**
 *	 个人博客控制器
 * @Title: UserspaceController.java
 * @Package:com.gkwang.blog.controller
 * @author:Wanggk
 * @date:2018年10月18日
 * @version:V1.0
 */
@Controller
@RequestMapping("/u")
public class UserspaceController {

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private UserService userService;
	@Autowired
	private EsBlogService esBlogService;
	@Autowired
	private BlogService blogService;
	@Autowired
	private CatalogService catalogService;
	/**
	 * 	进入修改个人设置界面
	 * @param:@param username
	 * @param:@param model
	 * @param:@return   
	 * @return:ModelAndView  
	 * @author:wanggk
	 * @date:2018年10月30日
	 * @version:V1.0
	 */
	@RequestMapping(value = "/{username}/profile",method=RequestMethod.GET)
	@PreAuthorize("authentication.name.equals(#username)")
	public ModelAndView profile(@PathVariable("username") String username, Model model) {
		System.out.println("UserspaceController-profile");
		User user = (User) userDetailsService.loadUserByUsername(username);
		model.addAttribute("user", user);
		return new ModelAndView("/userspace/profile", "userModel", model);
	}

	/**
	 * 	保存个人设置
	 * @param:@param username
	 * @param:@param user
	 * @param:@return   
	 * @return:String  
	 * @author:wanggk
	 * @date:2018年10月26日
	 * @version:V1.0
	 */
	@RequestMapping(value = "/{username}/profile", method = RequestMethod.POST)
	@PreAuthorize("authentication.name.equals(#username)")
	public String saveProfile(@PathVariable("username") String username, User user) {
		System.out.println("UserspaceController-saveProfile");
		System.out.println(username);
		System.out.println(user);
		User originalUser = userService.getUserById(user.getId());
		originalUser.setEmail(user.getEmail());
		originalUser.setName(user.getName());
		String rawPassword = originalUser.getPassword();
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		String encodePasswd = encoder.encode(user.getPassword());
		boolean isMatch = encoder.matches(rawPassword, encodePasswd);
		if (!isMatch) {
			originalUser.setEncodePassword(user.getPassword());
		}
		userService.saveUser(originalUser);
		return "redirect:/u/" + username + "/profile";
	}

	/**
	 *	 获取编辑头像的界面
	 * @param:@param username
	 * @param:@param model
	 * @param:@return   
	 * @return:ModelAndView  
	 * @author:wanggk
	 * @date:2018年10月26日
	 * @version:V1.0
	 */
	@RequestMapping(value = "/{username}/avatar", method = RequestMethod.GET)
	@PreAuthorize("authentication.name.equals(#username)")
	public ModelAndView avatar(@PathVariable("username") String username, Model model) {
		System.out.println("UserspaceController-avatar");
		User user = (User) userDetailsService.loadUserByUsername(username);
		model.addAttribute("user", user);
		return new ModelAndView("/userspace/avatar", "userModel", model);
	}

	/**
	 * 	保存编辑头像的界面
	 * @param:@param username
	 * @param:@param user
	 * @param:@return   
	 * @return:ResponseEntity<Response>  
	 * @author:wanggk
	 * @date:2018年10月26日
	 * @version:V1.0
	 */
	@RequestMapping(value = "/{username}/avatar", method = RequestMethod.POST)
	@PreAuthorize("authentication.name.equals(#username)")
	public ResponseEntity<Response> saveAvatar(@PathVariable("username") String username, @RequestBody User user) {
		System.out.println("UserspaceController-saveAvatar");
		System.out.println(username);
		System.out.println(user);
		String avatarUrl = user.getAvatar();

		User originalUser = userService.getUserById(user.getId());
		originalUser.setAvatar(avatarUrl);
		userService.saveUser(originalUser);

		return ResponseEntity.ok().body(new Response(true, "处理成功", avatarUrl));
	}
	/**
	 * 	进入个人主页
	 * @param:@param username
	 * @param:@param model
	 * @param:@return   
	 * @return:String  
	 * @author:wanggk
	 * @date:2018年10月30日
	 * @version:V1.0
	 */
	@RequestMapping(value = "/{username}", method = RequestMethod.GET)
	public String userSpace(@PathVariable("username") String username, Model model) {
		System.out.println("UserspaceController-userSpace");
		User user = (User) userDetailsService.loadUserByUsername(username);
		model.addAttribute("user", user);
		return "redirect:/u/" + username + "/blogs";
	}
	
	/**
	 *	 获得个人主页的博客
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
	@RequestMapping(value = "/{username}/blogs", method = RequestMethod.GET)
	public String listBlogsByOrder(@PathVariable("username") String username,
			@RequestParam(value = "order", required = false, defaultValue = "new") String order,
			@RequestParam(value = "category", required = false) Integer catalogId,
			@RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
			@RequestParam(value = "async", required = false) boolean async,
			@RequestParam(value = "pageIndex", required = false, defaultValue = "0") int pageIndex,
			@RequestParam(value = "pageSize", required = false, defaultValue = "5") int pageSize, Model model) {
		System.out.println("UserspaceController-listBlogsByOrder");
		User user = (User) userDetailsService.loadUserByUsername(username);

		Page<Blog> page = null;

		if (catalogId != null && catalogId > 0) { // 分类查询
			Catalog catalog = catalogService.getById(catalogId);
			Pageable pageable =new PageRequest(pageIndex, pageSize);
			page = blogService.listBlogsByCatalog(catalog, pageable);
			order = "";
		} else if (order.equals("hot")) { // 最热查询
			Sort sort = new Sort(Direction.DESC, "readSize", "commentSize", "voteSize");
			Pageable pageable = new PageRequest(pageIndex, pageSize, sort);
			page = blogService.listBlogsByTitleLikeAndSort(user, keyword, pageable);
		} else if (order.equals("new")) { // 最新查询
			Pageable pageable = new PageRequest(pageIndex, pageSize);
			page = blogService.listBlogsByTitleLike(user, keyword, pageable);
		}

		List<Blog> list = page.getContent(); // 当前所在页面数据列表

		model.addAttribute("user", user);
		model.addAttribute("order", order);
		model.addAttribute("catalogId", catalogId);
		model.addAttribute("keyword", keyword);
		model.addAttribute("page", page);
		model.addAttribute("blogList", list);
		System.out.println(list);
		System.out.println(page);
		return (async == true ? "/userspace/u :: #mainContainerRepleace" : "/userspace/u");
	}

	/**
	 * 	进入博客详情界面
	 * @param:@param username
	 * @param:@param id
	 * @param:@param model
	 * @param:@return   
	 * @return:String  
	 * @author:wanggk
	 * @date:2018年10月26日
	 * @version:V1.0
	 */
	@RequestMapping(value = "/{username}/blogs/{id}", method = RequestMethod.GET)
	public String getBlogById(@PathVariable("username") String username, @PathVariable("id") Integer id, Model model) {
		System.out.println("UserspaceController-getBlogById");
		User principal = null;
		Blog blog = blogService.getBlogById(id);
		// 每次读取，简单的可以认为阅读量增加1次
		blogService.readsIncrease(id);

		boolean isBlogOwner = false;

		// 判断操作用户是否是博客的所有者
		if (SecurityContextHolder.getContext().getAuthentication() != null
				&& SecurityContextHolder.getContext().getAuthentication().isAuthenticated() && !SecurityContextHolder
						.getContext().getAuthentication().getPrincipal().toString().equals("anonymousUser")) {
			principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (principal != null && username.equals(principal.getUsername())) {
				isBlogOwner = true;
			}
		}
		// 判断用户是否已经点赞
		List<Vote> votes = blog.getVotes();
		Vote currentVote = null; // 当前用户的点赞情况
		System.out.println(votes);
		if (principal != null) {
			for (Vote vote : votes) {
				if (vote.getUser().getUsername().equals(principal.getUsername())) {
					currentVote = vote;
					break;
				}
			}
		}
		model.addAttribute("isBlogOwner", isBlogOwner);
		model.addAttribute("blogModel", blogService.getBlogById(id));
		model.addAttribute("currentVote", currentVote);
		return "userspace/blog";
	}

	/**
	 * 	删除博客
	 * @param:@param username
	 * @param:@param id
	 * @param:@return   
	 * @return:ResponseEntity<Response>  
	 * @author:wanggk
	 * @date:2018年10月26日
	 * @version:V1.0
	 */
	@RequestMapping(value = "/{username}/blogs/{id}", method = RequestMethod.DELETE)
	@PreAuthorize("authentication.name.equals(#username)")
	public ResponseEntity<Response> deleteBlog(@PathVariable("username") String username,
			@PathVariable("id") Integer id) {
		System.out.println("UserspaceController-deleteBlog");
		try {
			blogService.removeBlog(id);
			esBlogService.removeByBlogId(id);
		} catch (Exception e) {
			return ResponseEntity.ok().body(new Response(false, e.getMessage()));
		}

		String redirectUrl = "/u/" + username + "/blogs";
		return ResponseEntity.ok().body(new Response(true, "处理成功", redirectUrl));
	}

	/**
	 *	 获取新增博客的界面
	 * @param:@param model
	 * @param:@return   
	 * @return:ModelAndView  
	 * @author:wanggk
	 * @date:2018年10月26日
	 * @version:V1.0
	 */
	@RequestMapping(value = "/{username}/blogs/edit", method = RequestMethod.GET)
	public ModelAndView createBlog(@PathVariable("username")String username,Model model) {
		System.out.println("UserspaceController-createBlog");
		User user = (User)userDetailsService.loadUserByUsername(username);
		List<Catalog> catalogs = catalogService.listCatalogs(user);
		model.addAttribute("catalogs", catalogs);
		model.addAttribute("blog", new Blog(null, null, null));
		return new ModelAndView("userspace/blogedit", "blogModel", model);
	}

	/**
	 *	 获取编辑博客的界面
	 * @param:@param username
	 * @param:@param id
	 * @param:@param model
	 * @param:@return   
	 * @return:ModelAndView  
	 * @author:wanggk
	 * @date:2018年10月26日
	 * @version:V1.0
	 */
	@RequestMapping(value = "/{username}/blogs/edit/{id}", method = RequestMethod.GET)
	public ModelAndView editBlog(@PathVariable("username") String username, @PathVariable("id") Integer id,
			Model model) {
		System.out.println("UserspaceController-editBlog");
		User user = (User)userDetailsService.loadUserByUsername(username);
		List<Catalog> catalogs = catalogService.listCatalogs(user);
		model.addAttribute("catalogs", catalogs);
		model.addAttribute("blog", blogService.getBlogById(id));
		return new ModelAndView("userspace/blogedit", "blogModel", model);
	}

	/**
	 *	保存博客
	 * @param:@param username
	 * @param:@param blog
	 * @param:@return   
	 * @return:ResponseEntity<Response>  
	 * @author:wanggk
	 * @date:2018年10月26日
	 * @version:V1.0
	 */
	@RequestMapping(value = "/{username}/blogs/edit", method = RequestMethod.POST)
	//@PreAuthorize("authentication.name.equals(#username)")
	public ResponseEntity<Response> saveBlog(@PathVariable("username") String username, @RequestBody Blog blog) {
		System.out.println("UserspaceController-saveBlog");
		System.out.println(blog);
		if (blog.getCatalog().getId() == null) {
			return ResponseEntity.ok().body(new Response(false,"未选择分类"));
		}
		try {

			// 判断是修改还是新增
			
			if (blog.getId()!=null) {
				Blog orignalBlog = blogService.getBlogById(blog.getId());
				orignalBlog.setTitle(blog.getTitle());
				orignalBlog.setContent(blog.getContent());
				orignalBlog.setSummary(blog.getSummary());
				orignalBlog.setCatalog(blog.getCatalog());
				orignalBlog.setTags(blog.getTags());
				orignalBlog.setTags(blog.getTags());
				blogService.saveBlog(orignalBlog);
	        } else {
	    		User user = (User)userDetailsService.loadUserByUsername(username);
	    		blog.setUser(user);
				blogService.saveBlog(blog);
	        }
			
		} catch (ConstraintViolationException e)  {
			return ResponseEntity.ok().body(new Response(false, ConstraintViolationExceptionHandler.getMessage(e)));
		} catch (Exception e) {
			return ResponseEntity.ok().body(new Response(false, e.getMessage()));
		}
		
		String redirectUrl = "/u/" + username + "/blogs/" + blog.getId();
		return ResponseEntity.ok().body(new Response(true, "处理成功", redirectUrl));
	}
}
