package com.asule.blog.modules.event.handler;

import com.asule.blog.base.lang.Consts;
import com.asule.blog.modules.event.MessageEvent;
import com.asule.blog.modules.event.PostUpdateEvent;
import com.asule.blog.modules.po.Comment;
import com.asule.blog.modules.repository.CommentRepository;
import com.asule.blog.modules.service.CommentService;
import com.asule.blog.modules.service.MessageService;
import com.asule.blog.modules.service.PostService;
import com.asule.blog.modules.service.UserEventService;
import com.asule.blog.modules.vo.MessageVO;
import com.asule.blog.modules.vo.PostVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class MessageEventHandler implements ApplicationListener<MessageEvent>{


    @Autowired
    private PostService postService;

    @Autowired
    private MessageService messageService;


    @Autowired
    private CommentRepository commentRepository;


    @Override
    public void onApplicationEvent(MessageEvent messageEvent) {
        MessageVO messageVO = new MessageVO();

        messageVO.setFromId(messageEvent.getFromUserId());  // 评论的from
        messageVO.setPostId(messageEvent.getPostId());      //文章id
        messageVO.setEvent(messageEvent.getEvent());

        switch (messageEvent.getEvent()){
                case Consts.MESSAGE_EVENT_COMMENT:
                        PostVO postVO = postService.get(messageEvent.getPostId());
                        Assert.notNull(postVO, "文章不存在");
                        messageVO.setUserId(postVO.getAuthorId());
                        //文章自增评论
                        postService.identityComments(messageEvent.getPostId());
                    break;

                    //代表回复了某条评论
                    case Consts.MESSAGE_EVENT_COMMENT_REPLY:

                        Comment comment = commentRepository.findById(messageEvent.getCommentParentId()).get();
                        Assert.notNull(comment,"你回复的评论不存在");
                        messageVO.setUserId(comment.getAuthorId());

                        //文章自增评论
                        postService.identityComments(messageEvent.getPostId());
                        break;


                default:
                    break;
        }

        messageService.send(messageVO);
    }
}
