package com.asule.blog.modules.repository;

import com.asule.blog.modules.po.Role;
import com.asule.blog.modules.po.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRoleRepository extends JpaRepository<UserRole,Long>{

    List<UserRole> findByUserId(Long userId);


}
