package com.asule.blog.modules.service;

import com.asule.blog.base.lang.Consts;
import com.asule.blog.modules.vo.PostVO;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Set;

/**
 * 该缓存相当于一个map。
 * key为缓存key，value为
 */


@CacheConfig(cacheNames = Consts.CACHE_USER)
public interface PostService {


    /**
     * 发布文章
     */
    //@CacheEvict，标记需要清除缓存。每一次该方法的执行都会触发清除缓存的操作。
    //value表示清除缓存的缓存名，key为具体缓存key。
    //allEntries为true，表示一下子清除所有缓存。
    @CacheEvict(allEntries = true)
    long post(PostVO post);


    /**
     * @param pageable
     * @return
     */
    Page<PostVO> paging(Pageable pageable, Set<Integer> includeChannelIds);




}
