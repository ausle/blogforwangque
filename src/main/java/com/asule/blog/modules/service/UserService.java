package com.asule.blog.modules.service;

import com.asule.blog.modules.po.User;
import com.asule.blog.modules.vo.AccountProfile;
import com.asule.blog.modules.vo.UserVO;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface UserService {


    void register(UserVO userVO);


    AccountProfile login(String username,String password);


    AccountProfile findProfile(Long id);


    Map<Long, UserVO> findByIdIn(Set<Long> ids);


}
