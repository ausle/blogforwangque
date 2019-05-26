package com.asule.blog.modules.service.impl;

import com.asule.blog.base.lang.Consts;
import com.asule.blog.base.lang.EntityStatus;
import com.asule.blog.base.utils.BeanMapUtils;
import com.asule.blog.base.utils.MD5;
import com.asule.blog.modules.po.User;
import com.asule.blog.modules.repository.UserRepository;
import com.asule.blog.modules.service.UserService;
import com.asule.blog.modules.vo.AccountProfile;
import com.asule.blog.modules.vo.UserVO;
import org.aspectj.lang.annotation.AfterThrowing;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
public class UserServiceImpl implements UserService{


    @Autowired
    private UserRepository userRepository;


    @Override
    public void register(UserVO userVO) {
        Assert.notNull(userVO, "Parameter user can not be null!");
        Assert.hasLength(userVO.getUsername(), "用户名不能为空!");
        Assert.hasLength(userVO.getPassword(), "密码不能为空!");

        User check = userRepository.findByUsername(userVO.getUsername());
        Assert.isNull(check, "用户名已经存在!");

        User user = new User();
        BeanUtils.copyProperties(userVO,user);


        if (StringUtils.isEmpty(user.getName())){
            user.setName(userVO.getUsername());
        }

        Date now = Calendar.getInstance().getTime();
        user.setPassword(MD5.md5(user.getPassword()));
        user.setStatus(EntityStatus.ENABLED);
        user.setCreated(now);

        userRepository.save(user);


        //把po数据再映射到一个新的vo上
    }

    @Override
    public AccountProfile login(String username, String password) {

        User po = userRepository.findByUsername(username);
        AccountProfile u = null;
        Assert.notNull(po, "账户不存在");
		Assert.state(po.getStatus() == EntityStatus.ENABLED, "您的账户已被封禁");
        Assert.state(org.apache.commons.lang3.StringUtils.equals(po.getPassword(), password), "密码错误");

        po.setLastLogin(Calendar.getInstance().getTime());
        userRepository.save(po);
        u = BeanMapUtils.copyPassport(po);


        return u;
    }

    @Override
    public AccountProfile findProfile(Long id) {
        User user = userRepository.findById(id).get();
        user.setLastLogin(Calendar.getInstance().getTime());
        AccountProfile accountProfile = BeanMapUtils.copyPassport(user);
        return accountProfile;
    }

    @Override
    public  Map<Long, UserVO> findByIdIn(Set<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return Collections.emptyMap();
        }

        List<User> users=userRepository.findByIdIn(ids);

        Map<Long, UserVO> map=new HashMap<>();

        users.forEach(user -> {
            map.put(user.getId(),BeanMapUtils.copy(user));
        });

        return map;
    }
}
