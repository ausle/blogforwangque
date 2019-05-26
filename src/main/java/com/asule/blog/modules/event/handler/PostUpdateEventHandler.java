package com.asule.blog.modules.event.handler;

import com.asule.blog.modules.event.PostUpdateEvent;
import com.asule.blog.modules.service.UserEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class PostUpdateEventHandler implements ApplicationListener<PostUpdateEvent>{

    @Autowired
    private UserEventService userEventService;

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
        }
    }
}
