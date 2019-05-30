package com.asule.blog.config;

import com.asule.blog.shiro.AccountRealm;
import com.asule.blog.shiro.AccountSubjectFactory;
import com.asule.blog.shiro.AuthenticatedFilter;
import org.apache.shiro.mgt.RememberMeManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.mgt.SubjectFactory;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSubjectFactory;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
@ConditionalOnProperty(name = "shiro.web.enabled",havingValue = "true")
public class ShiroConfiguration {


    @Bean
    public SubjectFactory subjectFactory() {
        return new AccountSubjectFactory();
    }

    @Bean
    public Realm accountRealm() {
        return new AccountRealm();
    }


    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        shiroFilter.setSecurityManager(securityManager);
        shiroFilter.setLoginUrl("/login");
        shiroFilter.setSuccessUrl("/index");
        shiroFilter.setUnauthorizedUrl("/error/reject.html");


        //添加一个过滤器链，所有
        HashMap<String, Filter> filters = new HashMap<>();
        filters.put("authc", new AuthenticatedFilter());
        shiroFilter.setFilters(filters);


        Map<String, String> hashMap = new LinkedHashMap<>();


        //静态文件允许通过
        hashMap.put("/dist/**", "anon");
        hashMap.put("/storage/**", "anon");

        hashMap.put("/","anon");
        hashMap.put("/index","anon");
        hashMap.put("/login","anon");
        hashMap.put("/register","anon");

        hashMap.put("/**","anon");
        shiroFilter.setFilterChainDefinitionMap(hashMap);

        return shiroFilter;
    }


//    /**
//     * remeberMe的cookie
//     * @return
//     */
//    @Bean
//    public SimpleCookie simpleCookie(){
//
//        SimpleCookie simpleCookie = new SimpleCookie();
//
//        simpleCookie.setName("asule-cookie");
//        simpleCookie.setHttpOnly(true);
//        simpleCookie.setMaxAge(259200);
//
//        return simpleCookie;
//    }
//
//
//    @Bean
//    public RememberMeManager rememberMeManager(){
//        CookieRememberMeManager rememberMeManager = new CookieRememberMeManager();
//        //默认对cookie加密为AES算法
//        rememberMeManager.setCookie(simpleCookie());
//        return rememberMeManager;
//    }
//
//
//    @Bean
//    public FormAuthenticationFilter formAuthenticationFilter(){
//        FormAuthenticationFilter authenticationFilter = new FormAuthenticationFilter();
//        authenticationFilter.setRememberMeParam("rememberMe");
//
//        return authenticationFilter;
//    }



}
