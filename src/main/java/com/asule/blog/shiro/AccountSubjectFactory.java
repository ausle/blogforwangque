package com.asule.blog.shiro;

import com.asule.blog.modules.service.UserService;
import com.asule.blog.modules.vo.AccountProfile;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.mgt.DefaultSubjectFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.SubjectContext;
import org.apache.shiro.web.subject.WebSubject;
import org.apache.shiro.web.subject.WebSubjectContext;
import org.apache.shiro.web.subject.support.WebDelegatingSubject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;


/*
    每个请求经过过滤器链时，过滤器链都会创建subject。
    当已经通过身份认证或记住我，在session中重新添加一个profile。
    防止因为session失效而导致个人信息读取不到。
*/
@Slf4j
public class AccountSubjectFactory extends DefaultSubjectFactory{

    @Autowired
    @Lazy
    private UserService userService;

    @Override
    public Subject createSubject(SubjectContext context) {

        if (!(context instanceof WebSubjectContext)) {
            return super.createSubject(context);
        } else {
            WebSubjectContext wsc = (WebSubjectContext)context;
            SecurityManager securityManager = wsc.resolveSecurityManager();
            Session session = wsc.resolveSession();
            boolean sessionEnabled = wsc.isSessionCreationEnabled();
            PrincipalCollection principals = wsc.resolvePrincipals();
            boolean authenticated = wsc.resolveAuthenticated();




            String host = wsc.resolveHost();
            ServletRequest request = wsc.resolveServletRequest();
            ServletResponse response = wsc.resolveServletResponse();

            WebDelegatingSubject subject = new WebDelegatingSubject(principals, authenticated, host, session, sessionEnabled, request, response, securityManager);


            if (null!=session){
                AccountProfile profile = (AccountProfile) session.getAttribute("profile");
                if (profile!=null)
                    log.info("session profile："+profile.toString());
            }
            log.info("是否记住我："+subject.isRemembered());
            log.info("用户是否已经通过身份认证："+authenticated);

            handlerSession(subject);
            return subject;
        }
    }


    private void handlerSession(WebSubject subject) {
        //当前session为null时，才会去强制创建session
        Session session = subject.getSession(true);
        log.info("subjectfactory session id：" +session.getId());

        if (subject.isAuthenticated()||subject.isRemembered()){

            if (session.getAttribute("profile")==null){
                AccountProfile profile = (AccountProfile) subject.getPrincipal();
                log.info("reload session - " + profile.getUsername());
                session.setAttribute("profile", userService.findProfile(profile.getId()));
            }
        }
    }
}
