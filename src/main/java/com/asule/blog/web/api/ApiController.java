/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package com.asule.blog.web.api;


import com.asule.blog.base.lang.Consts;
import com.asule.blog.base.lang.Result;
import com.asule.blog.base.utils.BeanMapUtils;
import com.asule.blog.modules.service.CommentService;
import com.asule.blog.modules.service.PostService;
import com.asule.blog.modules.vo.CommentVO;
import com.asule.blog.modules.vo.PostVO;
import com.asule.blog.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 侧边栏数据加载
 *
 */
@RestController
@RequestMapping("/api")
public class ApiController extends BaseController {
    @Autowired
    private PostService postService;
    @Autowired
    private CommentService commentService;

    @PostMapping(value = "/login")
    public Result login(String username, String password) {
        return executeLogin(username, password, false);
    }

}
