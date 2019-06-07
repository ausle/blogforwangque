package com.asule.blog.modules.event;

import org.springframework.context.ApplicationEvent;


public class PostUpdateEvent extends ApplicationEvent{

    public final static int ACTION_PUBLISH = 1;
    public final static int ACTION_DELETE = 2;

    private long postId;
    private long userId;
    private int action = ACTION_PUBLISH;
    private int channelId;


    public int getChannelId() {
        return channelId;
    }

    public void setChannelId(int channelId) {
        this.channelId = channelId;
    }

    public PostUpdateEvent(Object source) {
        super(source);
    }

    public long getPostId() {
        return postId;
    }

    public void setPostId(long postId) {
        this.postId = postId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }
}
