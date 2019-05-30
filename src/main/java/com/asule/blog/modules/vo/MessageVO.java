package com.asule.blog.modules.vo;


import com.asule.blog.modules.po.Message;

/**
 */
public class MessageVO extends Message {
    // extend
    private UserVO from;
    private PostVO post;

    public UserVO getFrom() {
        return from;
    }

    public void setFrom(UserVO from) {
        this.from = from;
    }

    public PostVO getPost() {
        return post;
    }

    public void setPost(PostVO post) {
        this.post = post;
    }
}
