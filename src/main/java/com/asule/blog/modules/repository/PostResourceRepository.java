package com.asule.blog.modules.repository;


import com.asule.blog.modules.po.Post;
import com.asule.blog.modules.po.PostResource;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostResourceRepository extends JpaRepository<PostResource,Long> {



    List<PostResource> findByPostId(Long postId);


    void deleteByPostId(Long postId);



}
