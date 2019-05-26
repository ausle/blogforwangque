package com.asule.blog.modules.template.directive;


import com.asule.blog.base.lang.Consts;
import com.asule.blog.modules.po.Channel;
import com.asule.blog.modules.repository.PostRepository;
import com.asule.blog.modules.service.ChannelService;
import com.asule.blog.modules.service.PostService;
import com.asule.blog.modules.template.DirectiveHandler;
import com.asule.blog.modules.template.TemplateDirective;
import com.asule.blog.modules.vo.ChannelCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.*;

@Component
public class ChannelContentsDirective extends TemplateDirective {


    @Autowired
    private ChannelService channelService;

    @Autowired
    private PostRepository postRepository;

    @Override
    public String getName() {
        return "channel_contents";
    }

    @Override
    protected void execute(DirectiveHandler directiveHandler) throws Exception {
        List<Channel> channels = channelService.findAll(Consts.STATUS_CHANNEL_NORMAL);
        directiveHandler.put("channels",channels).render();
    }



}
