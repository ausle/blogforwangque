package com.asule.blog.modules.service;

import com.asule.blog.modules.po.Role;

import java.util.List;
import java.util.Map;

public interface UserRoleService {



    List<Role>  findByUserId(Long userId);




}
