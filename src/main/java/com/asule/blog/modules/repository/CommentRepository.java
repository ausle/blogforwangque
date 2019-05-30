package com.asule.blog.modules.repository;

import com.asule.blog.modules.po.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface CommentRepository extends JpaRepository<Comment,Long>{


    Page<Comment> findByPostId(Pageable pageable, long postId);


    List<Comment> findByPidIn(Set<Long> pids);


    List<Comment> findByIdIn(Set<Long> pids);



}
