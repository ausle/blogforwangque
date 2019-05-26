package com.asule.blog.modules.repository;


import com.asule.blog.modules.po.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag,Long> {



    Tag findByName(String name);


}
