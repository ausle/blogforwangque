package com.asule.blog.modules.service.impl;

import com.asule.blog.modules.po.Message;
import com.asule.blog.modules.repository.MessageRepository;
import com.asule.blog.modules.service.MessageService;
import com.asule.blog.modules.vo.MessageVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Transactional
@Service
public class MessageServiceImpl implements MessageService{


    @Autowired
    private MessageRepository messageRepository;

    @Override
    public void send(MessageVO message) {
        if (message == null || message.getUserId() <=0 || message.getFromId() <= 0) {
            return;
        }

        Message po = new Message();
        BeanUtils.copyProperties(message, po);
        po.setCreated(new Date());

        messageRepository.save(po);
    }
}
