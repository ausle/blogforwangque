package com.asule.blog.modules.repository;

import com.asule.blog.modules.po.PostTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostTagRepository extends JpaRepository<PostTag,Long>{


    PostTag findByPostIdAndTagId(long postId,long tagId);


}
