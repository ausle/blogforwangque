package com.asule.blog.modules.service;

import com.asule.blog.modules.vo.CommentVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface CommentService {


    /**
     * 发表评论
     * @param commentVO
     * @return
     */
    long post(CommentVO commentVO);


    /**
     * 获取评论列表
     */

    Page<CommentVO> pagingByPostId(Pageable pageable, long postId);


    /**
     * 查询有子节点的评论
     * @param parentId
     * @return
     */
    Map<Long,CommentVO> findByIds(Set<Long> parentId);




}
