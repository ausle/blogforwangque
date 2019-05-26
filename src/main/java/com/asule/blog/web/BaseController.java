package com.asule.blog.web;

import com.asule.blog.base.lang.Result;
import com.asule.blog.base.utils.MD5;
import com.asule.blog.modules.vo.AccountProfile;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;

@Slf4j
public class BaseController {

    protected String view(String view) {
        return view;
    }


    protected Result<AccountProfile> executeLogin(String username, String password, boolean rememberMe) {
        Result<AccountProfile> ret = Result.failure("登录失败");

        if (StringUtils.isAnyBlank(username, password)) {
            return ret;
        }
        UsernamePasswordToken token = new UsernamePasswordToken(username, MD5.md5(password), rememberMe);
        try {
            SecurityUtils.getSubject().login(token);
            ret = Result.success(getProfile());
        } catch (UnknownAccountException e) {
            log.error(e.getMessage());
            ret = Result.failure("用户不存在");
        } catch (LockedAccountException e) {
            log.error(e.getMessage());
            ret = Result.failure("用户被禁用");
        } catch (AuthenticationException e) {
            log.error(e.getMessage());
            ret = Result.failure("用户认证失败");
        }
        return ret;
    }


    protected AccountProfile getProfile() {
        Subject subject = SecurityUtils.getSubject();
        return (AccountProfile) subject.getPrincipal();
    }

}
