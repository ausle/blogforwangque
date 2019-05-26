package com.asule.blog.modules.repository;

import com.asule.blog.modules.po.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface ResourceRepository extends JpaRepository<Resource,Long>{

    Resource findByMd5(String md5);


    List<Resource> findAllByMd5In(List<String> md5s);


    @Modifying
    @Query("update Resource set amount = amount + :increment where md5 in (:md5s)")
    int updateAmount(@Param("md5s") Collection<String> md5s, @Param("increment") long increment);


}
