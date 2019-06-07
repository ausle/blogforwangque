package com.asule.blog.shiro;

import com.asule.blog.base.lang.Consts;
import com.asule.blog.modules.po.Role;
import com.asule.blog.modules.service.UserRoleService;
import com.asule.blog.modules.service.UserService;
import com.asule.blog.modules.vo.AccountProfile;
import com.asule.blog.modules.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import java.util.List;
import java.util.Map;


@Slf4j
public class AccountRealm extends AuthorizingRealm{

    @Lazy
    @Autowired
    private UserService userService;


    @Lazy
    @Autowired
    private UserRoleService userRoleService;



    /*





    */

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        AccountProfile profile =
                (AccountProfile) SecurityUtils.getSubject().getPrincipal();

        if (profile!=null){

            UserVO userVO = userService.findUserVO(profile.getId());

            if (userVO!=null){

                SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

                List<Role> roleList = userRoleService.findByUserId(userVO.getId());


                roleList.forEach(role -> {
                    info.addRole(role.getName());

                    role.getPermissions().forEach(permission -> {
                        info.addStringPermission(permission.getName());
                    });

                });

                return info;
            }
        }
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        AccountProfile profile = getAccount(userService, token);

        if (profile.getStatus() == Consts.STATUS_CLOSED) {
            throw new LockedAccountException(profile.getName());
        }
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(profile, token.getCredentials(), getName());
        Session session = SecurityUtils.getSubject().getSession();
        session.setAttribute("profile", profile);
        log.info("realm session id：" +session.getId());
        return info;
    }

    protected AccountProfile getAccount(UserService userService, AuthenticationToken token) {
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        return userService.login(upToken.getUsername(), String.valueOf(upToken.getPassword()));
    }
}
