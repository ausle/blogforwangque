package com.asule.blog.modules.service;

import java.util.Set;

public interface PostTagService {



    Set<Long> findAllPostId();


    Set<Long> findPostIdByTagId(long tagId);

}
