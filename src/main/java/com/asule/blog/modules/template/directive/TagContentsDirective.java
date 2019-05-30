package com.asule.blog.modules.template.directive;


import com.asule.blog.modules.po.Tag;
import com.asule.blog.modules.service.ChannelService;
import com.asule.blog.modules.service.PostService;
import com.asule.blog.modules.service.TagService;
import com.asule.blog.modules.template.DirectiveHandler;
import com.asule.blog.modules.template.TemplateDirective;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TagContentsDirective extends TemplateDirective {


    @Autowired
    private ChannelService channelService;


    @Autowired
    private TagService tagService;


    @Override
    public String getName() {
        return "tag_contents";
    }

    @Override
    protected void execute(DirectiveHandler directiveHandler) throws Exception {
        List<Tag> tags =
                tagService.findTags();

        directiveHandler.put("tags",tags).render();
    }
}
