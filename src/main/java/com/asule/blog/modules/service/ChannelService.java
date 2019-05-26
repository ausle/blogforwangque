package com.asule.blog.modules.service;

import com.asule.blog.modules.po.Channel;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ChannelService {

    List<Channel> findAll(Integer status);

    List<Channel> findById(Integer status,Integer id);

    Map<Integer,Channel> findByIds(Set<Integer> ids,Integer status);
}
