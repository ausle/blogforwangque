package com.asule.blog.web.site.comment;

import com.asule.blog.base.lang.Consts;
import com.asule.blog.base.lang.Result;
import com.asule.blog.modules.event.MessageEvent;
import com.asule.blog.modules.service.CommentService;
import com.asule.blog.modules.vo.AccountProfile;
import com.asule.blog.modules.vo.CommentVO;
import com.asule.blog.web.BaseController;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/comment")
public class CommentController extends BaseController{

    @Autowired
    private CommentService commentService;

    @Autowired
    private ApplicationContext applicationContext;

    @GetMapping("/list/{postId}")
    @ResponseBody
    public Page<CommentVO> view(@PathVariable(value = "postId")Long postId){
        //根据评论id来降序(最近的评论出现在最上面)
        Pageable pageable=wrapPageable(Sort.by(Sort.Direction.DESC,"id"));
        Page<CommentVO> commentVOS = commentService.pagingByPostId(pageable, postId);
        return  commentVOS;
    }

    @PostMapping("/submit")
    @ResponseBody
    public Result post(Long toId, String text, HttpServletRequest request) {
        if (!isAuthenticated()){
            return Result.failure("请先登录在进行操作");
        }

        long pid = ServletRequestUtils.getLongParameter(request, "pid", 0);

        if (toId <= 0 || StringUtils.isBlank(text)) {
            return Result.failure("操作失败");
        }

        AccountProfile profile = getProfile();

        CommentVO c = new CommentVO();
        c.setPostId(toId);
        c.setContent(HtmlUtils.htmlEscape(text));
        c.setAuthorId(profile.getId());
        c.setPid(pid);

        commentService.post(c);
        sendMessage(c.getAuthorId(),c.getPostId(),c.getPid());

        return Result.successMessage("发表成功");

    }

    private void sendMessage(long userId, long postId, long pid) {

        MessageEvent messageEvent = new MessageEvent("messageEvent");
        messageEvent.setFromUserId(userId);

        if (pid>0){
            messageEvent.setEvent(Consts.MESSAGE_EVENT_COMMENT_REPLY);
            messageEvent.setCommentParentId(pid);   //你回复了谁的评论，为该评论id。
        }else{
            //评论事件
            messageEvent.setEvent(Consts.MESSAGE_EVENT_COMMENT);
        }

        messageEvent.setPostId(postId);
        applicationContext.publishEvent(messageEvent);
    }



}
