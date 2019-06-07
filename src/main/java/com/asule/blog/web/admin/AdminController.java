package com.asule.blog.web.admin;

import com.asule.blog.modules.service.ChannelService;
import com.asule.blog.modules.service.PostService;
import com.asule.blog.modules.service.UserService;
import com.asule.blog.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.Action;


@Controller
public class AdminController extends BaseController{



    @Autowired
    private PostService postService;

    @Autowired
    private ChannelService channelService;


    @Autowired
    private UserService userService;

    @RequestMapping("/admin")
    public String index(HttpServletRequest request, ModelMap model) {


        //文章数量
        int postCount = postService.getPostCount();
        //栏目数量
        int channelCount = channelService.getChannelCount();
        //用户数量
        int userCount = userService.getUserCount();

        model.put("posts",postCount);
        model.put("channels",channelCount);
        model.put("users",userCount);


        return "/admin/index";
    }

}
