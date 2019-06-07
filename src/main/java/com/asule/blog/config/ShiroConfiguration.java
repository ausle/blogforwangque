package com.asule.blog.config;

import com.asule.blog.shiro.AccountRealm;
import com.asule.blog.shiro.AccountSubjectFactory;
import com.asule.blog.shiro.AuthenticatedFilter;
import com.asule.blog.shiro.ShiroSessionListener;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.RememberMeManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.mgt.SubjectFactory;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.SessionListener;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.session.mgt.eis.SessionIdGenerator;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.config.web.autoconfigure.ShiroWebAutoConfiguration;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.mgt.DefaultWebSubjectFactory;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.CacheManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.*;

@Configuration
public class ShiroConfiguration{


    @Bean
    public SubjectFactory subjectFactory() {
        return new AccountSubjectFactory();
    }



    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor(){
        return new LifecycleBeanPostProcessor();
    }


    /*

        ShiroFilterFactoryBean是一个BeanPostProcessor，
        它的初始化时机要比一般的bean要早，依赖链是：

        ShiroFilterFactoryBean--->SecurityManager---->Realm----->Service---->Repository

        ShiroFilterFactoryBean会自动引入SecurityManager，
        而SecurityManager是由ShiroWebAutoConfiguration创建的。
            @Bean
            @ConditionalOnMissingBean
            protected SessionsSecurityManager securityManager(List<Realm> realms) {
                return super.securityManager(realms);
            }
        它依赖realm，于是会提前创建realm的bean。导致realm中注入的bean都被提前创建。


        解决方法是在realm注入的service上加上@lazy注解，
        把shiro的bean和service切断。
    */

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        shiroFilter.setSecurityManager(securityManager);
        shiroFilter.setLoginUrl("/login");
        shiroFilter.setSuccessUrl("/");
        shiroFilter.setUnauthorizedUrl("/error/reject.html");//若没有授权的访问某请求，去重定向到该路径


        //添加一个过滤器链，所有需验证的请求都需要经过该过滤器链
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
        hashMap.put("/post/*","anon");


        hashMap.put("/post/editing","authc");   //文章编辑
        hashMap.put("/post/upload","authc");    //图片上传
        hashMap.put("/post/submit","authc");    //文章发表
        hashMap.put("/post/delete","authc");    //删除文章


        hashMap.put("/admin", "authc,perms[admin]");
        hashMap.put("/admin/*", "authc,perms[admin]");


        hashMap.put("/**","authc");
        shiroFilter.setFilterChainDefinitionMap(hashMap);

        return shiroFilter;
    }


      /*
        shiroRealm属于filter过滤器，在spring注入bean之前就已经拦截了。因此无法注入realm。
    */

    @Bean
    public Realm accountRealm() {
        return new AccountRealm();
    }



    @Bean
    public SecurityManager securityManager(EhCacheManager ehCacheManager) {
        DefaultWebSecurityManager securityManager =  new DefaultWebSecurityManager();
        securityManager.setRealm(accountRealm());

        //配置 ehcache缓存管理器 参考博客：
        securityManager.setCacheManager(ehCacheManager);

        //配置自定义session管理，使用ehcache
        securityManager.setSessionManager(sessionManager(ehCacheManager));

        securityManager.setSubjectFactory(subjectFactory());


        return securityManager;
    }




    @Bean
    public EhCacheManager shiroCacheManager(net.sf.ehcache.CacheManager cacheManager) {
        EhCacheManager ehCacheManager = new EhCacheManager();
        //将cacheManager转换成shiro包装后的ehcacheManager对象
        ehCacheManager.setCacheManager(cacheManager);
        return ehCacheManager;
    }




    /**
     * 配置会话管理器，设定会话超时及保存
     * @return
     */
    @Bean("sessionManager")
    public SessionManager sessionManager(EhCacheManager cacheManager) {

        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        Collection<SessionListener> listeners = new ArrayList<>();
        //配置监听
        listeners.add(sessionListener());
        sessionManager.setSessionListeners(listeners);
        sessionManager.setSessionIdCookie(sessionIdCookie());
        sessionManager.setSessionDAO(sessionDAO(cacheManager));
        sessionManager.setCacheManager(cacheManager);

        return sessionManager;
    }


    /**
     * 配置session监听
     */
    @Bean
    public ShiroSessionListener sessionListener(){
        ShiroSessionListener sessionListener = new ShiroSessionListener();
        return sessionListener;
    }

    /**
     * 配置会话ID生成器
     */
    @Bean
    public SessionIdGenerator sessionIdGenerator() {
        return new JavaUuidSessionIdGenerator();
    }

    /**
     * SessionDAO的作用是为Session提供CRUD并进行持久化的一个shiro组件
     * MemorySessionDAO 直接在内存中进行会话维护
     * EnterpriseCacheSessionDAO
     * 提供了缓存功能的会话维护，默认情况下使用MapCache实现，内部使用ConcurrentHashMap保存缓存的会话。
     * @return
     */
    @Bean
    public SessionDAO sessionDAO(EhCacheManager cacheManager) {
        EnterpriseCacheSessionDAO enterpriseCacheSessionDAO = new EnterpriseCacheSessionDAO();
        //使用ehCacheManager
        enterpriseCacheSessionDAO.setCacheManager(cacheManager);
        //设置session缓存的名字 默认为 shiro-activeSessionCache
        enterpriseCacheSessionDAO.setActiveSessionsCacheName("shiro-activeSessionCache");
        //sessionId生成器
        enterpriseCacheSessionDAO.setSessionIdGenerator(sessionIdGenerator());
        return enterpriseCacheSessionDAO;
    }


    /**
     * 配置保存sessionId的cookie
     * 创建session时，发出一个Set-Cookie的响应头
     * Set-Cookie: sid=d68f140f-d32e-42be-941b-73c0d0323825;
     * @return
     */
    @Bean
    public SimpleCookie sessionIdCookie(){
        //这个参数是cookie的名称
        SimpleCookie simpleCookie = new SimpleCookie("sid");
        //setcookie的httponly属性如果设为true的话，会增加对xss防护的安全系数。它有以下特点：
        //设为true后，只能通过http访问，javascript无法访问，防止xss读取cookie
        simpleCookie.setHttpOnly(true);
        simpleCookie.setPath("/");

        //设置cookie的过期时间，此时会发下面一个响应头
        //Set-Cookie: sid=b8638b88-b3ad-4dc3-b3df-508d1bb429aa; Path=/; Max-Age=30; Expires=Fri, 07-Jun-2019 15:59:44 GMT; HttpOnly

        //会告诉客户端该cookie的失效时间。过了cookie的失效时间，那么客户端的请求就不会带着cookie来请求服务器了。
        //而此时服务器会创建一个新的session。

        //maxAge=-1表示浏览器关闭时失效此Cookie
        //单位为秒
        //由于个人博客在页面停留时间较长，session时间设置长一点
        simpleCookie.setMaxAge(60*60);
        return simpleCookie;
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
