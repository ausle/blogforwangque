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
        if (status==Consts.IGNORE_CHANNEL_STATUS){
            list = channelRepository.findAll(sort);
        }else {
            list = channelRepository.findAllByStatus(status, sort);
        }
        return list;
    }





    @Override
    public Channel findById(Integer status,Integer id) {
        Sort sort = Sort.by(Sort.Direction.DESC, "weight", "id");
        Channel channel;
        if (status==Consts.IGNORE_CHANNEL_STATUS){
            channel = channelRepository.findById(id).get();
        }else{
            channel = channelRepository.findAllByStatusAndId(status, id,sort);
        }
        return channel;
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

    @Transactional
    @Override
    public int setMaxWeight(Integer channelId) {

        Channel channel = channelRepository.findById(channelId).get();
        int maxWeight = channelRepository.getMaxWeight();
        channel.setWeight(maxWeight+1);

        channelRepository.save(channel);

        return channel.getWeight();
    }

    @Transactional
    @Override
    public int deleteChannel(Integer channelId) {
        channelRepository.deleteById(channelId);
        return 0;
    }

    @Transactional
    @Override
    public int update(Channel channel) {
        channelRepository.save(channel);
        return channel.getId();
    }


}
