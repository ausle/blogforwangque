package com.asule.blog.modules.service;

import com.asule.blog.base.lang.Consts;
import com.asule.blog.modules.po.Post;
import com.asule.blog.modules.vo.PostVO;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
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


    @CacheEvict(allEntries = true)
    void update(PostVO p);


    /**
     * 查看一部分类别的文章
     * @param pageable
     * @return
     */
    @Cacheable
    Page<PostVO> paging(Pageable pageable, Set<Integer> includeChannelIds,Set<Long> postIds);


    /**
     * 获取文章
     * @param id
     * @return
     */
    @Cacheable(key = "'post_' + #id")
    PostVO get(long id);


//    /**
//     * 获取后一篇文章
//     * @return
//     */
//    PostVO getNextPost(int orderValue);
//
//
//    /**
//     * 获取前一篇文章
//     * @return
//     */
//    PostVO getPrePost(int orderValue);



    /**
     * 把相应的文章点击量+1
     * @param id
     */
    @CacheEvict(key = "'view_' + #id")
    void identityViews(long id);


    /**
     * 文章评论+1
     * @param id
     */
    @CacheEvict(key = "'comment_' + #id")
    void identityComments(long id);


    /**
     * 根据类型和title模糊查询文章列表
     * @param pageable
     * @param channelId
     * @param title
     * @return
     */
    Page<PostVO> paging4Admin(Pageable pageable,Integer channelId,String title);



    /**
     * 批量删除文章, 且刷新缓存
     *
     * @param ids
     */
    @CacheEvict(allEntries = true)
    void delete(Collection<Long> ids);
}
