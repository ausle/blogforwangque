package com.asule.blog.modules.event.handler;

import com.asule.blog.base.lang.Consts;
import com.asule.blog.modules.event.PostUpdateEvent;
import com.asule.blog.modules.repository.ChannelRepository;
import com.asule.blog.modules.service.ChannelService;
import com.asule.blog.modules.service.CommentService;
import com.asule.blog.modules.service.UserEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class PostUpdateEventHandler implements ApplicationListener<PostUpdateEvent>{

    @Autowired
    private UserEventService userEventService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private ChannelService channelService;


    @Async
    @Override
    public void onApplicationEvent(PostUpdateEvent event) {
        if (event == null) {
            return;
        }
        switch (event.getAction()) {
            case PostUpdateEvent.ACTION_PUBLISH:
                userEventService.identityPost(event.getUserId(), true);
                break;
            case PostUpdateEvent.ACTION_DELETE:
                //用户文章数-1
                userEventService.identityPost(event.getUserId(), false);
                //该文章评论删除
                commentService.deleteByPostId(event.getPostId());

                //类型中的文章数量-1
                channelService.updatePostAmount(event.getChannelId());
                break;
        }
    }
}
