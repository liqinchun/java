package com.example.java.aop.advice;

import org.aopalliance.aop.Advice;
import org.junit.Test;
import org.springframework.aop.framework.ProxyFactory;

import static org.junit.Assert.*;

public class AdviceServiceTest {

    @Test
    public void adviceWaiter() {
        Waiter target = new NativeWaiter();
        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setTarget(target);
        //前置增强
        Advice beforeAdvice = new GreetingBeforeAdvice();
        //后置增强
        Advice afterAdvice = new GreetingAfterAdvice();
        //环绕增强
        Advice interceptor = new GreetingInterceptor();
        proxyFactory.addAdvice(afterAdvice);
        proxyFactory.addAdvice(beforeAdvice);
        proxyFactory.addAdvice(interceptor);
        Waiter waiter = (Waiter) proxyFactory.getProxy();
        waiter.serverTo("Li");
        waiter.greetTo("Li");
    }
}