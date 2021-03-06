package com.asule.blog.modules.repository;

import com.asule.blog.modules.po.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface UserRepository extends JpaRepository<User,Long>{


    User findByUsername(String username);


    @Modifying
    @Query("update User set posts = posts + :increment where id = :id")
    int updatePosts(@Param("id") long id, @Param("increment") int increment);



    @Modifying
    @Query("update User set comments = comments + :increment where id = :id")
    int updateComments(@Param("id") long id, @Param("increment") int increment);


    List<User> findByIdIn(Set<Long> ids);


    @Query(nativeQuery = true,value = "SELECT count(1) FROM asule_user")
    int getUserCount();

}
