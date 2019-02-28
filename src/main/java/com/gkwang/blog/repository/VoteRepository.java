package com.gkwang.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gkwang.blog.domain.Vote;

/**
 * 	点赞仓库类
 * @Title: VoteRepository.java
 * @Package:com.gkwang.blog.repository
 * @author:Wanggk 
 * @date:2018年10月25日
 * @version:V1.0
 */
@Repository
public interface VoteRepository extends JpaRepository<Vote, Integer> {

}
