package com.asule.blog.modules.service;


import com.asule.blog.base.lang.Consts;
import com.asule.blog.modules.vo.AccountProfile;
import com.asule.blog.modules.vo.UserVO;
import org.springframework.cache.annotation.CacheConfig;

import java.util.Map;
import java.util.Set;

@CacheConfig(cacheNames = Consts.CACHE_USER)
public interface UserService {


    void register(UserVO userVO);


    AccountProfile login(String username, String password);


    AccountProfile findProfile(Long id);


    Map<Long, UserVO> findByIdIn(Set<Long> ids);


    UserVO findUserVO(Long id);


    int getUserCount();
}
