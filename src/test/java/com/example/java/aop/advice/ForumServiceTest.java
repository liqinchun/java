package com.example.java.aop.advice;

import com.example.java.aop.proxy.CglibProxy;
import com.example.java.aop.proxy.PerformanHandler;
import org.junit.Test;
import org.springframework.aop.ThrowsAdvice;
import org.springframework.aop.framework.ProxyFactory;

import java.lang.reflect.Proxy;

public class ForumServiceTest {

    @Test
    public void removeTopic() {
    }

    @Test
    public void removeFroum() {
    }

    @Test
    public void throwAdviceTest() throws Exception {
        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setProxyTargetClass(true);
        ThrowsAdvice throwsAdvice = new TransactionManager();
        proxyFactory.addAdvice(throwsAdvice);
        ForumService target = new ForumService();
        proxyFactory.setTarget(target);

        ForumService forumService = (ForumService) proxyFactory.getProxy();

            forumService.removeForum(1);
            forumService.updateForum(1);


    }
}