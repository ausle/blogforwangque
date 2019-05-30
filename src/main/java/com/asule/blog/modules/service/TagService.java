package com.asule.blog.modules.service;

import com.asule.blog.modules.po.Tag;

import java.util.List;
import java.util.Set;

public interface TagService {


    /**
     * 处理文章与标签的关系
     * @param names
     * @param latestPostId
     */
    void batchUpdate(String names, long latestPostId);

    void deteleMappingByPostId(long postId);


    /**
     * 查询某个文章的所有标签
     */
    List<Tag> findTagsByPost(long postId);


    /**
     * 查询所有标签
     * @return
     */
    List<Tag> findTags();

//
//    Set<String> getAllTagName();
//
//
//    String getOneTagName(long tagId);

}
