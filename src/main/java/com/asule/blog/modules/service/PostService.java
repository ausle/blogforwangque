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



/*
    @Cacheable value指定缓存存放在哪块命名空间，key为该缓存的标识。

    如果一个类上使用缓存的地方很多，那么在类上使用@CacheConfig(cacheNames = Consts.CACHE_USER)
    为所有缓存统一指定一个value值、那么该类下就可以省略value值。
*/
@CacheConfig(cacheNames = Consts.CACHE_POST)
public interface PostService {


    /**
     * 发布文章
     */
    //@CacheEvict，标记需要清除缓存。每一次该方法的执行都会触发清除缓存的操作。allEntries为true，表示一下子清除所有缓存。
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
     * 获取轮播图相关的文章
     * @param pageable
     * @return
     */
    @Cacheable
    Page<PostVO> pagingCarousel(Pageable pageable);




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
     * 批量删除文章, 清空缓存
     * @param ids
     */
    @CacheEvict(allEntries = true)
    void delete(Collection<Long> ids);


    int getPostCount();

}
