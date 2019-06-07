package com.asule.blog.shiro;

import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.SessionEvent;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;

import java.util.concurrent.atomic.AtomicInteger;


/**
 *   shiro session的监听器
 */
@Slf4j
public class ShiroSessionListener implements SessionListener {


    private final AtomicInteger sessionCount = new AtomicInteger(0);


    //创建session触发
    @Override
    public void onStart(Session session) {
        log.info("session onStart");
        sessionCount.incrementAndGet();
    }

    //会话退出，如subject.logout时触发
    @Override
    public void onStop(Session session) {
        log.info("session onStop");
        sessionCount.decrementAndGet();
    }

    //会话过期
    @Override
    public void onExpiration(Session session) {
        log.info("session onExpiration");
        sessionCount.decrementAndGet();
    }

    public AtomicInteger getSessionCount() {
        return sessionCount;
    }
}
