package com.asule.blog.web.site.auth;

import com.asule.blog.base.lang.Consts;
import com.asule.blog.base.lang.Result;
import com.asule.blog.modules.service.UserService;
import com.asule.blog.modules.vo.AccountProfile;
import com.asule.blog.modules.vo.UserVO;
import com.asule.blog.web.BaseController;
import com.asule.blog.web.Views;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;



@Controller
public class RegisterController extends BaseController{


    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String view(){
        return view(Views.REGISTER);
    }

    @PostMapping("/register")
    public String register(UserVO post, HttpServletRequest request, ModelMap model) {
        String view = view(Views.REGISTER);
        try {
            post.setAvatar(Consts.AVATAR);
            userService.register(post);
            Result<AccountProfile> result = executeLogin(post.getUsername(), post.getPassword(), false);
            view = String.format(Views.REDIRECT_INDEX, result.getData().getId());
        } catch (Exception e) {
            model.addAttribute("post", post);
            model.put("data", Result.failure(e.getMessage()));
        }
        return view;
    }


}
