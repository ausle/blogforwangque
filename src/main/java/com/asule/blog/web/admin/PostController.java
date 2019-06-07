package com.asule.blog.web.admin;

import com.asule.blog.base.lang.Consts;
import com.asule.blog.base.lang.Result;
import com.asule.blog.modules.po.Channel;
import com.asule.blog.modules.service.ChannelService;
import com.asule.blog.modules.service.PostService;
import com.asule.blog.modules.vo.PostVO;
import com.asule.blog.web.BaseController;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Controller("adminPostController")
@RequestMapping("/admin/post")
public class PostController extends BaseController{


    @Autowired
    private PostService postService;


    @Autowired
    private ChannelService channelService;

    @RequestMapping("/list")
    public String list(ModelMap model, HttpServletRequest request) {
        int channelId = ServletRequestUtils.getIntParameter(request, "channelId", 0);
        Pageable pageable = wrapPageable(Sort.by(Sort.Direction.DESC, "weight", "created"));
        String title = ServletRequestUtils.getStringParameter(request, "title", "");

        Page<PostVO> page = postService.paging4Admin(pageable, channelId, title);

        //查询所有类别，包括状态为隐藏的
        List<Channel> channels = channelService.findAll(Consts.IGNORE_CHANNEL_STATUS);
        model.put("page", page);

        model.put("channelId", channelId);
        model.put("title", title);

        model.put("pageURI","?channelId="+channelId+"&title="+title);

        model.put("channels",channels);
        return "/admin/post/list";
    }


    /**
     * 编辑文章
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/view", method = RequestMethod.GET)
    public String toUpdate(Long id, ModelMap model) {
        if (null != id && id > 0) {
            PostVO view = postService.get(id);
            model.put("view", view);
        }
        model.put("channels", channelService.findAll(Consts.IGNORE));
        return "/admin/post/view";
    }


    /**
     * 删除文章
     */
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    @ResponseBody
    public Result toUpdate(@RequestParam(value = "id")List<Long> ids, ModelMap model) {
        Result data = Result.failure("操作失败");
        if (ids != null) {
            try {
                postService.delete(ids);
                data = Result.success();
            } catch (Exception e) {
                data = Result.failure(e.getMessage());
            }
        }
        return data;
    }
}
