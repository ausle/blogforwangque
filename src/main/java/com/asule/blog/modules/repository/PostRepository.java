package com.asule.blog.modules.repository;


import com.asule.blog.modules.po.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface PostRepository extends JpaRepository<Post,Long>,JpaSpecificationExecutor<Post> {


    /**
     *      PagingAndSortingRepository可以进行排序且分页的查询，但是无法进行带查询条件的分页。
     */

    Page<Post> findAllByChannelIdIn(Set<Integer> channels, Pageable pageable);


    Page<Post> findAllByChannelIdInAndIdIn(Set<Integer> channels, Set<Long> postIds,Pageable pageable);

    Page<Post> findAllByChannelIdAndTitle(Integer channelId, String title, Pageable pageable, Specification specification);

    List<Post> findAllByChannelIdIn(Set<Integer> channels);


    @Query(nativeQuery = true,value = "SELECT COUNT(1) FROM asule_post a WHERE a.channel_id= :channelId")
    int findCountByChannelId(@Param("channelId") Integer channelId);



    @Modifying
    @Query("update from Post set views=views+:increment where id=:postId")
    int identityViews(@Param("postId")long postId,@Param("increment")int increment);



    @Modifying
    @Query("update from Post set comments=comments+:increment where id=:postId")
    int identityComments(@Param("postId")long postId,@Param("increment")int increment);


}
