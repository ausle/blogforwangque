package com.asule.blog.modules.service;

public interface TagService {
    void batchUpdate(String names, long latestPostId);
    void deteleMappingByPostId(long postId);
}
