package com.gkwang.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gkwang.blog.domain.Vote;
import com.gkwang.blog.repository.VoteRepository;

/**
 * 	vote管理服务实现
 * @Title: VoteServiceImpl.java
 * @Package:com.gkwang.blog.service
 * @author:Wanggk 
 * @date:2018年10月25日
 * @version:V1.0
 */
@Service
public class VoteServiceImpl implements VoteService {

	@Autowired
	private VoteRepository voteRepository;
	
	@Override
	public Vote getVoteById(Integer id) {
		// TODO Auto-generated method stub
		Vote vote = voteRepository.findOne(id);
		return vote;
	}

	@Override
	public void removeVote(Integer id) {
		// TODO Auto-generated method stub
		voteRepository.delete(id);
	}

}
