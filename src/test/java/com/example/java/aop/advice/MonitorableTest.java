package com.example.java.aop.advice;

import com.example.java.service.ForumServiceImpl;
import com.example.java.service.ForumService;
import org.aopalliance.aop.Advice;
import org.junit.Test;
import org.springframework.aop.framework.ProxyFactory;

public class MonitorableTest {

    @Test
    public void delegatingInterceptorTestz(){
        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setProxyTargetClass(true);
        ForumServiceImpl target = new ForumServiceImpl();
        proxyFactory.setTarget(target);
        Advice advice = new ControllablePerformanceMonitor();
        proxyFactory.addAdvice(advice);
        ForumServiceImpl forumService = (ForumServiceImpl) proxyFactory.getProxy();
        Monitorable monitorable = (Monitorable) forumService;
        monitorable.setMonitorActive(true);
        forumService.removeTopic(1);


    }

}