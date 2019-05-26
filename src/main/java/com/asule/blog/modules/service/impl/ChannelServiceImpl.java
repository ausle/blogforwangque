package com.asule.blog.modules.service.impl;

import com.asule.blog.base.lang.Consts;
import com.asule.blog.modules.po.Channel;
import com.asule.blog.modules.repository.ChannelRepository;
import com.asule.blog.modules.service.ChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional(readOnly = true)
public class ChannelServiceImpl implements ChannelService{


    @Autowired
    private ChannelRepository channelRepository;


    @Override
    public List<Channel> findAll(Integer status) {
        Sort sort = Sort.by(Sort.Direction.DESC, "weight", "id");
        List<Channel> list;
        if (status > Consts.IGNORE) {
            list = channelRepository.findAllByStatus(status, sort);
        } else {
            list = channelRepository.findAll(sort);
        }
        return list;
    }

    @Override
    public List<Channel> findById(Integer status,Integer id) {
        Sort sort = Sort.by(Sort.Direction.DESC, "weight", "id");
        List<Channel> list;
        if (status > Consts.IGNORE) {
            list = channelRepository.findAllByStatusAndId(status, id,sort);
        } else {
            list = channelRepository.findAll(sort);
        }
        return list;
    }

    @Override
    public Map<Integer, Channel> findByIds(Set<Integer> ids, Integer status) {

        if (ids==null||ids.isEmpty()){
            return Collections.emptyMap();
        }

        List<Channel> channelList = channelRepository.findByIdIn(ids);

        Map<Integer, Channel> map=new HashMap<>();

        channelList.forEach(channel -> {
            map.put(channel.getId(),channel);
        });


        return map;
    }
}
