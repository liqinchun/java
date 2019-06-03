package com.example.java.aop.advice;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class GreetingInterceptor implements MethodInterceptor {
    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        Object[] args = methodInvocation.getArguments();
        String clientName = (String) args[0];
        System.out.println("How are you! Mr."+clientName+".");
        Object object = methodInvocation.proceed();
        System.out.println("Please enjoy yourself! Mr."+clientName+".");
        return object;
    }
}
