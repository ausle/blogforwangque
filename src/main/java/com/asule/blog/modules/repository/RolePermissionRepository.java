package com.asule.blog.modules.repository;

import com.asule.blog.modules.po.Permission;
import com.asule.blog.modules.po.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RolePermissionRepository extends JpaRepository<RolePermission,Long>{


    List<Permission> findByRoleId(long roleId);



}
