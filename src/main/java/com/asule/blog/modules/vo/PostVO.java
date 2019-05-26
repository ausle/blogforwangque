package com.asule.blog.modules.vo;


import com.alibaba.fastjson.annotation.JSONField;
import com.asule.blog.modules.po.Channel;
import com.asule.blog.modules.po.Post;
import com.asule.blog.modules.po.PostAttribute;
import javafx.geometry.Pos;

import java.io.Serializable;

public class PostVO extends Post implements Serializable {

    private static final long serialVersionUID = -1144627551517707139L;

//    private String editor;
    private String content;

    private UserVO author;
    private Channel channel;

    @JSONField(serialize = false)
    private PostAttribute attribute;


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

//    public String getEditor() {
//        return editor;
//    }
//
//    public void setEditor(String editor) {
//        this.editor = editor;
//    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public UserVO getAuthor() {
        return author;
    }

    public void setAuthor(UserVO author) {
        this.author = author;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public PostAttribute getAttribute() {
        return attribute;
    }

    public void setAttribute(PostAttribute attribute) {
        this.attribute = attribute;
    }
}
