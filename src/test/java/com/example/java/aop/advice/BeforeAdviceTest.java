package com.example.java.aop.advice;

import org.junit.Test;
import org.springframework.aop.BeforeAdvice;
import org.springframework.aop.framework.ProxyFactory;

import static org.junit.Assert.*;

public class BeforeAdviceTest {

    @Test
    public void before() {
        Waiter target = new NativeWaiter();
        BeforeAdvice advice = new GreetingBeforeAdvice();
        //Spring 提供的代理工厂
        ProxyFactory proxyFactory = new ProxyFactory();
        //设置代理目标
        proxyFactory.setTarget(target);
        //为代理目标添加增强
        proxyFactory.addAdvice(advice);
        //生成代理目标
        Waiter waiter = (Waiter) proxyFactory.getProxy();

        waiter.greetTo("Li");
        waiter.serverTo("Li");

    }
}