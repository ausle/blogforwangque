package com.asule.blog.modules.template.directive;

import com.asule.blog.base.lang.Consts;
import com.asule.blog.base.utils.BeanMapUtils;
import com.asule.blog.modules.po.Channel;
import com.asule.blog.modules.po.Tag;
import com.asule.blog.modules.repository.TagRepository;
import com.asule.blog.modules.service.ChannelService;
import com.asule.blog.modules.service.PostService;
import com.asule.blog.modules.service.PostTagService;
import com.asule.blog.modules.service.TagService;
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
    private PostTagService postTagService;




    @Autowired
    private TagRepository tagRepository;

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
        Long tagId = directiveHandler.getLong("tagId", 0);



        List<Channel> channels=new ArrayList<>();
        //为0获取所有类型的文章
        if (channelId==0){
            channels= channelService.findAll(Consts.STATUS_CHANNEL_NORMAL);
        }else{
            channels.add(channelService.findById(Consts.STATUS_CHANNEL_NORMAL,channelId));
        }
        Set<Integer> channelIds = new HashSet<>();
        if (channels!=null){
            channels.forEach(channel -> channelIds.add(channel.getId()));
        }


        //标签和文章 是多对多关系。
        Set<Long> postIds;
        //为0获取所有标签的文章
        if (tagId==0){
           postIds = postTagService.findAllPostId();
        }else{
            postIds = postTagService.findPostIdByTagId(tagId);
        }

        //包装一个分页对象Pageable，分别按热度和时间进行查询。
        Pageable pageable =
                wrapPageable(directiveHandler, Sort.by(Sort.Direction.DESC, BeanMapUtils.postOrder(order)));
        //但这不是带条件的分页查询，而是对类别channelId进行in操作，对文章id同样进行In操作
        Page<PostVO> postVOS = postService.paging(pageable, channelIds,postIds);

        directiveHandler.put(RESULTS,postVOS).render();
    }
}
