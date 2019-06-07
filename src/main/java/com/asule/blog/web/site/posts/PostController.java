package com.asule.blog.web.site.posts;

import com.asule.blog.base.lang.Consts;
import com.asule.blog.base.utils.BeanMapUtils;
import com.asule.blog.base.utils.MarkdownUtils;
import com.asule.blog.modules.po.Tag;
import com.asule.blog.modules.repository.PostTagRepository;
import com.asule.blog.modules.service.ChannelService;
import com.asule.blog.modules.service.PostService;
import com.asule.blog.modules.service.TagService;
import com.asule.blog.modules.vo.AccountProfile;
import com.asule.blog.modules.vo.PostVO;
import com.asule.blog.web.BaseController;
import com.asule.blog.web.Views;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.Assert;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@RequestMapping("/post")
@Controller
public class PostController extends BaseController{

    @Autowired
    private PostService postService;

    @Autowired
    private ChannelService channelService;

    @Autowired
    private TagService tagService;


    @GetMapping("/editing")
    public String view(Long id, ModelMap model) {
        model.put("channels",channelService.findAll(Consts.STATUS_CHANNEL_NORMAL));
        if (id!=null&&id>0){
            PostVO view = postService.get(id);
            model.put("view", view);
        }
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

        long authorId = postVO.getAuthorId();
        if(authorId!=0){
            postVO.setAuthorId(authorId);   //管理员修改某个作者文章，该文章依旧属于该作者
        }else{
            //此种情况，属于新建文章，谁处于登录状态，作者就属于谁
            AccountProfile profile = getProfile();
            if(null!=profile){
                postVO.setAuthorId(profile.getId());
            }
        }

        if (postVO.getId()>0){
            postService.update(postVO);
        }else{
            postService.post(postVO);
        }
        return Views.REDIRECT_INDEX;
    }


    /**
     * 查看文章
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/{id:\\d*}")
    public String getPost(@PathVariable Long id, ModelMap model,HttpServletRequest request) {
        PostVO view = postService.get(id);
        Assert.notNull(view, "该文章已被删除");

        PostVO post = new PostVO();
        BeanUtils.copyProperties(view, post);
        post.setContent(MarkdownUtils.renderMarkdown(view.getContent()));
        view = post;

        List<Tag> tags = tagService.findTagsByPost(post.getId());

        //文章数量+1
        postService.identityViews(id);
        model.put("view", view);
        model.put("tags", tags);
        return view(Views.POST_VIEW);
    }


}
