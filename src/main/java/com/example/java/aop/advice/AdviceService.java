package com.example.java.aop.advice;

import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.factory.BeanFactory;

public class AdviceService {


    public void adviceWaiter(){
        Waiter target = new NativeWaiter();
        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setTarget(target);

        Waiter waiter = (Waiter) proxyFactory.getProxy();
        waiter.serverTo("Li");
        waiter.greetTo("Li");
    }

}
