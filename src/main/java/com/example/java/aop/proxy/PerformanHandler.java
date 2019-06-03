package com.example.java.aop.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class PerformanHandler implements InvocationHandler {

    private Object target;
    public PerformanHandler(Object target){
        this.target = target;
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        PerformanceMonitor.begine(target.getClass().getName()+"."+method.getName());
        Object obj = method.invoke(target, args);

        PerformanceMonitor.end(target.getClass().getName()+"."+method.getName());

        return obj;
    }
}
