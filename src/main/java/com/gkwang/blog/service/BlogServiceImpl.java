package com.gkwang.blog.service;


import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.gkwang.blog.domain.Blog;
import com.gkwang.blog.domain.Catalog;
import com.gkwang.blog.domain.Comment;
import com.gkwang.blog.domain.EsBlog;
import com.gkwang.blog.domain.User;
import com.gkwang.blog.domain.Vote;
import com.gkwang.blog.repository.BlogRepository;
@Service
public class BlogServiceImpl implements BlogService {
	
	@Autowired
	private BlogRepository blogRepository;

	@Autowired
	private EsBlogService esBlogService;
	
	@Transactional
	@Override
	public Blog saveBlog(Blog blog) {
		// TODO Auto-generated method stub
		
		boolean isNew = (blog.getId() == null);
		EsBlog esBlog = null;
		
		Blog returnBlog = blogRepository.save(blog);
		
		if (isNew) {
			esBlog = new EsBlog(returnBlog);
		} else {
			esBlog = esBlogService.getEsBlogByBlogId(blog.getId());
			esBlog.update(returnBlog);
		}
		
		esBlogService.updateEsBlog(esBlog);
		return returnBlog;
	}
	
	@Transactional
	@Override
	public void removeBlog(Integer id) {
		// TODO Auto-generated method stub
		blogRepository.delete(id);
	}

	@Override
	public Blog getBlogById(Integer id) {
		// TODO Auto-generated method stub
		return  blogRepository.findOne(id);
	}

	@Override
	public Page<Blog> listBlogsByTitleLike(User user, String title, Pageable pageable) {
		// TODO Auto-generated method stub
		title = "%"+title+"%"; 
		Page<Blog> blogs = blogRepository.findByUserAndTitleLikeOrderByCreateTimeDesc(user,title,pageable);
		return blogs;
	}

	@Override
	public Page<Blog> listBlogsByTitleLikeAndSort(User user, String title, Pageable pageable) {
		// TODO Auto-generated method stub
		title = "%"+title+"%"; 
		Page<Blog> blogs = blogRepository.findByUserAndTitleLike(user, title, pageable);
		return blogs;
	}

	@Override
	public void readsIncrease(Integer id) {
		// TODO Auto-generated method stub
		Blog blog  = blogRepository.findOne(id);
		blog.setReadSize(blog.getReadSize()+1);
		this.saveBlog(blog);
	}

	@Override
	public Blog createComment(Integer blogId, String commentContent) {
		// TODO Auto-generated method stub
		Blog originalBlog  = blogRepository.findOne(blogId);
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Comment comment = new Comment(user, commentContent);
		originalBlog.addComment(comment);
		return this.saveBlog(originalBlog);
	}

	@Override
	public void removeComment(Integer blogId, Integer commentId) {
		// TODO Auto-generated method stub
		Blog originalBlog  = blogRepository.findOne(blogId);
		originalBlog.removeComment(commentId);
		this.saveBlog(originalBlog);
	}

	@Override
	public Blog createVote(Integer blogid) {
		// TODO Auto-generated method stub
		Blog originalBlog = blogRepository.findOne(blogid);
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Vote vote = new Vote(user);
		boolean isExist = originalBlog.addVote(vote);
		if (isExist) {
			throw new IllegalArgumentException("您已经点过赞了");
		}
		return this.saveBlog(originalBlog);
	}

	@Override
	public void removeVote(Integer blogid,Integer voteid) {
		// TODO Auto-generated method stub
		Blog originalBlog= blogRepository.findOne(blogid);
		originalBlog.removeVote(voteid);
		this.saveBlog(originalBlog);
		
	}

	@Override
	public Page<Blog> listBlogsByCatalog(Catalog catalog, Pageable pageable) {
		// TODO Auto-generated method stub
		return blogRepository.findByCatalog(catalog, pageable);
	}

	@Override
	public Page<Blog> listBlogsByTitleLike(String title, Pageable pageable) {
		// TODO Auto-generated method stub
		title = "%" + title + "%";
		Page<Blog> blogs = blogRepository.findByTitleLike(title,pageable);
		return blogs;
	}

	

}
