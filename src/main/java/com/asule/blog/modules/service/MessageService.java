package com.asule.blog.modules.service;

import com.asule.blog.modules.vo.MessageVO;

public interface MessageService {

    /**
     * 发送通知
     * @param message
     */
    void send(MessageVO message);
}
