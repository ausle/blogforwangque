package com.asule.blog.modules.service.impl;

import com.asule.blog.modules.po.Permission;
import com.asule.blog.modules.po.Role;
import com.asule.blog.modules.po.RolePermission;
import com.asule.blog.modules.po.UserRole;
import com.asule.blog.modules.repository.PermissionRepository;
import com.asule.blog.modules.repository.RolePermissionRepository;
import com.asule.blog.modules.repository.RoleRepository;
import com.asule.blog.modules.repository.UserRoleRepository;
import com.asule.blog.modules.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Transactional
@Service
public class UserRoleServiceImpl implements UserRoleService{


    @Autowired
    private UserRoleRepository userRoleRepository;


    @Autowired
    private RoleRepository roleRepository;


    @Autowired
    private RolePermissionRepository rolePermissionRepository;


    @Autowired
    private PermissionRepository permissionRepository;



    /*




    */


    @Override
    public List<Role> findByUserId(Long userId) {

        List<UserRole> userRoleList = userRoleRepository.findByUserId(userId);

        List<Role> roles=new ArrayList<>();

        userRoleList.forEach(userRole -> {
            Role role = roleRepository.findById(userRole.getRoleId()).get();

            List<RolePermission> rolePermissions = rolePermissionRepository.findByRoleId(role.getId());
            role.setPermissions(toPermission(rolePermissions));

            roles.add(role);
        });


        return roles;
    }

    private List<Permission> toPermission(List<RolePermission> rolePermissions) {

        Set<Long> permissionIds=new HashSet<>();

        rolePermissions.forEach(rolePermission -> {
            permissionIds.add(rolePermission.getPermissionId());
        });

        return permissionRepository.findByIdIn(permissionIds);
    }
}
