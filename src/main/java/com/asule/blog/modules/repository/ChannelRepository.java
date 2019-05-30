package com.asule.blog.modules.repository;

import com.asule.blog.modules.po.Channel;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface ChannelRepository extends JpaRepository<Channel,Integer>{


    List<Channel> findAllByStatus(Integer status, Sort sort);

    Channel findAllByStatusAndId(Integer status, Integer id,Sort sort);

    List<Channel> findByIdIn(Set<Integer> ids);


//    List<Channel> findAll(Sort sort);


    @Modifying
    @Query(value = "update Channel c set c.amount=c.amount+1 where c.id= :channelId")
    void updatePostAmount(@Param("channelId") Integer channelId);


//    @Query(value = "update Channel c set c.weight=:weight where c.id= :channelId")
//    @Modifying
//    void updateWeightTop(@Param("channelId") Integer channelId,@Param("channelId") Integer weight);
//
//
//
    @Query(nativeQuery = true,value = "SELECT max(weight) FROM asule_channel")
    int getMaxWeight();


    void deleteById(Integer channelId);

}
