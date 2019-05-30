package com.asule.blog.modules.repository;

import com.asule.blog.modules.po.PostTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostTagRepository extends JpaRepository<PostTag,Long>{


    PostTag findByPostIdAndTagId(long postId,long tagId);

    List<PostTag> findByPostId(long postId);

    List<PostTag> findByTagId(long tagId);

}
