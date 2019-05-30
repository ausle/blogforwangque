package com.asule.blog.modules.service.impl;

import com.asule.blog.modules.repository.PostTagRepository;
import com.asule.blog.modules.service.PostTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Transactional(readOnly = true)
@Service
public class PostTagServiceImpl implements PostTagService{

    @Autowired
    private PostTagRepository postTagRepository;

    @Override
    public Set<Long> findAllPostId() {

        Set<Long> postIds=new HashSet<>();

        postTagRepository.findAll().forEach(postTag -> postIds.add(postTag.getPostId()));

        return postIds;
    }

    @Override
    public Set<Long> findPostIdByTagId(long tagId) {
        Set<Long> postIds=new HashSet<>();
        postTagRepository.findByTagId(tagId).forEach(postTag -> postIds.add(postTag.getPostId()));
        return postIds;
    }
}
