package com.asule.blog.web.admin;

import com.asule.blog.web.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;


@Controller
public class AdminController extends BaseController{

    @RequestMapping("/admin")
    public String index(HttpServletRequest request, ModelMap model) {
        return "/admin/index";
    }

}
