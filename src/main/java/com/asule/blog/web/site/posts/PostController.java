package com.asule.blog.web.site.posts;

import com.asule.blog.base.lang.Consts;
import com.asule.blog.modules.service.ChannelService;
import com.asule.blog.modules.service.PostService;
import com.asule.blog.modules.vo.AccountProfile;
import com.asule.blog.modules.vo.PostVO;
import com.asule.blog.web.BaseController;
import com.asule.blog.web.Views;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@RequestMapping("/post")
@Controller
public class PostController extends BaseController{

    @Autowired
    private PostService postService;

    @Autowired
    private ChannelService channelService;

    @GetMapping("/editing")
    public String view(Long id, ModelMap model) {
        model.put("channels",channelService.findAll(Consts.STATUS_CHANNEL_NORMAL));

        return view(Views.POST_EDITING);
    }


    /**
     * 发布文章
     * @return
     */
    @PostMapping("/submit")
    public String post(PostVO postVO){
        Assert.notNull(postVO, "参数不完整");
        Assert.state(StringUtils.isNotBlank(postVO.getTitle()), "标题不能为空");
        Assert.state(StringUtils.isNotBlank(postVO.getContent()), "内容不能为空");
        AccountProfile profile = getProfile();
        postVO.setAuthorId(profile.getId());

        if (postVO.getId()>0){

        }else{
            postService.post(postVO);
        }
        return view(Views.REDIRECT_INDEX);
    }
}
