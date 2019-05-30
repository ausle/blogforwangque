package com.asule.blog.modules.service.impl;


import com.asule.blog.base.lang.Consts;
import com.asule.blog.modules.po.PostTag;
import com.asule.blog.modules.po.Tag;
import com.asule.blog.modules.repository.PostTagRepository;
import com.asule.blog.modules.repository.TagRepository;
import com.asule.blog.modules.service.TagService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Transactional(readOnly = true)
@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private PostTagRepository postTagRepository;

    @Autowired
    private TagRepository tagRepository;


    /*
        若标签名不存在，则保存该标签。默认posts，即该标签的文章数为1。
        若标签名存在，则更新该标签的文章id(一条tag只记录该标签下的最后一次的文章id。)，文章数量。

        post-tag记录文章和标签多对多的关系表。
    */
    @Override
    public void batchUpdate(String names, long latestPostId) {
        //没有标签的文章，直接return
        if (StringUtils.isBlank(names.trim())) {
            return;
        }
        String[] ns = names.split(Consts.SEPARATOR);
        Date current = new Date();
        for (String n : ns) {
            String name = n.trim();
            if (StringUtils.isBlank(name)) {
                continue;
            }
            Tag po = tagRepository.findByName(name);
            if (po != null) {
                PostTag pt = postTagRepository.findByPostIdAndTagId(latestPostId, po.getId());
                if (null != pt) {
                    pt.setWeight(System.currentTimeMillis());
                    postTagRepository.save(pt);
                    continue;
                }
                po.setPosts(po.getPosts() + 1);
                po.setUpdated(current);
            } else {
                po = new Tag();
                po.setName(name);
                po.setCreated(current);
                po.setUpdated(current);
                po.setPosts(1);
            }

            po.setLatestPostId(latestPostId);
            tagRepository.save(po);

            PostTag pt = new PostTag();
            pt.setPostId(latestPostId);
            pt.setTagId(po.getId());
            pt.setWeight(System.currentTimeMillis());
            postTagRepository.save(pt);
        }
    }

    @Override
    public void deteleMappingByPostId(long postId) {

    }


    /*

        文章1     标签1  标签2
        文章2     标签1

    */


    @Override
    public List<Tag> findTagsByPost(long postId) {
        List<PostTag> postTagList = postTagRepository.findByPostId(postId);
        List<Tag> tags=new ArrayList<>();
        postTagList.forEach(postTag ->
                        tags.add(tagRepository.findById(postTag.getTagId()).get())
        );

        return tags;
    }

    @Override
    public List<Tag> findTags() {
        return tagRepository.findAll();
    }

//    @Override
//    public Set<String> getAllTagName() {
//        Set<String> names=new HashSet<>();
//        tagRepository.findAll().forEach(e->names.add(e.getName()));
//        return null;
//    }
//
//    @Override
//    public String getOneTagName(long tagId) {
//
//        return tagRepository.findNameById(tagId);
//    }
}
