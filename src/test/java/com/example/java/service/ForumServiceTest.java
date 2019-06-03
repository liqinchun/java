package com.example.java.service;

import com.example.java.aop.advice.TransactionManager;
import com.example.java.aop.proxy.CglibProxy;
import com.example.java.aop.proxy.PerformanHandler;
import org.junit.Test;
import org.springframework.aop.ThrowsAdvice;
import org.springframework.aop.framework.ProxyFactory;

import java.lang.reflect.Proxy;

import static org.junit.Assert.*;

public class ForumServiceTest {

    @Test
    public void removeTopic() {
    }

    @Test
    public void removeFroum() {
    }

    @Test
    public void jdkProxy() {
        ForumService target = new ForumServiceImpl();

        PerformanHandler handler = new PerformanHandler(target);

        ForumService forumService = (ForumService) Proxy.newProxyInstance(
                target.getClass().getClassLoader(), target.getClass().getInterfaces(), handler);

        forumService.removeFroum(10);
        forumService.removeTopic(123);
    }

    @Test
    public void cglibProxy() {
        CglibProxy cglibProxy = new CglibProxy();
        ForumServiceImpl forumService =
                (ForumServiceImpl) cglibProxy.getProxy(ForumServiceImpl.class);
        forumService.removeFroum(1);
        forumService.removeTopic(2);
    }


}