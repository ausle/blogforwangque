package com.asule.blog.web.site.auth;

import com.asule.blog.base.lang.Result;
import com.asule.blog.modules.vo.AccountProfile;
import com.asule.blog.web.BaseController;
import com.asule.blog.web.Views;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController extends BaseController{

    @GetMapping(value = "/login")
    public String view() {
        return view(Views.LOGIN);
    }

    @PostMapping(value = "/login")
    public String login(String username,
                        String password,
                        @RequestParam(value = "rememberMe",defaultValue = "0") Boolean rememberMe,
                        ModelMap model) {
        String view = view(Views.LOGIN);
        Result<AccountProfile> result = executeLogin(username, password, rememberMe);
        if (result.isOk()) {
            view = String.format(Views.REDIRECT_INDEX, result.getData().getId());
        } else {
            model.put("message", result.getMessage());
        }
        return view;
    }

}
