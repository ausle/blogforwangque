package com.asule.blog.modules.service;

import com.asule.blog.modules.po.Channel;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ChannelService {

    List<Channel> findAll(Integer status);

    Channel findById(Integer status,Integer id);

    Map<Integer,Channel> findByIds(Set<Integer> ids,Integer status);

    /**
     * 获得最大权重
     * @param channelId
     * @return
     */
    int setMaxWeight(Integer channelId);

    /**
     * 删除栏目
     * @param channelId
     * @return
     */
    int deleteChannel(Integer channelId);


    /**
     * 更新栏目
     * @return
     */
    int update(Channel channel);


    int getChannelCount();


    /**
     * channel中文章数-1
     * @param channelId
     */
    void updatePostAmount(Integer channelId);

}
