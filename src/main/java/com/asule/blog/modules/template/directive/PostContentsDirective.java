package com.asule.blog.modules.template.directive;

import com.asule.blog.base.lang.Consts;
import com.asule.blog.base.utils.BeanMapUtils;
import com.asule.blog.modules.po.Channel;
import com.asule.blog.modules.service.ChannelService;
import com.asule.blog.modules.service.PostService;
import com.asule.blog.modules.template.DirectiveHandler;
import com.asule.blog.modules.template.TemplateDirective;
import com.asule.blog.modules.vo.PostVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class PostContentsDirective extends TemplateDirective{


    @Autowired
    private ChannelService channelService;


    @Autowired
    private PostService postService;


    @Override
    public String getName() {
        return "post_contents";
    }

    @Override
    protected void execute(DirectiveHandler directiveHandler) throws Exception {

        Integer channelId = directiveHandler.getInteger("channelId", 0);
        String order = directiveHandler.getString("order", Consts.order.NEWEST);


        List<Channel> channels;

        //为0获取所有类型的文章
        if (channelId==0){
            channels= channelService.findAll(Consts.STATUS_CHANNEL_NORMAL);
        }else{
            channels= channelService.findById(Consts.STATUS_CHANNEL_NORMAL,channelId);
        }

        Set<Integer> channelIds = new HashSet<>();
        if (channels!=null){
            channels.forEach(channel -> channelIds.add(channel.getId()));
        }

        //包装一个分页对象Pageable
        Pageable pageable =
                wrapPageable(directiveHandler, Sort.by(Sort.Direction.DESC, BeanMapUtils.postOrder(order)));

        Page<PostVO> postVOS = postService.paging(pageable, channelIds);

        directiveHandler.put(RESULTS,postVOS).render();
    }
}
