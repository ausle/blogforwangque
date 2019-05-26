package com.asule.blog.modules.repository;

import com.asule.blog.modules.po.Channel;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface ChannelRepository extends JpaRepository<Channel,Long>{


    List<Channel> findAllByStatus(Integer status, Sort sort);

    List<Channel> findAllByStatusAndId(Integer status, Integer id,Sort sort);

    List<Channel> findByIdIn(Set<Integer> ids);


    @Modifying
    @Query(value = "update Channel c set c.amount=c.amount+1 where c.id= :channleId")
    void updatePostAmount(@Param("channleId") Integer channleId);

}
