package com.asule.blog.modules.repository;

import com.asule.blog.modules.po.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission,Long>{

}
