package com.asule.blog.modules.repository;

import com.asule.blog.modules.po.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface PermissionRepository extends JpaRepository<Permission,Long>{

    List<Permission> findByIdIn(Set<Long> ids);

}
